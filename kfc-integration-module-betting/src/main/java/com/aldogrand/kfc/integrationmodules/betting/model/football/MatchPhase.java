package com.aldogrand.kfc.integrationmodules.betting.model.football;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A Phase in a football match related to a given {@link MatchStateFootball}
 * @author aldogrand
 *
 */
@XmlRootElement
public class MatchPhase implements Serializable {

	private static final long serialVersionUID = 550006953266146190L;

	private int phase;
	private XMLGregorianCalendar startTime;
	private String stoppageTime;
	private boolean isGameInPlay;
	private String standardPhaseDuration;
	private String startTimeOffset;
	private boolean isExtraTime;
	private boolean isTimePhased;
	private boolean isBookingsAllowed;
	private boolean isCornersAllowed;
	private boolean isGoalsAllowed;
	
	@JsonProperty("Phase")
	public int getPhase() {
		return phase;
	}
	
	public void setPhase(int phase) {
		this.phase = phase;
	}
	
	@JsonProperty("StartTime")
	public XMLGregorianCalendar getStartTime() {
		return startTime;
	}
	
	public void setStartTime(XMLGregorianCalendar startTime) {
		this.startTime = startTime;
	}
	
	@JsonProperty("StoppageTime")
	public String getStoppageTime() {
		return stoppageTime;
	}
	
	public void setStoppageTime(String stoppageTime) {
		this.stoppageTime = stoppageTime;
	}
	
	@JsonProperty("IsGameInPlay")
	public boolean isGameInPlay() {
		return isGameInPlay;
	}
	
	public void setGameInPlay(boolean isGameInPlay) {
		this.isGameInPlay = isGameInPlay;
	}
	
	@JsonProperty("StandardPhaseDuration")
	public String getStandardPhaseDuration() {
		return standardPhaseDuration;
	}
	
	public void setStandardPhaseDuration(String standardPhaseDuration) {
		this.standardPhaseDuration = standardPhaseDuration;
	}
	
	@JsonProperty("StartTimeOffset")
	public String getStartTimeOffset() {
		return startTimeOffset;
	}
	
	public void setStartTimeOffset(String startTimeOffset) {
		this.startTimeOffset = startTimeOffset;
	}
	
	@JsonProperty("IsExtraTime")
	public boolean isExtraTime() {
		return isExtraTime;
	}
	
	public void setExtraTime(boolean isExtraTime) {
		this.isExtraTime = isExtraTime;
	}
	
	@JsonProperty("IsTimedPhased")
	public boolean isTimePhased() {
		return isTimePhased;
	}
	
	public void setTimePhased(boolean isTimePhased) {
		this.isTimePhased = isTimePhased;
	}
	
	@JsonProperty("IsBookingsAllowed")
	public boolean isBookingsAllowed() {
		return isBookingsAllowed;
	}
	
	public void setBookingsAllowed(boolean isBookingsAllowed) {
		this.isBookingsAllowed = isBookingsAllowed;
	}
	
	@JsonProperty("IsGoalsAllowed")
	public boolean isCornersAllowed() {
		return isCornersAllowed;
	}
	
	public void setCornersAllowed(boolean isCornersAllowed) {
		this.isCornersAllowed = isCornersAllowed;
	}
	
	@JsonProperty("IsCornersAllowed")
	public boolean isGoalsAllowed() {
		return isGoalsAllowed;
	}
	
	public void setGoalsAllowed(boolean isGoalsAllowed) {
		this.isGoalsAllowed = isGoalsAllowed;
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
