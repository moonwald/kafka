package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a Market associated with an {@link Event}
 * 
 * @author aldogrand
 * 
 */
@XmlRootElement
public class Market implements Serializable {

	private static final long serialVersionUID = -6305523022243098425L;

	private Long id;
	private String name;
	private BigDecimal handicap;
	private int sequence;
	private XMLGregorianCalendar expiry;
	private MarketType marketType;
	private Long eventId;
	private MarketStatus marketStatus;
	private List<Selection> selections;

	@XmlElement(name = "Id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "Name", nillable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "Handicap", nillable = true)
	public BigDecimal getHandicap() {
		return handicap;
	}

	public void setHandicap(BigDecimal handicap) {
		this.handicap = handicap;
	}

	@XmlElement(name = "Sequence", nillable = true)
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@XmlElement(name = "Expiry")
	public XMLGregorianCalendar getExpiry() {
		return expiry;
	}

	public void setExpiry(XMLGregorianCalendar expiry) {
		this.expiry = expiry;
	}

	@XmlElement(name = "MarketType", nillable = true)
	public MarketType getMarketType() {
		return marketType;
	}

	public void setMarketType(MarketType marketType) {
		this.marketType = marketType;
	}

	@XmlElement(name = "EventId")
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@XmlElement(name = "TradingStatus")
	public MarketStatus getMarketStatus() {
		return marketStatus;
	}

	public void setMarketStatus(MarketStatus marketStatus) {
		this.marketStatus = marketStatus;
	}

	@XmlElementWrapper(name = "Selections", namespace = "http://schemas.betgenius.com/2009/07/integration", nillable = true)
	@XmlElement(name = "Selection", namespace = "http://schemas.betgenius.com/2009/07/integration")
	public List<Selection> getSelections() {
		return selections;
	}

	public void setSelections(List<Selection> selections) {
		this.selections = selections;
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
