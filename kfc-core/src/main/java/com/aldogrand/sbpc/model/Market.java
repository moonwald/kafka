package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> Market
 * </p>
 * <p>
 * <b>Description</b> An event market. 
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
@XmlRootElement
@XmlType(propOrder = {"id", "name", "eventId", "eventName", "type", "period", 
        "handicap", "marketStatus", "runners", "mappings", "lastChanged"})
public class Market implements Serializable
{
    private static final long serialVersionUID = -18727835193376920L;

    private Long id;
    private String name;
    private Long eventId;
    private String eventName;
    private MarketType type;
    private Period period;
    private Double handicap;
    private MarketStatus marketStatus;
    private List<Runner> runners;
    private List<Mapping> mappings;
    private Date lastChanged;
    
    /**
     * @return the id
     */
    @XmlAttribute
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
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
     * @return the eventId
     */
    @XmlElement(name = "event-id")
    public Long getEventId()
    {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    /**
     * @return the eventName
     */
    @XmlElement(name = "event-name")
    public String getEventName()
    {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName)
    {
        this.eventName = eventName;
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
     * Represents the current status of the Market
     * 
     * Statuses currently include
     * <p>
     * <ul>
     * <li>OPEN</li>
     * <li>CLOSED</li>
     * <li>RESULTED</li>
     * <li>SUSPENDED</li>
     * <li>UNKNOWN</li>
     * </ul>
     * </p>
     * @return
     */
    public MarketStatus getMarketStatus() {
		return marketStatus;
	}

    /**
     * @param marketStatus the marketStatus to set
     */
	public void setMarketStatus(MarketStatus marketStatus) {
		this.marketStatus = marketStatus;
	}

	/**
     * @return the runners
     */
    @XmlElementWrapper(name = "runners")
    @XmlElement(name ="runner")
    public List<Runner> getRunners()
    {
        return runners;
    }

    /**
     * @param runners the runners to set
     */
    public void setRunners(List<Runner> runners)
    {
        this.runners = runners;
    }

    /**
     * @return the mappings
     */
    @XmlElementWrapper(name = "mappings")
    @XmlElement(name ="mapping")
    public List<Mapping> getMappings()
    {
        return mappings;
    }

    /**
     * @param mappings the mappings to set
     */
    public void setMappings(List<Mapping> mappings)
    {
        this.mappings = mappings;
    }

    /**
     * @return the lastChanged
     */
    @XmlElement(name = "last-changed")
    public Date getLastChanged()
    {
        return lastChanged;
    }

    /**
     * @param lastChanged the lastChanged to set
     */
    public void setLastChanged(Date lastChanged)
    {
        this.lastChanged = lastChanged;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Market [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", type=");
        builder.append(type);
        builder.append(", period=");
        builder.append(period);
        builder.append("]");
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Market other = (Market) obj;
        if (id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        }
        else if (!id.equals(other.id))
        {
            return false;
        }
        return true;
    }
}
