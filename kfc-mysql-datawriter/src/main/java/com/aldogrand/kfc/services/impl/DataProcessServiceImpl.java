package com.aldogrand.kfc.services.impl;

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
import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.dataaccess.ConnectorDao;
import com.aldogrand.sbpc.dataaccess.SourceEventDao;
import com.aldogrand.sbpc.dataaccess.SourceMarketDao;
import com.aldogrand.sbpc.dataaccess.SourceRunnerDao;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.ModelFactory;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.dataaccess.model.SourceEventDto;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto;
import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.sbpc.model.SourceEvent;
import com.aldogrand.sbpc.model.SourceMarket;
import com.aldogrand.sbpc.model.SourceRunner;
import com.aldogrand.kfc.msg.events.processor.EventProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.MarketProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.RunnerProcessedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceEventCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceEventUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceMarketCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceMarketUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceRunnerCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceRunnerUpdatedEvent;
import com.aldogrand.kfc.services.DataProcessService;
import com.aldogrand.kfc.services.exception.DataProcessException;

/**
 * This service is to process the data created by the source data writer Currently this service process the data for
 * Event market and runner Created by aldogrand on 19/11/14.
 */
@Transactional(readOnly=true)
public class DataProcessServiceImpl implements DataProcessService
{
	private final Logger	logger	= LogManager.getLogger(getClass());
	
	private ConnectorDao	connectorDao;
	private SourceEventDao	sourceEventDao;
	private SourceMarketDao	sourceMarketDao;
	private SourceRunnerDao	sourceRunnerDao;

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;
        
