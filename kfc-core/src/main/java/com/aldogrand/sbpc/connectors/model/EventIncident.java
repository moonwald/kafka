package com.aldogrand.sbpc.connectors.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.CurrentDangerballStatus;
import com.aldogrand.sbpc.model.EventPhase;
import com.aldogrand.sbpc.model.IncidentType;

/**
 * Represents an incident which happens during an @{link Event} 
 * 
 * @author aldogrand
 *
 */
public class EventIncident {
	
	private long sourceEventId;
	private int sequenceNumber;
	private CurrentDangerballStatus currentDangerballStatus;
	private Score score;
	private EventPhase eventPhase;
	private String elapsedTime;
	private IncidentType incidentType;
	private long participantId;

	/**
	 * Constructor for DangerBall status change
	 * 
	 * @param sourceEventId
	 * @param sequenceNumber
	 * @param currentDangerballStatus
	 * @param score
	 * @param eventPhase
	 */
	public EventIncident(long sourceEventId, int sequenceNumber, CurrentDangerballStatus currentDangerballStatus, Score score,EventPhase eventPhase) {
		this.sourceEventId = sourceEventId;
		this.sequenceNumber = sequenceNumber;
		this.currentDangerballStatus = currentDangerballStatus;
		this.score = score;
		this.eventPhase = eventPhase;
		
	}
	
	/**
	 * Constructor for DangerBall status change
	 * 
	 * @param sourceEventId
	 * @param sequenceNumber
	 * @param currentDangerballStatus
	 * @param score
	 */
	public EventIncident(long sourceEventId, int sequenceNumber, CurrentDangerballStatus currentDangerballStatus, Score score) {
		this.sourceEventId = sourceEventId;
		this.sequenceNumber = sequenceNumber;
		this.currentDangerballStatus = currentDangerballStatus;
		this.score = score;
		
	}

	/**
	 * Constructor for full incident detail
	 * 
	 * @param sourceEventId
	 * @param sequenceNumber
	 * @param currentDangerballStatus
	 * @param score
	 */
	public EventIncident(long sourceEventId, int sequenceNumber, CurrentDangerballStatus currentDangerballStatus, Score score, EventPhase eventPhase, String elapsedTime, IncidentType incidentType, long participantId) {
		this.sourceEventId = sourceEventId;
		this.sequenceNumber = sequenceNumber;
		this.currentDangerballStatus = currentDangerballStatus;
		this.score = score;
		this.eventPhase = eventPhase;
		this.elapsedTime = elapsedTime;
		this.incidentType = incidentType;
		this.participantId = participantId;
	}
	
	/**
	 * Retrieves the sourceEventId
	 * 
	 * @return
	 */
	public long getSourceEventId() {
		return sourceEventId;
	}

	/**
	 * Sets the sourceEventId
	 * 
	 * @param sourceEventId
	 */
	public void setSourceEventId(long sourceEventId) {
		this.sourceEventId = sourceEventId;
	}
	
	/**
	 * Retrieves the currentDangerballStatus
	 * 
	 * @return
	 */
	public CurrentDangerballStatus getCurrentDangerBallStatus() {
		return this.currentDangerballStatus;
	}
	
	/**
	 * Sets the currentDangerballStatus
	 * 
	 * @param currentDangerballStatus
	 */
	public void setCurrentDangerballStatus(CurrentDangerballStatus currentDangerballStatus) {
		this.currentDangerballStatus = currentDangerballStatus;
	}
	
	/**
	 * Retrieves the sequenceNumber
	 * 
	 * @return
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * Sets the sequenceNumber
	 * 
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	/**
	 * Retrieves the score
	 * 
	 * @return
	 */
	public Score getScore() {
		return score;
	}

	/**
	 * Sets the score at the time of a given
	 * <code>EventIncident</code>
	 * 
	 * @param score
	 */
	public void setScore(Score score) {
		this.score = score;
	}

	/**
	 * @return the elapsedTime
	 */
	public String getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * @param elapsedTime the elapsedTime to set
	 */
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	/**
	 * Retrieve the incident type
	 * 
	 * @return
	 */
	public IncidentType getType() {
		return incidentType;
	}
	
	/**
	 * Set the incident type
	 * 
	 * @param type
	 */
	public void setType(IncidentType type) {
		this.incidentType = type;
	}
	
	/**
	 * Retreieve the participant involved in the Incident
	 * 
	 * @return
	 */
	public long getParticipantId() {
		return participantId;
	}
	
	/**
	 * Sets the participant involved in the Incident
	 * 
	 * @param participantId
	 */
	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}

	/**
	 * @return the eventPhase
	 */
	public EventPhase getEventPhase() {
		return eventPhase;
	}

	/**
	 * @param eventPhase the eventPhase to set
	 */
	public void setEventPhase(EventPhase eventPhase) {
		this.eventPhase = eventPhase;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
