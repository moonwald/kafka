/**
 * 
 */
package com.aldogrand.kfc.msg.events.processed;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

import com.aldogrand.sbpc.model.Market;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> MarketUpdatedEvent.java
 * </p>
 * com.aldogrand.kfc.msg.events.processed
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
@ContentType(EventContentType.MARKET_UPDATED)
public class MarketUpdatedEvent extends KFCEvent {

	@JsonProperty("body")
	private Market market;

	/**
	 * @return the market
	 */
	public Market getMarket() {
		return market;
	}

	/**
	 * @param market
	 *            the market to set
	 */
	public void setMarket(Market market) {
		this.market = market;
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
