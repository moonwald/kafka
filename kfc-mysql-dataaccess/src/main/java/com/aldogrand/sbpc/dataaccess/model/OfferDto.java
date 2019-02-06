package com.aldogrand.sbpc.dataaccess.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.aldogrand.sbpc.model.Currency;
import com.aldogrand.sbpc.model.OfferStatus;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.kfc.utils.general.OddsType;

/**
 * <p>
 * <b>Title</b> OfferDto
 * </p>
 * <p>
 * <b>Description</b> An offer that has been placed on the Betting Platform. 
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
@Entity(name = "Offer")
@Table(name = "offers", uniqueConstraints 
        = @UniqueConstraint(columnNames = {"connector", "source_id"}))
@Cacheable(false)
public class OfferDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = -18727835193376920L;

    private RunnerDto runner;
    private AccountDto account;
    private ConnectorDto connector;
    private String sourceId;
    private BigDecimal odds;
    private OddsType oddsType;
    private BigDecimal decimalOdds;
    private BigDecimal stake;
    private BigDecimal matchedAmount;
    private BigDecimal availableAmount;
    private Currency currency;
    private PriceSide side;
    private Date offerTime;
    private OfferStatus status;
    private ScoreDto score;
    private List<BetDto> bets;

    /**
     * @return the runner
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "runner", nullable = false)
    public RunnerDto getRunner()
    {
        return runner;
    }

    /**
     * @param runner the runner to set
     */
    public void setRunner(RunnerDto runner)
    {
        this.runner = runner;
    }

    /**
     * @return the account
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account", nullable = false)
    public AccountDto getAccount()
    {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(AccountDto account)
    {
        this.account = account;
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
     * @return the odds
     */
    @Column(name = "odds", precision = 19, scale = 8, nullable = false)
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
    @Column(name = "odds_type", length = 11, nullable = false)
    @Enumerated(EnumType.STRING)
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
    @Column(name = "decimal_odds", precision = 19, scale = 8, nullable = false)
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
    @Column(name = "stake", precision = 19, scale = 8)
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
    @Column(name = "matched_amount", precision = 19, scale = 8)
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
    @Column(name = "available_amount", precision = 19, scale = 8)
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
    @Column(name = "currency", length = 3)
    @Enumerated(EnumType.STRING)
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
    @Column(name = "side", length = 4, nullable = false)
    @Enumerated(EnumType.STRING)
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
    @Column(name = "offer_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
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
    @Column(name = "status", length = 17, nullable = false)
    @Enumerated(EnumType.STRING)
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "score", nullable = true)
	public ScoreDto getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(ScoreDto score) {
		this.score = score;
	}

	/**
     * @return the bets
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<BetDto> getBets()
    {
        return bets;
    }

    /**
     * @param bets the bets to set
     */
    public void setBets(List<BetDto> bets)
    {
        this.bets = bets;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("OfferDto [sourceId=");
        builder.append(sourceId);
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
        builder.append(", status=");
        builder.append(status);
        builder.append("]");
        return builder.toString();
    }
}
