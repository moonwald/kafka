package com.aldogrand.sbpc.model;

public enum EventPhase {
	PRE_MATCH(0),
	FIRST_HALF(1),
	HALF_TIME(2),
	SECOND_HALF(3),
	BEFORE_EXTRA_TIME(4),
	EXTRA_TIME_FIRST_HALF(5),
	EXTRA_TIME_HALF_TIME(6),
	EXTRA_TIME_SECOND_HALF(7),
	BEFORE_PENALTY_SHOOT_OUT(8),
	PENALTY_SHOOT_OUT(9),
	POST_MATCH(10),
	MATCH_ABANDONED(11);
	
	private int reference;
	
	private EventPhase(int reference) {
		this.reference = reference;
	}	
	
	public static EventPhase getStatus(int eventPhase){
		for (EventPhase status : EventPhase.values()) {
			if (eventPhase == status.reference) {
				return status;
			}
		}
		return null;
		
	}
}
