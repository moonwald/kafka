package com.aldogrand.sbpc.dataaccess.model;

import java.util.ArrayList;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.Period;

/**
 * <p>
 * <b>Title</b> SourceMarketDto
 * </p>
 * <p>
 * <b>Description</b> A market from a {@link ConnectorDto} that can be mapped to
 * a {@link MarketDto}.
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
@Entity(name = "SourceMarket")
@Table(name = "source_markets", 
        uniqueConstraints = {@UniqueConstraint(columnNames = {"connector", "source_id"}),
            @UniqueConstraint(columnNames = {"connector", "market"})})
@Cacheable(false)
public class SourceMarketDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = 3114642745627099934L;

    private MarketDto market;
    private ConnectorDto connector;
    private SourceEventDto sourceEvent;
    private String sourceId;
    private String sourceName;
    private boolean creator;
    private MarketType type;
    private Period period;
    private Double handicap;
    private MarketStatus marketStatus;
    private List<SourceRunnerDto> runners = new ArrayList<SourceRunnerDto>();

    /**
     * @return the market
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "market", nullable = true)
    public MarketDto getMarket()
    {
        return market;
    }

    /**
     * @param market the market to set
     */
    public void setMarket(MarketDto market)
    {
        this.market = market;
    }

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
     * @return the sourceEvent
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_event", nullable = false)
    public SourceEventDto getSourceEvent()
    {
        return sourceEvent;
    }

    /**
     * @param sourceEvent the sourceEvent to set
     */
    public void setSourceEvent(SourceEventDto sourceEvent)
    {
        this.sourceEvent = sourceEvent;
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
     * @return the runners
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, 
            mappedBy = "sourceMarket")
    @OrderBy("sequence ASC")
    public List<SourceRunnerDto> getRunners()
    {
        return runners;
    }

    /**
     * @param runners the runners to set
     */
    public void setRunners(List<SourceRunnerDto> runners)
    {
        this.runners = runners;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SourceMarketDto [connector=");
        builder.append(connector);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", sourceName=");
        builder.append(sourceName);
        builder.append(", type=");
        builder.append(type);
        builder.append(", period=");
        builder.append(period);
        builder.append(", handicap=");
        builder.append(handicap);
        builder.append("]");
        return builder.toString();
    }
}
