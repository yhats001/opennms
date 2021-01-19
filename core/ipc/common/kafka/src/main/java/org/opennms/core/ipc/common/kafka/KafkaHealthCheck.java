/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2018 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2018 The OpenNMS Group, Inc.
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

package org.opennms.core.ipc.common.kafka;


import java.util.Hashtable;
import java.util.Properties;

import org.opennms.core.health.api.Context;
import org.opennms.core.health.api.HealthCheck;
import org.opennms.core.health.api.Response;
import org.opennms.core.health.api.Status;


public class KafkaHealthCheck implements HealthCheck {

    private KafkaConfigProvider kafkaConfigProvider;
    // Differentiate between Sink/RPC
    private final String type;

    private Hashtable<String,String> hashtableTags = new Hashtable<String, String>() {{
        put("description", "Connecting to Kafka from null");
        put("name", "opennms-kafka-null");
        put("local", "false");
    }};

    public KafkaHealthCheck(KafkaConfigProvider kafkaConfigProvider, String type) {
        this.kafkaConfigProvider = kafkaConfigProvider;
        this.type = type;
        this.hashtableTags.put("description", "Connecting to Kafka from " + this.type);
        this.hashtableTags.put("name", "opennms-kafka-" + this.type);
    }

    @Override
    public String getDescription() {
        return getTag("description");
    }

    @Override
    public String getTag(String key) {
        return this.hashtableTags.get(key);
    }

    @Override
    public void setTag(String key, String value) {
        this.hashtableTags.put(key, value);
    }

    @Override
    public void setTags(Hashtable<String, String> hashtable) {
        this.hashtableTags = (Hashtable<String, String>) hashtable.clone();
    }

    @Override
    public Response perform(Context context) throws Exception {
        Properties kafkaConfig = kafkaConfigProvider.getProperties();
        int timeout = Math.toIntExact(context.getTimeout());
        kafkaConfig.put("request.timeout.ms", timeout);
        try {
            Utils.getTopics(kafkaConfig);
            return new Response(Status.Success);
        } catch (Exception e) {
            return new Response(e);
        }
    }
}
