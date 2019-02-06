/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper.impl;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.mapped.EventCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.EventMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerMappedEvent;
import com.aldogrand.kfc.msg.events.mapper.*;
import com.aldogrand.kfc.services.mysql.mapper.MappingNotificationEventGeneratorInterface;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

import java.io.IOException;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

/**
 * <p>
 * <b>Title</b> MappingNotificationEventGenerator.java
 * </p>
 * com.aldogrand.kfc.services.mysql.impl.mapper
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
public class MappingNotificationEventGenerator implements MappingNotificationEventGeneratorInterface
{

	private final Logger	logger	= LogManager.getLogger(getClass());

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;

	private KFCProducer		kfcKafkaProducer;

	/**
	 * 
	 */
	public MappingNotificationEventGenerator(KFCProducer kfcKafkaProducer)
	{
		this.kfcKafkaProducer = kfcKafkaProducer;
	}

	/*
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.MappingNotificationEventGeneratorInterface#generateEvent
	 * (com.aldogrand.sbpc.dataaccess.model.EventDto,
	 * com.aldogrand.kfc.msg.events.mapper.EventMapFoundEvent)
	 * 
	 * @param mapFoundEvent
	 */
	@Override
	public void generateEvent(EventMapFoundEvent mapFoundEvent)
	{
		// only generate event if event is mapped
		if (mapFoundEvent.getEvent() != null)
		{
			EventMappedEvent mappedEvent = new EventMappedEvent();
			mappedEvent.setIntegrationModuleId(mapFoundEvent.getIntegrationModuleId());
			mappedEvent.setIntegrationModuleName(mapFoundEvent.getIntegrationModuleName());
			mappedEvent.setEvent(mapFoundEvent.getEvent());
			try
			{
				kfcKafkaProducer.send(mappedEvent);
			} catch (ProducerException e)
			{
				logger.error(String.format("Error sending notification of the Mapped Event %s.", mappedEvent.getEvent().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<EventMapFoundEvent> builder = MessageBuilder.withPayload(mapFoundEvent).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Event \\\"{}\\\".\", new Object[] { mappedEvent.getEvent().getId() }", e);
				}
			} catch (IOException e)
			{
				logger.error(String.format("Error sending notification of the Mapped Event %s.", mappedEvent.getEvent().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<EventMapFoundEvent> builder = MessageBuilder.withPayload(mapFoundEvent).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Event \\\"{}\\\".\", new Object[] { mappedEvent.getEvent().getId() }", e);
				}
			}
		}else
		{
			logger.warn("Event for EventMapFoundEvent "+mapFoundEvent+" is null so notification can not be generated");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.MappingNotificationEventGeneratorInterface#generateEvent
	 * (com.aldogrand.sbpc.dataaccess.model.EventDto,
	 * com.aldogrand.kfc.msg.events.mapper.EventMapNewEvent)
	 */
	@Override
	public void generateEvent(EventMapNewEvent mapNewEvent)
	{
		if (mapNewEvent.getEvent() != null)
		{
			EventCreatedEvent mappedEvent = new EventCreatedEvent();
			mappedEvent.setIntegrationModuleId(mapNewEvent.getIntegrationModuleId());
			mappedEvent.setIntegrationModuleName(mapNewEvent.getIntegrationModuleName());
			mappedEvent.setEvent(mapNewEvent.getEvent());
			try
			{
				kfcKafkaProducer.send(mappedEvent);
			} catch (ProducerException e)
			{
				logger.error(String.format("Error sending notification of the Mapped Event %s.", mappedEvent.getEvent().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<EventMapNewEvent> builder = MessageBuilder.withPayload(mapNewEvent).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Event \\\"{}\\\".\", new Object[] { mappedEvent.getEvent().getId() }", e);
				}
			} catch (IOException e)
			{
				logger.error(String.format("Error sending notification of the Mapped Event %s.", mappedEvent.getEvent().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<EventMapNewEvent> builder = MessageBuilder.withPayload(mapNewEvent).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Event \\\"{}\\\".\", new Object[] { mappedEvent.getEvent().getId() }", e);
				}
			}
		} else
		{
			logger.warn("Event for EventMapNewEvent "+mapNewEvent+" is null so notification can not be generated");
		}
	}

	/*
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.MappingNotificationEventGeneratorInterface#generateEvent
	 * (com.aldogrand.sbpc.dataaccess.model.MarketDto,
	 * com.aldogrand.kfc.msg.events.mapper.MarketMapFoundEvent)
	 * 
	 * @param mapFoundEvent
	 */
	@Override
	public void generateEvent(MarketMapFoundEvent mapFoundEvent)
	{
		if (mapFoundEvent.getMarket() != null)
		{
			MarketMappedEvent mappedMarket = new MarketMappedEvent();
			mappedMarket.setIntegrationModuleId(mapFoundEvent.getIntegrationModuleId());
			mappedMarket.setIntegrationModuleName(mapFoundEvent.getIntegrationModuleName());
			mappedMarket.setMarket(mapFoundEvent.getMarket());
			try
			{
				kfcKafkaProducer.send(mappedMarket);
			} catch (ProducerException e)
			{
				logger.error(String.format("Error sending notification of the Mapped market %s.", mappedMarket.getMarket().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<MarketMappedEvent> builder = MessageBuilder.withPayload(mappedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Market \\\"{}\\\".\", new Object[] { mappedMarket.getMarket().getId() }", e);
				}
			} catch (IOException e)
			{
				logger.error(String.format("Error sending notification of the Mapped market %s.", mappedMarket.getMarket().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<MarketMappedEvent> builder = MessageBuilder.withPayload(mappedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Market \\\"{}\\\".\", new Object[] { mappedMarket.getMarket().getId() }", e);
				}
			}
		} else
		{
			logger.warn("Market for MarketMapFoundEvent "+mapFoundEvent+" is null so notification can not be generated");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.MappingNotificationEventGeneratorInterface#generateEvent
	 * (com.aldogrand.sbpc.dataaccess.model.MarketDto,
	 * com.aldogrand.kfc.msg.events.mapper.MarketMapNewEvent)
	 * 
	 * @param mapNewEvent
	 */
	@Override
	public void generateEvent(MarketMapNewEvent mapNewEvent)
	{
		if (mapNewEvent.getMarket() != null)
		{
			MarketCreatedEvent mappedMarket = new MarketCreatedEvent();
			mappedMarket.setIntegrationModuleId(mapNewEvent.getIntegrationModuleId());
			mappedMarket.setIntegrationModuleName(mapNewEvent.getIntegrationModuleName());
			mappedMarket.setMarket(mapNewEvent.getMarket());
			try
			{
				kfcKafkaProducer.send(mappedMarket);
			} catch (ProducerException e)
			{
				logger.error(String.format("Error sending notification of the Mapped market %s.", mappedMarket.getMarket().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<MarketCreatedEvent> builder = MessageBuilder.withPayload(mappedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Market \\\"{}\\\".\", new Object[] { mappedMarket.getMarket().getId() }", e);
				}
			} catch (IOException e)
			{
				logger.error(String.format("Error sending notification of the Mapped market %s.", mappedMarket.getMarket().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<MarketCreatedEvent> builder = MessageBuilder.withPayload(mappedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Market \\\"{}\\\".\", new Object[] { mappedMarket.getMarket().getId() }", e);
				}
			}
		} else
		{
			logger.warn("Market for MarketMapNewEvent "+mapNewEvent+" is null so notification can not be generated");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.MappingNotificationEventGeneratorInterface#generateEvent
	 * (com.aldogrand.sbpc.dataaccess.model.RunnerDto,
	 * com.aldogrand.kfc.msg.events.mapper.RunnerMapFoundEvent)
	 * 
	 * @param mapFoundEvent
	 */
	@Override
	public void generateEvent(RunnerMapFoundEvent mapFoundEvent)
	{
		if (mapFoundEvent.getRunner() != null)
		{
			RunnerMappedEvent mappedRunner = new RunnerMappedEvent();
			mappedRunner.setIntegrationModuleId(mapFoundEvent.getIntegrationModuleId());
			mappedRunner.setIntegrationModuleName(mapFoundEvent.getIntegrationModuleName());
			mappedRunner.setRunner(mapFoundEvent.getRunner());
			try
			{
				kfcKafkaProducer.send(mappedRunner);
			} catch (ProducerException e)
			{
				logger.error(String.format("Error sending notification of the Mapped runner %s.", mappedRunner.getRunner().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<RunnerMappedEvent> builder = MessageBuilder.withPayload(mappedRunner).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Runner \\\"{}\\\".\", new Object[] { mappedRunner.getRunner().getId() }", e);
				}
			} catch (IOException e)
			{
				logger.error(String.format("Error sending notification of the Mapped runner %s.", mappedRunner.getRunner().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<RunnerMappedEvent> builder = MessageBuilder.withPayload(mappedRunner).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Runner \\\"{}\\\".\", new Object[] { mappedRunner.getRunner().getId() }", e);
				}
			}
		} else
		{
			logger.warn("Runner for RunnerMapFoundEvent "+mapFoundEvent+" is null so notification can not be generated");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.services.mysql.impl.mapper.MappingNotificationEventGeneratorInterface#generateEvent
	 * (com.aldogrand.sbpc.dataaccess.model.RunnerDto,
	 * com.aldogrand.kfc.msg.events.mapper.RunnerMapNewEvent)
	 */
	@Override
	public void generateEvent(RunnerMapNewEvent mapNewEvent)
	{
		if (mapNewEvent.getRunner() != null)
		{
			RunnerCreatedEvent mappedRunner = new RunnerCreatedEvent();
			mappedRunner.setIntegrationModuleId(mapNewEvent.getIntegrationModuleId());
			mappedRunner.setIntegrationModuleName(mapNewEvent.getIntegrationModuleName());
			mappedRunner.setRunner(mapNewEvent.getRunner());
			try
			{
				kfcKafkaProducer.send(mappedRunner);
			} catch (ProducerException e)
			{
				logger.error(String.format("Error sending notification of the Mapped runner %s.", mappedRunner.getRunner().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<RunnerCreatedEvent> builder = MessageBuilder.withPayload(mappedRunner).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Runner \\\"{}\\\".\", new Object[] { mappedRunner.getRunner().getId() }", e);
				}
			} catch (IOException e)
			{
				logger.error(String.format("Error sending notification of the Mapped runner %s.", mappedRunner.getRunner().getId()), e);
				if (errorChannel != null)
				{
					MessageBuilder<RunnerCreatedEvent> builder = MessageBuilder.withPayload(mappedRunner).setHeader(ERROR_CHANNEL, "errorChanel");

					errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), e)).build());
				} else
				{
					throw new RuntimeException(
							"\"Error sending notification of the Mapped Runner \\\"{}\\\".\", new Object[] { mappedRunner.getRunner().getId() }", e);
				}
			}
		} else
		{
			logger.warn("Runner for RunnerMapNewEvent "+mapNewEvent+" is null so notification can not be generated");
		}
	}
}
