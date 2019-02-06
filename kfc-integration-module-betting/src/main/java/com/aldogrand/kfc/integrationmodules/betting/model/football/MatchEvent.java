package com.aldogrand.kfc.integrationmodules.betting.model.football;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * An event in a football match
 * 
 * @author aldogrand
 * 
 */
@XmlRootElement
public class MatchEvent implements Serializable {

	private static final long serialVersionUID = 5442683610271490469L;

	private int id;
	private int matchEventType;
	private int occurredDuring;
	private String occurredAt;
	private boolean confirmed;
	private int playerId;
	private int subOnPlayerId;
	private int subOffPlayerId;
	private boolean isSubPlayerEvent;

	@JsonProperty("Id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty("MatchEventType")
	public int getMatchEventType() {
		return matchEventType;
	}

	public void setMatchEventType(int matchEventType) {
		this.matchEventType = matchEventType;
	}

	@JsonProperty("OccurredDuring")
	public int getOccurredDuring() {
		return occurredDuring;
	}

	public void setOccurredDuring(int occurredDuring) {
		this.occurredDuring = occurredDuring;
	}

	@JsonProperty("OccurredAt")
	public String getOccurredAt() {
		return occurredAt;
	}

	public void setOccurredAt(String occurredAt) {
		this.occurredAt = occurredAt;
	}

	@JsonProperty("Confirmed")
	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@JsonProperty("PlayerId")
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@JsonProperty("SubstitutedOnPlayerId")
	public int getSubOnPlayerId() {
		return subOnPlayerId;
	}

	public void setSubOnPlayerId(int subOnPlayerId) {
		this.subOnPlayerId = subOnPlayerId;
	}

	@JsonProperty("SubstitutedOffPlayerId")
	public int getSubOffPlayerId() {
		return subOffPlayerId;
	}

	public void setSubOffPlayerId(int subOffPlayerId) {
		this.subOffPlayerId = subOffPlayerId;
	}

	@JsonProperty("IsSubstitutedPlayerEvent")
	public boolean isSubPlayerEvent() {
		return isSubPlayerEvent;
	}

	public void setSubPlayerEvent(boolean isSubPlayerEvent) {
		this.isSubPlayerEvent = isSubPlayerEvent;
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
