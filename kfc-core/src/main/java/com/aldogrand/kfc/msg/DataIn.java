/**
 * 
 */
package com.aldogrand.kfc.msg;

/**
 * <p>
 * <b>Title</b> ConnectorEvents.java
 * </p>
 * com.aldogrand.kfc.msg
 * <p>
 * <b>Description</b> kfc-core.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author aldogrand
 * @version 1.0
 */
public interface DataIn
{
	public static final String	ACCOUNT_RECEIVED				= "ACCOUNT_RECEIVED";
	public static final String	SOURCE_EVENT_RECEIVED			= "SOURCE_EVENT_RECEIVED";
	public static final String	SOURCE_MARKETS_RECEIVED			= "SOURCE_MARKETS_RECEIVED";
	public static final String	SOURCE_RUNNER_RECEIVED			= "SOURCE_RUNNER_RECEIVED";
	public static final String	SETTLED_BETS_RECEIVED			= "SETTLED_BETS_RECEIVED";
	public static final String	PRICE_RECEIVED					= "PRICE_RECEIVED";
	public static final String	POSITION_RECEIVED				= "POSITION_RECEIVED";
	public static final String	OFFER_RECEIVED					= "OFFER_RECEIVED";
	public static final String	EVENT_CLOSED_STATUS_RECEIVED	= "EVENT_CLOSED_STATUS_RECEIVED";
	public static final String	MARKETS_CLOSED_STATUS_RECEIVED  = "MARKETS_CLOSED_STATUS_RECEIVED";
	public static final String	EVENT_INCIDENT_RECEIVED			= "EVENT_INCIDENT_RECEIVED";
	public static final String	CONNECTOR_RECEIVED				= "CONNECTOR_RECEIVED";
	public static final String	BETTING_PLATFORM_RECEIVED		= "BETTING_PLATFORM_RECEIVED";
}
