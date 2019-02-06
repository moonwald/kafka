package com.aldogrand.kfc.services.mysql.impl;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.dataaccess.EventDao;
import com.aldogrand.sbpc.dataaccess.MarketDao;
import com.aldogrand.sbpc.dataaccess.RunnerDao;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.KFCEventFactoryImpl;
import com.aldogrand.kfc.msg.events.processed.EventUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.MarketUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.RunnerUpdatedEvent;
import com.aldogrand.kfc.msg.events.processor.EventProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.MarketProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.RunnerProcessedEvent;
import com.aldogrand.kfc.services.exception.DataWriterException;
import com.aldogrand.kfc.services.mysql.ProcessDataWriterService;

/**
 * 
 * <p>
 * <b>Title</b> DataWriterServiceImpl
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
 * 
 * @author aldogrand
 *
 */
@Transactional
public class ProcessDataWriterServiceImpl implements ProcessDataWriterService
{

	private final Logger	logger	= LogManager.getLogger(getClass());

	private EventDao		eventDao;
	private MarketDao		marketDao;
	private RunnerDao		runnerDao;

	private KFCEventFactory	eventFactory;
	private EventDto		eventDto;

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;

	@Autowired
	private KFCProducer		kafkaPublisher;

	@Autowired
	@Qualifier("KafkaProducerChannel")
	private MessageChannel     kafkaProducerChannel;

	public ProcessDataWriterServiceImpl()
	{
	}

	@Override
	public void update(EventProcessedEvent processedEvent)
	{
		logger.debug("Processed Event update request: " + processedEvent);
		try
		{
			updateEvent(processedEvent);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating EventProcessedEvent.");
			logOrThrowException(processedEvent, rte);
		} catch (AssertionError ae)
		{
			logger.error("Error updating EventProcessedEvent.");
			logOrThrowException(processedEvent, ae);
		} catch (DataWriterException dwe)
		{
			logger.error("Error updating EventProcessedEvent");
			logOrThrowException(processedEvent, dwe);
		}
	}

	@Override
	public void update(MarketProcessedEvent processedMarket)
	{
		logger.debug("Processed Event update request: " + processedMarket);
		try
		{
			updateMarket(processedMarket);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating MarketProcessedEvent.");
			logOrThrowException(processedMarket, rte);
		} catch (AssertionError ae)
		{
			logger.error("Error updating MarketProcessedEvent.");
			logOrThrowException(processedMarket, ae);
		} catch (DataWriterException dwe)
		{
			logger.error("Error updating MarketProcessedEvent.");
			logOrThrowException(processedMarket, dwe);
		}
	}

	@Override
	public void update(RunnerProcessedEvent processedRunner)
	{
		logger.debug("Processed Event update request: " + processedRunner);
		try
		{
			updateRunner(processedRunner);
		} catch (RuntimeException rte)
		{
			logger.error("Error updating RunnerProcessedEvent.");
			logOrThrowException(processedRunner, rte);
		} catch (AssertionError ae)
		{
			logger.error("Error updating RunnerProcessedEvent.");
			logOrThrowException(processedRunner, ae);
		}
	}

	/**
	 * Update the event from the {@link com.aldogrand.sbpc.model}. update the Event received from the Data
	 * Processor
	 *
	 * @param processedEvent
	 * @throws DataWriterException
	 * @throws IOException
	 * @throws ProducerException
	 */	
	public void updateEvent(final EventProcessedEvent processedEvent) throws DataWriterException
	{
		Event event = processedEvent.getEvent();
		assert event != null;
		logger.debug("Entering updateEvent() method.");
		// Read the event data for that event id.
		eventDto = eventDao.getEvent(event.getId());
		if (eventDto == null)
		{			
			// If there is no event we stop doing anything.
			logger.debug(String.format("Event %s is not mapped yet.", event.getId()));
			return;
		} else
		{
			doUpdateCompleteEvent(event);
			// Send notification after update
			EventUpdatedEvent eventUpdated = new EventUpdatedEvent();
			eventUpdated.setIntegrationModuleId(processedEvent.getIntegrationModuleId());
			eventUpdated.setIntegrationModuleName(processedEvent.getIntegrationModuleName());
			eventUpdated.setEvent(event);
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
			kafkaProducerChannel.send(KFCEvent.createMessage(eventUpdated));
		}
		logger.debug("Leaving updateEvent() method.");
	}

	/**
	 * Bus
	 * 
	 * @param event
	 */
	public void doUpdateCompleteEvent(Event event) throws DataWriterException
	{
		logger.debug("Writing event to database. doUpdateCompleteEvent()");
		EventDto eventDto = eventDao.getEvent(event.getId());
		eventDto.setName(event.getName());
		eventDto.setStartTime(event.getStartTime());
		eventDto.setEventStatus(event.getEventStatus());
		eventDao.saveEvent(eventDto);
		logger.debug("Event is updated in database. doUpdateCompleteEvent()");
	}

