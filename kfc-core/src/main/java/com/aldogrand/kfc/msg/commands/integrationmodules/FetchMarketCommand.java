package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.MarketFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchMarketCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchMarketCommand <br/>
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

@ContentType(EventContentType.FETCH_MARKET_COMMAND)
public class FetchMarketCommand extends AbstractFetchCommand{
	private MarketFilter filter;

	public FetchMarketCommand()	{}
	public FetchMarketCommand(RequestConnection connection, Account account, MarketFilter filter) {
		super(connection, account, filter);
	}

	public MarketFilter getFilter() {
		return filter;
	}

	public void setFilter(MarketFilter filter) {
		this.filter = filter;
	}

}
