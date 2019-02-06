package com.aldogrand.kfc.msg.events.fetcher;

import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.aldogrand.sbpc.connectors.model.Market;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

@ContentType(EventContentType.SOURCE_MARKETS_RECEIVED)
public class SourceMarketsReceivedEvent extends KFCEvent {

	private List<Market> markets;
	
	/**
	 * @return List of markets
	 */
	public List<Market> getMarkets() {
		return markets;
	}
	/**
	 * @param markets
	 */
	public void setMarkets(List<Market> markets) {
		this.markets = markets;
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
