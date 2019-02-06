package com.aldogrand.kfc.msg.commands.integrationmodules;

/**
 * 
 * @author aldogrand
 *
 */

public class Proxy {
	private String url;
	private String port;
	private String username;
	private String password;
	
	public Proxy()	{}
	public Proxy(String url, String port, String username, String password) {
		super();
		this.url = url;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
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
}
