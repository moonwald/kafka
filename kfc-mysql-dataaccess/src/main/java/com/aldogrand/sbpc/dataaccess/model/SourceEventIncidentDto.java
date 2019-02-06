package com.aldogrand.sbpc.dataaccess.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.CurrentDangerballStatus;
import com.aldogrand.sbpc.model.EventPhase;
import com.aldogrand.sbpc.model.IncidentType;

/**
 * An in-play incident in a game
 * 
 * @author aldogrand
 *
 */
@Entity(name = "SourceEventIncident")
@Table(name = "source_event_incident",
uniqueConstraints = {@UniqueConstraint(columnNames = {"source_event", "incident_type", "sequence_number"})})	 
@Cacheable
public class SourceEventIncidentDto extends AbstractFetchableDto {

	private static final long serialVersionUID = -2750672733222014542L;

	private SourceEventDto sourceEvent;
	private ScoreDto score;
	private EventPhase eventPhase;
	private long sequenceNumber;
	private Date elapsedTime;
	private IncidentType incidentType;
	private CurrentDangerballStatus dangerballStatus;
	private long participantId; //not currently populated but will be in the future

	/**
     * Create instance
     */
    public SourceEventIncidentDto()
    {
        super();
    }

    public SourceEventIncidentDto(EventPhase eventPhase, long sequenceNumber, Date elapsedTime, IncidentType incidentType, CurrentDangerballStatus dangerballStatus, long participantId) {
		super();
		this.eventPhase = eventPhase;
		this.sequenceNumber = sequenceNumber;
		this.elapsedTime = elapsedTime;
		this.incidentType = incidentType;
		this.dangerballStatus = dangerballStatus;
		this.participantId = participantId;
	}
    
    @Column(name = "event_phase", length = 50, nullable = true)
    @Enumerated(EnumType.STRING)
    public EventPhase getEventPhase() {
		return eventPhase;
	}
    
    public void setEventPhase(EventPhase eventPhase) {
		this.eventPhase = eventPhase;
	}
    
	
	@Column(name = "sequence_number", nullable = true)
	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Column(name = "elapsed_time", nullable = true) 
	@Temporal(TemporalType.TIME)
	public Date getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Date timestamp) {
		this.elapsedTime = timestamp;
	}

	@Column(name = "incident_type", length = 50, nullable = true)
    @Enumerated(EnumType.STRING)
	public IncidentType getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}
	
	@Column(name = "dangerball_status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
	public CurrentDangerballStatus getDangerballStatus() {
		return dangerballStatus;
	}

	public void setDangerballStatus(CurrentDangerballStatus dangerballStatus) {
		this.dangerballStatus = dangerballStatus;
	}
	

	@Column(name = "participant_id", nullable = true)
	public long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}

	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "score", nullable = false)
	public ScoreDto getScore() {
		return score;
	}

	public void setScore(ScoreDto score) {
		this.score = score;
	}
	
	/**
     * @return the SourceEvent
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_event", nullable = false)
    public SourceEventDto getSourceEvent()
    {
        return sourceEvent;
    }

    /**
     * @param the SourceEvent to set
     */
    public void setSourceEvent(SourceEventDto sourceEvent)
    {
        this.sourceEvent = sourceEvent;
    }
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
