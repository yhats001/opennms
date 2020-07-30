/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2019-2019 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.search.providers.node;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.opennms.core.criteria.CriteriaBuilder;
import org.opennms.core.criteria.restrictions.Restrictions;
import org.opennms.netmgt.dao.api.NodeDao;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.search.api.Contexts;
import org.opennms.netmgt.search.api.Matcher;
import org.opennms.netmgt.search.api.SearchContext;
import org.opennms.netmgt.search.api.SearchProvider;
import org.opennms.netmgt.search.api.SearchQuery;
import org.opennms.netmgt.search.api.SearchResult;
import org.opennms.netmgt.search.api.SearchResultItem;
import org.opennms.netmgt.search.api.QueryUtils;
import org.opennms.netmgt.search.providers.SearchResultItemBuilder;

import com.google.common.collect.Lists;

public class NodeAssetSearchProvider implements SearchProvider {

    private final NodeDao nodeDao;

    public NodeAssetSearchProvider(NodeDao nodeDao) {
        this.nodeDao = Objects.requireNonNull(nodeDao);
    }

    @Override
    public SearchContext getContext() {
        return Contexts.Node;
    }

    @Override
    public SearchResult query(SearchQuery query) {
        final String input = query.getInput();
        final CriteriaBuilder criteriaBuilder = new CriteriaBuilder(OnmsNode.class)
                .alias("metaData", "metaData")
                .and(Restrictions.eq("metaData.context", OnmsNode.NODE_ASSET_CONTEXT),
                     Restrictions.in("metaData.key", Lists.newArrayList("category",
                                                                        "manufacturer",
                                                                        "vendor",
                                                                        "modelNumber",
                                                                        "serialNumber",
                                                                        "description",
                                                                        "circuitId",
                                                                        "assetNumber",
                                                                        "operatingSystem",
                                                                        "rack",
                                                                        "slot",
                                                                        "port",
                                                                        "region",
                                                                        "division",
                                                                        "department",
                                                                        "building",
                                                                        "floor",
                                                                        "room",
                                                                        "vendorPhone",
                                                                        "vendorFax",
                                                                        "vendorAssetNumber",
                                                                        "username",
                                                                        "connection",
                                                                        "lease",
                                                                        "leaseExpires",
                                                                        "supportPhone",
                                                                        "maintContractExpiration",
                                                                        "displayCategory",
                                                                        "pollerCategory",
                                                                        "thresholdCategory",
                                                                        "comment",
                                                                        "cpu",
                                                                        "ram",
                                                                        "hdd1",
                                                                        "hdd2",
                                                                        "hdd3",
                                                                        "hdd4",
                                                                        "hdd5",
                                                                        "hdd6",
                                                                        "numpowersupplies",
                                                                        "inputpower",
                                                                        "additionalhardware",
                                                                        "admin",
                                                                        "snmpcommunity",
                                                                        "rackunitheight",
                                                                        "managedObjectType",
                                                                        "managedObjectInstance",
                                                                        "storagectrl")),
                     Restrictions.ilike("metaData.value", QueryUtils.ilike(input)))
                .distinct();

        final int totalCount = nodeDao.countMatching(criteriaBuilder.toCriteria());
        final List<OnmsNode> matchingNodes = nodeDao.findMatching(criteriaBuilder.orderBy("label").limit(query.getMaxResults()).toCriteria());
        final List<SearchResultItem> results = matchingNodes.stream()
            .map(node -> {
                final SearchResultItem result = new SearchResultItemBuilder().withOnmsNode(node).build();
                final List<Matcher> matcherList = Lists.newArrayList(
                        new Matcher("Category", node.getAsset("category")),
                        new Matcher("ManuFacturer", node.getAsset("manufacturer")),
                        new Matcher("Vendor", node.getAsset("vendor")),
                        new Matcher("Model Number", node.getAsset("number")),
                        new Matcher("Serial Number", node.getAsset("number")),
                        new Matcher("Description", node.getAsset("description")),
                        new Matcher("Circuit Id", node.getAsset("id")),
                        new Matcher("Asset Number", node.getAsset("number")),
                        new Matcher("OS", node.getAsset("system")),
                        new Matcher("Rack", node.getAsset("rack")),
                        new Matcher("Slot", node.getAsset("slot")),
                        new Matcher("Port", node.getAsset("port")),
                        new Matcher("Region", node.getAsset("region")),
                        new Matcher("Division", node.getAsset("division")),
                        new Matcher("Department", node.getAsset("department")),
                        new Matcher("Building", node.getAsset("building")),
                        new Matcher("Floor", node.getAsset("floor")),
                        new Matcher("Room", node.getAsset("room")),
                        new Matcher("Vendor Phone", node.getAsset("phone")),
                        new Matcher("Vendor Fax", node.getAsset("fax")),
                        new Matcher("Vendor Asset Number", node.getAsset("number")),
                        new Matcher("Username", node.getAsset("username")),
                        new Matcher("Connection", node.getAsset("connection")),
                        new Matcher("Lease", node.getAsset("lease")),
                        new Matcher("Lease Expires", node.getAsset("expires")),
                        new Matcher("Support Phone", node.getAsset("phone")),
                        new Matcher("Maint. Contract Expiration", node.getAsset("expiration")),
                        new Matcher("Display Category", node.getAsset("category")),
                        new Matcher("Poller Category", node.getAsset("category")),
                        new Matcher("Threshold Category", node.getAsset("category")),
                        new Matcher("Comment", node.getAsset("comment")),
                        new Matcher("CPU", node.getAsset("cpu")),
                        new Matcher("Ram", node.getAsset("ram")),
                        new Matcher("HDD1", node.getAsset("hdd1")),
                        new Matcher("HDD2", node.getAsset("hdd2")),
                        new Matcher("HDD3", node.getAsset("hdd3")),
                        new Matcher("HDD4", node.getAsset("hdd4")),
                        new Matcher("HDD5", node.getAsset("hdd5")),
                        new Matcher("HDD6", node.getAsset("hdd6")),
                        new Matcher("# Power Supplies", node.getAsset("numpowersupplies")),
                        new Matcher("Input Power", node.getAsset("inputpower")),
                        new Matcher("Additional Hardware", node.getAsset("additionalhardware")),
                        new Matcher("admin", node.getAsset("admin")),
                        new Matcher("SNMP Community", node.getAsset("snmpcommunity")),
                        new Matcher("RU Height", node.getAsset("rackunitheight")),
                        new Matcher("Managed Object Type", node.getAsset("type")),
                        new Matcher("Managed Object Instance", node.getAsset("instance")),
                        new Matcher("Storage Controller", node.getAsset("storagectrl"))
                );
                result.addMatches(matcherList, input);
                return result;
            })
            .collect(Collectors.toList());
        final SearchResult searchResult = new SearchResult(Contexts.Node).withMore(totalCount > results.size()).withResults(results);
        return searchResult;
    }
}
