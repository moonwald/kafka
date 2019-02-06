/**
 * 
 */
package com.aldogrand.kfc.notification.helper;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

import java.io.IOException;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessagingException;
import org.apache.log4j.Logger;
import org.springframework.messaging.MessageChannel;

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
 * <b>Title</b> NotificationExceptionHelper.java
 * </p>
 * com.aldogrand.kfc.notification.helper
 * <p>
 * <b>Description</b> kfc-notification-adapter.
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
public class NotificationExceptionHelper
{

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventCreatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR preparing notification Message for EventCreatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<EventCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventCreatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventCreatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventCreatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR preparing notification Message for EventCreatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<EventCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventCreatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventCreatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, OfferUpdatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for OfferUpdatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<OfferUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for OfferUpdatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for OfferUpdatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, OfferUpdatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for OfferUpdatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<OfferUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for OfferUpdatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for OfferUpdatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, OfferCreatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for OfferCreatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<OfferCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for OfferCreatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for OfferCreatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, OfferCreatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for OfferCreatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<OfferCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for OfferCreatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for OfferCreatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, PriceCreatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for PriceCreatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<PriceCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for PriceCreatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for PriceCreatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, PriceUpdatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for PriceUpdatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<PriceUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for PriceUpdatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for PriceUpdatedEvent", ae);
		}
	}
	
	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, PriceCreatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for PriceCreatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<PriceCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for PriceCreatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for PriceCreatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, PriceUpdatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for PriceUpdatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<PriceUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for PriceUpdatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for PriceUpdatedEvent", rte);
		}
	}
	
	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketCreatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for MarketCreatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<MarketCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketCreatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketCreatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketCreatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for MarketCreatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<MarketCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketCreatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketCreatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerCreatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for RunnerCreatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerCreatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerCreatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerCreatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for RunnerCreatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerCreatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerCreatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventUpdatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for EventUpdatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<EventUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventUpdatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventUpdatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventUpdatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for EventUpdatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<EventUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventUpdatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventUpdatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketUpdatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for MarketUpdatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<MarketUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketUpdatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketUpdatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketUpdatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for MarketUpdatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<MarketUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketUpdatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketUpdatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerUpdatedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for RunnerUpdatedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerUpdatedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerUpdatedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerUpdatedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for RunnerUpdatedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerUpdatedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerUpdatedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventMappedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for EventMappedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<EventMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventMappedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventMappedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventMappedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for EventMappedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<EventMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventMappedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventMappedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketMappedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for MarketMappedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<MarketMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketMappedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketMappedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketMappedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for MarketMappedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<MarketMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketMappedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketMappedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ae
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerMappedEvent payload, AssertionError ae)
	{
		logger.error("ERROR whie preparing notification Message for RunnerMappedEvent", ae);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerMappedEvent", ae);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerMappedEvent", ae);
		}
	}

	/**
	 * @param payload
	 * @param rte
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerMappedEvent payload, RuntimeException rte)
	{
		logger.error("ERROR whie preparing notification Message for RunnerMappedEvent", rte);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerMappedEvent", rte);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerMappedEvent", rte);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventCreatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for EventCreatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<EventCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventCreatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventCreatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketCreatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for MarketCreatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<MarketCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketCreatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketCreatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerCreatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for RunnerCreatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerCreatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerCreatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventUpdatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for EventUpdatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<EventUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventUpdatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventUpdatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketUpdatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for MarketUpdatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<MarketUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketUpdatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketUpdatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerUpdatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for RunnerUpdatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerUpdatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerUpdatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, EventMappedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for EventMappedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<EventMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for EventMappedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for EventMappedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, MarketMappedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for MarketMappedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<MarketMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for MarketMappedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for MarketMappedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, RunnerMappedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for RunnerMappedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<RunnerMappedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for RunnerMappedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for RunnerMappedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, PriceCreatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for PriceCreatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<PriceCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for PriceCreatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for PriceCreatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, PriceUpdatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for PriceUpdatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<PriceUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for PriceUpdatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for PriceUpdatedEvent", ioe);
		}
	}
	
	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, OfferCreatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for OfferCreatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<OfferCreatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for OfferCreatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for OfferCreatedEvent", ioe);
		}
	}

	/**
	 * @param payload
	 * @param ioe
	 */
	public static void logOrThrowException(MessageChannel errorChannel, Logger logger, OfferUpdatedEvent payload, IOException ioe)
	{
		logger.error("ERROR preparing notification Message for OfferUpdatedEvent", ioe);
		if (errorChannel != null)
		{
			MessageBuilder<OfferUpdatedEvent> builder = MessageBuilder.withPayload(payload).setHeader(ERROR_CHANNEL, "errorChanel");
			

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
			throw new RuntimeException("ERROR Preparing notification Message for OfferUpdatedEvent", ioe);
		} else
		{
			throw new RuntimeException("ERROR Preparing notification Message for OfferUpdatedEvent", ioe);
		}
	}
}
