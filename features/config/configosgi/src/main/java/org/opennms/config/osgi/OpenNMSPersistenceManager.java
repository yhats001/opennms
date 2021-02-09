/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2021 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 2021-2021 The OpenNMS Group, Inc.
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

package org.opennms.config.osgi;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.cm.PersistenceManager;
import org.apache.felix.cm.file.FilePersistenceManager;
import org.opennms.config.configservice.api.ConfigurationService;
import org.osgi.framework.BundleContext;

/**
 * Our own implementation of a PersistenceManager (subclass of FilePersistenceManager).
 * Must be activated in custom.properties: felix.cm.pm=org.opennms.config.OpenNMSPersistenceManager
 */
public class OpenNMSPersistenceManager extends FilePersistenceManager implements PersistenceManager {

    private final ConfigurationService configService;

    public OpenNMSPersistenceManager(final BundleContext bundleContext, final ConfigurationService configService) {
        super(bundleContext, null);
        this.configService = configService;
    }

    public Dictionary load(String pid) throws IOException {
        if(!isHandledByUs(pid)) {
            return super.load(pid); // nothing to do for us
        }
        return configService.getConfigurationAsDictionary(pid)
                .orElse(new Hashtable());
    }

    public void store(String pid, Dictionary props) throws IOException {
        if(!isHandledByUs(pid)) {
            super.store(pid, props);
            return; // nothing to do for us
        }

        configService.putConfiguration(pid, props);
    }

    private boolean isHandledByUs(final String pid) {
        return "org.opennms.features.topology.app.icons.application".equals(pid);
    }

}