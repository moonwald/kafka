package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;

import com.aldogrand.sbpc.model.Currency;

/**
 * <p>
 * <b>Title</b> Account
 * </p>
 * <p>
 * <b>Description</b> An account used to access and perform operations on a 
 * betting platform or data provider.
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
public class Account implements Serializable
{
    private static final long serialVersionUID = -2031543494400169191L;

    private String username;
    private String password;
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal availableAmount;
    private Properties otherProperties = new Properties();

    /**
     * @return the username
     */
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
     * Get the account property with the given name.
     * @param name
     * @return
     */
    public String getProperty(String name)
    {
        return otherProperties.getProperty(name);
    }
    
    /**
     * Set the account property with the given name to the given value.<br/>
     * A value of null removes the property.
     * @param name
     * @param value
     */
    public void setProperty(String name, String value)
    {
        if (value != null)
        {
            otherProperties.setProperty(name, value);
        }
        else
        {
            otherProperties.remove(name);
        }
    }



    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", availableAmount=");
		builder.append(availableAmount);
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
                + ((username == null) ? 0 : username.hashCode());
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
        if (username == null)
        {
            if (other.username != null)
            {
                return false;
            }
        }
        else if (!username.equals(other.username))
        {
            return false;
        }
        return true;
    }
}
