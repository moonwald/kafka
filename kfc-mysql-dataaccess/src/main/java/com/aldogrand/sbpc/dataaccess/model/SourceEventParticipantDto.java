package com.aldogrand.sbpc.dataaccess.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.PositionType;

/**
 * A participant in the {@link SourceEvent} which could be either
 * a team or a player
 * 
 * @author aldogrand
 *
 */
@Entity(name = "SourceEventParticipant")
@Table(name = "source_event_participant", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "participant_type"})})
@Cacheable(false)
public class SourceEventParticipantDto extends AbstractFetchableDto {

	private static final long serialVersionUID = 3271360624489389397L;

	private SourceEventDto sourceEvent;
	private long sourceParticipantId;
	private String name;
	private String firstName;
	private String lastName;
	private PositionType participantType;
	
	 /**
     * @return the event
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "source_event_id", nullable = false)
    public SourceEventDto getSourceEvent() {
        return sourceEvent;
    }

    /**
     * @param sourceEvent the event to set
     */
    public void setSourceEvent(SourceEventDto sourceEvent) {
        this.sourceEvent = sourceEvent;
    }

    @Column(name = "source_participant_id", nullable = false)
    public long getSourceParticipantId() {
		return sourceParticipantId;
	}

	public void setSourceParticipantId(long sourceParticipantId) {
		this.sourceParticipantId = sourceParticipantId;
	}

	@Column(name = "name", length = 50, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "firstname", length = 50, nullable = true)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastname", length = 50, nullable = true)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "participant_type", length = 25, nullable = true)
    @Enumerated(EnumType.STRING)
	public PositionType getPositionType()
	{
		return participantType == null ? PositionType.UNDEFINED : participantType;
	}

	public void setPositionType(PositionType positionType) {
		this.participantType = positionType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
