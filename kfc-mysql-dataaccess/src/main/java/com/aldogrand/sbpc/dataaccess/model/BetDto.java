package com.aldogrand.sbpc.dataaccess.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aldogrand.sbpc.model.Currency;
import com.aldogrand.kfc.utils.general.OddsType;

/**
 * <p>
 * <b>Title</b> BetDto
 * </p>
 * <p>
 * <b>Description</b> A matched bet as part of an offer.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
@Entity(name = "Bet")
@Table(name = "bets")
@Cacheable(false)
public class BetDto extends AbstractDto
{
    private static final long serialVersionUID = 1991897322464902563L;

    private OfferDto offer;
    private BettingPlatformDto bettingPlatform;
    private String sourceId;
    private BigDecimal odds;
    private OddsType oddsType;
    private BigDecimal decimalOdds;
    private BigDecimal stake;
    private Currency currency;
    private Date creationTime;

    /**
     * @return the offer
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offer", nullable = false)
    public OfferDto getOffer()
    {
        return offer;
    }

    /**
     * @param offer the offer to set
     */
    public void setOffer(OfferDto offer)
    {
        this.offer = offer;
    }

    /**
     * @return the bettingPlatform
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "betting_platform")
    public BettingPlatformDto getBettingPlatform()
    {
        return bettingPlatform;
    }

    /**
     * @param bettingPlatform the bettingPlatform to set
     */
    public void setBettingPlatform(BettingPlatformDto bettingPlatform)
    {
        this.bettingPlatform = bettingPlatform;
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
     * @return the creationTime
     */
    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationTime()
    {
        return creationTime;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(Date creationTime)
    {
        this.creationTime = creationTime;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BetDto [odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", stake=");
        builder.append(stake);
        builder.append("]");
        return builder.toString();
    }
}
