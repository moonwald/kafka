package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.util.List;

import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.Period;

/**
 * <p>
 * <b>Title</b> Market
 * </p>
 * <p>
 * <b>Description</b> A market of an {@link Event} on a betting platform.
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
public class Market implements Serializable
{
    private static final long serialVersionUID = -5990092184185789765L;

    private String id;
    private String eventId;
    private String name;
    private MarketType type;
    private Period period;
    private Double handicap;
    private MarketStatus marketStatus;
    private List<Runner> runners;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the eventId
     */
    public String getEventId()
    {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(String eventId)
    {
        this.eventId = eventId;
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
     * 
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
