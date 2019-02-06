package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.RunnerFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.RunnersFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchRunnersCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchRunnersCommand <br/>
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

@ContentType(EventContentType.FETCH_RUNNERS_COMMAND)
public class FetchRunnersCommand extends AbstractFetchCommand{
	private RunnersFilter filter;
	
	public FetchRunnersCommand()
	{}
	public FetchRunnersCommand(RequestConnection connection, Account account,
			RunnersFilter filter) {
		super(connection, account, filter);
		this.filter = filter;
	}

	public RunnersFilter getFilter() {
		return filter;
	}

	public void setFilter(RunnersFilter filter) {
		this.filter = filter;
	}
}
