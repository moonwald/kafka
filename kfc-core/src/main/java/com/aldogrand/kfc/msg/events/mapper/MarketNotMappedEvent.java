/**
 * 
 */
package com.aldogrand.kfc.msg.events.mapper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.SourceMarket;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> MarketNotMappedEvent.java
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
@ContentType(EventContentType.MARKET_NOT_MAPPED)
public class MarketNotMappedEvent extends KFCEvent {
	//NOTE This will not be used for now but may be in future
	/**
	 * market of type Market
	 */
	private Market market;
	
	/**
	 * sourceMarket of type SourceMarket
	 */
	private SourceMarket sourceMarket;

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

	/**
	 * @return the sourceMarket
	 */
	public SourceMarket getSourceMarket() {
		return sourceMarket;
	}

	/**
	 * @param sourceMarket
	 *            the sourceMarket to set
	 */
	public void setSourceMarket(SourceMarket sourceMarket) {
		this.sourceMarket = sourceMarket;
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
