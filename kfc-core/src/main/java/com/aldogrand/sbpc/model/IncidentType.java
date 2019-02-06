package com.aldogrand.sbpc.model;



/**
 * Represents a type of in-play {@link EventIncident}
 * 
 * @author aldogrand
 */
public enum IncidentType {
		UNKNOWN(-1),
		HOME_GOAL(0),
		AWAY_GOAL(1),
		HOME_CORNER(2), 
		AWAY_CORNER(3), 
		HOME_YELLOW_CARD(4), 
		AWAY_YELLOW_CARD(5), 
		HOME_RED_CARD(6), 
		AWAY_RED_CARD(7), 
		HOME_SUB(8),
		AWAY_SUB(9),
		GAME_COMPLETE(10),
		SET_COMPLETE(11),
		MATCH_COMPLETE(12);

	private int reference;

	IncidentType(int reference) {
		this.reference = reference;
	}

	/**
	 * Retrieve the Incident type matching the Integer
	 * 
	 * @param incidentType
	 * @return
	 */
	public static IncidentType getType(int incidentType) {
		for (IncidentType incident : IncidentType.values()) {
			if (incidentType == incident.reference) {
				return incident;
			}
		}
		return null;
	}
}
