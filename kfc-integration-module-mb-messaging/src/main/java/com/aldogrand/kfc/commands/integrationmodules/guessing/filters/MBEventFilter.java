package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.EventFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBEventFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBEventFilter <br/>
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

public class MBEventFilter extends EventFilter{

	//NOTE: id is the eventId
	String[] marketStates;
	boolean includeMarkets;
	boolean includeRunners;
	boolean includePrices;
	String[] runnerStates;
	Integer priceDepth;
	String priceOrder;
	String side;
	String oddsType;
	String currency;
	String exchangeType;
	String language;
	

	public MBEventFilter()
	{}
	
	public MBEventFilter(String eventId) {
		super(eventId);
	}


	/**
	 * @return the marketStates
	 */
	public String[] getMarketStates()
	{
		return marketStates;
	}

	/**
	 * @param marketStates the marketStates to set
	 */
	public void setMarketStates(String[] marketStates)
	{
		this.marketStates = marketStates;
	}

	/**
	 * @return the includeMarkets
	 */
	public boolean isIncludeMarkets()
	{
		return includeMarkets;
	}

	/**
	 * @param includeMarkets the includeMarkets to set
	 */
	public void setIncludeMarkets(boolean includeMarkets)
	{
		this.includeMarkets = includeMarkets;
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
