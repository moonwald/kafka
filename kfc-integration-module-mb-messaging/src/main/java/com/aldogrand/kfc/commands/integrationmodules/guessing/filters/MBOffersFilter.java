package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.OffersFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBOffersFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBOffersFilter <br/>
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

public class MBOffersFilter extends OffersFilter {
	//filled with params from https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=24543419
	
	Integer		offset;			// offset required
	Integer		perPage;		// per-page optional
	String[]	eventIds;		// event-ids optional
	String[]	marketIds;		// market-ids optional
	String[]	runnerIds;		// runner-ids optional
	String		side;			// side optional
	String		status;			// status optional
	String		exchangeType;	// exchange-type optional
	String		language;		// language optional
	Integer		interval;		// interval optional depends on status = matched

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
	 * @return the eventIds
	 */
	public String[] getEventIds()
	{
		return eventIds;
	}
	/**
	 * @param eventIds the eventIds to set
	 */
	public void setEventIds(String[] eventIds)
	{
		this.eventIds = eventIds;
	}
	/**
	 * @return the marketIds
	 */
	public String[] getMarketIds()
	{
		return marketIds;
	}
	/**
	 * @param marketIds the marketIds to set
	 */
	public void setMarketIds(String[] marketIds)
	{
		this.marketIds = marketIds;
	}
	/**
	 * @return the runnerIds
	 */
	public String[] getRunnerIds()
	{
		return runnerIds;
	}
	/**
	 * @param runnerIds the runnerIds to set
	 */
	public void setRunnerIds(String[] runnerIds)
	{
		this.runnerIds = runnerIds;
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
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
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
	/**
	 * @return the interval
	 */
	public Integer getInterval()
	{
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(Integer interval)
	{
		this.interval = interval;
	}

}
