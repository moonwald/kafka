package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> Position
 * </p>
 * <p>
 * <b>Description</b> The current potential profit for an account on
 * a betting platform if a particular runner should win.
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
@XmlType(propOrder = {"id", "eventId", "marketId", "runnerId", "accountId", 
        "connectorId", "bettingPlatformId", "currency", "value", "lastFetched", "lastChanged"})
public class Position implements Serializable
{
    private static final long serialVersionUID = -3517788947766037078L;

    private Long id;
    private Long eventId;
    private Long marketId;
    private Long runnerId;
    private Long accountId;
    private Long connectorId;
    private Long bettingPlatformId;
    private Currency currency;
    private BigDecimal value;
    private Date lastFetched;
    private Date lastChanged;

    /**
     * @return the id
     */
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
     * @return the marketId
     */
    @XmlElement(name = "market-id")
    public Long getMarketId()
    {
        return marketId;
    }

    /**
     * @param marketId the marketId to set
     */
    public void setMarketId(Long marketId)
    {
        this.marketId = marketId;
    }

    /**
     * @return the runnerId
     */
    @XmlElement(name = "runner-id")
    public Long getRunnerId()
    {
        return runnerId;
    }

    /**
     * @param runnerId the runnerId to set
     */
    public void setRunnerId(Long runnerId)
    {
        this.runnerId = runnerId;
    }

    /**
     * @return the accountId
     */
    @XmlElement(name = "account-id")
    public Long getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    /**
     * @return the connectorId
     */
    @XmlElement(name = "connector-id")
    public Long getConnectorId()
    {
        return connectorId;
    }

    /**
     * @param connectorId the connectorId to set
     */
    public void setConnectorId(Long connectorId)
    {
        this.connectorId = connectorId;
    }

    /**
     * @return the bettingPlatformId
     */
    @XmlElement(name = "betting-platform-id")
    public Long getBettingPlatformId()
    {
        return bettingPlatformId;
    }

    /**
     * @param bettingPlatformId the bettingPlatformId to set
     */
    public void setBettingPlatformId(Long bettingPlatformId)
    {
        this.bettingPlatformId = bettingPlatformId;
    }

    /**
     * @return the currency
     */
    @XmlElement(name = "currency")
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
    @XmlElement(name = "value")
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

    /**
     * @return the lastFetched
     */
    @XmlElement(name = "last-fetched")
    public Date getLastFetched()
    {
        return lastFetched;
    }

    /**
     * @param lastFetched the lastFetched to set
     */
    public void setLastFetched(Date lastFetched)
    {
        this.lastFetched = lastFetched;
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
        Position other = (Position) obj;
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
