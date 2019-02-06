package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBMarketsFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchMarketsCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchMarketsCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchMarketsCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_MARKETS_COMMAND)
public class MBFetchMarketsCommand extends FetchMarketsCommand {

	private MBMarketsFilter filter;
	
	public MBFetchMarketsCommand()	{}
	public MBFetchMarketsCommand(RequestConnection connection, Account account,	MBMarketsFilter filter) {
		super(connection, account, filter);
		this.filter = filter;
	}

	public MBMarketsFilter getFilter() {
		return filter;
	}

	public void setFilter(MBMarketsFilter filter) {
		this.filter = filter;
	}
	
}
