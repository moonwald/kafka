/**
 * 
 */
package com.aldogrand.kfc.services.mysql;

import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.mapper.EventMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.EventMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapNewEvent;

/**
 * <p>
 * <b>Title</b> MapperDataWriterService.java <br>
 * <pre>
 * This service to be called after the processing is done and it has been identified that mapping needs to be persisted in the database.
 * All the validation and the checks has been performed. This service expected the objects populated with valid data and does
 * not need to perform any validation on the data like <br>
 * 		Null checks <br>
 * 		empty checks etc etc.<br>
 * This class simple saves the mapping in the database which has already been identified.
 * </pre>
 * <b>Description</b> kfc-mysql-datawriter.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */
public interface MappingDataWriterService
{
	/**
	 * This method is to update the mapping of the source event into and existing target event
	 * 
	 * @param headers
	 *            as MessageHeaders
	 * @param payload
	 * @return KFCEvent
	 */
	public KFCEvent map(@Headers MessageHeaders headers, @Payload EventMapFoundEvent payload);

	/**
	 * This method is to create the new event in the database and map it with the source event and return
	 * EventMapNewEvent with newly added Event
	 * 
	 * @param headers
	 *            as MessageHeaders
	 * @param payload
	 * @return KFCEvent
	 */
	public KFCEvent map(@Headers MessageHeaders headers, @Payload EventMapNewEvent payload);

	/**
	 * This method is to update the mapping of the source Market into and existing target Market
	 * 
	 * @param headers
	 *            as MessageHeaders
	 * @param payload
	 *            as MarketMapFoundEvent
	 * @return KFCEvent with MarketMapFoundEvent
	 */
	public KFCEvent map(@Headers MessageHeaders headers, @Payload MarketMapFoundEvent payload);

	/**
	 * This method is to create the new market in the database and map it with the source market and return
	 * MarketMapNewEvent with newly added Market
	 * 
	 * @param headers
	 *            as MessageHeaders
	 * @param payload
	 *            as MarketMapNewEvent
	 * @return KFCEvent with MarketMapNewEvent
	 */
	public KFCEvent map(@Headers MessageHeaders headers, @Payload MarketMapNewEvent payload);

	/**
	 * This method is to update the mapping of the source Runner into and existing target Runner
	 * 
	 * @param headers
	 *            as MessageHeaders
	 * @param payload
	 *            as RunnerMapFoundEvent
	 * @return KFCEvent as RunnerMapFoundEvent with mapped target runner and source runner
	 */
	public KFCEvent map(@Headers MessageHeaders headers, @Payload RunnerMapFoundEvent payload);

	/**
	 * This method is to create the new runner in the database and map it with the source runner and return
	 * MarketMapNewEvent with newly added runner
	 * 
	 * @param headers
	 *            as MessageHeaders
	 * @param payload
	 *            as RunnerMapNewEvent
	 * @return KFCEvent as RunnerMapNewEvent with new target runner and mapped source runner
	 */
	public KFCEvent map(@Headers MessageHeaders headers, @Payload RunnerMapNewEvent payload);
}
