/**
 * 
 */
package com.aldogrand.kfc.msg;

/**
 * <p>
 * <b>Title</b> CommandContentType.java
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
public interface CommandContentType
{

	public static final String	INTEGRATION_MODULE_HEARTBEAT	= "INTEGRATION_MODULE_HEARTBEAT";

	public static final String	LOGIN_COMMAND					= "LOGIN_COMMAND";
	public static final String	LOGIN_EVENT						= "LOGIN_EVENT";
	public static final String	FETCH_EVENTS_COMMAND			= "FETCH_EVENTS_COMMAND";
	public static final String	FETCH_EVENT_COMMAND				= "FETCH_EVENT_COMMAND";
	public static final String	FETCH_MARKETS_COMMAND			= "FETCH_MARKETS_COMMAND";
	public static final String	FETCH_MARKET_COMMAND			= "FETCH_MARKET_COMMAND";
	public static final String	FETCH_RUNNERS_COMMAND			= "FETCH_RUNNERS_COMMAND";
	public static final String	FETCH_RUNNER_COMMAND			= "FETCH_RUNNER_COMMAND";
	public static final String	FETCH_PRICES_COMMAND			= "FETCH_PRICES_COMMAND";
	public static final String	FETCH_OFFERS_COMMAND			= "FETCH_OFFERS_COMMAND";
	public static final String	FETCH_OFFER_COMMAND				= "FETCH_OFFER_COMMAND";
	public static final String	FETCH_POSITIONS_COMMAND			= "FETCH_POSITIONS_COMMAND";

}
