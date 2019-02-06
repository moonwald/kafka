package com.aldogrand.kfc.msg.events;

import org.springframework.messaging.Message;

/**
 * Error event model.
 * 
 * <p>
 * <b>Title</b> KFCErrorEvent
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author aldogrand
 *
 */
public class KFCErrorEvent extends KFCEvent {
	
	private String error;
	
	private Long timestamp;
	
	private String producedBy;
	
	private Message<KFCEvent> originalEvent;
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getProducedBy() {
		return producedBy;
	}

	public void setProducedBy(String producedBy) {
		this.producedBy = producedBy;
	}

	public Message<KFCEvent> getOriginalEvent() {
		return originalEvent;
	}

	public void setOriginalEvent(Message<KFCEvent> originalEvent) {
		this.originalEvent = originalEvent;
	}
		
}
