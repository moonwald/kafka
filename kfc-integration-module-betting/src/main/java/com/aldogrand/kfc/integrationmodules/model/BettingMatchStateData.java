package com.aldogrand.kfc.integrationmodules.model;

import java.util.List;

/**
 * Holds the received transformed betting in-play data
 * 
 * @author aldogrand
 *
 */
public class BettingMatchStateData {

	private List<Incident> incidents;

	public BettingMatchStateData(List<Incident> incidents) {
		this.incidents = incidents;
	}
	
	public List<Incident> getIncidents() {
		return incidents;
	}

	public void setIncidents(List<Incident> incidents) {
		this.incidents = incidents;
	}
	
}
