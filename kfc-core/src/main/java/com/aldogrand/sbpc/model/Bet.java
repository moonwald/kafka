package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.aldogrand.kfc.utils.general.OddsType;


/**
 * <p>
 * <b>Title</b> Bet
 * </p>
 * <p>
 * <b>Description</b> One or more bets form an {@link Offer}.
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
@XmlRootElement(name = "bet")
@XmlType(propOrder = {"id", "bettingPlatformId", "bettingPlatformName", "sourceId", "odds", 
        "oddsType", "decimalOdds", "stake", "currency", "creationTime"})
public class Bet implements Serializable
{
    private static final long serialVersionUID = 4596420804273255081L;

    private Long id;
    private Long bettingPlatformId;
    private String bettingPlatformName;
    private String sourceId;
    private BigDecimal odds;
    private OddsType oddsType;
    private BigDecimal decimalOdds;
    private BigDecimal stake;
    private Currency currency;
    private Date creationTime;
    /**
     * @return the id
     */
    @XmlElement(name = "id")
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
     * @return the bettingPlatformName
     */
    @XmlElement(name = "betting-platform-name")
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
     * @return the creationTime
     */
    @XmlElement(name = "creation-time")
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
        builder.append("Bet [id=");
        builder.append(id);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", stake=");
        builder.append(stake);
        builder.append(", currency=");
        builder.append(currency);
        builder.append("]");
        return builder.toString();
    }
}
