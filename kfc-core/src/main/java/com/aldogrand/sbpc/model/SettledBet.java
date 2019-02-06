package com.aldogrand.sbpc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> SettledBet
 * </p>
 * <p>
 * <b>Description</b> Settled bets from an {@link Bet}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Dani Garcia
 * @version 1.0
 */
@XmlRootElement(name = "settledBet")
@XmlType(propOrder = {"id", "betId", "profitLoss", "settledTime"})
public class SettledBet implements Serializable
{

	private static final long serialVersionUID = 6629281646028153737L;
	private Long id;
	private Long betId;
    private BigDecimal profitLoss;
    private Date settledTime;

    /**
     * @return the id
     */
    @XmlElement(name = "id")
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
     * @return the betId
     */
    @XmlElement(name = "betId")
    public Long getBetId() {
		return betId;
	}

    /**
     * @param betId the betId to set
     */
	public void setBetId(Long betId) {
		this.betId = betId;
	}

	/**
     * @return the profitLoss
     */
	@XmlElement(name = "profitLoss")
	public BigDecimal getProfitLoss() {
		return profitLoss;
	}

	/**
     * @param profitLoss the profitLoss to set
     */
	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}

	/**
     * @return the settledTime
     */
	@XmlElement(name = "settledTime")
	public Date getSettledTime() {
		return settledTime;
	}

	/**
     * @param settledTime the settledTime to set
     */
	public void setSettledTime(Date settledTime) {
		this.settledTime = settledTime;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Bet [id=");
        builder.append(id);
        builder.append(", betId=");
        builder.append(betId);
        builder.append(", profitLoss=");
        builder.append(profitLoss);
        builder.append(", settledTime=");
        builder.append(settledTime);
        builder.append("]");
        return builder.toString();
    }
}
