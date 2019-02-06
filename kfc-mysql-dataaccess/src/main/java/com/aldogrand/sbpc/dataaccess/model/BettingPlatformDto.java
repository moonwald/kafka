package com.aldogrand.sbpc.dataaccess.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * <p>
 * <b>Title</b> BettingPlatformDto
 * </p>
 * <p>
 * <b>Description</b> A representation of a Betting Platform. A Betting Platform
 * can be an Exchange or a Sportbook. Aggregator s can support
 * multiple Betting Platforms.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
@Entity(name = "BettingPlatform")
@Table(name = "betting_platforms", 
        uniqueConstraints = @UniqueConstraint(columnNames = {"connector", "name"}))
@Cacheable(false)
public class BettingPlatformDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = -8820953413541242640L;

    private ConnectorDto connector;
    private String name;

    /**
     * @return the connector
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "connector", nullable = false)
    public ConnectorDto getConnector()
    {
        return connector;
    }

    /**
     * @param connector the connector to set
     */
    public void setConnector(ConnectorDto connector)
    {
        this.connector = connector;
    }

    /**
     * @return the name
     */
    @Column(name = "name", length = 100, nullable = false)
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BettingPlatformDto [connector=");
        builder.append(connector);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
