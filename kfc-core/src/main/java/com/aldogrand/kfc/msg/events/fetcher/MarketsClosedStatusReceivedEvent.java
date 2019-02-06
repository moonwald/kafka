package com.aldogrand.kfc.msg.events.fetcher;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.connectors.model.Market;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> MarketsClosedStatusReceivedEvent.java
 * </p>
 * com.aldogrand.kfc.msg.events.fetcher
 * <p>
 * <b>Description</b> kfc-core.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author aldogrand
 * @version 1.0
 */
@ContentType(EventContentType.MARKETS_CLOSED_STATUS_RECEIVED)
public class MarketsClosedStatusReceivedEvent extends KFCEvent {
	
	private List<Market> markets;	

	private String eventId;

	/**
	 * @return the markets
	 */
	public List<Market> getMarkets()
	{
		return markets;
	}

	/**
	 * @param markets the markets to set
	 */
	public void setMarkets(List<Market> markets)
	{
		this.markets = markets;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId()
	{
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId)
	{
		this.eventId = eventId;
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
