package com.aldogrand.sbpc.dataaccess.model;

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

import com.aldogrand.sbpc.model.ResultStatus;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;

/**
 * <p>
 * <b>Title</b> SourceRunnerDto
 * </p>
 * <p>
 * <b>Description</b> A runner from a {@link ConnectorDto} that can be mapped to
 * a {@link RunnerDto}.
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
@Entity(name = "SourceRunner")
@Table(name = "source_runners", 
        uniqueConstraints = {@UniqueConstraint(columnNames = {"connector", "source_id"}),
            @UniqueConstraint(columnNames = {"connector", "runner"})})
@Cacheable(false)
public class SourceRunnerDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = 6836746323150684634L;

    private RunnerDto runner;
    private ConnectorDto connector;
    private SourceMarketDto sourceMarket;
    private String sourceId;
    private String sourceName;
    private boolean creator;
    private RunnerType type;
    private RunnerSide side;
    private Integer rotationNumber;
    private Double handicap;
    private Integer sequence;
    private RunnerStatus runnerStatus;
   	private ResultStatus resultStatus;

//	private long sourceParticipantId;
    
    /**
     * @return the runner
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "runner", nullable = true)
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
     * @return the sourceMarket
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_market", nullable = false)
    public SourceMarketDto getSourceMarket()
    {
        return sourceMarket;
    }

    /**
     * @param sourceMarket the sourceMarket to set
     */
    public void setSourceMarket(SourceMarketDto sourceMarket)
    {
        this.sourceMarket = sourceMarket;
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
     * @return the sourceName
     */
    @Column(name = "source_name", nullable = false)
    public String getSourceName()
    {
        return sourceName;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }

    /**
     * @return the creator
     */
    @Column(name = "creator", nullable = false)
    public boolean isCreator()
    {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(boolean creator)
    {
        this.creator = creator;
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
     * @return the tradingStatus
     */
    @Column(name = "runner_status", length = 20, nullable = true)
    @Enumerated(EnumType.STRING)
    public RunnerStatus getRunnerStatus() 
    {
		return runnerStatus == null ? RunnerStatus.UNKNOWN : runnerStatus;
	}

    /**
     * 
     * @param tradingStatus the tradingStatus to set
     */
	public void setRunnerStatus(RunnerStatus runnerStatus) 
	{
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
	
//	/**
//	 * Retrieves the sourceParticipantId
//	 * 
//	 * @return
//	 */
//	@Column(name = "source_participant_id", nullable = false)
//	public long getSourceParticipantId() {
//		return this.sourceParticipantId;
//	}
//	
//	/**
//	 * Sets the sourceParticipantId
//	 * 
//	 * @param sourceParticipantId
//	 */
//	public void setSourceParticipantId(long sourceParticipantId) {
//		this.sourceParticipantId = sourceParticipantId;
//	}


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SourceRunnerDto [connector=");
        builder.append(connector);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", sourceName=");
        builder.append(sourceName);
        builder.append(", type=");
        builder.append(type);
        builder.append(", side=");
        builder.append(side);
        builder.append(", rotationNumber=");
        builder.append(rotationNumber);
        builder.append(", handicap=");
        builder.append(handicap);
        builder.append(", sequence=");
        builder.append(sequence);
        builder.append("]");
        return builder.toString();
    }

}
