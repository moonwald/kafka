package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.OfferFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchOfferCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchOfferCommand <br/>
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

@ContentType(EventContentType.FETCH_OFFER_COMMAND)
public class FetchOfferCommand extends AbstractFetchCommand {
	private OfferFilter filter;
	
	public FetchOfferCommand()	{}
	public FetchOfferCommand(RequestConnection connection, Account account,	OfferFilter filter) {
		super(connection, account, filter);
		this.filter = filter;
	}

	public OfferFilter getFilter() {
		return filter;
	}

	public void setFilter(OfferFilter filter) {
		this.filter = filter;
	}

}
