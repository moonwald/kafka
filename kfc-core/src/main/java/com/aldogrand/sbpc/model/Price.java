package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.aldogrand.kfc.utils.general.OddsType;



/**
 * <p>
 * <b>Title</b> Price
 * </p>
 * <p>
 * <b>Description</b> A price for a runner on a betting platform. 
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
@XmlType(propOrder = {"id", "eventId", "marketId", "runnerId", "connectorId", 
        "bettingPlatformId", "exchangeType", "side", "sequence", "odds", "oddsType", 
        "decimalOdds", "availableAmount", "currency", "lastFetched", "lastChanged"})
public class Price implements Serializable, Comparable<Price>
{
    private static final long serialVersionUID = -3755954724001931354L;
    
    private Long id;
    private Long eventId;
    private Long marketId;
    private Long runnerId;
    private Long connectorId;
    private Long bettingPlatformId;
    private ExchangeType exchangeType;
    private PriceSide side;
    private Integer sequence;
    private BigDecimal odds;
    private OddsType oddsType;
    private BigDecimal decimalOdds;
    private BigDecimal availableAmount;
    private Currency currency;
    private Date lastFetched;
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
     * @return the exchangeType
     */
    @XmlElement(name = "exchange-type")
    public ExchangeType getExchangeType()
    {
        return exchangeType;
    }

    /**
     * @param exchangeType the exchangeType to set
     */
    public void setExchangeType(ExchangeType exchangeType)
    {
        this.exchangeType = exchangeType;
    }

    /**
     * @return the side
     */
    public PriceSide getSide()
    {
        return side;
    }

    /**
     * @param side the side to set
     */
    public void setSide(PriceSide side)
    {
        this.side = side;
    }

    /**
     * @return the sequence
     */
    public Integer getSequence()
    {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(Integer sequence)
    {
        this.sequence = sequence;
    }

    /**
     * @return the odds
     */
    public BigDecimal getOdds()
    {
        return odds;
    }

    /**
     * @param odds the odds to set
     */
    public void setOdds(BigDecimal odds)
    {
        this.odds = odds;
    }

    /**
     * @return the oddsType
     */
    @XmlElement(name = "odds-type")
    public OddsType getOddsType()
    {
        return oddsType;
    }

    /**
     * @param oddsType the oddsType to set
     */
    public void setOddsType(OddsType oddsType)
    {
        this.oddsType = oddsType;
    }

    /**
     * @return the decimalOdds
     */
    @XmlElement(name = "decimal-odds")
    public BigDecimal getDecimalOdds()
    {
        return decimalOdds;
    }

    /**
     * @param decimalOdds the decimalOdds to set
     */
    public void setDecimalOdds(BigDecimal decimalOdds)
    {
        this.decimalOdds = decimalOdds;
    }

    /**
     * @return the availableAmount
     */
    @XmlElement(name = "available-amount")
    public BigDecimal getAvailableAmount()
    {
        return availableAmount;
    }

    /**
     * @param availableAmount the availableAmount to set
     */
    public void setAvailableAmount(BigDecimal availableAmount)
    {
        this.availableAmount = availableAmount;
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
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Price o)
    {
        if (o == null)
        {
            throw new NullPointerException("Price cannot be null.");
        }
        
        if ((side.equals(PriceSide.BACK)) && (o.getSide().equals(PriceSide.BACK)))
        {
            return decimalOdds.compareTo(o.getDecimalOdds());
        }
        else if ((side.equals(PriceSide.BACK)) && (o.getSide().equals(PriceSide.LAY)))
        {
            return -1;
        }
        else if ((side.equals(PriceSide.LAY)) && (o.getSide().equals(PriceSide.BACK)))
        {
            return 1;
        }
        else // Both sides == LAY
        {
            return o.getDecimalOdds().compareTo(decimalOdds);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Price [id=");
        builder.append(id);
        builder.append(", side=");
        builder.append(side);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", decimalOdds=");
        builder.append(decimalOdds);
        builder.append(", availableAmount=");
        builder.append(availableAmount);
        builder.append(", currency=");
        builder.append(currency);
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
        Price other = (Price) obj;
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
