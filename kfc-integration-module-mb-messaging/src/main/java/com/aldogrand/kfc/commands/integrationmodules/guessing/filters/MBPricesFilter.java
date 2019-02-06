package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.PricesFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBPricesFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBPricesFilter <br/>
 * <h1>parameters from https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=13598823
 * <h1>
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

public class MBPricesFilter extends PricesFilter
{
	// parameters from https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=13598823

	Integer	eventId;		// event_id ignored
	Integer	depth;			// depth optional
	Integer	marketId;		// market_id ignored
	String	order;			// order optional
	Integer	runnerId;		// runner_id required
	String	side;			// side optional
	String	oddsType;		// odds-type optional
	String	currency;		// currency optional
	String	exchangeType;	// exchange-type optional

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
	 * @return the depth
	 */
	public Integer getDepth()
	{
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(Integer depth)
	{
		this.depth = depth;
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
	 * @return the order
	 */
	public String getOrder()
	{
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(String order)
	{
		this.order = order;
	}

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

}
