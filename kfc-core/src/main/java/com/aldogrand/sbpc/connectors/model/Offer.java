package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.model.Currency;
import com.aldogrand.sbpc.model.Error;
import com.aldogrand.sbpc.model.OfferStatus;
import com.aldogrand.sbpc.model.OfferType;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.kfc.utils.general.OddsType;

/**
 * <p>
 * <b>Title</b> Offer
 * </p>
 * <p>
 * <b>Description</b> Represents an offer which has been placed / queried using a connector
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
public class Offer implements Serializable
{
    private static final long serialVersionUID = -18727835193376920L;

    private String id;
    private String eventId;
    private String marketId;
    private String runnerId;
    private BigDecimal odds;
    private OddsType oddsType;
    private OfferType offerType;
    private BigDecimal decimalOdds;
    private BigDecimal stake;
    private BigDecimal matchedAmount;
    private BigDecimal availableAmount;
    private Currency currency;
    private PriceSide side;
    private Date offerTime;
    private OfferStatus status;
    private Score score;
    private List<OfferMatch> matches = new ArrayList<OfferMatch>();
    private List<Error> errors;
    
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
	 * @return the offerType
	 */
	public OfferType getOfferType() {
		return offerType;
	}

	/**
	 * @param offerType the offerType to set
	 */
	public void setOfferType(OfferType offerType) {
		this.offerType = offerType;
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
     * @return the matchedAmount
     */
    public BigDecimal getMatchedAmount()
    {
        return matchedAmount;
    }

    /**
     * @param matchedAmount the matchedAmount to set
     */
    public void setMatchedAmount(BigDecimal matchedAmount)
    {
        this.matchedAmount = matchedAmount;
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
     * @return the offerTime
     */
    public Date getOfferTime()
    {
        return offerTime;
    }

    /**
     * @param offerTime the offerTime to set
     */
    public void setOfferTime(Date offerTime)
    {
        this.offerTime = offerTime;
    }

    /**
     * @return the status
     */
    public OfferStatus getStatus()
    {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(OfferStatus status)
    {
        this.status = status;
    }

    /**
	 * @return the score
	 */
	public Score getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Score score) {
		this.score = score;
	}

	/**
     * @return the matches
     */
    public List<OfferMatch> getMatches()
    {
        return matches;
    }

    /**
     * @param matches the matches to set
     */
    public void setMatches(List<OfferMatch> matches)
    {
        this.matches = matches;
    }

    /**
     * @return the errors
     */
    public List<Error> getErrors()
    {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<Error> errors)
    {
        this.errors = errors;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Offer [id=");
        builder.append(id);
        builder.append(", runnerId=");
        builder.append(runnerId);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", stake=");
        builder.append(stake);
        builder.append(", availableAmount=");
        builder.append(availableAmount);
        builder.append(", currency=");
        builder.append(currency);
        builder.append(", offerTime=");
        builder.append(offerTime);
        builder.append(", status=");
        builder.append(status);
        builder.append(", score=");
        builder.append(score);
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
        Offer other = (Offer) obj;
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
