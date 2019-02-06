package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.aldogrand.kfc.utils.general.OddsType;


/**
 * <p>
 * <b>Title</b> Offer
 * </p>
 * <p>
 * <b>Description</b> Represents an offer that has been placed on one of the betting platforms
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
@XmlRootElement
@XmlType(propOrder = {"id", "connectorId", "accountId", 
                "eventId", "marketId", "runnerId", "sourceId", "tempId", "odds", "oddsType", "offerType",
                "decimalOdds", "stake", "matchedAmount", "availableAmount", "currency", 
                "exchangeType", "side", "offerTime", "status", "score", "lastFetched", "lastChanged", 
                "bets", "errors"})
public class Offer implements Serializable
{
	private static final long serialVersionUID = -18727835193376920L;

	private Long id;
	private Long connectorId;
	private Long accountId;
	private Long eventId;
	private Long marketId;
	private Long runnerId;
	private String sourceId;
	private String tempId;
	private BigDecimal odds;
    private OddsType oddsType;
    private OfferType offerType;
    private BigDecimal decimalOdds;
    private BigDecimal stake;
    private BigDecimal matchedAmount;
    private BigDecimal availableAmount;
    private Currency currency;
    private ExchangeType exchangeType;
    private PriceSide side;
    private Date offerTime;
    private OfferStatus status;
    private Score score;
    private Date lastFetched;
    private Date lastChanged;
    private List<Bet> bets;
    private List<Error> errors;

    /**
     * Default constructor.
     */
    public Offer() {
    	super();
    }

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
     * @return the sourceId
     */
    @XmlElement(name = "source-id")
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
     * A temporary id that is optionally used when submitting offers.
     * @return the tempId
     */
    @XmlElement(name = "temp-id")
    public String getTempId()
    {
        return tempId;
    }

    /**
     * @param tempId the tempId to set
     */
    public void setTempId(String tempId)
    {
        this.tempId = tempId;
    }

    /**
     * @return the odds
     */
    @XmlElement(name = "odds")
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
	 * @return the offerType
	 */
    @XmlElement(name = "offer-type")
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
     * @return the stake
     */
    @XmlElement(name = "stake")
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
    @XmlElement(name = "matched-amount")
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
    @XmlElement(name = "side")
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
    @XmlElement(name = "offer-time")
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
    @XmlElement(name = "status")
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
    @XmlElement(name = "score")
    @JsonSerialize(include=Inclusion.NON_NULL)
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

    /**
     * @return the bets
     */
    @XmlElementWrapper(name = "bets")
    @XmlElement(name = "bet")
    public List<Bet> getBets()
    {
        return bets;
    }

    /**
     * @param bets the bets to set
     */
    public void setBets(List<Bet> bets)
    {
        this.bets = bets;
    }

    /**
     * A list of errors populated if there is a problem submitting the offer.
     * @return the errors
     */
    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error")
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
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", stake=");
        builder.append(stake);
        builder.append(", matchedAmount=");
        builder.append(matchedAmount);
        builder.append(", availableAmount=");
        builder.append(availableAmount);
        builder.append(", currency=");
        builder.append(currency);
        builder.append(", side=");
        builder.append(side);
        builder.append(", status=");
        builder.append(status);
        builder.append(", score=");
        builder.append(score);
        builder.append(", accountId=");
        builder.append(accountId);
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
