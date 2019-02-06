package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An Event which can be bet upon
 * 
 * @author aldogrand
 * 
 */
@XmlRootElement
public class Event implements Serializable {

	private static final long serialVersionUID = -328670905114767657L;

	private Long id;
	private String name;
	private String eventType;
	private XMLGregorianCalendar startTime;
	private Sport sport;
	private Season season;
	private Round round;
	private List<Team> teams;
	private List<Player> players;
	private MatchState matchState;

	@XmlElement(name = "Id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "EventType", nillable = true)
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@XmlElement(name = "StartTime")
	public XMLGregorianCalendar getStartTime() {
		return startTime;
	}

	public void setStartTime(XMLGregorianCalendar startTime) {
		this.startTime = startTime;
	}

	@XmlElement(name = "Sport", nillable = true)
	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	@XmlElement(name = "Season", nillable = true)
	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	@XmlElement(name = "Round", nillable = true)
	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	@XmlElementWrapper(name = "Teams")
	@XmlElement(name = "Team", namespace = "http://schemas.betgenius.com/2009/07/integration", nillable = true)
	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	@XmlElementWrapper(name = "Players")
	@XmlElement(name = "Player", namespace = "http://schemas.betgenius.com/2009/07/integration", nillable = true)
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	
	@XmlElement(name = "MatchState", nillable = true)
	public MatchState getMatchState() {
		return matchState;
	}

	public void setMatchState(MatchState matchState) {
		this.matchState = matchState;
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
