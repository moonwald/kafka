package com.aldogrand.kfc.integrationmodules.betting.model.tennis;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Details of all the scores in a Tennis Match
 * 
 * @author aldogrand
 *
 */
@XmlRootElement
public class MatchScore implements Serializable {

	private static final long serialVersionUID = 1282105932314047563L;

	private GameScore gameScore;
	private List<SetScore> setScores;
	private SetScore scoreInSets;
	private SetScore setScore;
	private Server server;
	private boolean isInSequence;
	private boolean isMatchComplete;
	
	@JsonProperty("GameScore")
	public GameScore getGameScore() {
		return gameScore;
	}
	
	public void setGameScore(GameScore gameScore) {
		this.gameScore = gameScore;
	}
	
	@JsonProperty("SetScores")
	public List<SetScore> getSetScores() {
		return setScores;
	}
	
	public void setSetScores(List<SetScore> setScores) {
		this.setScores = setScores;
	}
	
	@JsonProperty("ScoreInSets")
	public SetScore getScoreInSets() {
		return scoreInSets;
	}
	
	public void setScoreInSets(SetScore scoreInSets) {
		this.scoreInSets = scoreInSets;
	}
	
	@JsonProperty("SetScore")
	public SetScore getSetScore() {
		return setScore;
	}
	
	public void setSetScore(SetScore setScore) {
		this.setScore = setScore;
	}
	
	@JsonProperty("Server")
	public Server getServer() {
		return server;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	@JsonProperty("IsInSequence")
	public boolean isInSequence() {
		return isInSequence;
	}
	
	public void setInSequence(boolean isInSequence) {
		this.isInSequence = isInSequence;
	}
	
	@JsonProperty("IsMatchComplete")
	public boolean isMatchComplete() {
		return isMatchComplete;
	}
	
	public void setMatchComplete(boolean isMatchComplete) {
		this.isMatchComplete = isMatchComplete;
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
