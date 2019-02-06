package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.Duration;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement
public class MatchState implements Serializable {

	private static final long serialVersionUID = 6288233714545253830L;

	private MatchPhase phase;
	private Duration elapsedTimeInHalf;
	private TeamState homeTeam; 
	private TeamState awayTeam;
	private MatchHalf firstHalf;
	private MatchHalf secondHalf;
	private SuspendedMarketGroups suspendedMarketGroups;
	private EventSource eventSource;
	private MatchHalf extraTimeFirstHalf;
	private MatchHalf extraTimeSecondHalf;
	
	
	@XmlElement(name = "Phase", nillable = true)
	public MatchPhase getMatchPhase() {
		return phase;
	}
	
	public void setMatchPhase(MatchPhase matchPhase) {
		this.phase = matchPhase;
	}
	
	@XmlElement(name = "ElapsedTimeInHalf", nillable = true)
	public Duration getElapsedTimeInHalf() {
		return elapsedTimeInHalf;
	}
	
	public void setElapsedTimeInHalf(Duration elapsedTimeInHalf) {
		this.elapsedTimeInHalf = elapsedTimeInHalf;
	}
	
	@XmlElement(name = "HomeTeam", nillable = true)
	public TeamState getHomeTeam() {
		return homeTeam;
	}
	
	public void setHomeTeam(TeamState homeTeam) {
		this.homeTeam = homeTeam;
	}
	
	@XmlElement(name = "Phase")
	public MatchPhase getPhase() {
		return phase;
	}

	public void setPhase(MatchPhase phase) {
		this.phase = phase;
	}

	@XmlElement(name = "AwayTeam", nillable = true)
	public TeamState getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(TeamState awayTeam) {
		this.awayTeam = awayTeam;
	}

	@XmlElement(name = "FirstHalf", nillable = true)
	public MatchHalf getFirstHalf() {
		return firstHalf;
	}

	public void setFirstHalf(MatchHalf firstHalf) {
		this.firstHalf = firstHalf;
	}

	@XmlElement(name = "SecondHalf", nillable = true)
	public MatchHalf getSecondHalf() {
		return secondHalf;
	}

	public void setSecondHalf(MatchHalf secondHalf) {
		this.secondHalf = secondHalf;
	}

	@XmlElement(name = "SuspendedMarketGroups", nillable = true)
	public SuspendedMarketGroups getSuspendedMarketGroups() {
		return suspendedMarketGroups;
	}

	public void setSuspendedMarketGroups(SuspendedMarketGroups suspendedMarketGroups) {
		this.suspendedMarketGroups = suspendedMarketGroups;
	}

	@XmlElement(name = "EventSource")
	public EventSource getEventSource() {
		return eventSource;
	}

	public void setEventSource(EventSource eventSource) {
		this.eventSource = eventSource;
	}

	@XmlElement(name = "ExtraTimeFirstHalf", nillable = true)
	public MatchHalf getExtraTimeFirstHalf() {
		return extraTimeFirstHalf;
	}

	public void setExtraTimeFirstHalf(MatchHalf extraTimeFirstHalf) {
		this.extraTimeFirstHalf = extraTimeFirstHalf;
	}

	@XmlElement(name = "ExtraTimeSecondHalf", nillable = true)
	public MatchHalf getExtraTimeSecondHalf() {
		return extraTimeSecondHalf;
	}

	public void setExtraTimeSecondHalf(MatchHalf extraTimeSecondHalf) {
		this.extraTimeSecondHalf = extraTimeSecondHalf;
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
