package com.aldogrand.sbpc.connectors.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a Player involved in an {@link Event}
 * 
 */
public class Player extends Participant {

	private static final long serialVersionUID = -7022427065572031767L;

	private String firstName;
	private String lastName;
	
	/**
	 * Retrieves the players first name
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Retrieves the players last name
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Retrieves the players last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
