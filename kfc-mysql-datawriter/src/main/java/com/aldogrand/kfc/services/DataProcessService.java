package com.aldogrand.kfc.services;

import com.aldogrand.kfc.msg.events.processor.EventProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.MarketProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.RunnerProcessedEvent;
import com.aldogrand.kfc.msg.events.raw.*;
import com.aldogrand.kfc.services.exception.DataProcessException;

import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;
import org.springframework.messaging.MessageHeaders;

/**
 * This service is to process the data created by the source data writer Currently this service process the data for
 * Event Market and Runner when they gets Updated
 * 
 * @author aldogrand on 19/11/14.
 */
public interface DataProcessService
{
	/**
	 * Process and prepare the new target event when source event is created
	 * 
	 * @param headers
	 *            MessageHeaders
	 * @param sourceEventCreated
	 *            payload
	 * @throws DataProcessException
	 *             in case of error while performing the operation
	 */
     void process(@Headers MessageHeaders headers, @Payload SourceEventCreatedEvent sourceEventCreated) throws DataProcessException;

	/**
	 * process the existing target event when source event is updated
	 * 
	 * @param headers
	 *            MessageHeaders
	 * @param sourceEventUpdated
	 *            payload
	 * @throws DataProcessException
	 *             in case of error while performing the operation
	 */
     EventProcessedEvent process(@Headers MessageHeaders headers, @Payload SourceEventUpdatedEvent sourceEventUpdated) throws DataProcessException;

	/**
	 * Process and prepare the new target market when source market is created
	 * 
	 * @param headers
	 *            MessageHeaders
	 * @param sourceMarketCreated
	 *            payload
	 * @throws DataProcessException
	 *             in case of error while performing the operation
	 */
	void process(@Headers MessageHeaders headers, @Payload SourceMarketCreatedEvent sourceMarketCreated) throws DataProcessException;

	/**
	 * process the existing target event when source event is updated
	 * 
	 * @param headers
	 *            MessageHeaders
	 * @param sourceMarketUpdated
	 *            payload
	 * @throws DataProcessException
	 *             in case of error while performing the operation
	 */
	MarketProcessedEvent process(@Headers MessageHeaders headers, @Payload SourceMarketUpdatedEvent sourceMarketUpdated) throws DataProcessException;

	/**
	 * Process and prepare the new target runner when source runner is created
	 * 
	 * @param headers
	 *            MessageHeaders
	 * @param sourceRunnerCreated
	 *            payload
	 * @throws DataProcessException
	 *             in case of error while performing the operation
	 */
	void process(@Headers MessageHeaders headers, @Payload SourceRunnerCreatedEvent sourceRunnerCreated) throws DataProcessException;

	/**
	 * process the existing target event when source event is updated
	 * 
	 * @param headers
	 *            MessageHeaders
	 * @param sourceRunnerUpdated
	 *            payload
	 * @throws DataProcessException
	 *             in case of error while performing the operation
	 */
	RunnerProcessedEvent process(@Headers MessageHeaders headers, @Payload SourceRunnerUpdatedEvent sourceRunnerUpdated) throws DataProcessException;
}
