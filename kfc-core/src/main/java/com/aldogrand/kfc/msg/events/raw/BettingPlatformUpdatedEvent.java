/**
 * 
 */
package com.aldogrand.kfc.msg.events.raw;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.BettingPlatform;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> BettingPlatformCreatedEvent.java
 * </p>
 * com.aldogrand.kfc.msg.events.writer.processed
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
@ContentType(EventContentType.BETTING_PLATFORM_UPDATED)
public class BettingPlatformUpdatedEvent extends KFCEvent {
	
	private BettingPlatform bettingPlatform;

	public BettingPlatform getBettingPlatform() {
		return bettingPlatform;
	}

	public void setBettingPlatform(BettingPlatform bettingPlatform) {
		this.bettingPlatform = bettingPlatform;
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
