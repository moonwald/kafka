package com.aldogrand.kfc.msg.events.integrationmodules;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * 
 * <p>
 * <b>Title</b> LoginEvent.java
 * </p>
 * <p>
 * <b>Description</b> LoginEvent <br/>
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

@ContentType(EventContentType.LOGIN_EVENT)
public class LoginEvent extends KFCEvent {
	private String sessionToken;
	private String userId;
	
	public LoginEvent(){}

	public LoginEvent(String sessionToken, String userId) {
		super();
		this.sessionToken = sessionToken;
		this.userId = userId;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
