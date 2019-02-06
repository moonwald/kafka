package com.aldogrand.kfc.commands.integrationmodules.guessing.filters;

import com.aldogrand.kfc.msg.commands.integrationmodules.filters.OfferFilter;

/**
 * 
 * <p>
 * <b>Title</b> MBOfferFilter.java
 * </p>
 * <p>
 * <b>Description</b> MBOfferFilter <br/>
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

public class MBOfferFilter extends OfferFilter
{
	//filled with params from https://xcl-mb.jira.com/wiki/pages/viewpage.action?pageId=11239515

	Integer	offerId;		// id required integer
	String	exchangeType;	// optional string
	String	language;		// optional string

	/**
	 * @return the offerId
	 */
	public Integer getOfferId()
	{
		return offerId;
	}

	/**
	 * @param offerId
	 *            the offerId to set
	 */
	public void setOfferId(Integer offerId)
	{
		this.offerId = offerId;
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
	 * @return the language
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}
}
