package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.MetaTag;

/**
 * <p>
 * <b>Title</b> Event
 * </p>
 * <p>
 * <b>Description</b> An event on a betting platform.
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
public class Event implements Serializable
{
    private static final long serialVersionUID = 4663456973365118599L;

    private String id;
    private String name;
    private Date startTime;
    private List<MetaTag> metaTags = new ArrayList<MetaTag>();
    private List<Market> markets;
    private EventStatus status;
    // Pariticpants not implemented currently as part of Betgenius
//    private List<Participant> participants = new ArrayList<Participant>();
    

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
     * Retrieves the current status of the event
     * which is related to the status of
     * the associated {@link Market}s
     * 
     * @return status
     */
    public EventStatus getStatus() {
		return status;
	}

    /**
     * @param status the status to set
     */
	public void setStatus(EventStatus status) {
		this.status = status;
	}
    
//    /**
//     * Retrieve participants which can be either Teams
//     * or Players, teams having a given type and 
//     * players have a first and last name
//     * 
//     * @return
//     */
//    public List<Participant> getParticipants() {
//		return participants;
//	}
//
//    /**
//     * Set the participants for the Event
//     * 
//     * @param pariticpants
//     */
//	public void setParticipants(List<Participant> pariticpants) {
//		this.participants = pariticpants;
//	}

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
