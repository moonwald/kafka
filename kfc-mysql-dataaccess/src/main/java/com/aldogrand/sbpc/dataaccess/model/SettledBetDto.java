package com.aldogrand.sbpc.dataaccess.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * <b>Title</b> SettledBetDto
 * </p>
 * <p>
 * <b>Description</b> A Settled Bet.
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

@Entity(name = "SettledBet")
@Table(name = "settled_bets")
@Cacheable(false)
public class SettledBetDto extends AbstractDto {


	private static final long serialVersionUID = 6055874633014181422L;
	
	private BetDto bet;
	private BigDecimal profitLoss;
	private Date settledTime;
	
	/**
	 * 
	 * @return the bet
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bet", nullable = false)
	public BetDto getBet() {
		return bet;
	}
	
	/**
	 * 
	 * @param bet the bet to set
	 */
	public void setBet(BetDto bet) {
		this.bet = bet;
	}
	
	/**
	 * 
	 * @return the profit loss
	 */
	@Column(name = "profit_loss", precision = 19, scale = 8, nullable = false)
	public BigDecimal getProfitLoss() {
		return profitLoss;
	}
	
	/**
	 * 
	 * @param profitLoss the profit loss to set
	 */
	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}
	
	/**
	 * 
	 * @return the settled time
	 */
	@Column(name = "settled_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getSettledTime() {
		return settledTime;
	}
	
	/**
	 * 
	 * @param settledTime the settled time to set
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
        builder.append("SettledBetDto [profitLoss=");
        builder.append(profitLoss);
        builder.append(", settledTime=");
        builder.append(settledTime);
        builder.append("]");
        return builder.toString();
    }
}
