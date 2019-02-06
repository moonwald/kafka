package com.aldogrand.sbpc.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> BettingPlatform
 * </p>
 * <p>
 * <b>Description</b> A betting platform
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Ken Barry
 * @version 1.0
 */
@XmlRootElement(name = "betting-platform")
@XmlType(propOrder = {"id", "name", "connectorId", "connectorName"})
public class BettingPlatform implements Serializable
{
    private static final long serialVersionUID = 5033635798003762632L;

    private Long id;
    private String name;
    private Long connectorId;
    private String connectorName;
    
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BettingPlatform [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", connectorId=");
        builder.append(connectorId);
        builder.append(", connectorName=");
        builder.append(connectorName);
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
        result = prime * result
                + ((connectorId == null) ? 0 : connectorId.hashCode());
        result = prime * result
                + ((connectorName == null) ? 0 : connectorName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        BettingPlatform other = (BettingPlatform) obj;
        if (connectorId == null)
        {
            if (other.connectorId != null)
            {
                return false;
            }
        }
        else if (!connectorId.equals(other.connectorId))
        {
            return false;
        }
        if (connectorName == null)
        {
            if (other.connectorName != null)
            {
                return false;
            }
        }
        else if (!connectorName.equals(other.connectorName))
        {
            return false;
        }
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
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        return true;
    }		
}
