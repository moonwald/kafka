package com.aldogrand.kfc.integrationmodules.betting.model.tennis;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

import com.aldogrand.kfc.integrationmodules.betting.model.football.BettingMatchState;

/**
 * Represents the current state of a Tennis match
 * 
 * @author aldogrand
 *
 */
@XmlRootElement
public class MatchStateTennis extends BettingMatchState implements Serializable {

	private static final long serialVersionUID = 3497203672064535410L;

	private MatchScore currentMatchScore;
	private Server firstServerOfMatch;
	private boolean hasFirstServeFault;
	private boolean  isReliable;
	private boolean isScoreConfirmed;
	private MatchScore matchScoreHistory;
	
	
	@JsonProperty("CurrentMatchScore")
	public MatchScore getCurrentMatchScore() {
		return currentMatchScore;
	}
	
	public void setCurrentMatchScore(MatchScore currentMatchScore) {
		this.currentMatchScore = currentMatchScore;
	}
	
	@JsonProperty("FirstServerOfMatch")
	public Server getFirstServerOfMatch() {
		return firstServerOfMatch;
	}
	
	public void setFirstServerOfMatch(Server firstServerOfMatch) {
		this.firstServerOfMatch = firstServerOfMatch;
	}
	
	@JsonProperty("HasFirstServeFault")
	public boolean isFirstServeFault() {
		return hasFirstServeFault;
	}
	
	public void setFirstServeFault(boolean hasFirstServeFault) {
		this.hasFirstServeFault = hasFirstServeFault;
	}
	
	@JsonProperty("IsReliable")
	public boolean isReliable() {
		return isReliable;
	}
	
	public void setReliable(boolean isReliable) {
		this.isReliable = isReliable;
	}
	
	@JsonProperty("IsScoreConfirmed")
	public boolean isScoreConfirmed() {
		return isScoreConfirmed;
	}
	
	public void setScoreConfirmed(boolean isScoreConfirmed) {
		this.isScoreConfirmed = isScoreConfirmed;
	}
	
	@JsonProperty("MatchScoreHistory")
	public MatchScore getMatchScoreHistory() {
		return matchScoreHistory;
	}
	
	public void setMatchScoreHistory(MatchScore matchScoreHistory) {
		this.matchScoreHistory = matchScoreHistory;
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
