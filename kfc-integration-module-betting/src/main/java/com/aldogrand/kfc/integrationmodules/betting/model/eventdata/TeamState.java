package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents statistics for a given {@link Team}
 *  
 * @author aldogrand
 *
 */
@XmlRootElement(name = "TeamState")
public class TeamState implements Serializable {

	private static final long serialVersionUID = 3697404050407030377L;
	
	private int goals;
	private int corners;
	private int yellowcards;
	private int redCards;

	@XmlElement(name = "Goals")
	public int getGoals() {
		return goals;
	}
	
	public void setGoals(int goals) {
		this.goals = goals;
	}
	
	@XmlElement(name = "Corners")
	public int getCorners() {
		return corners;
	}
	
	public void setCorners(int corners) {
		this.corners = corners;
	}
	
	@XmlElement(name = "YellowCards")
	public int getYellowcards() {
		return yellowcards;
	}
	
	public void setYellowcards(int yellowcards) {
		this.yellowcards = yellowcards;
	}
	
	@XmlElement(name = "RedCards")
	public int getRedCards() {
		return redCards;
	}
	
	public void setRedCards(int redCards) {
		this.redCards = redCards;
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
