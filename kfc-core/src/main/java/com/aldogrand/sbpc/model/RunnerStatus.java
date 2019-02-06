package com.aldogrand.sbpc.model;


/**
 * Represents the current trading status of a given Runner
 * 
 * @author aldogrand
 *
 */
public enum RunnerStatus {
	OPEN, CLOSED, UNKNOWN;
	
//	private String type;
//
//	RunnerStatus(String name) {
//		this.type = name;
//	}
//	
//	public static RunnerStatus fromString(String eventType) {
//		if (StringUtils.isNotBlank(eventType)) {
//			for (RunnerStatus status : RunnerStatus.values()) {
//				if (eventType.equalsIgnoreCase(status.type)) {
//					return status;
//				}
//			}
//		}
//		return null;
//	}
}
