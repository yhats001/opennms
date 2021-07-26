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

import org.opennms.features.newgui.rest.NodeDiscoverRestService;
import org.opennms.features.newgui.rest.model.DiscoveryResultDTO;
import org.opennms.features.newgui.rest.model.IPAddressScanRequestDTO;
import org.opennms.features.newgui.rest.model.IPScanResult;
import org.opennms.features.newgui.rest.model.SNMPFitRequestDTO;
import org.opennms.features.newgui.rest.model.SNMPFitResultDTO;
import org.opennms.netmgt.icmp.proxy.LocationAwarePingClient;
import org.opennms.netmgt.icmp.proxy.PingSweepSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeDiscoverServiceImpl implements NodeDiscoverRestService {
    private static Logger LOG = LoggerFactory.getLogger(NodeDiscoverServiceImpl.class);
    private final LocationAwarePingClient locationAwarePingClient;

    public NodeDiscoverServiceImpl(LocationAwarePingClient locationAwarePingClient) {
        this.locationAwarePingClient = locationAwarePingClient;
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
        int[] counter = new int[] {0};
        requestList.forEach(request -> {
            request.getIpAddresses().forEach(ip -> {
                try {
                    InetAddress ipAddress = InetAddress.getByName(ip);
                    request.getConfigurations().forEach(config -> {
                        SNMPFitResultDTO result = new SNMPFitResultDTO();
                        result.setLocation(request.getLocation());
                        result.setSysOID("test oid" + counter[0]++);
                        result.setIpAddress(ipAddress.getHostAddress());
                        result.setHostname(ipAddress.getHostName());
                        result.setCommunityString(config.getCommunityString());
                        results.add(result);
                    });
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            });
        });
        return results;
    }
}
