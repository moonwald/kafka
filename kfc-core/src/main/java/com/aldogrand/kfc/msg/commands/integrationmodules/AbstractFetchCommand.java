package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.Filter;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * 
 * <p>
 * <b>Title</b> AbstractFetchCommand.java
 * </p>
 * <p>
 * <b>Description</b> AbstractFetchCommand <br/>
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

public abstract class AbstractFetchCommand extends KFCEvent{
	private RequestConnection connection;
	private Account account;
	private Filter filter;

	public AbstractFetchCommand()	{}
	public AbstractFetchCommand(RequestConnection connection, Account account, Filter filter) {
		super();
		this.connection = connection;
		this.account = account;
		this.filter = filter;
	}
	public RequestConnection getConnection() {
		return connection;
	}
	public void setConnection(RequestConnection connection) {
		this.connection = connection;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
}
