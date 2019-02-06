package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBMarketFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchMarketCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchMarketCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchMarketCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_MARKET_COMMAND)
public class MBFetchMarketCommand extends FetchMarketCommand {
	private MBMarketFilter filter;
	
	public MBFetchMarketCommand(){}
	public MBFetchMarketCommand(RequestConnection connection, Account account, MBMarketFilter filter) {
		super(connection, account, filter);
		this.setFilter(filter);
	}
	
	public MBMarketFilter getFilter() {
		return filter;
	}
	public void setFilter(MBMarketFilter filter) {
		this.filter = filter;
	}

}
