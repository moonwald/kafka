package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> Connector
 * </p>
 * <p>
 * <b>Description</b> A Connector represents a connection to a source of betting
 * data from Betting Platforms. A Connector can be a Betting Platform or a third party
 * aggregator.
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
@XmlRootElement(name = "connector")
@XmlType(propOrder = {"id", "name", "enabled", "eventContributor", "offerManagementEnabled",
        "bettingPlatforms", "accounts"})
public class Connector implements Serializable
{
    private static final long serialVersionUID = 5033635798003762632L;

    private Long id;
    private String name;
    private Boolean enabled;
    private Boolean eventContributor;
    private Boolean offerManagementEnabled;
    private List<BettingPlatform> bettingPlatforms;
    private List<Account> accounts;
    
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
     * @return the enabled
     */
    public Boolean isEnabled()
    {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * @return the eventContributor
     */
	@XmlElement(name = "event-contributor")
    public Boolean isEventContributor()
    {
        return eventContributor;
    }

    /**
     * @param eventContributor the eventContributor to set
     */
    public void setEventContributor(Boolean eventContributor)
    {
        this.eventContributor = eventContributor;
    }

    /**
     * @return the offerManagementEnabled
     */
    @XmlElement(name = "offer-management-enabled")
    public Boolean isOfferManagementEnabled()
    {
        return offerManagementEnabled;
    }

    /**
     * @param offerManagementEnabled the offerManagementEnabled to set
     */
    public void setOfferManagementEnabled(Boolean offerManagementEnabled)
    {
        this.offerManagementEnabled = offerManagementEnabled;
    }

    /**
     * @return the bettingPlatforms
     */
    @XmlElementWrapper(name = "betting-platforms")
    @XmlElement(name = "betting-platform")
    public List<BettingPlatform> getBettingPlatforms()
    {
        return bettingPlatforms;
    }

    /**
     * @param bettingPlatforms the bettingPlatforms to set
     */
    public void setBettingPlatforms(List<BettingPlatform> bettingPlatforms)
    {
        this.bettingPlatforms = bettingPlatforms;
    }

    /**
     * @return the accounts
     */
    @XmlElementWrapper(name = "accounts")
    @XmlElement(name = "account")
    public List<Account> getAccounts()
    {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(List<Account> accounts)
    {
        this.accounts = accounts;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Connector [id=");
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
        Connector other = (Connector) obj;
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
