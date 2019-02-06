package com.aldogrand.sbpc.connectors.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SettledBet implements Serializable {

	private static final long serialVersionUID = -2491339471740975463L;
	
	private String betId;
	private String offerId;
	private BigDecimal profitLoss;
	private Date settledTime;
	private BigDecimal odds;
	private BigDecimal stake;
	private String side;
	private String marketId;
	private Date placedTime;
	private String runnerId;
	private String eventId;
	
	
	public String getBetId() {
		return betId;
	}
	public void setBetId(String betId) {
		this.betId = betId;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public BigDecimal getProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(BigDecimal profitLoss) {
		this.profitLoss = profitLoss;
	}
	public Date getSettledTime() {
		return settledTime;
	}
	public void setSettledTime(Date settledTime) {
		this.settledTime = settledTime;
	}
	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}
	public void setStake(BigDecimal stake) {
		this.stake = stake;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public void setPlacedTime(Date placedTime) {
		this.placedTime = placedTime;
	}
	public void setRunnerId(String runnerId) {
		this.runnerId = runnerId;
	}
	public BigDecimal getOdds() {
		return odds;
	}
	public BigDecimal getStake() {
		return stake;
	}
	public String getSide() {
		return side;
	}
	public String getMarketId() {
		return marketId;
	}
	public Date getPlacedTime() {
		return placedTime;
	}
	public String getRunnerId() {
		return runnerId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	
	
}
