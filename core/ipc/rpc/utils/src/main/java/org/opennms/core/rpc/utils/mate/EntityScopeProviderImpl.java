/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2019 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2019 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.core.rpc.utils.mate;

import static org.opennms.core.rpc.utils.mate.EntityScopeProvider.Contexts.INTERFACE;
import static org.opennms.core.rpc.utils.mate.EntityScopeProvider.Contexts.NODE;
import static org.opennms.core.rpc.utils.mate.EntityScopeProvider.Contexts.SERVICE;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.opennms.core.utils.InetAddressUtils;
import org.opennms.netmgt.dao.api.IpInterfaceDao;
import org.opennms.netmgt.dao.api.MonitoredServiceDao;
import org.opennms.netmgt.dao.api.NodeDao;
import org.opennms.netmgt.dao.api.SessionUtils;
import org.opennms.netmgt.dao.api.SnmpInterfaceDao;
import org.opennms.netmgt.model.OnmsIpInterface;
import org.opennms.netmgt.model.OnmsMetaData;
import org.opennms.netmgt.model.OnmsMonitoredService;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.OnmsSnmpInterface;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;

public class EntityScopeProviderImpl implements EntityScopeProvider {

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private IpInterfaceDao ipInterfaceDao;

    @Autowired
    private SnmpInterfaceDao snmpInterfaceDao;

    @Autowired
    private MonitoredServiceDao monitoredServiceDao;

    @Autowired
    private SessionUtils sessionUtils;

    @Override
    public Scope getScopeForNode(final Integer nodeId) {
        if (nodeId == null) {
            return EmptyScope.EMPTY;
        }

        return this.sessionUtils.withReadOnlyTransaction(() -> {
            final OnmsNode node = nodeDao.get(nodeId);
            if (node == null) {
                return EmptyScope.EMPTY;
            }

            List<Scope> scopes = new ArrayList<>();
            scopes.add(transform(node.getMetaData()));

            Scope nodeScope = new ObjectScope<>(node)
                    .map(NODE, "criteria", this::getNodeCriteria)
                    .map(NODE, "label", (n) -> Optional.ofNullable(n.getLabel()))
                    .map(NODE, "foreign-source", (n) -> Optional.ofNullable(n.getForeignSource()))
                    .map(NODE, "foreign-id", (n) -> Optional.ofNullable(n.getForeignId()))
                    .map(NODE, "netbios-domain", (n) -> Optional.ofNullable(n.getNetBiosDomain()))
                    .map(NODE, "netbios-name", (n) -> Optional.ofNullable(n.getNetBiosName()))
                    .map(NODE, "os", (n) -> Optional.ofNullable(n.getOperatingSystem()))
                    .map(NODE, "sys-name", (n) -> Optional.ofNullable(n.getSysName()))
                    .map(NODE, "sys-location", (n) -> Optional.ofNullable(n.getSysLocation()))
                    .map(NODE, "sys-contact", (n) -> Optional.ofNullable(n.getSysContact()))
                    .map(NODE, "sys-description", (n) -> Optional.ofNullable(n.getSysDescription()))
                    .map(NODE, "sys-object-id", (n) -> Optional.ofNullable(n.getSysObjectId()))
                    .map(NODE, "location", (n) -> Optional.ofNullable(n.getLocation().getLocationName()))
                    .map(NODE, "area", (n) -> Optional.ofNullable(n.getLocation().getMonitoringArea()));
            scopes.add(nodeScope);

            return new FallbackScope(scopes);
        });

    }

    private Optional<String> getNodeCriteria(final OnmsNode node) {
        Objects.requireNonNull(node, "Node can not be null");
        if (node.getForeignSource() != null) {
            return Optional.of(node.getForeignSource() + ":" + node.getForeignId());
        } else {
            return Optional.of(Integer.toString(node.getId()));
        }
    }

