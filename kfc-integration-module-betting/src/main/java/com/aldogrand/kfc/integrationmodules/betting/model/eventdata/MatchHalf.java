package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.Duration;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Describes details of a given half of a Match
 * 
 * @author aldogrand
 *
 */
@XmlRootElement(name = "MatchHalf")
public class MatchHalf implements Serializable {

	private static final long serialVersionUID = -7373728086223626127L;

	private Date startTime;
	private Duration stoppageTime;
	
	@XmlElement(name = "StartTime")
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@XmlElement(name = "StoppageTime")
	public Duration getStoppageTime() {
		return stoppageTime;
	}
	
	public void setStoppageTime(Duration stoppageTime) {
		this.stoppageTime = stoppageTime;
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
