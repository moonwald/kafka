package com.aldogrand.sbpc.dataaccess.model;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.aldogrand.sbpc.model.Currency;

/**
 * <p>
 * <b>Title</b> PositionDto
 * </p>
 * <p>
 * <b>Description</b> The current potential profit for an {@link AccountDto} on
 * a {@link BettingPlatformDto} if a particular {@link RunnerDto} should win.
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
@Entity(name = "Position")
@Table(name = "positions", uniqueConstraints 
        = @UniqueConstraint(columnNames = {"runner", "account", "betting_platform"}))
@Cacheable(false)
public class PositionDto extends AbstractFetchableDto
{
    private static final long serialVersionUID = 2281972491712205249L;

    private RunnerDto runner;
    private AccountDto account;
    private Currency currency;
    private BigDecimal value;
    private BettingPlatformDto bettingPlatform;

    /**
     * @return the runner
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "runner", nullable = false)
    public RunnerDto getRunner()
    {
        return runner;
    }

    /**
     * @param runner the runner to set
     */
    public void setRunner(RunnerDto runner)
    {
        this.runner = runner;
    }

    /**
     * @return the account
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account", nullable = false)
    public AccountDto getAccount()
    {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(AccountDto account)
    {
        this.account = account;
    }

    /**
     * @return the currency
     */
    @Column(name = "currency", nullable = false, length = 3)
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
     * @return the value
     */
    @Column(name = "value", precision = 19, scale = 8, nullable = false)
    public BigDecimal getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value)
    {
        this.value = value;
    }

    /**
     * @return the bettingPlatform
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "betting_platform", nullable = false)
    public BettingPlatformDto getBettingPlatform()
    {
        return bettingPlatform;
    }

    /**
     * @param bettingPlatform the bettingPlatform to set
     */
    public void setBettingPlatform(BettingPlatformDto bettingPlatform)
    {
        this.bettingPlatform = bettingPlatform;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PositionDto [bettingPlatform=");
        builder.append(bettingPlatform);
        builder.append(", account=");
        builder.append(account);
        builder.append(", runner=");
        builder.append(runner);
        builder.append(", currency=");
        builder.append(currency);
        builder.append(", value=");
        builder.append(value);
        builder.append("]");
        return builder.toString();
    }
}
