package com.aldogrand.sbpc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * <p>
 * <b>Title</b> SourceEvent.java
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
public class SourceEvent
{
    private static final long serialVersionUID = 3708102447327440649L;

    private Long id;
    private Long version;
	private Long eventId;
    private Long connectorId;
    private String sourceEventId;
    private String sourceEventName;
    private Date startTime;
    private boolean creator;
    private Set<MetaTag> metaTags = new HashSet<MetaTag>();
    private List<SourceMarket> markets = new ArrayList<SourceMarket>();
    private EventStatus status;
    private List<String> nameVariants = new ArrayList<String>();;
    
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
    /**
     * @return the event
     */
    public Long getEventId()
    {
        return eventId;
    }

    /**
     * @param event the event to set
     */
    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
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
    public void setConnectorId(Long connectorId)
    {
        this.connectorId = connectorId;
    }

    /**
     * @return the sourceId
     */
    public String getSourceId()
    {
        return sourceEventId;
    }

    /**
     * @param sourceId the sourceId to set
     */
    public void setSourceId(String sourceId)
    {
        this.sourceEventId = sourceId;
    }

    /**
     * @return the sourceName
     */
    public String getSourceName()
    {
        return sourceEventName;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName)
    {
        this.sourceEventName = sourceName;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime()
    {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
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
     * @return the metaTags
     */
    public Set<MetaTag> getMetaTags()
    {
        return metaTags;
    }

    /**
     * @param metaTags the metaTags to set
     */
    public void setMetaTags(Set<MetaTag> metaTags)
    {
        this.metaTags = metaTags;
    }

    /**
     * @return the markets
     */
    public List<SourceMarket> getMarkets()
    {
        return markets;
    }

    /**
     * @param markets the markets to set
     */
    public void setMarkets(List<SourceMarket> markets)
    {
        this.markets = markets;
    }
    
    /**
     * Retrieves the current status of the Event
     * 
     * @return
     */
    public EventStatus getStatus() {
    	return this.status;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(EventStatus status) {
    	this.status = status;
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
	 * @return the nameVariants
	 */
	public List<String> getNameVariants() {
		return nameVariants;
	}

	/**
	 * @param nameVariants the nameVariants to set
	 */
	public void setNameVariants(List<String> nameVariants) {
		this.nameVariants = nameVariants;
	}
}
