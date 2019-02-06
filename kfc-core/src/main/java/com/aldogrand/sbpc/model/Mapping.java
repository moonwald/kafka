package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * <p>
 * <b>Title</b> Mapping
 * </p>
 * <p>
 * <b>Description</b> Info about the id of an {@link Event}, {@link Market} or 
 * {@link Runner} from a {@link Connector}.
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
@XmlRootElement(name = "mapping")
@XmlType(propOrder = {"id", "sourceId", "name", "connectorId", "connectorName", "lastFetched" ,"marketStatus", "score"})
public class Mapping implements Serializable
{
    private static final long serialVersionUID = -5411589658861612499L;

    private Long id;
    private String sourceId;
    private String name;
    private Long connectorId;
    private String connectorName;
    private Date lastFetched;
    private Score score;
    private MarketStatus marketStatus;   

    
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
     * @return the connectorName
     */
    @XmlElement(name = "connector-name")
    public String getConnectorName()
    {
        return connectorName;
    }

    /**
     * @param connectorName the connectorName to set
     */
    public void setConnectorName(String connectorName)
    {
        this.connectorName = connectorName;
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
     * @return the market status
     * */
    @XmlElement(name = "marketStatus")
    public MarketStatus getMarketStatus() {
		return marketStatus;
	}
    /**
     * @param market status of the source
     */
	public void setMarketStatus(MarketStatus marketStatus) {
		this.marketStatus = marketStatus;
	}


    /**
	 * @return the score
	 */
    @XmlElement(name = "score")
    @JsonSerialize(include=Inclusion.NON_NULL)
	public Score getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Score score) {
		this.score = score;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Mapping [id=");
        builder.append(id);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", name=");
        builder.append(name);
        builder.append(", connectorId=");
        builder.append(connectorId);
        builder.append(", connectorName=");
        builder.append(connectorName);
        builder.append(", lastFetched=");
        builder.append(lastFetched);
        builder.append(", score=");
        builder.append(score);
        builder.append(", marketStatus=");
        builder.append(marketStatus);
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
        Mapping other = (Mapping) obj;
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
