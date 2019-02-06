package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBOfferFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchOfferCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchOfferCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchOfferCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_OFFER_COMMAND)
public class MBFetchOfferCommand extends FetchOfferCommand {
	private MBOfferFilter filter;
	
	public MBFetchOfferCommand()	{}
	public MBFetchOfferCommand(RequestConnection connection, Account account, MBOfferFilter filter) {
		super(connection, account, filter);
		this.filter = filter;
	}

	public MBOfferFilter getFilter() {
		return filter;
	}

	public void setFilter(MBOfferFilter filter) {
		this.filter = filter;
	}
}
