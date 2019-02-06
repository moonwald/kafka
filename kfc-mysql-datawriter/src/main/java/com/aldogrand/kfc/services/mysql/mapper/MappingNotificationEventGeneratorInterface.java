/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper;

import com.aldogrand.kfc.msg.events.mapper.EventMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.EventMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapNewEvent;

/**
 * <p>
 * <b>Title</b> MappingNotificationEventGeneratorInterface.java
 * </p>
 * <pre>
 * Input to the methods of this interface is the output of MappingDataWriterService
 * The methods of this class are supposed to grab the events and then generate new events.
 * 	1. ******CreatedEvent
 *  2. *******MappedEvent
 * 
 * </pre>
 * <p>
 * <b>Description</b> kfc-mysql-datawriter.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author aldogrand
 * @version 1.0
 */
public interface MappingNotificationEventGeneratorInterface
{

	/**
	 * @param mapFoundEvent
	 */
	public void generateEvent(EventMapFoundEvent mapFoundEvent);

	/**
	 * @param mapNewEvent
	 */
	public void generateEvent(EventMapNewEvent mapNewEvent);

	/**
	 * @param mapFoundEvent
	 */
	public void generateEvent(MarketMapFoundEvent mapFoundEvent);

	/**
	 * @param mapNewEvent
	 */
	public void generateEvent(MarketMapNewEvent mapNewEvent);

	/**
	 * @param mapFoundEvent
	 */
	public void generateEvent(RunnerMapFoundEvent mapFoundEvent);

	/**
	 * @param mapNewEvent
	 */
	public void generateEvent(RunnerMapNewEvent mapNewEvent);

}