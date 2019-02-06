package com.aldogrand.kfc.integrationmodules.betting.model.football;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The score in a football match
 * 
 * @author aldogrand
 *
 */
@XmlRootElement
public class Score implements Serializable {

	private static final long serialVersionUID = 3487016815094829500L;

	private int home;
	private int away;
	private boolean areReliable;
	private int normalTimeHome;
	private int normalTimeAway;
	private int extraTimeHome;
	private int extraTimeAway;
	private int penaltyHome;
	private int penaltyAway;
	
	@JsonProperty("Home")
	public int getHome() {
		return home;
	}
	
	public void setHome(int home) {
		this.home = home;
	}
	
	@JsonProperty("Away")
	public int getAway() {
		return away;
	}
	
	public void setAway(int away) {
		this.away = away;
	}
	
	@JsonProperty("AreReliable")
	public boolean isAreReliable() {
		return areReliable;
	}
	
	public void setAreReliable(boolean areReliable) {
		this.areReliable = areReliable;
	}
	
	@JsonProperty("NormalTimeHome")
	public int getNormalTimeHome() {
		return normalTimeHome;
	}
	
	public void setNormalTimeHome(int normalTimeHome) {
		this.normalTimeHome = normalTimeHome;
	}
	
	@JsonProperty("NormalTimeAway")
	public int getNormalTimeAway() {
		return normalTimeAway;
	}
	
	public void setNormalTimeAway(int normalTimeAway) {
		this.normalTimeAway = normalTimeAway;
	}
	
	@JsonProperty("ExtraTimeHome")
	public int getExtraTimeHome() {
		return extraTimeHome;
	}
	
	public void setExtraTimeHome(int extraTimeHome) {
		this.extraTimeHome = extraTimeHome;
	}
	
	@JsonProperty("ExtraTimeAway")
	public int getExtraTimeAway() {
		return extraTimeAway;
	}
	
	public void setExtraTimeAway(int extraTimeAway) {
		this.extraTimeAway = extraTimeAway;
	}
	
	@JsonProperty("PenaltyHome")
	public int getPenaltyHome() {
		return penaltyHome;
	}
	
	public void setPenaltyHome(int penaltyHome) {
		this.penaltyHome = penaltyHome;
	}
	
	@JsonProperty("PenaltyAway")
	public int getPenaltyAway() {
		return penaltyAway;
	}
	
	public void setPenaltyAway(int penaltyAway) {
		this.penaltyAway = penaltyAway;
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
