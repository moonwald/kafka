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

import com.aldogrand.kfc.utils.general.OddsType;

/**
 * <p>
 * <b>Title</b> GFeedMidPointDto
 * </p>
 * <p>
 * <b>Description</b> A manually entered mid point used to calculate prices by the
 *  implementation that corresponds to the {@link BettingPlatformDto}.
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
@Entity(name = "GFeedMidPoint")
@Table(name = "gfeed_mid_points", uniqueConstraints
        = @UniqueConstraint(columnNames = {"betting_platform", "runner"}))
@Cacheable(false)
public class GFeedMidPointDto extends AbstractDto
{
    private static final long serialVersionUID = -6881888934721426417L;

    private BettingPlatformDto bettingPlatform;
    private RunnerDto runner;
    private BigDecimal odds;
    private OddsType oddsType;
    private Boolean enabled;

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
     * @return the enabled
     */
    @Column(name = "enabled", nullable = false)
    public Boolean isEnabled()
    {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GFeedMidPointDto [odds=");
        builder.append(odds);
        builder.append(", oddsType=");
        builder.append(oddsType);
        builder.append("]");
        return builder.toString();
    }
}
