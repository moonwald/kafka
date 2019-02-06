package com.aldogrand.sbpc.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * <b>Title</b> SourceMarket.java
 * </p>
 * <p>
 * <b>Description</b> kfc-core.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author aldogrand
 * @version 1.0
 */
public class SourceMarket
{
    private static final long serialVersionUID = 3114642745627099934L;

    private Long id;
    private Long version;
    private Long marketId;
    private Long connectorId;
    private Long sourceEventId;
    private String sourceId;
    private String sourceName;
    private boolean creator;
    private MarketType type;
    private Period period;
    private Double handicap;
    private MarketStatus marketStatus;
    private List<SourceRunner> runners = new ArrayList<SourceRunner>();

    /**
     * @return the market
     */
    public Long getMarketId()
    {
        return marketId;
    }

    /**
     * @param market the market to set
     */
    public void setMarketId(Long market)
    {
        this.marketId = market;
    }

    /**
     * @return the connector
     */
    public Long getConnectorId()
    {
        return connectorId;
    }

    /**
     * @param connector the connector to set
     */
    public void setConnectorId(Long connector)
    {
        this.connectorId = connector;
    }

    /**
     * @return the sourceEvent
     */
    public Long getSourceEventId()
    {
        return sourceEventId;
    }

    /**
     * @param sourceEvent the sourceEvent to set
     */
    public void setSourceEventId(Long sourceEventId)
    {
        this.sourceEventId = sourceEventId;
    }

    /**
     * @return the sourceId
     */
    public String getSourceId()
    {
        return sourceId;
    }

    /**
     * @param sourceId the sourceId to set
     */
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    /**
     * @return the sourceName
     */
    public String getSourceName()
    {
        return sourceName;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }

    /**
     * @return the creator
     */
    public boolean isCreator()
    {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(boolean creator)
    {
        this.creator = creator;
    }

    /**
     * @return the type
     */
    public MarketType getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MarketType type)
    {
        this.type = type;
    }

    /**
     * @return the period
     */
    public Period getPeriod()
    {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Period period)
    {
        this.period = period;
    }

    /**
     * @return the handicap
     */
    public Double getHandicap()
    {
        return handicap;
    }

    /**
     * @param handicap the handicap to set
     */
    public void setHandicap(Double handicap)
    {
        this.handicap = handicap;
    }

    /**
     * @return the marketStatus
     */
    public MarketStatus getMarketStatus() {
		return marketStatus;
	}

    /**
     * @param marketStatus the status to set
     */
	public void setMarketStatus(MarketStatus marketStatus) {
		this.marketStatus = marketStatus;
	}

	/**
     * @return the runners
     */
    public List<SourceRunner> getRunners()
    {
        return runners;
    }

    /**
     * @param runners the runners to set
     */
    public void setRunners(List<SourceRunner> runners)
    {
        this.runners = runners;
    }

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
}
