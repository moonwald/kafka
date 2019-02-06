package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> Runner
 * </p>
 * <p>
 * <b>Description</b> A runner in a market.
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
@XmlRootElement
@XmlType(propOrder = {"id", "name", "eventId", "eventName", "marketId", "marketName", 
        "type", "side", "sequence", "rotationNumber", "handicap", "runnerStatus", "resultStatus", "prices", 
        "mappings", "lastChanged"})
public class Runner implements Serializable
{
    private static final long serialVersionUID = -1107738887959122692L;

    private Long id;
    private String name;
    private Long eventId;
    private String eventName;
    private Long marketId;
    private String marketName;
    private RunnerType type;
    private RunnerSide side;
    private Integer sequence;
    private Integer rotationNumber;
    private Double handicap;
    private RunnerStatus runnerStatus;
    private ResultStatus resultStatus;
    private List<Price> prices;
    private List<Mapping> mappings;
    private Date lastChanged;
    
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
     * @return the eventName
     */
    @XmlElement(name = "event-name")
    public String getEventName()
    {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName)
    {
        this.eventName = eventName;
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
     * @return the marketName
     */
    @XmlElement(name = "market-name")
    public String getMarketName()
    {
        return marketName;
    }

    /**
     * @param marketName the marketName to set
     */
    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
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
     * @return the rotationNumber
     */
    @XmlElement(name = "rotation-number")
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
     * Represents the current status of the Runner
     * 
     * Statuses currently include
     * <p>
     * <ul>
     * <li>OPEN</li>
     * <li>CLOSED</li>
     * <li>UKNOWN</li>
     * </ul>
     * </p>
     * @return the runnerStatus
     */
    public RunnerStatus getRunnerStatus() {
		return runnerStatus;
	}

    /**
     * @param runnerStatus the runnerStatus to set
     */
	public void setRunnerStatus(RunnerStatus runnerStatus) {
		this.runnerStatus = runnerStatus;
	}

	/**
     * Represents the current result status of the Runner
     * 
     * Statuses currently include
     * <p>
     * <ul>
     * <li>WINNER</li>
     * <li>LOSER</li>
     * <li>PLACED</li>
     * <li>UKNOWN</li>
     * </ul>
     * </p>
     * @return the resultStatus
     */
	public ResultStatus getResultStatus() {
		return resultStatus;
	}

	/**
	 * @param resultStatus the resultStatus to set
	 */
	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}

	/**
     * @return the prices
     */
    @XmlElementWrapper(name = "prices")
    @XmlElement(name = "price")
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
     * @return the mappings
     */
    @XmlElementWrapper(name = "mappings")
    @XmlElement(name = "mapping")
    public List<Mapping> getMappings()
    {
        return mappings;
    }

    /**
     * @param mappings the mappings to set
     */
    public void setMappings(List<Mapping> mappings)
    {
        this.mappings = mappings;
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
