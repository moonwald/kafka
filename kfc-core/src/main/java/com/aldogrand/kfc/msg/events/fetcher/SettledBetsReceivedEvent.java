/**
 * 
 */
package com.aldogrand.kfc.msg.events.fetcher;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.connectors.model.SettledBet;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> SettledBetReceived.java
 * </p>
 * <p>
 * <b>Description</b> kfc-core.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author aldogrand
 * @version 1.0
 */
@ContentType(EventContentType.SETTLED_BETS_RECEIVED)
public class SettledBetsReceivedEvent extends KFCEvent {

	private List<SettledBet> settledBets;
	
	private Long accountId;
	
	/**
	 * @return the list of settledBet
	 */
	public List<SettledBet> getSettledBets() {
		return settledBets;
	}

	/**
	 * @param settledBets the list of settledBet to set
	 */
	public void setSettledBets(List<SettledBet> settledBets) {
		this.settledBets = settledBets;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
