package com.aldogrand.kfc.integrationmodules.betting.model.eventdata;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A Selection in a given {@link Market}
 * 
 * @author aldogrand
 *
 */
@XmlRootElement
public class Selection implements Serializable {

	private static final long serialVersionUID = 6423118234890305841L;

	private Long id;
	private String name;
	private int displayOrder;
	private Integer competitorId;
	private SelectionStatus tradingStatus;
	private int numerator;
	private int denominator;
	private BigDecimal european;
	private ResultStatus resultStatus;// Enum in xsd
	private String additionalResultdata;
	// Also need to add range from XSD
	private Outcome outcome;
	
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
	
	@XmlElement(name = "DisplayOrder")
	public int getDisplayOrder() {
		return displayOrder;
	}
	
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	@XmlElement(name = "CompetitorId", nillable = true)
	public Integer getCompetitorId() {
		return competitorId;
	}
	
	public void setCompetitorId(Integer competitorId) {
		this.competitorId = competitorId;
	}
	
	@XmlElement(name = "TradingStatus")
	public SelectionStatus getTradingStatus() {
		return tradingStatus;
	}
	
	public void setTradingStatus(SelectionStatus tradingStatus) {
		this.tradingStatus = tradingStatus;
	}
	
	@XmlElement(name = "Numerator")
	public int getNumerator() {
		return numerator;
	}
	
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}
	
	@XmlElement(name = "Denominator")
	public int getDenominator() {
		return denominator;
	}
	
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
	
	@XmlElement(name = "European")
	public BigDecimal getEuropean() {
		return european;
	}
	
	public void setEuropean(BigDecimal european) {
		this.european = european;
	}
	
	@XmlElement(name = "ResultStatus")
	public ResultStatus getResultStatus() {
		return resultStatus;
	}
	
	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	@XmlElement(name = "AdditionalResultData", nillable = true)
	public String getAdditionalResultdata() {
		return additionalResultdata;
	}
	
	public void setAdditionalResultdata(String additionalResultdata) {
		this.additionalResultdata = additionalResultdata;
	}
	
	@XmlElement(name = "Outcome", nillable = true)
	public Outcome getOutcome() {
		return outcome;
	}
	
	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
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
