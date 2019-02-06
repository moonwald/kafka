package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * <p>
 * <b>Title</b> Event
 * </p>
 * <p>
 * <b>Description</b> An event.
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
@XmlRootElement(name = "event")
@XmlType(propOrder = {"id", "name", "startTime", "eventStatus", "metaTags", "markets", "mappings",
        "score", "lastChanged"})
public class Event implements Serializable
{
    private static final long serialVersionUID = 313214159611009393L;

    private Long id;
    private String name;
    private Date startTime;
    private List<MetaTag> metaTags;
    private List<Market> markets;
    private List<Mapping> mappings;
    private EventStatus eventStatus;
    private Score score;
    private Date lastChanged;

    /**
     * Default constructor.
     */
    public Event() {
    	super();
    }

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
     * @return the startTime
     */
    @XmlElement(name = "start-time")
    public Date getStartTime()
    {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    /**
     * @return the metaTags
     */
    @XmlElementWrapper(name = "meta-tags")
    @XmlElement(name = "meta-tag")
    public List<MetaTag> getMetaTags()
    {
        return metaTags;
    }

    /**
     * @param metaTags the metaTags to set
     */
    public void setMetaTags(List<MetaTag> metaTags)
    {
        this.metaTags = metaTags;
    }

    /**
     * @return the markets
     */
    @XmlElementWrapper(name = "markets")
    @XmlElement(name = "market")
    public List<Market> getMarkets()
    {
        return markets;
    }

    /**
     * @param markets the markets to set
     */
    public void setMarkets(List<Market> markets)
    {
        this.markets = markets;
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
     * Represents the current status of the Event
     * 
     * Statuses currently include
     * <p>
     * <ul>
     * <li>OPEN</li>
     * <li>CLOSED</li>
     * </ul>
     * </p>
     * @return
     */
    public EventStatus getEventStatus() {
		return eventStatus;
	}

    /**
     * @param eventStatus sets the eventStatus
     */
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
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
        builder.append("Event [id=");
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
        Event other = (Event) obj;
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
