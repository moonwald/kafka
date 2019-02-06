package com.aldogrand.kfc.integrationmodules.betting.model.football;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents the current state of a Football match
 * 
 * @author aldogrand
 *
 */
public class MatchStateFootball extends BettingMatchState implements Serializable {

	private static final long serialVersionUID = -3647950320089824209L;
	
	private List<MatchPhase> allMatchPhaseDetails;
	private int currentMatchPhase;
	private boolean matchPhaseIsConfirmed;
	private int currentDangerState;
	private int currentBookingState;
	private Score goals;
	private Score corners;
	private Score yellowCards;
	private Score redCards;
	private Score substitutions;
	private List<MatchEvent> matchEvent;
	private List<Integer> currentHomePlayerIds;
	private List<Integer> currentAwayPlayerIds;
	private List<Integer> waitingHomePlayerIds;
	private List<Integer> waitingAwayPlayerIds;
	private boolean isSecondLeg;
	private Score firstLegScore;
	
	
	@JsonProperty("AllMatchPhaseDetails")
	public List<MatchPhase> getAllMatchPhaseDetails() {
		return allMatchPhaseDetails;
	}
	
	public void setAllMatchPhaseDetails(List<MatchPhase> allMatchPhaseDetails) {
		this.allMatchPhaseDetails = allMatchPhaseDetails;
	}
	
	@JsonProperty("CurrentMatchPhase")
	public int getCurrentMatchPhase() {
		return currentMatchPhase;
	}
	
	public void setCurrentMatchPhase(int currentMatchPhase) {
		this.currentMatchPhase = currentMatchPhase;
	}
	
	@JsonProperty("MatchPhaseIsConfirmed")
	public boolean isMatchPhaseIsConfirmed() {
		return matchPhaseIsConfirmed;
	}
	
	public void setMatchPhaseIsConfirmed(boolean matchPhaseIsConfirmed) {
		this.matchPhaseIsConfirmed = matchPhaseIsConfirmed;
	}
	
	@JsonProperty("CurrentDangerState")
	public int getCurrentDangerState() {
		return currentDangerState;
	}
	
	public void setCurrentDangerState(int currentDangerState) {
		this.currentDangerState = currentDangerState;
	}
	
	@JsonProperty("CurrentBookingState")
	public int getCurrentBookingState() {
		return currentBookingState;
	}
	
	public void setCurrentBookingState(int currentBookingState) {
		this.currentBookingState = currentBookingState;
	}
	
	@JsonProperty("Goals")
	public Score getGoals() {
		return goals;
	}
	
	public void setGoals(Score goals) {
		this.goals = goals;
	}
	
	@JsonProperty("Corners")
	public Score getCorners() {
		return corners;
	}
	
	public void setCorners(Score corners) {
		this.corners = corners;
	}
	
	@JsonProperty("YellowCards")
	public Score getYellowCards() {
		return yellowCards;
	}
	
	public void setYellowCards(Score yellowCards) {
		this.yellowCards = yellowCards;
	}
	
	@JsonProperty("RedCards")
	public Score getRedCards() {
		return redCards;
	}
	
	public void setRedCards(Score redCards) {
		this.redCards = redCards;
	}
	
	@JsonProperty("Substitutions")
	public Score getSubstitutions() {
		return substitutions;
	}
	
	public void setSubstitutions(Score substitutions) {
		this.substitutions = substitutions;
	}
	
	@JsonProperty("MatchEvents")
	public List<MatchEvent> getMatchEvent() {
		return matchEvent;
	}
	
	public void setMatchEvent(List<MatchEvent> matchEvent) {
		this.matchEvent = matchEvent;
	}
	
	@JsonProperty("CurrentHomePlayerIds")
	public List<Integer> getCurrentHomePlayerIds() {
		return currentHomePlayerIds;
	}
	
	public void setCurrentHomePlayerIds(List<Integer> currentHomePlayerIds) {
		this.currentHomePlayerIds = currentHomePlayerIds;
	}
	
	@JsonProperty("CurrentAwayPlayerIds")
	public List<Integer> getCurrentAwayPlayerIds() {
		return currentAwayPlayerIds;
	}
	
	public void setCurrentAwayPlayerIds(List<Integer> currentAwayPlayerIds) {
		this.currentAwayPlayerIds = currentAwayPlayerIds;
	}
	
	@JsonProperty("WaitingHomePlayerIds")
	public List<Integer> getWaitingHomePlayerIds() {
		return waitingHomePlayerIds;
	}
	
	public void setWaitingHomePlayerIds(List<Integer> waitingHomePlayerIds) {
		this.waitingHomePlayerIds = waitingHomePlayerIds;
	}
	
	@JsonProperty("WaitingAwayPlayerIds")
	public List<Integer> getWaitingAwayPlayerIds() {
		return waitingAwayPlayerIds;
	}
	
	public void setWaitingAwayPlayerIds(List<Integer> waitingAwayPlayerIds) {
		this.waitingAwayPlayerIds = waitingAwayPlayerIds;
	}
	
	@JsonProperty("IsSecondLeg")
	public boolean isSecondLeg() {
		return isSecondLeg;
	}
	
	public void setSecondLeg(boolean isSecondLeg) {
		this.isSecondLeg = isSecondLeg;
	}
	
	@JsonProperty("FirstLegScore")
	public Score getFirstLegScore() {
		return firstLegScore;
	}
	
	public void setFirstLegScore(Score firstLegScore) {
		this.firstLegScore = firstLegScore;
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
