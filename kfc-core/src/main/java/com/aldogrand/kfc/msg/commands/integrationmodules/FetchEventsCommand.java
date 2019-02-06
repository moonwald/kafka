package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.EventsFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchEventsCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchEventsCommand <br/>
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

@ContentType(EventContentType.FETCH_EVENTS_COMMAND)
public class FetchEventsCommand extends AbstractFetchCommand {
	private EventsFilter filter;
	
	public FetchEventsCommand()	{}

	public FetchEventsCommand(RequestConnection connection, Account account, EventsFilter filter) {
		super(connection, account, filter);
	}
	public EventsFilter getFilter() {
		return filter;
	}
	public void setFilter(EventsFilter filter) {
		this.filter = filter;
	}
}
