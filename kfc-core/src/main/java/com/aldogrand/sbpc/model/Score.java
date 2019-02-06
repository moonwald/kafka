package com.aldogrand.sbpc.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents the current score of the given {@link Event}
 * 
 * @author aldogrand, Aldo Grand
 *
 */
@XmlRootElement(name = "score")
public class Score {

	private ScoreType scoreType;
	private Integer home;
	private Integer away;

	/**
	 * Default constructor.
	 */
	public Score() {
		super();
	}

	/**
	 * @param scoreType
	 * @param home
	 * @param away
	 */
	public Score(ScoreType scoreType, Integer home, Integer away) {
		this();
		this.scoreType = scoreType;
		this.home = home;
		this.away = away;
	}

	/**
	 * Retrieves the type of score which varies with Sport
	 * 
	 * @return
	 */
	@XmlElement(name = "score-type")
	public ScoreType getScoreType() {
		return scoreType;
	}
	
	/**
	 * Set the score type for this current score
	 * 
	 * @param scoreType
	 */
	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}
	
	/**
	 * @return the home
	 */
	@XmlElement(name = "home")
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
	@XmlElement(name = "away")
	public Integer getAway() {
		return away;
	}

	/**
	 * @param away the away to set
	 */
	public void setAway(Integer away) {
		this.away = away;
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
