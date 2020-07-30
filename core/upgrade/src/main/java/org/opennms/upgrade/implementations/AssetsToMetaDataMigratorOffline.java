/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2020 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2020 The OpenNMS Group, Inc.
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

package org.opennms.upgrade.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.opennms.core.db.DataSourceFactory;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.upgrade.api.AbstractOnmsUpgrade;
import org.opennms.upgrade.api.OnmsUpgradeException;

public class AssetsToMetaDataMigratorOffline extends AbstractOnmsUpgrade {

    private final static int BATCH_SIZE = 2000;

    public AssetsToMetaDataMigratorOffline() throws OnmsUpgradeException {
        super();
    }

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public String getDescription() {
        return "Moves asset data to the node's metadata.";
    }

    @Override
    public boolean requiresOnmsRunning() {
        return false;
    }

    @Override
    public void preExecute() throws OnmsUpgradeException {
        try (final Connection connection = DataSourceFactory.getInstance().getConnection()) {
            final Statement preExecutionStatement = connection.createStatement();
            try (final ResultSet preExecutionResultSet = preExecutionStatement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'assets')")) {
                preExecutionResultSet.next();
                if (!preExecutionResultSet.getBoolean(1)) {
                    throw new OnmsUpgradeException("The 'assets' table do not exist anymore");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new OnmsUpgradeException("Error checking for table 'assets'", e);
            }
        } catch (SQLException e) {
            throw new OnmsUpgradeException("Error opening database connection", e);
        }
    }

    @Override
    public void postExecute() throws OnmsUpgradeException {
    }

    @Override
    public void rollback() throws OnmsUpgradeException {
    }

