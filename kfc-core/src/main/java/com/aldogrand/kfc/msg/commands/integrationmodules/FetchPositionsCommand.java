package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;

/**
 * 
 * <p>
 * <b>Title</b> FetchPositionsCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchPositionsCommand <br/>
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

@ContentType(EventContentType.FETCH_POSITIONS_COMMAND)
public class FetchPositionsCommand extends AbstractFetchCommand {

	public FetchPositionsCommand()	{}
	public FetchPositionsCommand(RequestConnection connection, Account account,	Filter filter) {
		super(connection, account, filter);
	}

}