	/**
	 * Update the market from the {@link com.aldogrand.sbpc.model}. update the Market received from the Data
	 * Processor
	 *
	 * @param marketProcessedEvent
	 * @throws DataWriterException
	 * @throws IOException
	 * @throws ProducerException
	 */	
	public void updateMarket(final MarketProcessedEvent marketProcessedEvent) throws DataWriterException
	{
		assert marketProcessedEvent != null;
		Market market = marketProcessedEvent.getMarket();
		assert market != null;
		logger.debug("Entering updateMarket() method");
		MarketDto marketDto;
		// Read the event data for that event id.
		marketDto = marketDao.getMarket(market.getId().longValue());
		if (marketDto == null)
		{
			// If there is no event we stop doing anything.
			logger.debug(String.format("Market %s is not mapped yet.", market.getId()));
			return;
		} else
		{
			doUpdateCompleteMarket(market);
			// Send notification after update
			MarketUpdatedEvent marketUpdated = new MarketUpdatedEvent();
			marketUpdated.setIntegrationModuleId(marketProcessedEvent.getIntegrationModuleId());
			marketUpdated.setIntegrationModuleName(marketProcessedEvent.getIntegrationModuleName());
			marketUpdated.setMarket(market);
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
			kafkaProducerChannel.send(KFCEvent.createMessage(marketUpdated));
		}
		logger.debug("Leaving updateMarket() method");
	}

	/**
	 * Bus
	 * 
	 * @param market
	 */
	public void doUpdateCompleteMarket(Market market) throws DataWriterException
	{
		logger.debug("Writing Market to database. doUpdateCompleteMarket()");
		MarketDto marketDto = marketDao.getMarket(market.getId());

		marketDto.setId(market.getId());
		marketDto.setEvent(eventDao.getEvent(market.getEventId()));
		marketDto.setName(market.getName());
		marketDto.setType(market.getType());
		marketDto.setHandicap(market.getHandicap());
		marketDto.setPeriod(market.getPeriod());
		marketDto.setMarketStatus(market.getMarketStatus());

		marketDao.saveMarket(marketDto);
		logger.debug("Market is updated in database. doUpdateCompleteMarket()");
	}

	/**
	 * Update the runner from the {@link com.aldogrand.sbpc.model}. update the Runner received from the Data
	 * Processor
	 *
	 * @param runnerProcessedEvent
	 * @throws IOException
	 * @throws ProducerException
	 */
	public void updateRunner(final RunnerProcessedEvent runnerProcessedEvent)
	{
		Runner runner = runnerProcessedEvent.getRunner();
		assert runner != null;

		logger.debug("inside updateRunner() method");
		if (runnerDao.getRunner(runner.getId().longValue()) == null)
		{
			// If there is no event we stop doing anything.
			logger.debug(String.format("Runner %s is not mapped yet.", runner.getId()));
			return;
		} else
		{
			doUpdateCompleteRunner(runner);
			// Send notification after update
			RunnerUpdatedEvent runnerUpdated = new RunnerUpdatedEvent();
			runnerUpdated.setIntegrationModuleId(runnerProcessedEvent.getIntegrationModuleId());
			runnerUpdated.setIntegrationModuleName(runnerProcessedEvent.getIntegrationModuleName());
			runnerUpdated.setRunner(runner);
			KFCEventFactory KFCEvent = new KFCEventFactoryImpl();
			kafkaProducerChannel.send(KFCEvent.createMessage(runnerUpdated));
		}
		logger.debug("Leaving updateRunner() method");
	}

	/**
	 * @param runner
	 */
	public void doUpdateCompleteRunner(final Runner runner)
	{
		logger.debug("Writing Runer to database. doUpdateCompleteRunner()");
		RunnerDto runnerDto = runnerDao.getRunner(runner.getId().longValue());
		runnerDto.setName(runner.getName());
		runnerDto.setHandicap(runner.getHandicap());
		runnerDto.setSide(runner.getSide());
		runnerDto.setType(runner.getType());
		runnerDto.setSequence(runner.getSequence());
		runnerDto.setRotationNumber(runner.getRotationNumber());
		runnerDto.setRunnerStatus(runner.getRunnerStatus());
		runnerDto.setResultStatus(runner.getResultStatus());
		runnerDao.saveRunner(runnerDto);
		logger.debug("Runner is updated in database. doUpdateCompleteRunner()");
	}

	public EventDao getEventDao()
	{
		return eventDao;
	}

	public void setEventDao(EventDao eventDao)
	{
		this.eventDao = eventDao;
	}

	public MarketDao getMarketDao()
	{
		return marketDao;
	}

	public void setMarketDao(MarketDao marketDao)
	{
		this.marketDao = marketDao;
	}

	public RunnerDao getRunnerDao()
	{
		return runnerDao;
	}

	public void setRunnerDao(RunnerDao runnerDao)
	{
		this.runnerDao = runnerDao;
	}

