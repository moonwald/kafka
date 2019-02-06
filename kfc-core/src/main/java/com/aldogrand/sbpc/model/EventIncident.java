package com.aldogrand.sbpc.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Represents an in-play incident during an {@link Event}
 * 
 * @author aldogrand
 *
 */
@XmlRootElement(name = "event-incident")
public class EventIncident {

	private long eventId;
	private long sequenceNumber;
	private CurrentDangerballStatus currentDangerState;
	private long participantId;
	private IncidentType type;
	private String elapsedTime;
	private Score score;
	private EventPhase eventPhase;
	
	public EventIncident() {
	}
	
	public EventIncident(long eventId, long sequenceNumber, CurrentDangerballStatus currentDangerState, long participantId, IncidentType type, String elapsedTime, Score score,EventPhase eventPhase) {
		this.eventId = eventId;
		this.sequenceNumber = sequenceNumber;
		this.currentDangerState = currentDangerState;
		this.participantId = participantId;
		this.type = type;
		this.elapsedTime = elapsedTime;
		this.score = score;
		this.eventPhase = eventPhase;
	}
	
	@XmlElement(name = "event")
	public long getEventId() {
		return eventId;
	}
	
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	@XmlElement(name = "sequence-number")
	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@XmlElement(name = "current-danger-state")
	public CurrentDangerballStatus getCurrentDangerState() {
		return currentDangerState;
	}

	public void setCurrentDangerState(CurrentDangerballStatus currentDangerState) {
		this.currentDangerState = currentDangerState;
	}

	@XmlElement(name = "participant-id")
	public long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}

	@XmlElement(name = "incident-type")
	public IncidentType getType() {
		return type;
	}

	public void setType(IncidentType type) {
		this.type = type;
	}

	@XmlElement(name = "elapsed-time")
	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

    @XmlElement(name = "score")
    @JsonSerialize(include=Inclusion.NON_NULL)
	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
	
	@XmlElement(name = "event-phase")	
	public EventPhase getEventPhase() {
		return eventPhase;
	}
	
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
