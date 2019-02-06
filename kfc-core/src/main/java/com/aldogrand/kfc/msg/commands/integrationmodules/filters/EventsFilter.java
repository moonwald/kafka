package com.aldogrand.kfc.msg.commands.integrationmodules.filters;

import java.util.Date;

/**
 * 
 * <p>
 * <b>Title</b> EventsFilter.java
 * </p>
 * <p>
 * <b>Description</b> EventsFilter <br/>
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

public class EventsFilter extends Filter{
	private Date before;
	private Date after;
	
	public EventsFilter() {
	}

	public Date getBefore() {
		return before;
	}

	public void setBefore(Date before) {
		this.before = before;
	}

	public Date getAfter() {
		return after;
	}

	public void setAfter(Date after) {
		this.after = after;
	}
	
	
}
