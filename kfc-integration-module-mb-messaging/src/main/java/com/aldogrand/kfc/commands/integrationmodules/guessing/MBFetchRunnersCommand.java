package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBRunnersFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchRunnersCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchRunnersCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchRunnersCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_RUNNERS_COMMAND)
public class MBFetchRunnersCommand extends FetchRunnersCommand {
	private MBRunnersFilter filter;
	
	public MBFetchRunnersCommand()	{}
	public MBFetchRunnersCommand(RequestConnection connection, Account account, MBRunnersFilter filter) {
		super(connection, account, filter);
		this.setFilter(filter);
	}

	public MBRunnersFilter getFilter() {
		return filter;
	}

	public void setFilter(MBRunnersFilter filter) {
		this.filter = filter;
	}
	
}
