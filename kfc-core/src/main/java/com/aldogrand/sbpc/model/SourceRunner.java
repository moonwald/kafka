package com.aldogrand.sbpc.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;



/**
 * <p>
 * <b>Title</b> SourceRunner.java
 * </p>
 * <p>
 * <b>Description</b> kfc-core.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author aldogrand
 * @version 1.0
 */
public class SourceRunner
{
    private static final long serialVersionUID = 6836746323150684634L;

    private Long id;
    private Long version;
    private Long runnerId;
    private Long connectorId;
    private Long sourceMarketId;
    private Long sourceEventId;
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

    /**
     * @return the runner
     */
    public Long getRunnerId()
    {
        return runnerId;
    }

    /**
     * @param runner the runner to set
     */
    public void setRunnerId(Long runner)
    {
        this.runnerId = runner;
    }

    /**
     * @return the connector
     */
    public Long getConnectorId()
    {
        return connectorId;
    }

    /**
     * @param connector the connector to set
     */
    public void setConnectorId(Long connector)
    {
        this.connectorId = connector;
    }

    /**
     * @return the sourceMarket
     */
    public Long getSourceMarketId()
    {
        return sourceMarketId;
    }

    /**
     * @param sourceMarket the sourceMarket to set
     */
    public void setSourceMarketId(Long sourceMarket)
    {
        this.sourceMarketId = sourceMarket;
    }

    /**
     * @return the sourceId
     */
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
    public RunnerStatus getRunnerStatus() {
		return runnerStatus;
	}

    /**
     * 
     * @param tradingStatus the tradingStatus to set
     */
	public void setRunnerStatus(RunnerStatus runnerStatus) {
		this.runnerStatus = runnerStatus;
	}

	/**
	 * 
	 * @return the result status
	 */
	public ResultStatus getResultStatus() {
		return resultStatus;
	}

	/**
	 * 
	 * @param resultStatus the resultStatus to set
	 */
	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getSourceEventId() {
		return sourceEventId;
	}

	public void setSourceEventId(Long sourceEventId) {
		this.sourceEventId = sourceEventId;
	}
}
