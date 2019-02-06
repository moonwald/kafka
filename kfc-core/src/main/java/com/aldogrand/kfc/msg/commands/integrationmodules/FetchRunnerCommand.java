package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.RunnerFilter;

/**
 * 
 * <p>
 * <b>Title</b> FetchRunnerCommand.java
 * </p>
 * <p>
 * <b>Description</b> FetchRunnerCommand <br/>
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

@ContentType(EventContentType.FETCH_RUNNER_COMMAND)
public class FetchRunnerCommand extends AbstractFetchCommand{
	private RunnerFilter filter;

	/**
	 * 
	 */
	public FetchRunnerCommand()
	{}
	public FetchRunnerCommand(RequestConnection connection, Account account, RunnerFilter filter) {
		super(connection, account, filter);
	}

	public RunnerFilter getFilter() {
		return filter;
	}

	public void setFilter(RunnerFilter filter) {
		this.filter = filter;
	}

}
