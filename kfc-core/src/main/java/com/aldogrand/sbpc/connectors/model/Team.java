package com.aldogrand.sbpc.connectors.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.aldogrand.sbpc.model.PositionType;

/**
 * Represents a Team involved in an {@link Event}
 * 
 * @author aldogrand
 *
 */
public class Team extends Participant {

	private static final long serialVersionUID = 1950476397304691454L;

	private PositionType positionType;

	/**
	 * Retrieves the PositionType
	 * 
	 * @return
	 */
	public PositionType getPositionType() {
		return positionType;
	}

	/**
	 * Sets the PositionType
	 * 
	 * @param position
	 */
	public void setPositionType(PositionType position) {
		this.positionType = position;
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
