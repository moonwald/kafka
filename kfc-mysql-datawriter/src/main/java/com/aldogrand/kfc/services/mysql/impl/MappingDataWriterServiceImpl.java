/**
 * 
 */
package com.aldogrand.kfc.services.mysql.impl;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.mapper.EventMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.EventMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapNewEvent;
import com.aldogrand.kfc.services.mysql.MappingDataWriterService;
import com.aldogrand.kfc.services.mysql.mapper.EventMappingWriterInterface;
import com.aldogrand.kfc.services.mysql.mapper.MarketMappingWriterInterface;
import com.aldogrand.kfc.services.mysql.mapper.RunnerMappingWriterInterface;

/**
 * <p>
 * <b>Title</b> MappingDataWriterServiceImpl.java
 * </p>
 * com.aldogrand.kfc.services.mysql.impl
 * <p>
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
public class MappingDataWriterServiceImpl implements MappingDataWriterService
{

	private final Logger					logger	= LogManager.getLogger(getClass());

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel					errorChannel;

	private EventMappingWriterInterface		eventMappingWriter;
	private MarketMappingWriterInterface	marketMappingWriter;
	private RunnerMappingWriterInterface	runnerMappingWriter;

	/**
	 * 
	 */
	public MappingDataWriterServiceImpl(EventMappingWriterInterface eventMappingWriter, MarketMappingWriterInterface marketMappingWriter,
			RunnerMappingWriterInterface runnerMappingWriter)
	{
		this.eventMappingWriter = eventMappingWriter;
		this.marketMappingWriter = marketMappingWriter;
		this.runnerMappingWriter = runnerMappingWriter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.MapperDataWriterService#map(org.springframework.messaging.MessageHeaders
	 * , com.aldogrand.kfc.msg.events.mapper.EventMapFoundEvent)
	 * 
	 * @param headers
	 * 
	 * @param payload
	 */
	@Override
	public KFCEvent map(@Headers MessageHeaders headers, @Payload EventMapFoundEvent payload)
	{
		try
		{
			logger.debug("Maping the existing event to source event");
			payload.setEvent(eventMappingWriter.updateEvent(payload.getSourceEvent(), payload.getEvent()));
			logger.debug("Mapped the existing event to source event ");
		} catch (RuntimeException rte)
		{
			logOrThrow(payload, rte);
			payload = null;
		}
		return payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.MapperDataWriterService#map(org.springframework.messaging.MessageHeaders
	 * , com.aldogrand.kfc.msg.events.mapper.EventMapNewEvent)
	 */
	@Override
	public KFCEvent map(@Headers MessageHeaders headers, @Payload EventMapNewEvent payload)
	{
		try
		{
			logger.debug("Creating and Mapping the source event");
			payload.setEvent(this.eventMappingWriter.createEvent(payload.getSourceEvent(), payload.getEvent()));
			logger.debug("Created and Mapping the source event ");
			return payload;
		} catch (RuntimeException rte)
		{
			logOrThrow(payload, rte);
			payload = null;
		}
		return payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.MapperDataWriterService#map(org.springframework.messaging.MessageHeaders
	 * , com.aldogrand.kfc.msg.events.mapper.MarketMapFoundEvent)
	 */
	@Override
	public KFCEvent map(@Headers MessageHeaders headers, @Payload MarketMapFoundEvent payload)
	{
		try
		{
			logger.debug("Mapping the existing Market to source Market");
			payload.setMarket(this.marketMappingWriter.updateMarket(payload.getSourceMarket(), payload.getMarket()));
			logger.debug("Mapped the existing Market to source Market");
			return payload;
		} catch (RuntimeException rte)
		{
			logOrThrow(payload, rte);
			payload = null;
		}
		return payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.MapperDataWriterService#map(org.springframework.messaging.MessageHeaders
	 * , com.aldogrand.kfc.msg.events.mapper.MarketMapNewEvent)
	 */
	@Override
	public KFCEvent map(@Headers MessageHeaders headers, @Payload MarketMapNewEvent payload)
	{
		try
		{
			logger.debug("Creating and Mapping the source Market ");
			payload.setMarket(this.marketMappingWriter.createMarket(payload.getSourceMarket(), payload.getMarket()));
			logger.debug("Created and Mapping the source Market");
			return payload;
		} catch (RuntimeException rte)
		{
			logOrThrow(payload, rte);
			payload = null;
		}
		return payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.MapperDataWriterService#map(org.springframework.messaging.MessageHeaders
	 * , com.aldogrand.kfc.msg.events.mapper.RunnerMapFoundEvent)
	 */
	@Override
	public KFCEvent map(@Headers MessageHeaders headers, @Payload RunnerMapFoundEvent payload)
	{
		try
		{
			logger.debug("Mapping the existing Runner to source Runner");
			payload.setRunner(this.runnerMappingWriter.updateRunner(payload.getSourceRunner(), payload.getRunner()));
			logger.debug("Mapped the existing Runner to source Runner");
			return payload;
		} catch (RuntimeException rte)
		{
			logOrThrow(payload, rte);
			payload = null;
		}
		return payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.MapperDataWriterService#map(org.springframework.messaging.MessageHeaders
	 * , com.aldogrand.kfc.msg.events.mapper.RunnerMapNewEvent)
	 */
	@Override
	public KFCEvent map(@Headers MessageHeaders headers, @Payload RunnerMapNewEvent payload)
	{
		try
		{
			logger.debug("Creating and Mapping the source Runner");
			payload.setRunner(this.runnerMappingWriter.createRunner(payload.getSourceRunner(), payload.getRunner()));
			logger.debug("Created and Mapping the source Runner");
			return payload;
		} catch (RuntimeException rte)
		{
			logOrThrow(payload, rte);
			payload = null;
		}
		return payload;
	}

	// Error Logging and re throwing part goes here//
	/**
	 * @param payload
	 * @param rte
	 */
	private void logOrThrow(EventMapFoundEvent payload, RuntimeException rte)
	{
		logger.error("Error updating the mapping for the existing event.");
		if (errorChannel != null)
		{
			MessageBuilder<EventMapFoundEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error updating the mapping for the existing event.", rte);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	private void logOrThrow(EventMapNewEvent payload, RuntimeException rte)
	{
		logger.error("Error creating the mapping for the event.");
		if (errorChannel != null)
		{
			MessageBuilder<EventMapNewEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error creating the mapping for the event.", rte);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	private void logOrThrow(MarketMapFoundEvent payload, RuntimeException rte)
	{
		logger.error("Error Mapping the existing Market to source Market");
		if (errorChannel != null)
		{
			MessageBuilder<MarketMapFoundEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error Mapping the existing Market to source Market", rte);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	private void logOrThrow(MarketMapNewEvent payload, RuntimeException rte)
	{
		logger.error("Error creating the market.");
		if (errorChannel != null)
		{
			MessageBuilder<MarketMapNewEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error creating the market.", rte);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	private void logOrThrow(RunnerMapFoundEvent payload, RuntimeException rte)
	{
		logger.error("Error updating the runner.");
		if (errorChannel != null)
		{
			MessageBuilder<RunnerMapFoundEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error updating the runner.", rte);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	private void logOrThrow(RunnerMapNewEvent payload, RuntimeException rte)
	{
		logger.error("Error creating the runner.");
		if (errorChannel != null)
		{
			MessageBuilder<RunnerMapNewEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error creating the runner.", rte);
		}
	}
}
