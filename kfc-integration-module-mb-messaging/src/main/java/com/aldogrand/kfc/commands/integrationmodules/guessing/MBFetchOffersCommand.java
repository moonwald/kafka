package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBOffersFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchOffersCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchOffersCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchOffersCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_OFFERS_COMMAND)
public class MBFetchOffersCommand extends FetchOffersCommand {
	private MBOffersFilter filter;
	
	public MBFetchOffersCommand(){}
	public MBFetchOffersCommand(RequestConnection connection, Account account, MBOffersFilter filter) {
		super(connection, account, filter);
		this.setFilter(filter);
	}

	public MBOffersFilter getFilter() {
		return filter;
	}

	public void setFilter(MBOffersFilter filter) {
		this.filter = filter;
	}

}
