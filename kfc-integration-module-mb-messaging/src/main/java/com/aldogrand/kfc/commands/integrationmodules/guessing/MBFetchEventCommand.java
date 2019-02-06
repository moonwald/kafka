package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBEventFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchEventCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;



/**
 * 
 * <p>
 * <b>Title</b> MBFetchEventCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchEventCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_EVENT_COMMAND)
public class MBFetchEventCommand extends FetchEventCommand{
	private MBEventFilter filter;

	public MBFetchEventCommand(){}
	
	public MBFetchEventCommand(RequestConnection connection, Account account, MBEventFilter filter) {
		super(connection, account, filter);
		this.filter = filter;
	}

	public MBEventFilter getFilter() {
		return filter;
	}

	public void setFilter(MBEventFilter filter) {
		this.filter = filter;
	}

}