    @Override
    public void execute() throws OnmsUpgradeException {
        long nodeCount = 0;

        try (final Connection connection = DataSourceFactory.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            do {
                try (final Statement selectStatement = connection.createStatement(); final ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM assets LIMIT " + BATCH_SIZE)) {

                    if (!resultSet.next()) {
                        break;
                    }

                    try (final PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO node_metadata (id, context, key, value) VALUES  (?,?,?,?)");
                         final PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM assets WHERE nodeid=?")) {

                        do {
                            final Integer nodeId = resultSet.getInt("nodeid");

                            insertMetaData(insertStatement, nodeId, resultSet, "nodeid", "nodeid");
                            insertMetaData(insertStatement, nodeId, resultSet, "category", "category");
                            insertMetaData(insertStatement, nodeId, resultSet, "manufacturer", "manufacturer");
                            insertMetaData(insertStatement, nodeId, resultSet, "vendor", "vendor");
                            insertMetaData(insertStatement, nodeId, resultSet, "modelnumber", "modelnumber");
                            insertMetaData(insertStatement, nodeId, resultSet, "serialnumber", "serialnumber");
                            insertMetaData(insertStatement, nodeId, resultSet, "description", "description");
                            insertMetaData(insertStatement, nodeId, resultSet, "circuitid", "circuitid");
                            insertMetaData(insertStatement, nodeId, resultSet, "assetnumber", "assetnumber");
                            insertMetaData(insertStatement, nodeId, resultSet, "operatingsystem", "operatingsystem");
                            insertMetaData(insertStatement, nodeId, resultSet, "rack", "rack");
                            insertMetaData(insertStatement, nodeId, resultSet, "slot", "slot");
                            insertMetaData(insertStatement, nodeId, resultSet, "port", "port");
                            insertMetaData(insertStatement, nodeId, resultSet, "region", "region");
                            insertMetaData(insertStatement, nodeId, resultSet, "division", "division");
                            insertMetaData(insertStatement, nodeId, resultSet, "department", "department");
                            insertMetaData(insertStatement, nodeId, resultSet, "address1", "address1");
                            insertMetaData(insertStatement, nodeId, resultSet, "address2", "address2");
                            insertMetaData(insertStatement, nodeId, resultSet, "city", "city");
                            insertMetaData(insertStatement, nodeId, resultSet, "state", "state");
                            insertMetaData(insertStatement, nodeId, resultSet, "zip", "zip");
                            insertMetaData(insertStatement, nodeId, resultSet, "building", "building");
                            insertMetaData(insertStatement, nodeId, resultSet, "floor", "floor");
                            insertMetaData(insertStatement, nodeId, resultSet, "room", "room");
                            insertMetaData(insertStatement, nodeId, resultSet, "vendorphone", "vendorphone");
                            insertMetaData(insertStatement, nodeId, resultSet, "vendorfax", "vendorfax");
                            insertMetaData(insertStatement, nodeId, resultSet, "vendorassetnumber", "vendorassetnumber");
                            insertMetaData(insertStatement, nodeId, resultSet, "userlastmodified", "userlastmodified");
                            insertMetaData(insertStatement, nodeId, resultSet, "lastmodifieddate", "lastmodifieddate");
                            insertMetaData(insertStatement, nodeId, resultSet, "dateinstalled", "dateinstalled");
                            insertMetaData(insertStatement, nodeId, resultSet, "lease", "lease");
                            insertMetaData(insertStatement, nodeId, resultSet, "leaseexpires", "leaseexpires");
                            insertMetaData(insertStatement, nodeId, resultSet, "supportphone", "supportphone");
                            insertMetaData(insertStatement, nodeId, resultSet, "maintcontract", "maintcontract");
                            insertMetaData(insertStatement, nodeId, resultSet, "maintcontractexpires", "maintcontractexpires");
                            insertMetaData(insertStatement, nodeId, resultSet, "displaycategory", "displaycategory");
                            insertMetaData(insertStatement, nodeId, resultSet, "notifycategory", "notifycategory");
                            insertMetaData(insertStatement, nodeId, resultSet, "pollercategory", "pollercategory");
                            insertMetaData(insertStatement, nodeId, resultSet, "thresholdcategory", "thresholdcategory");
                            insertMetaData(insertStatement, nodeId, resultSet, "comment", "comment");
                            insertMetaData(insertStatement, nodeId, resultSet, "managedobjectinstance", "managedobjectinstance");
                            insertMetaData(insertStatement, nodeId, resultSet, "managedobjecttype", "managedobjecttype");
                            insertMetaData(insertStatement, nodeId, resultSet, "username", "username");
                            insertMetaData(insertStatement, nodeId, resultSet, "password", "password");
                            insertMetaData(insertStatement, nodeId, resultSet, "enable", "enable");
                            insertMetaData(insertStatement, nodeId, resultSet, "autoenable", "autoenable");
                            insertMetaData(insertStatement, nodeId, resultSet, "connection", "connection");
                            insertMetaData(insertStatement, nodeId, resultSet, "cpu", "cpu");
                            insertMetaData(insertStatement, nodeId, resultSet, "ram", "ram");
                            insertMetaData(insertStatement, nodeId, resultSet, "storagectrl", "storagectrl");
                            insertMetaData(insertStatement, nodeId, resultSet, "hdd1", "hdd1");
                            insertMetaData(insertStatement, nodeId, resultSet, "hdd2", "hdd2");
                            insertMetaData(insertStatement, nodeId, resultSet, "hdd3", "hdd3");
                            insertMetaData(insertStatement, nodeId, resultSet, "hdd4", "hdd4");
                            insertMetaData(insertStatement, nodeId, resultSet, "hdd5", "hdd5");
                            insertMetaData(insertStatement, nodeId, resultSet, "hdd6", "hdd6");
                            insertMetaData(insertStatement, nodeId, resultSet, "numpowersupplies", "numpowersupplies");
                            insertMetaData(insertStatement, nodeId, resultSet, "inputpower", "inputpower");
                            insertMetaData(insertStatement, nodeId, resultSet, "additionalhardware", "additionalhardware");
                            insertMetaData(insertStatement, nodeId, resultSet, "admin", "admin");
                            insertMetaData(insertStatement, nodeId, resultSet, "snmpcommunity", "snmpcommunity");
                            insertMetaData(insertStatement, nodeId, resultSet, "rackunitheight", "rackunitheight");
                            insertMetaData(insertStatement, nodeId, resultSet, "country", "country");
                            insertMetaData(insertStatement, nodeId, resultSet, "longitude", "longitude");
                            insertMetaData(insertStatement, nodeId, resultSet, "latitude", "latitude");

                            deleteStatement.setInt(1, nodeId);
                            deleteStatement.execute();

                            nodeCount++;
                        } while (resultSet.next());

                        log("Processed %d node entries, %d metadata entries inserted...\n", nodeCount, nodeCount * 5);
                        connection.commit();
                    } catch (SQLException e) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        throw e;
                    }
                }

            } while (true);

            connection.setAutoCommit(true);
            log("Rows migrated. Dropping table 'assets'...\n");

            final Statement postMigrationStatement = connection.createStatement();
            postMigrationStatement.execute("DROP TABLE assets");
        } catch (Throwable e) {
            throw new OnmsUpgradeException("Can't move asset data to metadata table: " + e.getMessage(), e);
        }
    }

    private void insertMetaData(final PreparedStatement preparedStatement, final int nodeId, final ResultSet resultSet, final String dbKey, final String key) throws SQLException {
        final String value = resultSet.getString(dbKey);
        preparedStatement.setInt(1, nodeId);
        preparedStatement.setString(2, OnmsNode.NODE_ASSET_CONTEXT);
        preparedStatement.setString(3, key);
        preparedStatement.setString(4, value);
        preparedStatement.execute();
    }
}
