package com.aldogrand.kfc.lmax;

import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;

/**
 * 
 * <p>
 * <b>Title</b> KFCLMAXEvent.java
 * </p>
 * <p>
 * <b>Description</b> KFCLMAXEvent <br/>
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

public class KFCLMAXEvent {

	private Message<JsonNode> event;

	public Message<JsonNode> getEvent() {
		return event;
	}

	public void setEvent(Message<JsonNode> event) {
		this.event = event;
	}
}
