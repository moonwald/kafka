/**
 * 
 */
package com.aldogrand.kfc.msg.events.raw;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.SourceEvent;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;


/**
 * <p>
 * <b>Title</b> SourceEventCreatedEvent.java
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
@ContentType(EventContentType.SOURCE_EVENT_CREATED)
public class SourceEventCreatedEvent  extends KFCEvent {

	/**
	 * sourceEvent of type SourceEvent
	 */
	private SourceEvent sourceEvent;
	
	/**
	 * event of type Event
	 */
	private Event event;

	/**
	 * @return the sourceEvent
	 */
	public SourceEvent getSourceEvent() {
		return sourceEvent;
	}

	/**
	 * @param sourceEvent the sourceEvent to set
	 */
	public void setSourceEvent(SourceEvent sourceEvent) {
		this.sourceEvent = sourceEvent;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
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
