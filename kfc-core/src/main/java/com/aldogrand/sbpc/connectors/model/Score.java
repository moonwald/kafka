package com.aldogrand.sbpc.connectors.model;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.ScoreType;

/**
 * Represents the current score for a given participant of a
 * certain type.
 * 
 * Always referenced by a given {@link Incident}
 * @author aldogrand
 *
 */
public class Score {

	private ScoreType type;
	private Integer home;
	private Integer away;
	private Date creationTime;
	private Date lastFetchTime;
	private Date lastChangeTime;
	
	public Score(ScoreType type, Integer home, Integer away) {
		this.type = type;
		this.home = home;
		this.away = away;
	}

	/**
	 * Retrieves the type of score for context
	 * 
	 * @return
	 */
	public ScoreType getType() {
		return type;
	}

	/**
	 * Sets the ScoreType
	 * 
	 * @param type
	 */
	public void setType(ScoreType type) {
		this.type = type;
	}

	/**
	 * @return the home
	 */
	public Integer getHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(Integer home) {
		this.home = home;
	}

	/**
	 * @return the away
	 */
	public Integer getAway() {
		return away;
	}

	/**
	 * @param away the away to set
	 */
	public void setAway(Integer away) {
		this.away = away;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the lastFetchTime
	 */
	public Date getLastFetchTime() {
		return lastFetchTime;
	}

	/**
	 * @param lastFetchTime the lastFetchTime to set
	 */
	public void setLastFetchTime(Date lastFetchTime) {
		this.lastFetchTime = lastFetchTime;
	}

	/**
	 * @return the lastChangeTime
	 */
	public Date getLastChangeTime() {
		return lastChangeTime;
	}

	/**
	 * @param lastChangeTime the lastChangeTime to set
	 */
	public void setLastChangeTime(Date lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
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
