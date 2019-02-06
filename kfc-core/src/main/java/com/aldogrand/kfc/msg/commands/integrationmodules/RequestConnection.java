package com.aldogrand.kfc.msg.commands.integrationmodules;

/**
 * 
 * <p>
 * <b>Title</b> RequestConnection.java
 * </p>
 * <p>
 * <b>Description</b> RequestConnection <br/>
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

public class RequestConnection extends Connection {
	private String sessionToken;
	
	public RequestConnection()
	{}
	public RequestConnection(String username, String sessionToken, Proxy proxy) {
		super(username, proxy);
		this.sessionToken = sessionToken;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

}
