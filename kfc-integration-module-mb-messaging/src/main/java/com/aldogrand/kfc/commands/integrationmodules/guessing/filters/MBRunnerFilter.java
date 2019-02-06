package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.RunnerFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBRunnerFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBRunnerFilter <br/>
 * <h1>more information on this filter https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=12451898</h1>
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

public class MBRunnerFilter extends RunnerFilter
{
	//filled with params from https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=12451898

	Integer		runnerId;		// id required
	Integer		eventId;		// event_id required
	Integer		marketId;		// market_id required
	boolean		includePrices;	// include-prices optional
	Integer		priceDepth;		// price-depth optional
	String		priceOrder;		// price-order optional
	String		side;			// side optional
	String		oddsType;		// odds-type optional
	String		currency;		// currency optional
	String		exchangeType;	// exchange-type optional
	String[]	states;			// states optional

	/**
	 * @return the runnerId
	 */
	public Integer getRunnerId()
	{
		return runnerId;
	}

	/**
	 * @param runnerId
	 *            the runnerId to set
	 */
	public void setRunnerId(Integer runnerId)
	{
		this.runnerId = runnerId;
	}

	/**
	 * @return the eventId
	 */
	public Integer getEventId()
	{
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(Integer eventId)
	{
		this.eventId = eventId;
	}

	/**
	 * @return the marketId
	 */
	public Integer getMarketId()
	{
		return marketId;
	}

	/**
	 * @param marketId
	 *            the marketId to set
	 */
	public void setMarketId(Integer marketId)
	{
		this.marketId = marketId;
	}

	/**
	 * @return the includePrices
	 */
	public boolean isIncludePrices()
	{
		return includePrices;
	}

	/**
	 * @param includePrices
	 *            the includePrices to set
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
	 * @param priceDepth
	 *            the priceDepth to set
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
	 * @param priceOrder
	 *            the priceOrder to set
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
	 * @param side
	 *            the side to set
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
	 * @param oddsType
	 *            the oddsType to set
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
	 * @param currency
	 *            the currency to set
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
	 * @param exchangeType
	 *            the exchangeType to set
	 */
	public void setExchangeType(String exchangeType)
	{
		this.exchangeType = exchangeType;
	}

	/**
	 * @return the states
	 */
	public String[] getStates()
	{
		return states;
	}

	/**
	 * @param states
	 *            the states to set
	 */
	public void setStates(String[] states)
	{
		this.states = states;
	}

}
