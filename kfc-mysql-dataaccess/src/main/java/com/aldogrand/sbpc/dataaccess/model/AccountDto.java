package com.aldogrand.sbpc.dataaccess.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.aldogrand.sbpc.model.Currency;

/**
 * <p>
 * <b>Title</b> AccountDto
 * </p>
 * <p>
 * <b>Description</b> An account used to access data via a {@link Connector}. 
 * This could be shared by multiple users.
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
@Entity(name = "Account")
@Table(name = "accounts", uniqueConstraints 
        = @UniqueConstraint(columnNames = {"connector", "username"}))
@Cacheable(false)
public class AccountDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = 1153939058369079736L;

    private ConnectorDto connector;
    private String username;
    private String password;
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal availableAmount;
    private Set<AccountPropertyDto> accountProperties = new HashSet<AccountPropertyDto>();
    


	/**
	 * @return the availableAmount
	 */
    @Column(name = "available_amount")
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
	 * @return the balance
	 */
    @Column(name = "balance")
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
     * @return the connector
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "connector", nullable = false)
    public ConnectorDto getConnector()
    {
        return connector;
    }

    /**
     * @param connector the connector to set
     */
    public void setConnector(ConnectorDto connector)
    {
        this.connector = connector;
    }

    /**
     * @return the username
     */
    @Column(name = "username", length = 50, nullable = false)
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
    @Column(name = "password", length = 100, nullable = false)
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
    @Column(name = "currency", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
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
     * @return the accountProperties
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account")
    @OrderBy("name ASC")
    public Set<AccountPropertyDto> getAccountProperties()
    {
        return accountProperties;
    }

    /**
     * @param accountProperties the accountProperties to set
     */
    public void setAccountProperties(Set<AccountPropertyDto> accountProperties)
    {
        this.accountProperties = accountProperties;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountDto [connector=");
		builder.append(connector);
		builder.append(", username=");
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
}
