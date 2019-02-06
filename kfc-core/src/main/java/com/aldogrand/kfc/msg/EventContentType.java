//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd.= "";
//
package com.aldogrand.kfc.msg;


/**
 * Event content type enumeration.
 */
public class EventContentType implements CommandContentType, DataIn, DataInSaved, PlatformDataSaved
{

	// public static final String EVENTS GENERATED BY MAPPING DATA WRITER
	public static final String	EVENT_MAPPED					= "EVENT_MAPPED";
	public static final String	MARKET_MAPPED					= "MARKET_MAPPED";
	public static final String	RUNNER_MAPPED					= "RUNNER_MAPPED";
	public static final String	EVENT_CREATED					= "EVENT_CREATED";
	public static final String	MARKET_CREATED					= "MARKET_CREATED";
	public static final String	RUNNER_CREATED					= "RUNNER_CREATED";
	// EVENTS GENERATED BY MAPPING SERVICE
	public static final String	EVENT_MAP_FOUND					= "EVENT_MAP_FOUND";
	public static final String	EVENT_MAP_NEW					= "EVENT_MAP_NEW";
	public static final String	MARKET_MAP_FOUND				= "MARKET_MAP_FOUND";
	public static final String	MARKET_MAP_NEW					= "MARKET_MAP_NEW";
	public static final String	RUNNER_MAP_FOUND				= "RUNNER_MAP_FOUND";
	public static final String	RUNNER_MAP_NEW					= "RUNNER_MAP_NEW";
	// GENERATED BY TARGET DATA WRITER
	// EVENT_CLOSED_STATUS_CREATED= "";
	public static final String	EVENT_CLOSED_STATUS_UPDATED		= "EVENT_CLOSED_STATUS_UPDATED";
	public static final String	EVENT_UPDATED					= "EVENT_UPDATED";
	public static final String	MARKET_UPDATED					= "MARKET_UPDATED";
	public static final String	RUNNER_UPDATED					= "RUNNER_UPDATED";
	// From Processor
	public static final String	SOURCE_EVENT_PROCESSED			= "SOURCE_EVENT_PROCESSED";
	public static final String	SOURCE_MARKET_PROCESSED			= "SOURCE_MARKET_PROCESSED";
	public static final String	SOURCE_RUNNER_PROCESSED			= "SOURCE_RUNNER_PROCESSED";
	// Not mapped events
	public static final String	EVENT_NOT_MAPPED				= "EVENT_NOT_MAPPED";
	public static final String	MARKET_NOT_MAPPED				= "MARKET_NOT_MAPPED";
	public static final String	RUNNER_NOT_MAPPED				= "RUNNER_NOT_MAPPED";

	// Events error
	public static final String	CREATION_EVENT_ERROR			= "CREATION_EVENT_ERROR";
	public static final String	DELETE_EVENT_ERROR				= "DELETE_EVENT_ERROR";
	public static final String	FETCH_EVENT_ERROR				= "FETCH_EVENT_ERROR";
	public static final String	MAP_EVENT_ERROR					= "MAP_EVENT_ERROR";
	public static final String	UPDATE_EVENT_ERROR				= "UPDATE_EVENT_ERROR";
}
