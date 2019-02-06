package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.aldogrand.sbpc.model.Currency;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.kfc.utils.general.OddsType;


/**
 * <p>
 * <b>Title</b> Price
 * </p>
 * <p>
 * <b>Description</b> A price for a {@link Runner} on a betting platform.
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
public class Price implements Serializable
{
    private static final long serialVersionUID = 8171911895141334433L;

    private String id;
    private String eventId;
    private String marketId;
    private String runnerId;
    private PriceSide side;
    private BigDecimal odds;
    private OddsType oddsType;
    private BigDecimal decimalOdds;
    private BigDecimal availableAmount;
    private Currency currency;
    private String bettingPlatformName;
    private int homeScore;
    private int awayScore;

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
     * @return the availableAmount
     */
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
     * @return the bettingPlatformId
     */
    public String getBettingPlatformName()
    {
        return bettingPlatformName;
    }

    /**
     * @param bettingPlatformId the bettingPlatformId to set
     */
    public void setBettingPlatformName(String bettingPlatformName)
    {
        this.bettingPlatformName = bettingPlatformName;
    }
    
    /**
     * @return the homeScore
     */
    public Integer getHomeScore()
    {
        return this.homeScore;
    }

    /**
     * @param homeScore the homeScore to set
     */
    public void setHomeScore(Integer homeScore)
    {
        this.homeScore = homeScore;
    }

    /**
     * @return the awayScore
     */
    public Integer getAwayScore()
    {
        return this.awayScore;
    }

    /**
     * @param awayScore the awayScore to set
     */
    public void setAwayScore(Integer awayScore)
    {
        this.awayScore = awayScore;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Price [side=");
        builder.append(side);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", currency=");
        builder.append(currency);
        builder.append(", availableAmount=");
        builder.append(availableAmount);
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
                + ((availableAmount == null) ? 0 : availableAmount.hashCode());
        result = prime
                * result
                + ((bettingPlatformName == null) ? 0 : bettingPlatformName
                        .hashCode());
        result = prime * result
                + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result
                + ((decimalOdds == null) ? 0 : decimalOdds.hashCode());
        result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((marketId == null) ? 0 : marketId.hashCode());
        result = prime * result + ((odds == null) ? 0 : odds.hashCode());
        result = prime * result
                + ((oddsType == null) ? 0 : oddsType.hashCode());
        result = prime * result
                + ((runnerId == null) ? 0 : runnerId.hashCode());
        result = prime * result + ((side == null) ? 0 : side.hashCode());
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
        if (availableAmount == null)
        {
            if (other.availableAmount != null)
            {
                return false;
            }
        }
        else if (!availableAmount.equals(other.availableAmount))
        {
            return false;
        }
        if (bettingPlatformName == null)
        {
            if (other.bettingPlatformName != null)
            {
                return false;
            }
        }
        else if (!bettingPlatformName.equals(other.bettingPlatformName))
        {
            return false;
        }
        if (currency != other.currency)
        {
            return false;
        }
        if (decimalOdds == null)
        {
            if (other.decimalOdds != null)
            {
                return false;
            }
        }
        else if (!decimalOdds.equals(other.decimalOdds))
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
        if (odds == null)
        {
            if (other.odds != null)
            {
                return false;
            }
        }
        else if (!odds.equals(other.odds))
        {
            return false;
        }
        if (oddsType != other.oddsType)
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
        if (side != other.side)
        {
            return false;
        }
        return true;
    }
}
