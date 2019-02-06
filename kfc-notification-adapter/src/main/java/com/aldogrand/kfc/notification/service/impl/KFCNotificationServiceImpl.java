/**
 * 
 */
package com.aldogrand.kfc.notification.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.Offer;
import com.aldogrand.sbpc.model.Price;
import com.aldogrand.sbpc.model.Runner;
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
import com.aldogrand.kfc.notification.helper.NotificationExceptionHelper;
import com.aldogrand.kfc.notification.service.KFCNotificationService;

/**
 * <p>
 * <b>Title</b> kfcNotificationServiceImpl.java
 * </p>
 * com.aldogrand.kfc.amq.service.impl
 * <p>
 * <b>Description</b> kfc-amq-adapter.
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
public class KFCNotificationServiceImpl implements KFCNotificationService
{
	private final Logger	logger			= Logger.getLogger(this.getClass());

	private ObjectMapper objectMapper = new ObjectMapper().configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);;

	private static enum NotificationType
	{
		ADDED, CHANGED, REMOVED
	}

	/**
	 * HeaderFieldName enumeration to be used for header property for the AMQ message which is sent as a notification to
	 * other modules.
	 * 
	 * @author aldogrand
	 * @version 1.0
	 */
	public static enum HeaderFieldName
	{
		NOTIFICATION_TYPE("notification_type"), OBJECT_CLASS("notification_object_class"), CONNECTOR_ID("connector_id"), BETTING_PLATFORM_ID(
				"betting_platform_id"), EVENT_ID("event_id"), MARKET_ID("market_id"), RUNNER_ID("runner_id"), PRICE_ID("price_id"), SIDE("side"), SEQUENCE(
				"sequence"), ODD_TYPE("odds_type"), TOPIC_NAME("topic_name"), ACCOUNT_ID("account_id"), OFFER_ID("offer_id"), STATUS("status");
		private String	name;

		private HeaderFieldName(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return this.name;
		}
	}

	private String			eventNotificationTopicName;
	private String			marketNotificationTopicName;
	private String			runnerNotificationTopicName;
	private String			priceNotificationTopicName;
	private String			offerNotificationTopicName;

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.processed.EventCreatedEvent)
	 */
	public Message<String> notify(@Headers MessageHeaders headers, EventCreatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for EventCreatedEvent");
			message = getEventNotificationMessage(payload.getEvent(), NotificationType.ADDED);
			logger.debug("Prepared notification Message for EventCreatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aldogrand.kfc.amq.service.kfcNotificationService#notify(com.aldogrand.kfc.msg.events.processed
	 * .MarketCreatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, MarketCreatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for MarketCreatedEvent");
			message = getMarketNotificationMessage(payload.getMarket(), NotificationType.ADDED);
			logger.debug("Prepared notification Message for MarketCreatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.processed.RunnerCreatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, RunnerCreatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for RunnerCreatedEvent");
			message = getRunnerNotificationMessage(payload.getRunner(), NotificationType.ADDED);
			logger.debug("Prepared notification Message for RunnerCreatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);

		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.processed.EventUpdatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, EventUpdatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for EventUpdatedEvent");
			message = getEventNotificationMessage(payload.getEvent(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for EventUpdatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.processed.MarketUpdatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, MarketUpdatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for MarketUpdatedEvent");
			message = getMarketNotificationMessage(payload.getMarket(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for MarketUpdatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.processed.RunnerUpdatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, RunnerUpdatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for RunnerUpdatedEvent");
			message = getRunnerNotificationMessage(payload.getRunner(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for RunnerUpdatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.mapped.EventMappedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, EventMappedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for EventMappedEvent");
			message = getEventNotificationMessage(payload.getEvent(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for EventMappedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.mapped.MarketMappedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, MarketMappedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for MarketMappedEvent");
			message = getMarketNotificationMessage(payload.getMarket(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for MarketMappedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);

		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.mapped.RunnerMappedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, RunnerMappedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for RunnerMappedEvent");
			message = getRunnerNotificationMessage(payload.getRunner(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for RunnerMappedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);

		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.raw.PriceCreatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, PriceCreatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for PriceCreatedEvent");
			message = getPriceNotificationMessage(payload.getPrice(), NotificationType.ADDED);
			logger.debug("Prepared notification Message for PriceCreatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);

		}
		return message;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.raw.PriceCreatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, PriceUpdatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for PriceUpdatedEvent");
			message = getPriceNotificationMessage(payload.getPrice(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for PriceUpdatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);

		}
		return message;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.raw.OfferCreatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, OfferCreatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for OfferCreatedEvent");
			message = getOfferNotificationMessage(payload.getOffer(), NotificationType.ADDED);
			logger.debug("Prepared notification Message for OfferCreatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);

		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aldogrand.kfc.amq.service.kfcNotificationService#notify(@Headers MessageHeaders headers,
	 * com.aldogrand.kfc.msg.events.raw.OfferUpdatedEvent)
	 */
	@Override
	public Message<String> notify(@Headers MessageHeaders headers, OfferUpdatedEvent payload)
	{
		Message<String> message = null;
		try
		{
			logger.debug("Preparing notification Message for OfferUpdatedEvent");
			message = getOfferNotificationMessage(payload.getOffer(), NotificationType.CHANGED);
			logger.debug("Prepared notification Message for OfferUpdatedEvent");
		} catch (RuntimeException rte)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, rte);
		} catch (AssertionError ae)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ae);
		} catch (IOException ioe)
		{
			NotificationExceptionHelper.logOrThrowException(errorChannel,logger,payload, ioe);

		}
		return message;
	}

	/**
	 * @param eventCreated
	 *            TODO
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private Message<String> getEventNotificationMessage(Event event, NotificationType notificationType) throws JsonGenerationException,
			JsonMappingException, IOException
	{
		assert event != null;
		String str = this.objectMapper.writeValueAsString(event);
		MessageBuilder<String> builder = MessageBuilder.withPayload(str)
				.setHeader(HeaderFieldName.OBJECT_CLASS.getName(), event.getClass().getName())
				.setHeader(HeaderFieldName.NOTIFICATION_TYPE.getName(), notificationType.toString())
				.setHeader(HeaderFieldName.TOPIC_NAME.getName(), eventNotificationTopicName)
				.setHeader(HeaderFieldName.EVENT_ID.getName(), event.getId());

		return builder.build();

	}

	/**
	 * @param eventCreated
	 *            TODO
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private Message<String> getMarketNotificationMessage(Market market, NotificationType notificationType) throws JsonGenerationException,
			JsonMappingException, IOException
	{
		assert market != null;
		String str = this.objectMapper.writeValueAsString(market);
		MessageBuilder<String> builder = MessageBuilder.withPayload(str)
				.setHeader(HeaderFieldName.OBJECT_CLASS.getName(), market.getClass().getName())
				.setHeader(HeaderFieldName.NOTIFICATION_TYPE.getName(), notificationType.toString())
				.setHeader(HeaderFieldName.EVENT_ID.getName(), market.getEventId()).setHeader(HeaderFieldName.MARKET_ID.getName(), market.getId())
				.setHeader(HeaderFieldName.TOPIC_NAME.getName(), marketNotificationTopicName);
		return builder.build();
	}

	/**
	 * @param eventCreated
	 *            TODO
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private Message<String> getRunnerNotificationMessage(Runner runner, NotificationType notificationType) throws JsonGenerationException,
			JsonMappingException, IOException
	{
		assert runner != null;
		String str = this.objectMapper.writeValueAsString(runner);
		MessageBuilder<String> builder = MessageBuilder.withPayload(str)
				.setHeader(HeaderFieldName.OBJECT_CLASS.getName(), runner.getClass().getName())
				.setHeader(HeaderFieldName.NOTIFICATION_TYPE.getName(), notificationType.toString())
				.setHeader(HeaderFieldName.EVENT_ID.getName(), runner.getEventId())
				.setHeader(HeaderFieldName.MARKET_ID.getName(), runner.getMarketId()).setHeader(HeaderFieldName.RUNNER_ID.getName(), runner.getId())
				.setHeader(HeaderFieldName.TOPIC_NAME.getName(), runnerNotificationTopicName);

		return builder.build();
	}

	/**
	 * @param eventCreated
	 *            TODO
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private Message<String> getPriceNotificationMessage(Price price, NotificationType notificationType) throws JsonGenerationException,
			JsonMappingException, IOException
	{
		assert price != null;
		String str = this.objectMapper.writeValueAsString(price);
		MessageBuilder<String> builder = MessageBuilder.withPayload(str)
				.setHeader(HeaderFieldName.OBJECT_CLASS.getName(), price.getClass().getName())
				.setHeader(HeaderFieldName.NOTIFICATION_TYPE.getName(), notificationType.toString())
				.setHeader(HeaderFieldName.CONNECTOR_ID.getName(), price.getConnectorId())
				.setHeader(HeaderFieldName.BETTING_PLATFORM_ID.getName(), price.getBettingPlatformId())
				.setHeader(HeaderFieldName.EVENT_ID.getName(), price.getEventId())
				.setHeader(HeaderFieldName.MARKET_ID.getName(), price.getMarketId())
				.setHeader(HeaderFieldName.RUNNER_ID.getName(), price.getRunnerId()).setHeader(HeaderFieldName.PRICE_ID.getName(), price.getId())
				.setHeader(HeaderFieldName.SIDE.getName(), price.getSide().toString())
				.setHeader(HeaderFieldName.SEQUENCE.getName(), price.getSequence())
				.setHeader(HeaderFieldName.ODD_TYPE.getName(), price.getOddsType().toString())
				.setHeader(HeaderFieldName.TOPIC_NAME.getName(), priceNotificationTopicName);
		return builder.build();
	}

	/**
	 * @param eventCreated
	 *            TODO
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private Message<String> getOfferNotificationMessage(Offer offer, NotificationType notificationType) throws JsonGenerationException,
			JsonMappingException, IOException
	{
		assert offer != null;
		String str = this.objectMapper.writeValueAsString(offer);
		MessageBuilder<String> builder = MessageBuilder.withPayload(str).setHeader(HeaderFieldName.CONNECTOR_ID.getName(), offer.getConnectorId())
				.setHeader(HeaderFieldName.EVENT_ID.getName(), offer.getEventId())
				.setHeader(HeaderFieldName.MARKET_ID.getName(), offer.getMarketId())
				.setHeader(HeaderFieldName.RUNNER_ID.getName(), offer.getRunnerId())
				.setHeader(HeaderFieldName.ACCOUNT_ID.getName(), offer.getAccountId()).setHeader(HeaderFieldName.OFFER_ID.getName(), offer.getId())
				.setHeader(HeaderFieldName.SIDE.getName(), offer.getSide().toString())
				.setHeader(HeaderFieldName.STATUS.getName(), offer.getStatus().toString())
				.setHeader(HeaderFieldName.OBJECT_CLASS.getName(), offer.getClass().getName())
				.setHeader(HeaderFieldName.NOTIFICATION_TYPE.getName(), notificationType.toString())
				.setHeader(HeaderFieldName.TOPIC_NAME.getName(), offerNotificationTopicName);

		return builder.build();
	}

	/**
	 * @return the eventNotificationTopicName
	 */
	public String getEventNotificationTopicName()
	{
		return eventNotificationTopicName;
	}

	/**
	 * @param eventNotificationTopicName
	 *            the eventNotificationTopicName to set
	 */
	public void setEventNotificationTopicName(String eventNotificationTopicName)
	{
		this.eventNotificationTopicName = eventNotificationTopicName;
	}

	/**
	 * @return the marketNotificationTopicName
	 */
	public String getMarketNotificationTopicName()
	{
		return marketNotificationTopicName;
	}

	/**
	 * @param marketNotificationTopicName
	 *            the marketNotificationTopicName to set
	 */
	public void setMarketNotificationTopicName(String marketNotificationTopicName)
	{
		this.marketNotificationTopicName = marketNotificationTopicName;
	}

	/**
	 * @return the runnerNotificationTopicName
	 */
	public String getRunnerNotificationTopicName()
	{
		return runnerNotificationTopicName;
	}

	/**
	 * @param runnerNotificationTopicName
	 *            the runnerNotificationTopicName to set
	 */
	public void setRunnerNotificationTopicName(String runnerNotificationTopicName)
	{
		this.runnerNotificationTopicName = runnerNotificationTopicName;
	}

	/**
	 * @return the priceNotificationTopicName
	 */
	public String getPriceNotificationTopicName()
	{
		return priceNotificationTopicName;
	}

	/**
	 * @param priceNotificationTopicName
	 *            the priceNotificationTopicName to set
	 */
	public void setPriceNotificationTopicName(String priceNotificationTopicName)
	{
		this.priceNotificationTopicName = priceNotificationTopicName;
	}

	/**
	 * @return the offerNotificationTopicName
	 */
	public String getOfferNotificationTopicName()
	{
		return offerNotificationTopicName;
	}

	/**
	 * @param offerNotificationTopicName
	 *            the offerNotificationTopicName to set
	 */
	public void setOfferNotificationTopicName(String offerNotificationTopicName)
	{
		this.offerNotificationTopicName = offerNotificationTopicName;
	}
}
