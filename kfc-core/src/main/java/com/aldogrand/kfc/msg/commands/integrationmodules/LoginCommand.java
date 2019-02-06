package com.aldogrand.kfc.msg.commands.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * 
 * <p>
 * <b>Title</b> LoginCommand.java
 * </p>
 * <p>
 * <b>Description</b> LoginCommand <br/>
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

@ContentType(EventContentType.LOGIN_COMMAND)
public class LoginCommand extends KFCEvent {
	private Connection connection;
	private Account account;
	
	public LoginCommand()
	{}
	public LoginCommand(Connection connection, Account account) {
		super();
		this.connection = connection;
		this.account = account;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

	
}
