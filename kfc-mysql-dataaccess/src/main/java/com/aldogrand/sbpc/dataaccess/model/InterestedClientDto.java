package com.aldogrand.sbpc.dataaccess.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * <p>
 * <b>Title</b> InterestedClientDto
 * </p>
 * <p>
 * <b>Description</b> A marker to indicate that a client has interest in a {@link MarketDto}.
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
@Entity(name = "InterestedClient")
@Table(name = "interested_clients", 
        uniqueConstraints = @UniqueConstraint(columnNames = {"account", "market", "client_token"}))
@Cacheable(false)
public class InterestedClientDto extends AbstractDto
{

	private static final long serialVersionUID = -8737875157681783880L;

    private MarketDto market;
    private String clientToken;
    private AccountDto account;

    /**
	 * @return the account
	 */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "account", nullable = true)
	public AccountDto getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(AccountDto account) {
		this.account = account;
	}

	/**
     * @return the market
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "market", nullable = false)
    public MarketDto getMarket()
    {
        return market;
    }

    /**
     * @param market the market to set
     */
    public void setMarket(MarketDto market)
    {
        this.market = market;
    }

    /**
     * @return the clientToken
     */
    @Column(name = "client_token", length = 255, nullable = false)
    public String getClientToken()
    {
        return clientToken;
    }

    /**
     * @param clientToken the clientToken to set
     */
    public void setClientToken(String clientToken)
    {
        this.clientToken = clientToken;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("InterestedClientDto [market=");
        builder.append(market);
        builder.append(", clientToken=");
        builder.append(clientToken);
        builder.append("]");
        return builder.toString();
    }
    
    /* (non-Javadoc)
   	 * @see java.lang.Object#hashCode()
   	 */
   	@Override
   	public int hashCode() {
   		final int prime = 31;
   		int result = super.hashCode();
   		result = prime * result + ((account == null) ? 0 : account.hashCode());
   		result = prime * result + ((clientToken == null) ? 0 : clientToken.hashCode());
   		result = prime * result + ((market == null) ? 0 : market.hashCode());
   		return result;
   	}

   	/* (non-Javadoc)
   	 * @see java.lang.Object#equals(java.lang.Object)
   	 */
   	@Override
   	public boolean equals(Object obj) {
   		if (this == obj)
   			return true;
   		if (!super.equals(obj))
   			return false;
   		if (getClass() != obj.getClass())
   			return false;
   		InterestedClientDto other = (InterestedClientDto) obj;
   		if (account == null) {
   			if (other.account != null)
   				return false;
   		} else if (!account.equals(other.account))
   			return false;
   		if (clientToken == null) {
   			if (other.clientToken != null)
   				return false;
   		} else if (!clientToken.equals(other.clientToken))
   			return false;
   		if (market == null) {
   			if (other.market != null)
   				return false;
   		} else if (!market.equals(other.market))
   			return false;
   		return true;
   	}
}
