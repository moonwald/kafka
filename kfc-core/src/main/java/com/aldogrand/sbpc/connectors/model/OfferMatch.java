package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.aldogrand.sbpc.model.Currency;
import com.aldogrand.kfc.utils.general.OddsType;

/**
 * <p>
 * <b>Title</b> OfferMatch
 * </p>
 * <p>
 * <b>Description</b> A match made between two {@link Offer}s detailing the odds, 
 * the stake and the time matched at.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Ken Barry
 * @version 1.0
 */
public class OfferMatch implements Serializable
{
	private static final long serialVersionUID = -1771091333900668967L;
	
	private String id;
	private String bettingPlatformName;
    private BigDecimal odds;
    private OddsType oddsType;
    private BigDecimal decimalOdds;
	private BigDecimal stake;
	private Currency currency;
	private Date matchTime;	

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
     * @return the stake
     */
    public BigDecimal getStake()
    {
        return stake;
    }

    /**
     * @param stake the stake to set
     */
    public void setStake(BigDecimal stake)
    {
        this.stake = stake;
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
     * @return the matchTime
     */
    public Date getMatchTime()
    {
        return matchTime;
    }

    /**
     * @param matchTime the matchTime to set
     */
    public void setMatchTime(Date matchTime)
    {
        this.matchTime = matchTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("OfferMatch [id=");
        builder.append(id);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", stake=");
        builder.append(stake);
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
        OfferMatch other = (OfferMatch) obj;
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
