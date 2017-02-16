/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2017-2017 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2017 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.dao.hibernate;

import java.util.Optional;

import org.opennms.netmgt.dao.api.OnmsRequisitionDao;
import org.opennms.netmgt.model.requisition.OnmsRequisition;
import org.opennms.netmgt.model.requisition.OnmsRequisitionInterface;
import org.opennms.netmgt.model.requisition.OnmsRequisitionMonitoredService;
import org.opennms.netmgt.model.requisition.OnmsRequisitionNode;

public class OnmsRequistionDaoHibernate extends AbstractDaoHibernate<OnmsRequisition, String> implements OnmsRequisitionDao {
    public OnmsRequistionDaoHibernate() {
        super(OnmsRequisition.class);
    }

    @Override
    public OnmsRequisition getByForeignSource(String foreignSource) {
        return get(foreignSource); // ForeignSource and requisitionName are identical
    }

    @Override
    public OnmsRequisitionNode getNode(String foreignSource, String foreignId) {
        return Optional.ofNullable(get(foreignSource))
                .map(req -> req.getNode(foreignId))
                .orElse(null);
    }

    @Override
    public OnmsRequisitionInterface getIpInterface(String foreignSource, String foreignId, String ipAddress) {
        return Optional.ofNullable(getNode(foreignSource, foreignId))
                .map(node -> node.getInterface(ipAddress))
                .orElse(null);
    }

    @Override
    public OnmsRequisitionMonitoredService getService(String foreignSource, String foreignId, String ipAddress, String serviceName) {
        return Optional.ofNullable(getIpInterface(foreignSource, foreignId, ipAddress))
                .map(iface -> iface.getMonitoredService(serviceName))
                .orElse(null);
    }
}
