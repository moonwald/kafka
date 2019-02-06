package com.aldogrand.sbpc.dataaccess.model;

import com.aldogrand.sbpc.model.ResultStatus;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>Title</b> RunnerDto
 * </p>
 * <p>
 * <b>Description</b> A runner in a {@link MarketDto}.
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
@Entity(name = "Runner")
@Table(name = "runners")
@Cacheable(false)
public class RunnerDto extends AbstractDto
{
    private static final long serialVersionUID = 6748973384179521108L;

    private MarketDto market;
    private ParticipantDto participant;
    private String name;
    private RunnerType type;
    private RunnerSide side;
    private Integer rotationNumber;
    private Double handicap;
    private Integer sequence;
    private RunnerStatus runnerStatus;
	private ResultStatus resultStatus;
    private List<SourceRunnerDto> mappings = new ArrayList<SourceRunnerDto>();
    private List<PriceDto> prices = new ArrayList<PriceDto>();
    private List<GFeedMidPointDto> gfeedMidPoints = new ArrayList<GFeedMidPointDto>();
    private Date lastChangeTime = new Date();
    
    /**
     * @return the market
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "market", nullable = false)
    public MarketDto getMarket()
    {
        return market;
    }

    /**
     * @param market the market to set
     */
    public void setMarket(MarketDto market)
    {
        this.market = market;
    }

    /**
     * @return the participant
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "participant", nullable = true)
    public ParticipantDto getParticipant()
    {
        return participant;
    }

    /**
     * @param participant the participant to set
     */
    public void setParticipant(ParticipantDto participant)
    {
        this.participant = participant;
    }

    /**
     * @return the name
     */
    @Column(name = "name", length = 255, nullable = false)
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the type
     */
    @Column(name = "type", length = 11, nullable = false)
    @Enumerated(EnumType.STRING)
    public RunnerType getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(RunnerType type)
    {
        this.type = type;
    }

    /**
     * @return the side
     */
    @Column(name = "side", length = 7, nullable = false)
    @Enumerated(EnumType.STRING)
    public RunnerSide getSide()
    {
        return side;
    }

    /**
     * @param side the side to set
     */
    public void setSide(RunnerSide side)
    {
        this.side = side;
    }

    /**
     * @return the rotationNumber
     */
    @Column(name = "rotation_number")
    public Integer getRotationNumber()
    {
        return rotationNumber;
    }

    /**
     * @param rotationNumber the rotationNumber to set
     */
    public void setRotationNumber(Integer rotationNumber)
    {
        this.rotationNumber = rotationNumber;
    }

    /**
     * @return the handicap
     */
    @Column(name = "handicap", precision = 5, scale = 2)
    public Double getHandicap()
    {
        return handicap;
    }

    /**
     * @param handicap the handicap to set
     */
    public void setHandicap(Double handicap)
    {
        this.handicap = handicap;
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
     * 
     * @return the runnerStatus
     */
    @Column(name = "runner_status", length = 20, nullable = true)
    @Enumerated(EnumType.STRING)
    public RunnerStatus getRunnerStatus() {
		return runnerStatus == null? RunnerStatus.UNKNOWN : runnerStatus;
	}

    /**
     * 
     * @param runnerStatus the runnerStatus to set
     */
	public void setRunnerStatus(RunnerStatus runnerStatus) {
		this.runnerStatus = runnerStatus;
	}

	/**
	 * 
	 * @return the result status
	 */
	@Column(name = "result_status", length = 20, nullable = true)
    @Enumerated(EnumType.STRING)
	public ResultStatus getResultStatus()
	{
		return resultStatus == null ? ResultStatus.UNKNOWN : resultStatus;
	}

	/**
	 * 
	 * @param resultStatus the resultStatus to set
	 */
	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}

    /**
     * @return the mappings
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "runner")
    public List<SourceRunnerDto> getMappings()
    {
        return mappings;
    }

    /**
     * @param mappings the mappings to set
     */
    public void setMappings(List<SourceRunnerDto> mappings)
    {
        this.mappings = mappings;
    }

    /**
     * @return the prices
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "runner")
    @OrderBy("bettingPlatform ASC, side ASC, sequence ASC")
    public List<PriceDto> getPrices()
    {
        return prices;
    }

    /**
     * @param prices the prices to set
     */
    public void setPrices(List<PriceDto> prices)
    {
        this.prices = prices;
    }
    
    /**
     * @return the gfeedMidPoints
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "runner")
    @OrderBy("bettingPlatform ASC")
    public List<GFeedMidPointDto> getGfeedMidPoints()
    {
        return gfeedMidPoints;
    }

    /**
     * @param gfeedMidPoints the gfeedMidPoints to set
     */
    public void setGfeedMidPoints(List<GFeedMidPointDto> gfeedMidPoints)
    {
        this.gfeedMidPoints = gfeedMidPoints;
    }

    /**
     * @return the lastChangeTime
     */
    @Column(name = "last_change_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastChangeTime()
    {
        return lastChangeTime;
    }

    /**
     * @param lastChangeTime the lastChangeTime to set
     */
    public void setLastChangeTime(Date lastChangeTime)
    {
        this.lastChangeTime = lastChangeTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RunnerDto [name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
