package com.aldogrand.sbpc.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.aldogrand.sbpc.model.Currency;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.kfc.utils.general.OddsType;

/**
 * <p>
 * <b>Title</b> PriceDto
 * </p>
 * <p>
 * <b>Description</b> A Betting Platform specific price.
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
@Entity(name = "Price")
@Table(name = "prices", 
    uniqueConstraints = @UniqueConstraint(columnNames = {"runner", "betting_platform", "side", "sequence"}))
@Cacheable(false)
public class PriceDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = -7538287779233944525L;

    private RunnerDto runner;
    private BettingPlatformDto bettingPlatform;
    private PriceSide side;
    private Integer sequence;
    private BigDecimal odds;
    private OddsType oddsType;
    private BigDecimal decimalOdds;
    private BigDecimal availableAmount;
    private Currency currency;
    
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
     * @return the bettingPlatform
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "betting_platform", nullable = false)
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
     * @return the sequence
     */
    @Column(name = "sequence", nullable = false)
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
        return oddsType == null ? OddsType.UNKNOWN : oddsType;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PriceDto [bettingPlatform=");
        builder.append(bettingPlatform);
        builder.append(", side=");
        builder.append(side);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append(", odds=");
        builder.append(odds);
        builder.append(", decimalOdds=");
        builder.append(decimalOdds);
        builder.append(", currency=");
        builder.append(currency);
        builder.append(", availableAmount=");
        builder.append(availableAmount);
        builder.append("]");
        return builder.toString();
    }
}
