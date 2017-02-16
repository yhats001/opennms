/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2009-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.01.29 at 01:15:48 PM EST 
//


package org.opennms.netmgt.model.requisition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="requisition")
public class OnmsRequisition implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(OnmsRequisition.class);

    // TODO MVR ...
    private static final long serialVersionUID = 1629774241824443273L;

    @Id
    @Column(name="name")
    protected String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "requisition")
    protected List<OnmsRequisitionNode> nodes = new ArrayList<>();

    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastUpdate;
    
    @Column(name="lastimport")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastImport;
    
    public OnmsRequisition() {
    }

    // requisistionName or foreignSource name (they were something different in the past, but are now the same (mvrueden: 2017-02-18)
    public OnmsRequisition(final String requisitionName) {
        name = requisitionName;
        lastUpdate = new Date();
        lastImport = new Date();
    }

    public OnmsRequisitionNode getNode(String foreignId) {
        for (OnmsRequisitionNode n : nodes) {
            if (n.getForeignId().equals(foreignId)) {
                LOG.debug("returning node '{}' for foreign id '{}'", n, foreignId);
                return n;
            }
        }
        return null;
    }

    public void removeNode(OnmsRequisitionNode node) {
        if (nodes.remove(node)) {
            node.setRequisition(null); // remove parent relationship
        }
    }

    public void removeNode(final String foreignId) {
        removeNode(getNode(foreignId));
    }

    public List<OnmsRequisitionNode> getNodes() {
        return nodes;
    }

    public void setNodes(final List<OnmsRequisitionNode> nodes) {
        this.nodes = Objects.requireNonNull(nodes);
    }

    public void addNode(final OnmsRequisitionNode node) {
        Objects.requireNonNull(node);
        if (!nodes.contains(node)) {
            node.setRequisition(this);
            nodes.add(node);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForeignSource() {
        return getName();
    }

    public void setForeignSource(final String foreignSource) {
        setName(foreignSource);
    }

    public Date getLastImport() {
        return lastImport;
    }

    public void setLastImport(final Date value) {
        lastImport = value;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof OnmsRequisition)) return false;
        final OnmsRequisition other = (OnmsRequisition) obj;
        if (getName() != null) {
            return getName().equals(other.getName());
        }
        return super.equals(obj);
    }

    public void updateLastImported() {
        lastImport = new Date();
    }

    public void updateLastUpdated() {
        lastUpdate = new Date();
    }
}
