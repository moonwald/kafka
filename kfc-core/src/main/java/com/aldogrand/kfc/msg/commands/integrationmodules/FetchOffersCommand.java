package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.OffersFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchOffersCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchOffersCommand <br/>
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

@ContentType(EventContentType.FETCH_OFFERS_COMMAND)
public class FetchOffersCommand extends AbstractFetchCommand {
	private OffersFilter filter;
	
	public FetchOffersCommand()	{}
	public FetchOffersCommand(RequestConnection connection, Account account, OffersFilter filter) {
		super(connection, account, filter);
		this.filter = filter;
	}

	public OffersFilter getFilter() {
		return filter;
	}

	public void setFilter(OffersFilter filter) {
		this.filter = filter;
	}

	
}
