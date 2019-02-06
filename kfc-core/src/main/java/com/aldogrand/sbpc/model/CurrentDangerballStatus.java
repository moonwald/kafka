package com.aldogrand.sbpc.model;

/**
 * Represents the current 
 *
 */
public enum CurrentDangerballStatus {
		AWAY_GOAL(-700),
		AWAY_PENALTY(-600),
		AWAY_DANGEROUS_FREEKICK(-500),
		AWAY_CORNER(-400),
		AWAY_ATTACKING_FREEKICK(-300),
		AWAY_DANGEROUS_ATTACK(-200),
		AWAY_CORNER_DANGER(-100),
		SAFE(0),
		HOME_CORNER_DANGER(100),
		HOME_DANGEROUS_ATTACK(200),
		HOME_ATTACKING_FREEKICK(300),
		HOME_CORNER(400),
		HOME_DANGEROUS_FREEKICK(500),
		HOME_PENALTY(600),
		HOME_GOAL(700),
		MISSED_INCIDENT(-1);
	
	private int reference;
	
	CurrentDangerballStatus(int reference) {
		this.reference = reference;
	}
	
	/**
	 * Retrieve the Incident type matching the Integer
	 * 
	 * @param incidentType
	 * @return
	 */
	public static CurrentDangerballStatus getStatus(int currentDangerballStatus) {
		for (CurrentDangerballStatus status : CurrentDangerballStatus.values()) {
			if (currentDangerballStatus == status.reference) {
				return status;
			}
		}
		return null;
	}
}
