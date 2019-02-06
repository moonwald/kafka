/**
 * 
 */
package com.aldogrand.kfc.notification.service;

import org.springframework.integration.annotation.Headers;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.msg.events.mapped.EventCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.EventMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerMappedEvent;
import com.aldogrand.kfc.msg.events.processed.EventUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.MarketUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.RunnerUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceUpdatedEvent;

/**
 * <p>
 * <b>Title</b> kfcNotificationService.java <br>
 * <b>Currently this service supports notification of the following models.</b><br>
 * Event<br>
 * Market<br>
 * Runner<br>
 * Price<br>
 * Offer<br>
 * <b>support can be added for following in future if required</b>
 * Connector<br>
 * BettingPlatform<br>
 * Position<br>
 * EventIncident<br>
 * </p>
 * <b>Description</b> kfc-notification-adapter
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
public interface KFCNotificationService
{
	/**
	 * @param eventCreated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, EventCreatedEvent payload);
	/**
	 * @param marketCreated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, MarketCreatedEvent payload);
	/**
	 * @param runnerCreated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, RunnerCreatedEvent payload);
	/**
	 * @param eventUpdated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, EventUpdatedEvent payload);
	/**
	 * @param marketUpdated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, MarketUpdatedEvent payload);
	/**
	 * @param runnerUpdated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, RunnerUpdatedEvent payload);
	/**
	 * @param eventCreated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, EventMappedEvent payload);
	/**
	 * @param marketCreated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, MarketMappedEvent payload);
	/**
	 * @param runnerMapped
	 */
	public Message<String> notify(@Headers MessageHeaders headers, RunnerMappedEvent payload);
	/**
	 * @param priceCreated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, PriceCreatedEvent payload);
	
	/**
	 * Notify price updated
	 * @param headers Headers of the message
	 * @param payload Payload of the message
	 * @return Message
	 */
	public Message<String> notify(@Headers MessageHeaders headers, PriceUpdatedEvent payload);
	
	/**
	 * @param offerCreated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, OfferCreatedEvent payload);
	/**
	 * @param offerUpdated
	 */
	public Message<String> notify(@Headers MessageHeaders headers, OfferUpdatedEvent payload);
}
