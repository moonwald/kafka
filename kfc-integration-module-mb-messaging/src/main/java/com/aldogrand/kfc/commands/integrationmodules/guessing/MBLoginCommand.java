package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.Connection;
import com.aldogrand.kfc.msg.commands.integrationmodules.LoginCommand;

/**
 * 
 * <p>
 * <b>Title</b> MBLoginCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBLoginCommand <br/>
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

@ContentType(MbCommandContentType.MB_LOGIN_COMMAND)
public class MBLoginCommand extends LoginCommand {

	public MBLoginCommand()	{}
	public MBLoginCommand(Connection connection, Account account) {
		super(connection, account);
	}

}
