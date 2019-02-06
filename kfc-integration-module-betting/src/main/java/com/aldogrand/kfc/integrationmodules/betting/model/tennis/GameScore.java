package com.aldogrand.kfc.integrationmodules.betting.model.tennis;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * Score of a set in a Tennis Match
 * 
 * @author aldogrand
 *
 */
public class GameScore implements Serializable {

	private static final long serialVersionUID = -4236777977270441810L;

	private String dispCompOneScore;
	private String dispCompTwoScore;
	private int compOneScore;
	private int compTwoScore;
	private boolean isComplete;
	private int gameType;
	
	@JsonProperty("DisplayCompetitorOneScore")
	public String getDispCompOneScore() {
		return dispCompOneScore;
	}
	
	public void setDispCompOneScore(String dispCompOneScore) {
		this.dispCompOneScore = dispCompOneScore;
	}
	
	@JsonProperty("DisplayCompetitorTwoScore")
	public String getDispCompTwoScore() {
		return dispCompTwoScore;
	}
	
	public void setDispCompTwoScore(String dispCompTwoScore) {
		this.dispCompTwoScore = dispCompTwoScore;
	}
	
	@JsonProperty("CompetitorOneScore")
	public int getCompOneScore() {
		return compOneScore;
	}
	
	public void setCompOneScore(int compOneScore) {
		this.compOneScore = compOneScore;
	}
	
	@JsonProperty("CompetitorTwoScore")
	public int getCompTwoScore() {
		return compTwoScore;
	}
	
	public void setCompTwoScore(int compTwoScore) {
		this.compTwoScore = compTwoScore;
	}
	
	@JsonProperty("IsComplete")
	public boolean isComplete() {
		return isComplete;
	}
	
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	@JsonProperty("GameType")
	public int getGameType() {
		return gameType;
	}
	
	public void setGameType(int gameType) {
		this.gameType = gameType;
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
