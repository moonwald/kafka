package com.aldogrand.kfc.commands.integrationmodules.guessing;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBPricesFilter;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchPricesCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;

/**
 * 
 * <p>
 * <b>Title</b> MBFetchPricesCommand.java
 * </p>
 * <p>
 * <b>Description</b> MBFetchPricesCommand <br/>
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

@ContentType(MbCommandContentType.MB_FETCH_PRICES_COMMAND)
public class MBFetchPricesCommand extends FetchPricesCommand {
	private MBPricesFilter filiter;
	
	public MBFetchPricesCommand()	{}
	public MBFetchPricesCommand(RequestConnection connection, Account account, MBPricesFilter filter) {
		super(connection, account, filter);
		this.setFiliter(filter);
	}

	public MBPricesFilter getFiliter() {
		return filiter;
	}

	public void setFiliter(MBPricesFilter filiter) {
		this.filiter = filiter;
	}
	
	

}
