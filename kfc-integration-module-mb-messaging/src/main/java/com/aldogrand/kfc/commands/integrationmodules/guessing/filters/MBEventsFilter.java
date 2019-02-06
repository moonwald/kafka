package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.EventsFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBEventsFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBEventsFilter <br/>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */

public class MBEventsFilter extends EventsFilter {

	String [] categoryIds;
	String [] marketStates;
	String [] marketTypes;
	String [] gradingTypes;
	String order;
	String [] sportIds;
	String [] states;
	String [] tagIds;
	Boolean includeMarkets;
	Boolean includeRunners;
	String [] runnerStates;
	Boolean includePrices;
	Integer priceDepth;
	String priceOrder;
	String side;
	String oddsType;
	String currency;
	String exchangeType;
	String language;
	
	public MBEventsFilter() {
		super();
	}

	public String[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String[] getMarketStates() {
		return marketStates;
	}

	public void setMarketStates(String[] marketStates) {
		this.marketStates = marketStates;
	}

	public String[] getMarketTypes() {
		return marketTypes;
	}

	public void setMarketTypes(String[] marketTypes) {
		this.marketTypes = marketTypes;
	}

	public String[] getGradingTypes() {
		return gradingTypes;
	}

	public void setGradingTypes(String[] gradingTypes) {
		this.gradingTypes = gradingTypes;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String[] getSportIds() {
		return sportIds;
	}

	public void setSportIds(String[] sportIds) {
		this.sportIds = sportIds;
	}

	public String[] getStates() {
		return states;
	}

	public void setStates(String[] states) {
		this.states = states;
	}

	public String[] getTagIds() {
		return tagIds;
	}

	public void setTagIds(String[] tagIds) {
		this.tagIds = tagIds;
	}

	public Boolean getIncludeMarkets() {
		return includeMarkets;
	}

	public void setIncludeMarkets(Boolean includeMarkets) {
		this.includeMarkets = includeMarkets;
	}

	public Boolean getIncludeRunners() {
		return includeRunners;
	}

	public void setIncludeRunners(Boolean includeRunners) {
		this.includeRunners = includeRunners;
	}

	public String[] getRunnerStates() {
		return runnerStates;
	}

	public void setRunnerStates(String[] runnerStates) {
		this.runnerStates = runnerStates;
	}

	public Boolean getIncludePrices() {
		return includePrices;
	}

	public void setIncludePrices(Boolean includePrices) {
		this.includePrices = includePrices;
	}

	public Integer getPriceDepth() {
		return priceDepth;
	}

	public void setPriceDepth(Integer priceDepth) {
		this.priceDepth = priceDepth;
	}

	public String getPriceOrder() {
		return priceOrder;
	}

	public void setPriceOrder(String priceOrder) {
		this.priceOrder = priceOrder;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getOddsType() {
		return oddsType;
	}

	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
