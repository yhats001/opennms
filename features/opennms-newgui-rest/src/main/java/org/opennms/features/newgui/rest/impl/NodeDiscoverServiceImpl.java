/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2021 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2021 The OpenNMS Group, Inc.
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

package org.opennms.features.newgui.rest.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.opennms.features.newgui.rest.NodeDiscoverRestService;
import org.opennms.features.newgui.rest.model.DiscoveryResultDTO;
import org.opennms.features.newgui.rest.model.IPAddressScanRequestDTO;
import org.opennms.features.newgui.rest.model.IPScanResult;
import org.opennms.features.newgui.rest.model.SNMPFitRequestDTO;
import org.opennms.features.newgui.rest.model.SNMPFitResultDTO;
import org.opennms.netmgt.config.api.SnmpAgentConfigFactory;
import org.opennms.netmgt.icmp.proxy.LocationAwarePingClient;
import org.opennms.netmgt.icmp.proxy.PingSweepSummary;
import org.opennms.netmgt.snmp.SnmpAgentConfig;
import org.opennms.netmgt.snmp.SnmpObjId;
import org.opennms.netmgt.snmp.SnmpValue;
import org.opennms.netmgt.snmp.proxy.LocationAwareSnmpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeDiscoverServiceImpl implements NodeDiscoverRestService {
    private static Logger LOG = LoggerFactory.getLogger(NodeDiscoverServiceImpl.class);
    private final LocationAwarePingClient locationAwarePingClient;
    private final LocationAwareSnmpClient locationAwareSnmpClient;
    private final SnmpAgentConfigFactory snmpAgentConfigFactory;

    private static final String DEFAULT_SYS_OBJECTID_INSTANCE = ".1.3.6.1.2.1.1.2.0";

    public NodeDiscoverServiceImpl(LocationAwarePingClient locationAwarePingClient,
                                   LocationAwareSnmpClient locationAwareSnmpClient,
                                   SnmpAgentConfigFactory snmpAgentConfigFactory) {
        this.locationAwarePingClient = locationAwarePingClient;
        this.locationAwareSnmpClient = locationAwareSnmpClient;
        this.snmpAgentConfigFactory = snmpAgentConfigFactory;
    }

    @Override
    public List<DiscoveryResultDTO> discoverByRange(List<IPAddressScanRequestDTO> ipRangeList) {
        List<DiscoveryResultDTO> results = new ArrayList<>();

        ipRangeList.forEach(ipRange -> {
            try {
                CompletableFuture<PingSweepSummary> future = locationAwarePingClient.sweep().withRange(InetAddress.getByName(ipRange.getStartIP()), InetAddress.getByName(ipRange.getEndIP()))
                        .withLocation(ipRange.getLocation())
                        .execute();
                while (true) {
                    try {
                        try {
                            PingSweepSummary summary = future.get(1, TimeUnit.SECONDS);
                            if (!summary.getResponses().isEmpty()) {
                                List<IPScanResult> scanResults = new ArrayList<>();
                                summary.getResponses().forEach((address, rtt) -> scanResults.add(new IPScanResult(address.getHostName(), address.getHostAddress(), rtt)));
                                DiscoveryResultDTO resultDTO = new DiscoveryResultDTO(ipRange.getLocation(), scanResults);
                                results.add(resultDTO);
                            } else {
                                LOG.debug("No response from any IP address in the range");
                            }
                        } catch (InterruptedException e) {

                        } catch (ExecutionException e) {
                            LOG.error("IP range scan failed with location {} start IP {} and end {}", ipRange.getLocation(), ipRange.getStartIP(), ipRange.getEndIP());
                        }
                        break;
                    } catch (TimeoutException e) {
                        // continue
                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
        return results;
    }

    @Override
    public List<SNMPFitResultDTO> fitSNMP(List<SNMPFitRequestDTO> requestList) {
        List<SNMPFitResultDTO> results = new ArrayList<>();
        SnmpObjId objId = SnmpObjId.get(DEFAULT_SYS_OBJECTID_INSTANCE);
        requestList.forEach(request -> {
            request.getIpAddresses().forEach(ip -> {
                try {
                    InetAddress inetAddress = InetAddress.getByName(ip);
                    request.getConfigurations().forEach(config -> {
                        final SnmpAgentConfig agentConfig = new SnmpAgentConfig();
                        agentConfig.setAddress(inetAddress);
                        if(StringUtils.isNotEmpty(config.getCommunityString())) {
                            agentConfig.setWriteCommunity(config.getCommunityString());
                            agentConfig.setReadCommunity(config.getCommunityString());
                        }
                        agentConfig.setSecurityLevel(SnmpAgentConfig.DEFAULT_SECURITY_LEVEL);
                        agentConfig.setRetries(config.getRetry());
                        agentConfig.setTimeout(config.getTimeout());
                        CompletableFuture<SnmpValue> snmpResult = locationAwareSnmpClient.get(agentConfig, objId)
                                .withLocation(request.getLocation())
                                .execute();

                        SNMPFitResultDTO resultDTO = new SNMPFitResultDTO();
                        resultDTO.setHostname(inetAddress.getHostName());
                        resultDTO.setIpAddress(inetAddress.getHostAddress());
                        resultDTO.setLocation(request.getLocation());
                        resultDTO.setCommunityString(config.getCommunityString());
                        results.add(resultDTO);
                        while (true) {
                            try {
                                try {
                                    SnmpValue snmpValue = snmpResult.get(1, TimeUnit.SECONDS);
                                    if(snmpValue != null && !snmpValue.isError()) {
                                        resultDTO.setSysOID(snmpValue.toString());
                                    }
                                } catch (InterruptedException e) {
                                    //do nothing
                                } catch (ExecutionException e) {
                                    LOG.error("Couldn't find SNMP service at {} ", inetAddress.getHostAddress());
                                }
                                break;
                            } catch (TimeoutException e) {
                                //continue
                            }
                        }
                    });
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            });
        });
        return results;
    }
}