	public KFCEventFactory getEventFactory()
	{
		return eventFactory;
	}

	public void setEventFactory(KFCEventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public KFCProducer getKafkaPublisher()
	{
		return kafkaPublisher;
	}

	public void setKafkaPublisher(KFCProducer kafkaPublisher)
	{
		this.kafkaPublisher = kafkaPublisher;
	}

	/**
	 * @param processedEvent
	 * @param rte
	 */
	private void logOrThrowException(EventProcessedEvent processedEvent, RuntimeException rte)
	{
		if (errorChannel != null)
		{
			MessageBuilder<EventProcessedEvent> builder = MessageBuilder.withPayload(processedEvent).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error updating event.", rte);
		}
	}

	/**
	 * @param processedEvent
	 * @param ae
	 */
	private void logOrThrowException(EventProcessedEvent processedEvent, AssertionError ae)
	{
		if (errorChannel != null)
		{
			MessageBuilder<EventProcessedEvent> builder = MessageBuilder.withPayload(processedEvent).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
		} else
		{
			throw new RuntimeException("Error updating event.", ae);
		}
	}

	/**
	 * @param processedEvent
	 * @param dwe
	 */
	private void logOrThrowException(EventProcessedEvent processedEvent, DataWriterException dwe)
	{
		if (errorChannel != null)
		{
			MessageBuilder<EventProcessedEvent> builder = MessageBuilder.withPayload(processedEvent).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), dwe)).build());
		} else
		{
			throw new RuntimeException("Error updating event.", dwe);
		}
	}

	/**
	 * @param processedEvent
	 * @param ioe
	 */
	private void logOrThrowException(EventProcessedEvent processedEvent, IOException ioe)
	{
		if (errorChannel != null)
		{
			MessageBuilder<EventProcessedEvent> builder = MessageBuilder.withPayload(processedEvent).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
		} else
		{
			throw new RuntimeException("Error updating event.", ioe);
		}
	}

	/**
	 * @param processedMarket
	 * @param rte
	 */
	private void logOrThrowException(MarketProcessedEvent processedMarket, RuntimeException rte)
	{
		if (errorChannel != null)
		{
			MessageBuilder<MarketProcessedEvent> builder = MessageBuilder.withPayload(processedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error updating market.", rte);
		}
	}

	/**
	 * @param processedMarket
	 * @param ae
	 */
	private void logOrThrowException(MarketProcessedEvent processedMarket, AssertionError ae)
	{
		if (errorChannel != null)
		{
			MessageBuilder<MarketProcessedEvent> builder = MessageBuilder.withPayload(processedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
		} else
		{
			throw new RuntimeException("Error updating Market.", ae);
		}
	}

	/**
	 * @param processedMarket
	 * @param dwe
	 */
	private void logOrThrowException(MarketProcessedEvent processedMarket, DataWriterException dwe)
	{
		if (errorChannel != null)
		{
			MessageBuilder<MarketProcessedEvent> builder = MessageBuilder.withPayload(processedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), dwe)).build());
		} else
		{
			throw new RuntimeException("Error updating Market.", dwe);
		}
	}

	/**
	 * @param processedMarket
	 * @param ioe
	 */
	private void logOrThrowException(MarketProcessedEvent processedMarket, IOException ioe)
	{
		if (errorChannel != null)
		{
			MessageBuilder<MarketProcessedEvent> builder = MessageBuilder.withPayload(processedMarket).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
		} else
		{
			throw new RuntimeException("Error updating Market.", ioe);
		}
	}

	/**
	 * @param processedRunner
	 * @param ioe
	 */
	private void logOrThrowException(RunnerProcessedEvent processedRunner, IOException ioe)
	{
		if (errorChannel != null)
		{
			MessageBuilder<RunnerProcessedEvent> builder = MessageBuilder.withPayload(processedRunner).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ioe)).build());
		} else
		{
			throw new RuntimeException("Error updating Runner.", ioe);
		}
	}

	/**
	 * @param processedRunner
	 * @param ae
	 */
	private void logOrThrowException(RunnerProcessedEvent processedRunner, AssertionError ae)
	{
		if (errorChannel != null)
		{
			MessageBuilder<RunnerProcessedEvent> builder = MessageBuilder.withPayload(processedRunner).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
		} else
		{
			throw new RuntimeException("Error updating Runner.", ae);
		}
	}

	/**
	 * @param processedRunner
	 * @param rte
	 */
	private void logOrThrowException(RunnerProcessedEvent processedRunner, RuntimeException rte)
	{
		if (errorChannel != null)
		{
			MessageBuilder<RunnerProcessedEvent> builder = MessageBuilder.withPayload(processedRunner).setHeader(ERROR_CHANNEL, "errorChanel");

			errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), rte)).build());
		} else
		{
			throw new RuntimeException("Error updating Runner.", rte);
		}
	}

}
