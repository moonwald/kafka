package com.aldogrand.sbpc.dataaccess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.Period;

/**
 * <p>
 * <b>Title</b> MarketDto
 * </p>
 * <p>
 * <b>Description</b> A market of an {@link EventDto} that can be bet on. 
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
@Entity(name = "Market")
@Table(name = "markets") 
@Cacheable(false)
public class MarketDto extends AbstractDto
{
    private static final long serialVersionUID = 8131247790009625486L;
    private EventDto event;
    private String name;
    private MarketType type;
    private Period period;
    private Double handicap;
    private MarketStatus marketStatus;
    private List<SourceMarketDto> mappings = new ArrayList<SourceMarketDto>();
    private List<RunnerDto> runners = new ArrayList<RunnerDto>();
    private Date lastChangeTime = new Date();
    private List<InterestedClientDto> interestedClients = new ArrayList<InterestedClientDto>();
    
    /**
     * @return the event
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event", nullable = false)
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
     * @return the type
     */
    @Column(name = "type", length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
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
    @Column(name = "period", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
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
    @Column(name = "handicap", precision = 5, scale = 2)
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
    @Column(name = "status", length = 20, nullable = true)
    @Enumerated(EnumType.STRING)
	public MarketStatus getMarketStatus()
	{
		return marketStatus == null ? MarketStatus.UNKNOWN : marketStatus;
	}

    /**
     * @param marketStatus the status to set
     */
	public void setMarketStatus(MarketStatus marketStatus) {
		this.marketStatus = marketStatus;
	}
	

    /**
     * @return the mappings
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "market")
    public List<SourceMarketDto> getMappings()
    {
        return mappings;
    }

    /**
     * @param mappings the mappings to set
     */
    public void setMappings(List<SourceMarketDto> mappings)
    {
        this.mappings = mappings;
    }

    /**
     * @return the runners
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, 
            mappedBy = "market")
    @OrderBy("sequence ASC")
    public List<RunnerDto> getRunners()
    {
        return runners;
    }

    /**
     * @param runners the runners to set
     */
    public void setRunners(List<RunnerDto> runners)
    {
        this.runners = runners;
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

    /**
     * @return the interestedClients
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, 
            mappedBy = "market")
    @OrderBy("clientToken ASC")
    public List<InterestedClientDto> getInterestedClients()
    {
        return interestedClients;
    }

    /**
     * @param interestedClients the interestedClients to set
     */
    public void setInterestedClients(List<InterestedClientDto> interestedClients)
    {
        this.interestedClients = interestedClients;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MarketDto [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
