package com.aldogrand.sbpc.dataaccess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.aldogrand.sbpc.model.EventStatus;

/**
 * <p>
 * <b>Title</b> SourceEventDto
 * </p>
 * <p>
 * <b>Description</b> An event from a {@link ConnectorDto} that can be mapped to
 * an {@link EventDto}.
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
@Entity(name = "SourceEvent")
@Table(name = "source_events", 
        uniqueConstraints = {@UniqueConstraint(columnNames = {"connector", "source_id"}),
            @UniqueConstraint(columnNames = {"connector", "event"})})
@Cacheable(false)
public class SourceEventDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = 3708102447327440649L;

    private EventDto event;
    private ConnectorDto connector;
    private String sourceId;
    private String sourceName;
    private Date startTime;
    private boolean creator;
    private Set<MetaTagDto> metaTags = new HashSet<MetaTagDto>();
    private List<SourceMarketDto> markets = new ArrayList<SourceMarketDto>();
    private EventStatus status;
    private List<SourceEventIncidentDto> incidents = new ArrayList<SourceEventIncidentDto>();
//    private List<SourceEventParticipantDto> participants = new ArrayList<SourceEventParticipantDto>();
    
        
    /**
     * @return the event
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "event", nullable = true)
    public EventDto getEvent()
    {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(EventDto event)
    {
        this.event = event;
    }

    /**
     * @return the connector
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
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
     * @return the sourceId
     */
    @Column(name = "source_id", length = 100, nullable = false)
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
    @Column(name = "source_name", nullable = false)
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
     * @return the startTime
     */
    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
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
    @Column(name = "creator", nullable = false)
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "source_events_meta_tags", 
            joinColumns = @JoinColumn(name = "source_event", referencedColumnName = "id", nullable = false), 
            inverseJoinColumns = @JoinColumn(name = "meta_tag", referencedColumnName = "id", nullable = false), 
            uniqueConstraints = @UniqueConstraint(columnNames = {"source_event", "meta_tag"}))
    public Set<MetaTagDto> getMetaTags()
    {
        return metaTags;
    }

    /**
     * @param metaTags the metaTags to set
     */
    public void setMetaTags(Set<MetaTagDto> metaTags)
    {
        this.metaTags = metaTags;
    }

    /**
     * @return the markets
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, 
            mappedBy = "sourceEvent")
    public List<SourceMarketDto> getMarkets()
    {
        return markets;
    }

    /**
     * @param markets the markets to set
     */
    public void setMarkets(List<SourceMarketDto> markets)
    {
        this.markets = markets;
    }
    
    /**
     * Retrieves the current status of the Event
     * 
     * @return
     */
    @Column(name = "status", length = 20, nullable = true)
    @Enumerated(EnumType.STRING)
	public EventStatus getStatus()
	{
		return this.status == null ? EventStatus.UNKNOWN : this.status;
	}
    
    /**
     * @param status the status to set
     */
    public void setStatus(EventStatus status) {
    	this.status = status;
	}
    
//    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "sourceEvent")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    public List<SourceEventParticipantDto> getParticipants() {
//		return participants;
//	}
//
//	public void setParticipants(List<SourceEventParticipantDto> participants) {
//		this.participants = participants;
//	}    

    /**
	 * @return the incidents
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceEvent")
	public List<SourceEventIncidentDto> getIncidents() {
		return incidents;
	}

	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(List<SourceEventIncidentDto> incidents) {
		this.incidents = incidents;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SourceEventDto [connector=");
        builder.append(connector);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", sourceName=");
        builder.append(sourceName);
        builder.append(", startTime=");
        builder.append(startTime);
        builder.append(", status=");
        builder.append(status);
        builder.append("]");
        return builder.toString();
    }

}
