package com.aldogrand.kfc.msg.commands.integrationmodules;

/**
 * 
 * <p>
 * <b>Title</b> Connection.java
 * </p>
 * <p>
 * <b>Description</b> Connection <br/>
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

public class Connection {

	private String url;
	private Proxy proxy;
	
	public Connection()	{}
	public Connection(String url, Proxy proxy) {
		super();
		this.url = url;
		this.proxy = proxy;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Proxy getProxy() {
		return proxy;
	}
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
}
