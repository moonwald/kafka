package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents the type of the associated {@link Market}
 * 
 * @author aldogrand
 * 
 */
@XmlRootElement
public class MarketType implements Serializable {

	private static final long serialVersionUID = 4595369821268369823L;

	private int id;
	private String name;
	private boolean ishandicap;
	private boolean isoverunder;

	@XmlElement(name = "Id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name = "Name", nillable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "IsHandicap")
	public boolean isHandicap() {
		return ishandicap;
	}

	public void setIshandicap(boolean ishandicap) {
		this.ishandicap = ishandicap;
	}

	@XmlElement(name = "IsOverUnder")
	public boolean isOverunder() {
		return isoverunder;
	}

	public void setIsoverunder(boolean isoverunder) {
		this.isoverunder = isoverunder;
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
