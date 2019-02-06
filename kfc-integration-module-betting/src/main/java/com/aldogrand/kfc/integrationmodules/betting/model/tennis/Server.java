package com.aldogrand.kfc.integrationmodules.betting.model.tennis;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The current server in a Tennis Match
 * 
 * @author aldogrand
 *
 */
public class Server implements Serializable {

	private static final long serialVersionUID = 118205613219183326L;
	
	private int competitor;
	private int player;
	
	@JsonProperty("Competitor")
	public int getCompetitor() {
		return competitor;
	}
	
	public void setCompetitor(int competitor) {
		this.competitor = competitor;
	}
	
	@JsonProperty("Player")
	public int getPlayer() {
		return player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
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
