package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.MarketsFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchMarketsCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchMarketsCommand <br/>
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

@ContentType(EventContentType.FETCH_MARKETS_COMMAND)
public class FetchMarketsCommand extends AbstractFetchCommand{

	private MarketsFilter filter;
	
	public FetchMarketsCommand() {}
	public FetchMarketsCommand(RequestConnection connection, Account account, MarketsFilter filter) {
		super(connection, account, filter);
	}

	public MarketsFilter getFilter() {
		return filter;
	}

	public void setFilter(MarketsFilter filter) {
		this.filter = filter;
	}

}
