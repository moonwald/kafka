package com.aldogrand.sbpc.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * <b>Title</b> GFeedSetting
 * </p>
 * <p>
 * <b>Description</b> An G Feed Setting. 
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Cillian Kelly
 * @version 1.0
 */
@XmlRootElement(name = "gfeed-setting")
@XmlType(propOrder = {"id", "eventId", "otherPriceThreshold", "oldPriceThreshold"})
public class GFeedSetting implements Serializable
{

	private static final long serialVersionUID = -4776849964585765202L;
	
	private Long id;
    
    private Long eventId;
    
    private Double otherPriceThreshold;
    
    private Double oldPriceThreshold;
    
    private Double anchorPercentage;

	/**
	 * @return the anchorPercentage
	 */
    @XmlElement(name = "anchor-percentage")
	public Double getAnchorPercentage() {
		return anchorPercentage;
	}

	/**
	 * @param anchorPercentage the anchorPercentage to set
	 */
	public void setAnchorPercentage(Double anchorPercentage) {
		this.anchorPercentage = anchorPercentage;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the eventId
	 */
	@XmlElement(name = "event-id")
	public Long getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the otherPriceThreshold
	 */
	@XmlElement(name = "other-price-threshold")
	public Double getOtherPriceThreshold() {
		return otherPriceThreshold;
	}

	/**
	 * @param otherPriceThreshold the otherPriceThreshold to set
	 */
	public void setOtherPriceThreshold(Double otherPriceThreshold) {
		this.otherPriceThreshold = otherPriceThreshold;
	}
	
	/**
	 * @return the oldPriceThreshold
	 */
	@XmlElement(name = "old-price-threshold")
	public Double getOldPriceThreshold() {
		return oldPriceThreshold;
	}

	/**
	 * @param oldPriceThreshold the oldPriceThreshold to set
	 */
	public void setOldPriceThreshold(Double oldPriceThreshold) {
		this.oldPriceThreshold = oldPriceThreshold;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((anchorPercentage == null) ? 0 : anchorPercentage.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((oldPriceThreshold == null) ? 0 : oldPriceThreshold
						.hashCode());
		result = prime
				* result
				+ ((otherPriceThreshold == null) ? 0 : otherPriceThreshold
						.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GFeedSetting other = (GFeedSetting) obj;
		if (anchorPercentage == null) {
			if (other.anchorPercentage != null)
				return false;
		} else if (!anchorPercentage.equals(other.anchorPercentage))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (oldPriceThreshold == null) {
			if (other.oldPriceThreshold != null)
				return false;
		} else if (!oldPriceThreshold.equals(other.oldPriceThreshold))
			return false;
		if (otherPriceThreshold == null) {
			if (other.otherPriceThreshold != null)
				return false;
		} else if (!otherPriceThreshold.equals(other.otherPriceThreshold))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GFeedSetting [id=");
		builder.append(id);
		builder.append(", eventId=");
		builder.append(eventId);
		builder.append(", otherPriceThreshold=");
		builder.append(otherPriceThreshold);
		builder.append(", oldPriceThreshold=");
		builder.append(oldPriceThreshold);
		builder.append(", anchorPercentage=");
		builder.append(anchorPercentage);
		builder.append("]");
		return builder.toString();
	}

	
    
    
    
}
