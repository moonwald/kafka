package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.EventFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchEventCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchEventCommand <br/>
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

@ContentType(EventContentType.FETCH_EVENT_COMMAND)
public class FetchEventCommand extends AbstractFetchCommand {
	private EventFilter filter;

	public FetchEventCommand()	{}
	public FetchEventCommand(RequestConnection connection, Account account,	EventFilter filter) {
		super(connection, account, filter);
	}

	public EventFilter getFilter() {
		return filter;
	}

	public void setFilter(EventFilter filter) {
		this.filter = filter;
	}
	
}
