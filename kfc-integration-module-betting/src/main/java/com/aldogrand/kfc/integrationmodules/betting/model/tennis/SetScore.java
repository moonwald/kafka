package com.aldogrand.kfc.integrationmodules.betting.model.tennis;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Score in single set of a Tennis Match
 * 
 * @author aldogrand
 *
 */
public class SetScore implements Serializable {

	private static final long serialVersionUID = -8713450370273294339L;

	private int compOneScore;
	private int compTwoScore;
	private boolean isComplete;
	private int setType;
	
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
	
	@JsonProperty("SetType")
	public int getSetType() {
		return setType;
	}
	
	public void setSetType(int setType) {
		this.setType = setType;
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