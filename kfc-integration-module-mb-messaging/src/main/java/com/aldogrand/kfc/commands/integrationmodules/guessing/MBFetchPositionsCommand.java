package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchPositionsCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchPositionsCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchPositionsCommand <br/>
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
@ContentType(MbCommandContentType.MB_FETCH_POSITIONS_COMMAND)
public class MBFetchPositionsCommand extends FetchPositionsCommand {

	public MBFetchPositionsCommand(){}
	public MBFetchPositionsCommand(RequestConnection connection, Account account, Filter filter) {
		super(connection, account, filter);
	}

}
