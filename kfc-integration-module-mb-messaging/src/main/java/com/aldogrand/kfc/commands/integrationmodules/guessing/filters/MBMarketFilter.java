package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.MarketFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBMarketFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBMarketFilter <br/>
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

public class MBMarketFilter extends MarketFilter {
	
	//filled with params from https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=12451879
	
	String		eventId;
	String		marketId;
	boolean		includeRunners;
	String[]	runnerStates;
	boolean		includePrices;
	Integer		priceDepth;
	String		priceOrder;
	String		side;
	String		oddsType;
	String		currency;
	String		exchangeType;
	String		language;
	
	public MBMarketFilter()	{}
	
	/**
	 * @return the eventId
	 */
	public String getEventId()
	{
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId)
	{
		this.eventId = eventId;
	}
	/**
	 * @return the marketId
	 */
	public String getMarketId()
	{
		return marketId;
	}
	/**
	 * @param marketId the marketId to set
	 */
	public void setMarketId(String marketId)
	{
		this.marketId = marketId;
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
