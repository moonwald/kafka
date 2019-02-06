package com.aldogrand.sbpc.dataaccess.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>
 * <b>Title</b> ConnectorDto
 * </p>
 * <p>
 * <b>Description</b> Connector is needed to keep a reference to where prices come 
 * from and also so that we can return a list of the supported connectors.
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
@Entity(name = "Connector")
@Table(name = "connectors")
@Cacheable(false)
public class ConnectorDto extends AbstractFetchableDto 
{
    private static final long serialVersionUID = -8708073534710195600L;

    private String name;
    private boolean enabled;
    private boolean eventContributor;
    private boolean offerManagementEnabled;
    private boolean pushSourceData;
    private List<BettingPlatformDto> bettingPlatforms = new ArrayList<BettingPlatformDto>();
    private List<AccountDto> accounts = new ArrayList<AccountDto>();

    /**
     * @return the name
     */
    @Column(nullable = false, unique = true, length = 100)
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
    @Column(name = "enabled", nullable = false)
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * @return the eventContributor
     */
    @Column(name = "event_contributor", nullable = false)
    public boolean isEventContributor()
    {
        return eventContributor;
    }

    /**
     * @param eventContributor the eventContributor to set
     */
    public void setEventContributor(boolean eventContributor)
    {
        this.eventContributor = eventContributor;
    }

    /**
     * @return the offerManagementEnabled
     */
    @Column(name = "offer_management", nullable = false)
    public boolean isOfferManagementEnabled()
    {
        return offerManagementEnabled;
    }

    /**
     * @param offerManagementEnabled the offerManagementEnabled to set
     */
    public void setOfferManagementEnabled(boolean offerManagementEnabled)
    {
        this.offerManagementEnabled = offerManagementEnabled;
    }

    /**
     * @return pushConnector true where the connector
     * pushes source data into the SBPC
     */
    @Column(name = "push_source_data", nullable = false)
    public boolean isPushSourceData() {
		return pushSourceData;
	}

    /**
     * @param pushSourceData the pushSourceData to set
     */
	public void setPushSourceData(boolean pushSourceData) {
		this.pushSourceData = pushSourceData;
	}
	
    /**
     * @return the bettingPlatforms
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, 
            mappedBy = "connector")
    @OrderBy("name ASC")
    public List<BettingPlatformDto> getBettingPlatforms()
    {
        return bettingPlatforms;
    }

    /**
     * @param bettingPlatforms the bettingPlatforms to set
     */
    public void setBettingPlatforms(List<BettingPlatformDto> bettingPlatforms)
    {
        this.bettingPlatforms = bettingPlatforms;
    }

    /**
     * @return the accounts
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, 
            mappedBy = "connector")
    @OrderBy("username ASC")
    public List<AccountDto> getAccounts()
    {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(List<AccountDto> accounts)
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
        builder.append("ConnectorDto [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

	
}
