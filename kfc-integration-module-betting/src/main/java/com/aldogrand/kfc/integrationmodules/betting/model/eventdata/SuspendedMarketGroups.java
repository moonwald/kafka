package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Market groups which are suspended for a given {@link Event}
 * 
 * @author aldogrand
 *
 */
@XmlRootElement
public class SuspendedMarketGroups implements Serializable {
	
	private static final long serialVersionUID = -8612337232086575286L;

	private boolean goals;
	private boolean corners;
	private boolean bookings;
	
	@XmlElement(name = "Goals")
	public boolean isGoals() {
		return goals;
	}
	
	public void setGoals(boolean goals) {
		this.goals = goals;
	}
	
	@XmlElement(name = "Corners")
	public boolean isCorners() {
		return corners;
	}
	
	public void setCorners(boolean corners) {
		this.corners = corners;
	}
	
	@XmlElement(name = "Bookings")
	public boolean isBookings() {
		return bookings;
	}
	
	public void setBookings(boolean bookings) {
		this.bookings = bookings;
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