    @Override
    public Scope getScopeForInterface(final Integer nodeId, final String ipAddress) {
        if (nodeId == null || Strings.isNullOrEmpty(ipAddress)) {
            return EmptyScope.EMPTY;
        }

        return this.sessionUtils.withReadOnlyTransaction(() -> {
            final OnmsIpInterface ipInterface = this.ipInterfaceDao.findByNodeIdAndIpAddress(nodeId, ipAddress);
            if (ipInterface == null) {
                return EmptyScope.EMPTY;
            }

            return new FallbackScope(transform(ipInterface.getMetaData()),
                    mapIpInterfaceKeys(ipInterface)
                            .map(INTERFACE, "if-alias", (i) -> Optional.ofNullable(i.getSnmpInterface()).map(OnmsSnmpInterface::getIfAlias))
                            .map(INTERFACE, "if-description", (i) -> Optional.ofNullable(i.getSnmpInterface()).map(OnmsSnmpInterface::getIfDescr))
                            .map(INTERFACE, "phy-addr", (i) -> Optional.ofNullable(i.getSnmpInterface()).map(OnmsSnmpInterface::getPhysAddr))
            );
        });
    }

    private static ObjectScope<OnmsIpInterface> mapIpInterfaceKeys(OnmsIpInterface ipInterface) {
        return new ObjectScope<>(ipInterface)
                .map(INTERFACE, "hostname", (i) -> Optional.ofNullable(i.getIpHostName()))
                .map(INTERFACE, "address", (i) -> Optional.ofNullable(i.getIpAddress()).map(InetAddressUtils::toIpAddrString))
                .map(INTERFACE, "netmask", (i) -> Optional.ofNullable(i.getNetMask()).map(InetAddressUtils::toIpAddrString))
                .map(INTERFACE, "if-index", (i) -> Optional.ofNullable(i.getIfIndex()).map(Object::toString));
    }

    @Override
    public Scope getScopeForInterfaceByIfIndex(final Integer nodeId, final int ifIndex) {
        if (nodeId == null) {
            return EmptyScope.EMPTY;
        }

        return this.sessionUtils.withReadOnlyTransaction(() -> {
            final OnmsSnmpInterface snmpInterface = this.snmpInterfaceDao.findByNodeIdAndIfIndex(nodeId, ifIndex);
            if (snmpInterface == null) {
                return EmptyScope.EMPTY;
            }

            ArrayList<Scope> scopes = new ArrayList<>();

            // SNMP interface facts
            scopes.add(new ObjectScope<>(snmpInterface)
                    .map(INTERFACE, "if-alias", (i) -> Optional.ofNullable(i.getIfAlias()))
                    .map(INTERFACE, "if-description", (i) -> Optional.ofNullable(i.getIfDescr()))
                    .map(INTERFACE, "phy-addr", (i) -> Optional.ofNullable(i.getPhysAddr())));

            // IP interface facts w/ meta-data extracted from IP interface
            Optional.ofNullable(snmpInterface.getPrimaryIpInterface())
                    .ifPresent(ipInterface -> {
                        scopes.add(transform(ipInterface.getMetaData()));
                        scopes.add(mapIpInterfaceKeys(ipInterface));
                    });

            return new FallbackScope(scopes);
        });
    }

    @Override
    public Scope getScopeForService(final Integer nodeId, final InetAddress ipAddress, final String serviceName) {
        if (nodeId == null || ipAddress == null || Strings.isNullOrEmpty(serviceName)) {
            return EmptyScope.EMPTY;
        }

        return this.sessionUtils.withReadOnlyTransaction(() -> {
            final OnmsMonitoredService monitoredService = this.monitoredServiceDao.get(nodeId, ipAddress, serviceName);
            if (monitoredService == null) {
                return EmptyScope.EMPTY;
            }

            return new FallbackScope(transform(monitoredService.getMetaData()),
                    new ObjectScope<>(monitoredService)
                            .map(SERVICE, "name", (s) -> Optional.of(s.getServiceName()))
            );
        });
    }

    private static MapScope transform(final Collection<OnmsMetaData> metaData) {
        final Map<ContextKey, String> map = metaData.stream()
                .collect(Collectors.toMap(e -> new ContextKey(e.getContext(), e.getKey()), OnmsMetaData::getValue));
        return new MapScope(map);
    }

    public void setNodeDao(NodeDao nodeDao) {
        this.nodeDao = Objects.requireNonNull(nodeDao);
    }

    public void setIpInterfaceDao(IpInterfaceDao ipInterfaceDao) {
        this.ipInterfaceDao = Objects.requireNonNull(ipInterfaceDao);
    }

    public void setMonitoredServiceDao(MonitoredServiceDao monitoredServiceDao) {
        this.monitoredServiceDao = Objects.requireNonNull(monitoredServiceDao);
    }

    public void setSessionUtils(SessionUtils sessionUtils) {
        this.sessionUtils = Objects.requireNonNull(sessionUtils);
    }
}
