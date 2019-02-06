package com.aldogrand.kfc.msg.commands.integrationmodules.filters;


/**
 * 
 * <p>
 * <b>Title</b> EventFilter.java
 * </p>
 * <p>
 * <b>Description</b> EventFilter <br/>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */

public class EventFilter extends Filter{
	private String eventId;
	
	public EventFilter(){}
	
	public EventFilter(String eventId){
		super();
		this.eventId = eventId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
}
