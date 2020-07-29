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

package org.opennms.features.topology.plugins.topo.asset.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.opennms.core.xml.JaxbUtils;
import org.opennms.features.topology.plugins.topo.asset.NodeProvider;
import org.opennms.features.topology.plugins.topo.asset.layers.LayerDefinition;
import org.opennms.features.topology.plugins.topo.asset.layers.NodeParamLabels;
import org.opennms.netmgt.model.OnmsNode;

public class TestNodeProvider implements NodeProvider {

    private static final String NODE_TEST_DATA_FILE_NAME="/mock-testdata.xml";

    @Override
    public List<OnmsNode> getNodes(List<LayerDefinition> definitions) {
            final InputStream nodeTestDataStream = getClass().getResourceAsStream(NODE_TEST_DATA_FILE_NAME);
            NodeInfoRepositoryXML nodeInfoRepositoryXML = JaxbUtils.unmarshal(NodeInfoRepositoryXML.class, nodeTestDataStream);
            final List<OnmsNode> nodes = nodeInfoRepositoryXML.getNodeInfoList().stream().map(eachEntry -> {
                final NodeBuilder nodeBuilder = new NodeBuilder().withId(eachEntry.getNodeId());
                final Map<String, String> parameters = eachEntry.getParameters();

                // Apply Node parameters
                apply(parameters, NodeParamLabels.NODE_NODELABEL, value -> nodeBuilder.withLabel(value));
                apply(parameters, NodeParamLabels.NODE_NODEID, value -> nodeBuilder.withId(value));
                apply(parameters, NodeParamLabels.NODE_FOREIGNSOURCE, value -> nodeBuilder.withForeignSource(value));
                apply(parameters, NodeParamLabels.NODE_FOREIGNID, value -> nodeBuilder.withForeignId(value));
                apply(parameters, NodeParamLabels.NODE_NODESYSNAME, value -> nodeBuilder.withSyslocation(value));
                apply(parameters, NodeParamLabels.NODE_OPERATINGSYSTEM, value -> nodeBuilder.withOperatingSystem(value));
                apply(parameters, NodeParamLabels.NODE_CATEGORIES, value -> nodeBuilder.withCategories(value));
                apply(parameters, NodeParamLabels.PARENT_NODELABEL, value -> nodeBuilder.withParentLabel(value));
                apply(parameters, NodeParamLabels.PARENT_NODEID, value -> nodeBuilder.withParentId(value));
                apply(parameters, NodeParamLabels.PARENT_FOREIGNSOURCE, value -> nodeBuilder.withParentForeignSource(value));
                apply(parameters, NodeParamLabels.PARENT_FOREIGNID, value -> nodeBuilder.withParentForeignId(value));

                // Apply Asset parameters
                apply(parameters, NodeParamLabels.ASSET_COUNTRY, value -> nodeBuilder.withAsset("country",value));
                apply(parameters, NodeParamLabels.ASSET_ADDRESS1, value -> nodeBuilder.withAsset("address1",value));
                apply(parameters, NodeParamLabels.ASSET_ADDRESS2, value -> nodeBuilder.withAsset("address2",value));
                apply(parameters, NodeParamLabels.ASSET_CITY, value -> nodeBuilder.withAsset("city",value));
                apply(parameters, NodeParamLabels.ASSET_ZIP, value -> nodeBuilder.withAsset("zip",value));
                apply(parameters, NodeParamLabels.ASSET_STATE, value -> nodeBuilder.withAsset("state",value));
                apply(parameters, NodeParamLabels.ASSET_LATITUDE, value -> nodeBuilder.withAsset("latitude",value));
                apply(parameters, NodeParamLabels.ASSET_LONGITUDE, value -> nodeBuilder.withAsset("longitude",value));
                apply(parameters, NodeParamLabels.ASSET_REGION, value -> nodeBuilder.withAsset("region",value));
                apply(parameters, NodeParamLabels.ASSET_DIVISION, value -> nodeBuilder.withAsset("division",value));
                apply(parameters, NodeParamLabels.ASSET_DEPARTMENT, value -> nodeBuilder.withAsset("department",value));
                apply(parameters, NodeParamLabels.ASSET_BUILDING, value -> nodeBuilder.withAsset("building",value));
                apply(parameters, NodeParamLabels.ASSET_FLOOR, value -> nodeBuilder.withAsset("floor",value));
                apply(parameters, NodeParamLabels.ASSET_ROOM, value -> nodeBuilder.withAsset("room",value));
                apply(parameters, NodeParamLabels.ASSET_RACK, value -> nodeBuilder.withAsset("rack",value));
                apply(parameters, NodeParamLabels.ASSET_SLOT, value -> nodeBuilder.withAsset("slot",value));
                apply(parameters, NodeParamLabels.ASSET_PORT, value -> nodeBuilder.withAsset("port",value));
                apply(parameters, NodeParamLabels.ASSET_CIRCUITID, value -> nodeBuilder.withAsset("circuitId",value));
                apply(parameters, NodeParamLabels.ASSET_CATEGORY, value -> nodeBuilder.withAsset("category",value));
                apply(parameters, NodeParamLabels.ASSET_DISPLAYCATEGORY, value -> nodeBuilder.withAsset("displayCategory",value));
                apply(parameters, NodeParamLabels.ASSET_NOTIFYCATEGORY, value -> nodeBuilder.withAsset("notifyCategory",value));
                apply(parameters, NodeParamLabels.ASSET_POLLERCATEGORY, value ->nodeBuilder.withAsset("pollerCategory",value));
                apply(parameters, NodeParamLabels.ASSET_THRESHOLDCATEGORY, value -> nodeBuilder.withAsset("thresholdCategory",value));
                apply(parameters, NodeParamLabels.ASSET_MANAGEDOBJECTTYPE, value -> nodeBuilder.withAsset("managedObjectType",value));
                apply(parameters, NodeParamLabels.ASSET_MANAGEDOBJECTINSTANCE, value -> nodeBuilder.withAsset("managedObjectInstance",value));
                apply(parameters, NodeParamLabels.ASSET_MANUFACTURER, value -> nodeBuilder.withAsset("manufacturer",value));
                apply(parameters, NodeParamLabels.ASSET_VENDOR, value -> nodeBuilder.withAsset("vendor",value));
                apply(parameters, NodeParamLabels.ASSET_MODELNUMBER, value -> nodeBuilder.withAsset("modelNumber",value));
                apply(parameters, NodeParamLabels.ASSET_DESCRIPTION, value -> nodeBuilder.withAsset("description",value));
                apply(parameters, NodeParamLabels.ASSET_OPERATINGSYSTEM, value -> nodeBuilder.withAsset("operatingSystem",value));

                OnmsNode node = nodeBuilder.getNode();
                return node;
            }).collect(Collectors.toList());
            return nodes;
    }


    private static void apply(Map<String, String> parameters, String parameterKey, Consumer<String> consumer) {
        if (parameters.get(parameterKey) != null) {
            consumer.accept(parameters.get(parameterKey));
        }
    }

}
