package com.aldogrand.kfc.integrationmodules.betting.model.football;

import javax.xml.datatype.XMLGregorianCalendar;

import org.codehaus.jackson.annotate.JsonProperty;

public class BettingMatchState {

	private int bookMakerId;
	private int eventId;
	private XMLGregorianCalendar messageTimestampUTC;
	private String externalId;
	private String userInControl;
	private boolean userIsOnline;
	private int stateOfPlay;
	
	
	@JsonProperty("BookmakerId")
	public int getBookMakerId() {
		return bookMakerId;
	}
	
	public void setBookMakerId(int bookMakerId) {
		this.bookMakerId = bookMakerId;
	}
	
	@JsonProperty("EventId")
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	@JsonProperty("MessageTimestampUTC")
	public XMLGregorianCalendar getMsgTimestampUTC() {
		return messageTimestampUTC;
	}
	
	public void setMsgTimestampUTC(XMLGregorianCalendar messageTimestampUTC) {
		this.messageTimestampUTC = messageTimestampUTC;
	}
	
	@JsonProperty("ExternalId")
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	@JsonProperty("UserInControl")
	public String getUserInControl() {
		return userInControl;
	}
	
	public void setUserInControl(String userInControl) {
		this.userInControl = userInControl;
	}
	
	@JsonProperty("UserIsOnline")
	public boolean isUserIsOnline() {
		return userIsOnline;
	}
	
	public void setUserIsOnline(boolean userIsOnline) {
		this.userIsOnline = userIsOnline;
	}
	
	@JsonProperty("StateOfPlay")
	public int getStateOfPlay() {
		return this.stateOfPlay;
	}
	
	public void setStateOfPlay(int stateOfPlay) {
		this.stateOfPlay = stateOfPlay;
	}
}