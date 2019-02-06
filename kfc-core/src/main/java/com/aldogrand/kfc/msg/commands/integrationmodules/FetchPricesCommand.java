package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.PricesFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchPricesCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchPricesCommand <br/>
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

@ContentType(EventContentType.FETCH_PRICES_COMMAND)
public class FetchPricesCommand extends AbstractFetchCommand {
	private PricesFilter filter;

	public FetchPricesCommand()	{}
	public FetchPricesCommand(RequestConnection connection, Account account, PricesFilter filter) {
		super(connection, account, filter);
		this.setFilter(filter);
	}

	public PricesFilter getFilter() {
		return filter;
	}

	public void setFilter(PricesFilter filter) {
		this.filter = filter;
	}

}
