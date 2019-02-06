package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBEventsFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchEventsCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchEventsCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchEventsCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_EVENTS_COMMAND)
public class MBFetchEventsCommand extends FetchEventsCommand {
	private MBEventsFilter filter;

	public MBFetchEventsCommand()	{}
	public MBFetchEventsCommand(RequestConnection connection, Account account, MBEventsFilter filter) {
		super(connection, account, filter);
	}

	public MBEventsFilter getFilter() {
		return filter;
	}

	public void setFilter(MBEventsFilter filter) {
		this.filter = filter;
	}

}
