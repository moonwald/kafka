package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <b>Title</b> Account
 * </p>
 * <p>
 * <b>Description</b> A betting platform / data provider account.
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
@XmlRootElement(name = "account")
public class Account implements Serializable
{
    private static final long serialVersionUID = 5033635798003762632L;

    private Long id;
    private String connectorName;
    private String username;
    private String password;
    private Currency currency;
	private BigDecimal balance;
    private BigDecimal availableAmount;
    private List<AccountProperty> otherProperties;
    
    /**
     * @return the id
     */
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
     * @return the username
     */
    @XmlElement(name = "username")
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the currency
     */
    public Currency getCurrency()
    {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency)
    {
        this.currency = currency;
    }
    
    /**
     * @return the balance
     */
    public BigDecimal getBalance() {
		return balance;
	}
    
    /**
     * @param balance the balance to set
     */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * @return the availableAmount
	 */
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	/**
	 * @param availableAmount the availableAmount to set
	 */
	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

    /**
     * @return the otherProperties
     */
    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    public List<AccountProperty> getOtherProperties()
    {
        return otherProperties;
    }

    /**
     * @param otherProperties the otherProperties to set
     */
    public void setOtherProperties(List<AccountProperty> otherProperties)
    {
        this.otherProperties = otherProperties;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Account [id=");
        builder.append(id);
        builder.append(", connectorName=");
        builder.append(connectorName);
        builder.append(", username=");
        builder.append(username);
        builder.append(", currency=");
        builder.append(currency);
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
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
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
        Account other = (Account) obj;
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
        if (password == null)
        {
            if (other.password != null)
            {
                return false;
            }
        }
        else if (!password.equals(other.password))
        {
            return false;
        }
        return true;
    }
}
