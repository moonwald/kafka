/**
 * 
 */
package com.aldogrand.kfc.msg.events.fetcher;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> BettingPlatformReceived.java
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
@ContentType(EventContentType.BETTING_PLATFORM_RECEIVED)
public class BettingPlatformReceivedEvent extends KFCEvent {

	private String bettingPlatform;
	
	/**
	 * @return the bettingPlateform
	 */
	public String getBettingPlatform() {
		return bettingPlatform;
	}

	/**
	 * @param bettingPlateform the bettingPlateform to set
	 */
	public void setBettingPlatform(String bettingPlateform) {
		this.bettingPlatform = bettingPlateform;
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
