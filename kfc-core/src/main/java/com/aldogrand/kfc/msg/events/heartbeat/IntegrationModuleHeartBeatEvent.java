package com.aldogrand.kfc.msg.events.heartbeat;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> IntegrationModuleHeartBeatEvent.java
 * </p>
 * <p>
 * <b>Description</b> kfc-core.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author aldogrand
 * @version 1.0
 */

@ContentType(EventContentType.INTEGRATION_MODULE_HEARTBEAT)
public class IntegrationModuleHeartBeatEvent extends KFCEvent {
	
	public static final String DATA_IN_HEART_BEAT = "DATA_IN_HEARTBEAT";

	private Long timestamp;
	private String heartBeatName;
	
	/**
	 * 
	 */
	public IntegrationModuleHeartBeatEvent()
	{}
	
	public IntegrationModuleHeartBeatEvent(String integrationModule, Long timestamp){
		this.setIntegrationModuleName(integrationModule);
		this.setTimestamp(timestamp);
		this.setHeartBeatName(DATA_IN_HEART_BEAT);
	}
	
	public IntegrationModuleHeartBeatEvent(String integrationModule, Long timestamp, String heartBeatName){
		this.setIntegrationModuleName(integrationModule);
		this.setTimestamp(timestamp);
		this.setHeartBeatName(heartBeatName);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getHeartBeatName() {
		return heartBeatName;
	}

	public void setHeartBeatName(String heartBeatName) {
		this.heartBeatName = heartBeatName;
	}
	
	
}
