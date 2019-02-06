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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.aldogrand.sbpc.model.EventStatus;

/**
 * <p>
 * <b>Title</b> EventDto
 * </p>
 * <p>
 * <b>Description</b> An event that can be bet on. 
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
@Entity(name = "Event")
@Table(name = "events")
@Cacheable(false)
public class EventDto extends AbstractDto
{
    private static final long serialVersionUID = 4196777281143748481L;

    private String name;
    private Date startTime;
    private Set<MetaTagDto> metaTags = new HashSet<MetaTagDto>();
    private Set<NameVariantDto> nameVariants = new HashSet<NameVariantDto>();
    private List<SourceEventDto> mappings = new ArrayList<SourceEventDto>();
    private List<MarketDto> markets = new ArrayList<MarketDto>();
    private EventStatus eventStatus;
    private Date lastChangeTime = new Date();

    /**
     * @return the name
     */
    @Column(name = "name", length = 255, nullable = false)
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
     * @return the metaTags
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "events_meta_tags", 
            joinColumns = @JoinColumn(name = "event", referencedColumnName = "id", nullable = false), 
            inverseJoinColumns = @JoinColumn(name = "meta_tag", referencedColumnName = "id", nullable = false), 
            uniqueConstraints = @UniqueConstraint(columnNames = {"event", "meta_tag"}))
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
     * @return the nameVariants
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "events_name_variants", 
            joinColumns = @JoinColumn(name = "event", referencedColumnName = "id", nullable = false), 
            inverseJoinColumns = @JoinColumn(name = "name_variant", referencedColumnName = "id", nullable = false), 
            uniqueConstraints = @UniqueConstraint(columnNames = {"event", "name_variant"}))
    @OrderBy("variant ASC")
    public Set<NameVariantDto> getNameVariants()
    {
        return nameVariants;
    }

    /**
     * @param nameVariants the nameVariants to set
     */
    public void setNameVariants(Set<NameVariantDto> nameVariants)
    {
        this.nameVariants = nameVariants;
    }

    /**
     * @return the mappings
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    public List<SourceEventDto> getMappings()
    {
        return mappings;
    }

    /**
     * @param mappings the mappings to set
     */
    public void setMappings(List<SourceEventDto> mappings)
    {
        this.mappings = mappings;
    }

    /**
     * @return the markets
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, 
            mappedBy = "event")
    public List<MarketDto> getMarkets()
    {
        return markets;
    }

    /**
     * @param markets the markets to set
     */
    public void setMarkets(List<MarketDto> markets)
    {
        this.markets = markets;
    }
    
    /**
     * @return the eventStatus
     */
    @Column(name = "status", length = 20, nullable = true)
    @Enumerated(EnumType.STRING)
	public EventStatus getEventStatus()
	{
		return eventStatus == null ? EventStatus.UNKNOWN : eventStatus;
	}

    /**
     * @param eventStatus the eventStatus to set
     */
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	/**
     * @return the lastChangeTime
     */
    @Column(name = "last_change_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastChangeTime()
    {
        return lastChangeTime;
    }

    /**
     * @param lastChangeTime the lastChangeTime to set
     */
    public void setLastChangeTime(Date lastChangeTime)
    {
        this.lastChangeTime = lastChangeTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("EventDto [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }    
}