	/**
	 * 
	 */
	public DataProcessServiceImpl(ConnectorDao connectorDao, SourceEventDao sourceEventDao, SourceMarketDao sourceMarketDao,
			SourceRunnerDao sourceRunnerDao)
	{
		this.connectorDao = connectorDao;
		this.sourceEventDao = sourceEventDao;
		this.sourceMarketDao = sourceMarketDao;
		this.sourceRunnerDao = sourceRunnerDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.services.DataProcessService#process(org.springframework.messaging.MessageHeaders, com.aldogrand.kfc.msg.events.raw.SourceEventCreatedEvent)
	 */
	public void process(@Headers MessageHeaders headers, @Payload SourceEventCreatedEvent sourceEventCreated) throws DataProcessException
	{
		assert sourceEventCreated != null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.services.DataProcessService#process(org.springframework.messaging.MessageHeaders, com.aldogrand.kfc.msg.events.raw.SourceEventUpdatedEvent)
	 */
	public EventProcessedEvent process(@Headers MessageHeaders headers, @Payload SourceEventUpdatedEvent sourceEventUpdated) throws DataProcessException
	{
		assert sourceEventUpdated != null;

		Event event = null;
		SourceEvent sourceEvent = sourceEventUpdated.getSourceEvent();
		Long connectorId = new Long(sourceEventUpdated.getIntegrationModuleId());

		boolean updated = false;

		logger.debug("Processing the source event " +sourceEvent.getSourceName() + " from connector " + connectorId + ".");

		try
		{
			ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
			assert connectorDto != null;

			SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(connectorDto.getId(), sourceEvent.getSourceId().toString());

			/*
             * All the sources events coming here have to creators.
             * No need to check again.
             */
			if (sourceEventDto != null)
			{
				EventDto eventDto = sourceEventDto.getEvent();

				boolean mapped = (eventDto != null);

				if (mapped)
				{
					updated = false;
					if (isNotEqual(eventDto.getName(), sourceEvent.getSourceName()))
					{
						eventDto.setName(sourceEvent.getSourceName());
						updated = true;
					} else if (isNotEqual(eventDto.getEventStatus(), sourceEvent.getStatus()))
					{
						eventDto.setEventStatus(sourceEvent.getStatus());
						updated = true;
					} else if (Math.abs(eventDto.getStartTime().getTime() - sourceEvent.getStartTime().getTime()) > 1000)
					{
						eventDto.setStartTime(sourceEvent.getStartTime());
						updated = true;
					}
					if (updated)
					{
						event = ModelFactory.createEvent(eventDto, false);
						EventProcessedEvent processedEvent = new EventProcessedEvent();
						processedEvent.setIntegrationModuleId(sourceEventUpdated.getIntegrationModuleId());
						processedEvent.setIntegrationModuleName(sourceEventUpdated.getIntegrationModuleName());
						processedEvent.setEvent(event);
										
						return processedEvent;						
					}
				}
				return null;
			} 			
		} catch (RuntimeException ex)
		{
			if (errorChannel != null)
			{
				MessageBuilder<SourceEventUpdatedEvent> builder = MessageBuilder.withPayload(sourceEventUpdated).setHeader(ERROR_CHANNEL,
						"errorChanel");
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ex)).build());
			} else
			{
				throw new DataProcessException("Error processing event", ex);
			}			
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.services.DataProcessService#process(org.springframework.messaging.MessageHeaders, com.aldogrand.kfc.msg.events.raw.SourceMarketCreatedEvent)
	 */
	public void process(@Headers MessageHeaders headers, @Payload SourceMarketCreatedEvent sourceMarketCreated) throws DataProcessException
	{
		assert sourceMarketCreated != null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.services.DataProcessService#process(org.springframework.messaging.MessageHeaders, com.aldogrand.kfc.msg.events.raw.SourceMarketUpdatedEvent)
	 */
	@Transactional
	public MarketProcessedEvent process(@Headers MessageHeaders headers, @Payload SourceMarketUpdatedEvent sourceMarketUpdated) throws DataProcessException
	{
		assert sourceMarketUpdated != null;

		Market market = null;
		SourceMarket sourceMarket = sourceMarketUpdated.getSourceMarket();
		Long connectorId = new Long(sourceMarketUpdated.getIntegrationModuleId());
		boolean updated = false;

		logger.debug("Processing the source market " +sourceMarket.getSourceName() + " from connector " + connectorId + ".");		
		try
		{
			ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
			assert connectorDto != null;
			SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorDto.getId(), sourceMarket.getSourceId().toString());
			
			/*
             * All the sources markets coming here have to creators.
             * No need to check again.
             */
			if (sourceMarketDto != null)
			{
				MarketDto marketDto = sourceMarketDto.getMarket();
				boolean mapped = (marketDto != null);
				
				if (mapped)
				{
					updated = false;
					if (isNotEqual(marketDto.getName(), sourceMarket.getSourceName()))
					{
						marketDto.setName(sourceMarket.getSourceName());
						updated = true;
					} else if (isNotEqual(marketDto.getMarketStatus(), sourceMarket.getMarketStatus()))
					{
						marketDto.setMarketStatus(sourceMarket.getMarketStatus());
						updated = true;
					} else if (isNotEqual(marketDto.getType(), sourceMarket.getType()))
					{
						marketDto.setType(sourceMarket.getType());
						updated = true;
					} else if (isNotEqual(marketDto.getHandicap(), sourceMarket.getHandicap()))
					{
						marketDto.setHandicap(sourceMarket.getHandicap());
						updated = true;
					} else if (isNotEqual(marketDto.getPeriod(), sourceMarket.getPeriod()))
					{
						marketDto.setPeriod(sourceMarket.getPeriod());
						updated = true;
					}

					if (updated)
					{
						market = ModelFactory.createMarket(marketDto, false);
						MarketProcessedEvent processedMarket = new MarketProcessedEvent();
						processedMarket.setIntegrationModuleId(sourceMarketUpdated.getIntegrationModuleId());
						processedMarket.setIntegrationModuleName(sourceMarketUpdated.getIntegrationModuleName());
						processedMarket.setMarket(market);
						
						return processedMarket;
					}
				}
			}
		} catch (RuntimeException ex)
		{
			if (errorChannel != null)
			{
				MessageBuilder<SourceMarketUpdatedEvent> builder = MessageBuilder.withPayload(sourceMarketUpdated).setHeader(ERROR_CHANNEL,
						"errorChanel");
				ex = new RuntimeException(builder.build().toString());
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ex)).build());
			} else
			{
				throw new DataProcessException("Error processing event", ex);
			}
		} 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.services.DataProcessService#process(org.springframework.messaging.MessageHeaders, com.aldogrand.kfc.msg.events.raw.SourceRunnerCreatedEvent)
	 */
	@Override
	public void process(@Headers MessageHeaders headers, SourceRunnerCreatedEvent sourceRunnerCreated) throws DataProcessException
	{
		assert sourceRunnerCreated != null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.services.DataProcessService#process(org.springframework.messaging.MessageHeaders, com.aldogrand.kfc.msg.events.raw.SourceRunnerUpdatedEvent)
	 */
	@Override
	public RunnerProcessedEvent process(@Headers MessageHeaders headers, @Payload SourceRunnerUpdatedEvent sourceRunnerUpdated) throws DataProcessException
	{
		assert sourceRunnerUpdated != null;
		Runner runner = null;
		SourceRunner sourceRunner = sourceRunnerUpdated.getSourceRunner();
		Long connectorId = new Long(sourceRunnerUpdated.getIntegrationModuleId());

		boolean updated = false;

		logger.debug("Processing the source runner " +sourceRunner.getSourceName() + " from connector " + connectorId + ".");

		try
		{
			ConnectorDto connectorDto = connectorDao.getConnector(connectorId);
			assert connectorDto != null;
			SourceRunnerDto sourceRunnerDto = sourceRunnerDao.getSourceRunner(connectorId, sourceRunner.getSourceId().toString());

			/*
			 * All the sources events coming here have to creators.
			 * No need to check again.
			 */
			if (sourceRunnerDto != null)
			{
				RunnerDto runnerDto = sourceRunnerDto.getRunner();
				boolean mapped = (runnerDto != null);
				
				if (mapped)
				{
					updated = false;
					if (!runnerDto.getName().equalsIgnoreCase(sourceRunner.getSourceName()))
					{
						runnerDto.setName(sourceRunner.getSourceName());
						updated = true;
					} else if (isNotEqual(runnerDto.getRunnerStatus(), sourceRunner.getRunnerStatus()))
					{
						runnerDto.setRunnerStatus(sourceRunner.getRunnerStatus());
						updated = true;
					} else if (isNotEqual(runnerDto.getType(), sourceRunner.getType()))
					{
						runnerDto.setType(sourceRunner.getType());
						updated = true;
					} else if (isNotEqual(runnerDto.getHandicap(), sourceRunner.getHandicap()))
					{
						runnerDto.setHandicap(sourceRunner.getHandicap());
						updated = true;
					} else if (isNotEqual(runnerDto.getSide(), sourceRunner.getSide()))
					{
						runnerDto.setSide(sourceRunner.getSide());
						updated = true;
					} else if (isNotEqual(runnerDto.getSequence(), sourceRunner.getSequence()))
					{
						runnerDto.setSequence(sourceRunner.getSequence());
						updated = true;
					} else if (isNotEqual(runnerDto.getRotationNumber(), sourceRunner.getRotationNumber()))
					{
						runnerDto.setRotationNumber(sourceRunner.getRotationNumber());
						updated = true;
					} else if (isNotEqual(runnerDto.getResultStatus(), sourceRunner.getResultStatus()))
					{
						runnerDto.setResultStatus(sourceRunner.getResultStatus());
						updated = true;
					}

					if (updated)
					{
						runner = ModelFactory.createRunner(runnerDto, false);
						RunnerProcessedEvent processedRunner = new RunnerProcessedEvent();
						processedRunner.setIntegrationModuleId(sourceRunnerUpdated.getIntegrationModuleId());
						processedRunner.setIntegrationModuleName(sourceRunnerUpdated.getIntegrationModuleName());
						processedRunner.setRunner(runner);
						
						return processedRunner;          
					}
				}
			}
		} catch (RuntimeException ex)
		{
			if (errorChannel != null)
			{
				MessageBuilder<SourceRunnerUpdatedEvent> builder = MessageBuilder.withPayload(sourceRunnerUpdated).setHeader(ERROR_CHANNEL,
						"errorChanel");
				ex = new RuntimeException(builder.build().toString());
				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ex)).build());
			} else
			{
				throw new DataProcessException("Error processing event", ex);
			}
		}
		return null;
	}
    
	/**
	 * This method compare the two objects of type AbstractDto It returns true if they are not equal and false otherwise.
	 * 
	 * @param a
	 *            first object
	 * @param b
	 *            second object
	 * @return boolean
	 */
	private boolean isNotEqual(Object a, Object b)
	{
		if (a == null && b == null)
		{
			return false;
		} else if (a != null && b == null)
		{
			return true;
		} else if (a == null && b != null)
		{
			return true;
		} else if (a.equals(b))
		{
			return false;
		} else
		{
			return true;
		}
	}
}
