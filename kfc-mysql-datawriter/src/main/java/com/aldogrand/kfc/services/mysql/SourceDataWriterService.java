package com.aldogrand.kfc.services.mysql;

import com.aldogrand.kfc.msg.events.fetcher.*;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;


/**
 * 
 * <p>
 * <b>Title</b> DataWriterService
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author aldogrand
 *
 */
public interface SourceDataWriterService {
	
	public void write(@Headers MessageHeaders headers, SourceEventReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, SourceMarketsReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, SourceRunnersReceivedEvent payload);    

	public void write(@Headers MessageHeaders headers, PricesReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, AccountReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, ConnectorReceivedEvent payload);

	public void write(@Headers MessageHeaders headers, EventIncidentReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, OffersReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, PositionsReceivedEvent payload);

	public void write(@Headers MessageHeaders headers, SettledBetsReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, BettingPlatformReceivedEvent payload);

    public void write(@Headers MessageHeaders headers, EventsClosedStatusReceivedEvent payload);

	/**
	 * @param headers
	 * @param payload
	 */
	void write(MessageHeaders headers, MarketsClosedStatusReceivedEvent payload);
	
}
