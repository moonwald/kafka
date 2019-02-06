package com.aldogrand.kfc.integrationmodules.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.aldogrand.sbpc.model.IncidentType;

/**
 * Represents an in-play incident in a Match
 * 
 * @author aldogrand
 *
 */
@XmlRootElement(name = "incident")
public class Incident {

	private int eventId;
	private Date incidentDateTime;
	private IncidentType type;
	private int participantId;
	
	public Incident(int eventId, Date incidentDateTime, IncidentType type, int participantId) {
		this.eventId = eventId;
		this.incidentDateTime = incidentDateTime;
		this.type = type;
		this.participantId = participantId;
	}
	
	@XmlElement(name = "eventId")
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	@XmlElement(name = "incidentDateTime")
	public Date getIncidentDateTime() {
		return incidentDateTime;
	}
	
	public void setIncidentDateTime(Date incidentDateTime) {
		this.incidentDateTime = incidentDateTime;
	}
	
	@XmlElement(name = "type")
	public IncidentType getType() {
		return type;
	}
	
	public void setType(IncidentType type) {
		this.type = type;
	}
	
	@XmlElement(name = "participant")
	public int getParticipantId() {
		return participantId;
	}
	
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	
}

