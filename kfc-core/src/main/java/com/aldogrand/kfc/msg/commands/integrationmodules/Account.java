package com.aldogrand.kfc.msg.commands.integrationmodules;

/**
 * 
 * <p>
 * <b>Title</b> Account.java
 * </p>
 * <p>
 * <b>Description</b> Account <br/>
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

public class Account {
	private String username;
	private String password;
	private String currency;
	
	public Account()	{}
	public Account(String username, String password, String currency) {
		super();
		this.username = username;
		this.password = password;
		this.currency = currency;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
