package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBRunnerFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchRunnerCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;
/**
 * 
 * <p>
 * <b>Title</b> MBFetchRunnerCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchRunnerCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_RUNNER_COMMAND)
public class MBFetchRunnerCommand extends FetchRunnerCommand {
	private MBRunnerFilter mbRunnerFilter;
	
	public MBFetchRunnerCommand(){}
	public MBFetchRunnerCommand(RequestConnection connection, Account account, MBRunnerFilter filter) {
		super(connection, account, filter);
		this.mbRunnerFilter = filter;
	}
	/**
	 * @return the mbRunnerFilter
	 */
	public MBRunnerFilter getMbRunnerFilter()
	{
		return mbRunnerFilter;
	}
	/**
	 * @param mbRunnerFilter the mbRunnerFilter to set
	 */
	public void setMbRunnerFilter(MBRunnerFilter mbRunnerFilter)
	{
		this.mbRunnerFilter = mbRunnerFilter;
	}
	
}
