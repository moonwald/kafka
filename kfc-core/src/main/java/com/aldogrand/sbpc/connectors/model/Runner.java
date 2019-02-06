package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.util.List;

import com.aldogrand.sbpc.model.ResultStatus;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;

/**
 * <p>
 * <b>Title</b> Runner
 * </p>
 * <p>
 * <b>Description</b> A runner in a {@link Market} that can be bet on.
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
public class Runner implements Serializable
{
    private static final long serialVersionUID = -3823517232750617494L;

    private String id;
    private String eventId;
    private String marketId;
    private String name;
    private RunnerType type;
    private Integer sequence;
    private RunnerSide side;
    private Integer rotationNumber;
    private Double handicap;
    private RunnerStatus runnerStatus;
	private ResultStatus resultStatus;
    private List<Price> prices;
	private long sourceParticipantId;

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
     * @return the name
     */
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
     * 
     * @return the runnerStatus
     */
    public RunnerStatus getRunnerStatus() {
		return runnerStatus;
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

    /**
     * @return the prices
     */
    public List<Price> getPrices()
    {
        return prices;
    }

    /**
     * @param prices the prices to set
     */
    public void setPrices(List<Price> prices)
    {
        this.prices = prices;
    }
    
    /**
     * Retrieves the sourceParticipantId
     * 
     * @return
     */
    public long getSourceParticipantId() {
    	return this.sourceParticipantId;
    }
    
    /**
     * Sets the sourceParticipantId
     * 
     * @param sourceParticipantId
     */
    public void setSourceParticipantId(long sourceParticipantId) {
    	this.sourceParticipantId = sourceParticipantId;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Runner [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
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
        Runner other = (Runner) obj;
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
