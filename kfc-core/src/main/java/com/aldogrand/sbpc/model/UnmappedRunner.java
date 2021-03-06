package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> UnmappedRunner
 * </p>
 * <p>
 * <b>Description</b> An unmapped Runner.
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
@XmlRootElement(name = "unmapped-runner")
@XmlType(propOrder = {"id", "eventId", "marketId", "connectorId", "sourceId", "name",  
        "type", "side", "sequence", "rotationNumber", "handicap", "lastFetched", 
        "lastChanged"})
public class UnmappedRunner implements Serializable
{
    private static final long serialVersionUID = -1969676551792146747L;

    private Long id;
    private Long eventId;
    private Long marketId;
    private Long connectorId;
    private String sourceId;
    private String name;
    private RunnerType type;
    private RunnerSide side;
    private Integer sequence;
    private Integer rotationNumber;
    private Double handicap;
    private Date lastFetched;
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
     * @return the connectorId
     */
    @XmlElement(name = "connector-id")
    public Long getConnectorId()
    {
        return connectorId;
    }

    /**
     * @param connectorId the connectorId to set
     */
    public void setConnectorId(Long connectorId)
    {
        this.connectorId = connectorId;
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
     * @return the name
     */
    @XmlElement(name = "name")
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
    @XmlElement(name = "type")
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
    @XmlElement(name = "side")
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
    @XmlElement(name = "sequence")
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
    @XmlElement(name = "handicap")
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
     * @return the lastFetched
     */
    @XmlElement(name = "last-fetched")
    public Date getLastFetched()
    {
        return lastFetched;
    }

    /**
     * @param lastFetched the lastFetched to set
     */
    public void setLastFetched(Date lastFetched)
    {
        this.lastFetched = lastFetched;
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
        builder.append("UnmappedRunner [id=");
        builder.append(id);
        builder.append(", eventId=");
        builder.append(eventId);
        builder.append(", marketId=");
        builder.append(marketId);
        builder.append(", connectorId=");
        builder.append(connectorId);
        builder.append(", sourceId=");
        builder.append(sourceId);
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
        UnmappedRunner other = (UnmappedRunner) obj;
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
