package com.aldogrand.sbpc.model;


public enum ResultStatus {
	WINNER, LOSER, PLACED, UNKNOWN;
	
//	private String type;
//
//	ResultStatus(String name) {
//		this.type = name;
//	}
//	
//	public static ResultStatus fromString(String eventType) {
//		if (StringUtils.isNotBlank(eventType)) {
//			for (ResultStatus status : ResultStatus.values()) {
//				if (eventType.equalsIgnoreCase(status.type)) {
//					return status;
//				}
//			}
//		}
//		return null;
//	}
}
