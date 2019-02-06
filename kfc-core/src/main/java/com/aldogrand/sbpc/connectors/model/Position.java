package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.aldogrand.sbpc.model.Currency;

/**
 * <p>
 * <b>Title</b> Position
 * </p>
 * <p>
 * <b>Description</b> 
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Ken Barry
 * @version 1.0
 */
public class Position implements Serializable 
{
	private static final long serialVersionUID = -1921590135081812245L;
	
    private String bettingPlatformName;
    private String eventId;
    private String marketId;
    private String runnerId;
    private Currency currency;
    private BigDecimal value;

    /**
     * @return the bettingPlatformName
     */
    public String getBettingPlatformName()
    {
        return bettingPlatformName;
    }

    /**
     * @param bettingPlatformName the bettingPlatformName to set
     */
    public void setBettingPlatformName(String bettingPlatformName)
    {
        this.bettingPlatformName = bettingPlatformName;
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
     * @return the marketId
     */
    public String getMarketId()
    {
        return marketId;
    }

    /**
     * @param marketId the marketId to set
     */
    public void setMarketId(String marketId)
    {
        this.marketId = marketId;
    }

    /**
     * @return the runnerId
     */
    public String getRunnerId()
    {
        return runnerId;
    }

    /**
     * @param runnerId the runnerId to set
     */
    public void setRunnerId(String runnerId)
    {
        this.runnerId = runnerId;
    }

    /**
     * @return the currency
     */
    public Currency getCurrency()
    {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency)
    {
        this.currency = currency;
    }

    /**
     * @return the value
     */
    public BigDecimal getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value)
    {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Position [currency=");
        builder.append(currency);
        builder.append(", value=");
        builder.append(value);
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
        result = prime * result
                + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
        result = prime * result
                + ((marketId == null) ? 0 : marketId.hashCode());
        result = prime * result
                + ((runnerId == null) ? 0 : runnerId.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Position other = (Position) obj;
        if (currency != other.currency)
        {
            return false;
        }
        if (eventId == null)
        {
            if (other.eventId != null)
            {
                return false;
            }
        }
        else if (!eventId.equals(other.eventId))
        {
            return false;
        }
        if (marketId == null)
        {
            if (other.marketId != null)
            {
                return false;
            }
        }
        else if (!marketId.equals(other.marketId))
        {
            return false;
        }
        if (runnerId == null)
        {
            if (other.runnerId != null)
            {
                return false;
            }
        }
        else if (!runnerId.equals(other.runnerId))
        {
            return false;
        }
        if (value == null)
        {
            if (other.value != null)
            {
                return false;
            }
        }
        else if (!value.equals(other.value))
        {
            return false;
        }
        return true;
    }
}
