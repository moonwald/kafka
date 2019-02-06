package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.MarketsFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBMarketsFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBMarketsFilter <br/>
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

public class MBMarketsFilter extends MarketsFilter {
	//filled with params from https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=12451875
	
	Integer		eventId;		// event_id
	String[]	marketNames;	// names
	String		order;			// order
	Integer		offset;			// offset
	Integer		perPage;		// per-page
	String[]	states;			// states
	String[]	types;			// types	
	String[]	gradingTypes;	// grading-types
	boolean		includeRunners;	// include-runners
	String[]	runnerStates;	// runner-states
	boolean		includePrices;	// include-prices
	Integer		priceDepth;		// price-depth
	String		priceOrder;		// price-order
	String		side;			// side
	String		oddsType;		// odds-type
	String		currency;		// currency
	String		exchangeType;	// exchange-type
	String		language;		// language

	public MBMarketsFilter(){}

	/**
	 * @return the eventId
	 */
	public Integer getEventId()
	{
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId)
	{
		this.eventId = eventId;
	}

	/**
	 * @return the marketNames
	 */
	public String[] getMarketNames()
	{
		return marketNames;
	}

	/**
	 * @param marketNames the marketNames to set
	 */
	public void setMarketNames(String[] marketNames)
	{
		this.marketNames = marketNames;
	}

	/**
	 * @return the order
	 */
	public String getOrder()
	{
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order)
	{
		this.order = order;
	}

	/**
	 * @return the offset
	 */
	public Integer getOffset()
	{
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset)
	{
		this.offset = offset;
	}

	/**
	 * @return the perPage
	 */
	public Integer getPerPage()
	{
		return perPage;
	}

	/**
	 * @param perPage the perPage to set
	 */
	public void setPerPage(Integer perPage)
	{
		this.perPage = perPage;
	}

	/**
	 * @return the states
	 */
	public String[] getStates()
	{
		return states;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(String[] states)
	{
		this.states = states;
	}

	/**
	 * @return the types
	 */
	public String[] getTypes()
	{
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(String[] types)
	{
		this.types = types;
	}

	/**
	 * @return the gradingTypes
	 */
	public String[] getGradingTypes()
	{
		return gradingTypes;
	}

	/**
	 * @param gradingTypes the gradingTypes to set
	 */
	public void setGradingTypes(String[] gradingTypes)
	{
		this.gradingTypes = gradingTypes;
	}

	/**
	 * @return the includeRunners
	 */
	public boolean isIncludeRunners()
	{
		return includeRunners;
	}

	/**
	 * @param includeRunners the includeRunners to set
	 */
	public void setIncludeRunners(boolean includeRunners)
	{
		this.includeRunners = includeRunners;
	}

	/**
	 * @return the runnerStates
	 */
	public String[] getRunnerStates()
	{
		return runnerStates;
	}

	/**
	 * @param runnerStates the runnerStates to set
	 */
	public void setRunnerStates(String[] runnerStates)
	{
		this.runnerStates = runnerStates;
	}

	/**
	 * @return the includePrices
	 */
	public boolean isIncludePrices()
	{
		return includePrices;
	}

	/**
	 * @param includePrices the includePrices to set
	 */
	public void setIncludePrices(boolean includePrices)
	{
		this.includePrices = includePrices;
	}

	/**
	 * @return the priceDepth
	 */
	public Integer getPriceDepth()
	{
		return priceDepth;
	}

	/**
	 * @param priceDepth the priceDepth to set
	 */
	public void setPriceDepth(Integer priceDepth)
	{
		this.priceDepth = priceDepth;
	}

	/**
	 * @return the priceOrder
	 */
	public String getPriceOrder()
	{
		return priceOrder;
	}

	/**
	 * @param priceOrder the priceOrder to set
	 */
	public void setPriceOrder(String priceOrder)
	{
		this.priceOrder = priceOrder;
	}

	/**
	 * @return the side
	 */
	public String getSide()
	{
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(String side)
	{
		this.side = side;
	}

	/**
	 * @return the oddsType
	 */
	public String getOddsType()
	{
		return oddsType;
	}

	/**
	 * @param oddsType the oddsType to set
	 */
	public void setOddsType(String oddsType)
	{
		this.oddsType = oddsType;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency()
	{
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	/**
	 * @return the exchangeType
	 */
	public String getExchangeType()
	{
		return exchangeType;
	}

	/**
	 * @param exchangeType the exchangeType to set
	 */
	public void setExchangeType(String exchangeType)
	{
		this.exchangeType = exchangeType;
	}

	/**
	 * @return the language
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}
}
