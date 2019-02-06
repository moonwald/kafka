/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper.impl;

import com.aldogrand.sbpc.dataaccess.EventDao;
import com.aldogrand.sbpc.dataaccess.SourceEventDao;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.ModelFactory;
import com.aldogrand.sbpc.dataaccess.model.NameVariantDto;
import com.aldogrand.sbpc.dataaccess.model.SourceEventDto;
import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.SourceEvent;
import com.aldogrand.kfc.services.mysql.mapper.EventMappingWriterInterface;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

/**
 * <p>
 * <b>Title</b> EventMappingWriter.java
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
@Transactional
public class EventMappingWriter implements EventMappingWriterInterface
{
	private final Logger	logger	= LogManager.getLogger(getClass());
	private EventDao		eventDao;

	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;

	private SourceEventDao	sourceEventDao;

	/**
	 * @param eventDao
	 * @param sourceEventDao
	 */
	public EventMappingWriter(EventDao eventDao, SourceEventDao sourceEventDao)
	{
		this.eventDao = eventDao;
		this.sourceEventDao = sourceEventDao;
	}

	/**
	 * @see com.aldogrand.kfc.services.mysql.mapper.EventMappingWriterInterface#updateEvent(com.aldogrand.sbpc.model.SourceEvent,
	 *      com.aldogrand.sbpc.model.Event)
	 * @param sourceEvent
	 * @param event
	 */
	public Event updateEvent(SourceEvent sourceEvent, Event event) throws RuntimeException
	{
		Event returnEvent = null;
		try
		{
			logger.debug("updating the mapping for the existing event");
			assert event != null;
			assert sourceEvent != null;
			EventDto eventDto = eventDao.getEvent(event.getId());
			SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(sourceEvent.getId(), false);
			// Since this is an update event event should exists in the database all the time but just in case.
			if (eventDto != null && sourceEventDto != null)
			{
				logger.debug(String.format("Mapping source event Id %s to existing event Id %s", sourceEventDto.getId(), eventDto.getId()));
				eventDto.setLastChangeTime(new Date());
				eventDao.saveEvent(eventDto);
				sourceEventDto.setEvent(eventDto);
				sourceEventDao.saveSourceEvent(sourceEventDto);
				returnEvent = ModelFactory.createEvent(eventDto, false);
				logger.debug(String.format("Mapped source event Id %s to existing event Id %s", sourceEventDto.getId(), eventDto.getId()));
			}
		} catch (TransactionException te) {
			logger.error(String.format("Error updating the mapping for the existing event %s", sourceEvent.getSourceName()), te.getMostSpecificCause());
			if (errorChannel != null)
			{
				MessageBuilder<SourceEvent> builder = MessageBuilder.withPayload(sourceEvent).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error updating the mapping for the existing event.", te);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error updating the mapping for the existing event.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceEvent> builder = MessageBuilder.withPayload(sourceEvent).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error updating the mapping for the existing event.", ae);
			}
		}
		return returnEvent;
	}

	/**
	 *
	 * @param sourceEvent
	 * @param event
	 */
	@Override	
	public Event createEvent(SourceEvent sourceEvent, Event event) throws RuntimeException
	{
		Event returnEvent = null;
		try
		{
			assert sourceEvent != null;
			SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(sourceEvent.getId(), false);
			List<String> eventNameVariants = sourceEvent.getNameVariants();
			if (sourceEventDto != null && sourceEventDto.getEvent() == null)
			{
				// check if the mapping is already updated or not
				logger.debug(String.format("Creating new Event for source event Id %s", sourceEventDto.getId()));
				EventDto eventDto = new EventDto();
				eventDto.setName(eventNameVariants.get(0));
				for (String eventNameVariant : eventNameVariants)
				{
					NameVariantDto nameVariantDto = new NameVariantDto();
					nameVariantDto.setVariant(eventNameVariant);
					eventDto.getNameVariants().add(nameVariantDto);
				}
				eventDto.getMetaTags().addAll(sourceEventDto.getMetaTags());
				eventDto.setStartTime(sourceEventDto.getStartTime());
				eventDto.setLastChangeTime(new Date());
				eventDto.setEventStatus(sourceEventDto.getStatus());

				eventDto = eventDao.saveEvent(eventDto);
				sourceEventDto.setEvent(eventDto);
				sourceEventDto.setCreator(true);
				sourceEventDao.saveSourceEvent(sourceEventDto);
				returnEvent = ModelFactory.createEvent(eventDto, false);
				logger.debug(String.format("Mapped source event %s new Event for source event Id %s", sourceEventDto.getId(), eventDto.getId()));
			} else
			{
				logger.warn("Mapping of the events can not be performed as source event is having problems.");
			}
		} catch (TransactionException te) {
			logger.error(String.format("Mapped source event %s to new event.", sourceEvent.getSourceName()), te.getMostSpecificCause());
			
			if (errorChannel != null)
			{
				MessageBuilder<SourceEvent> builder = MessageBuilder.withPayload(sourceEvent).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), te)).build());
			} else
			{
				throw new RuntimeException("Error creating the mapping for event.", te);
			}
		} catch (AssertionError ae)
		{
			logger.error("Error creating the mapping for event.");
			if (errorChannel != null)
			{
				MessageBuilder<SourceEvent> builder = MessageBuilder.withPayload(sourceEvent).setHeader(ERROR_CHANNEL, "errorChanel");

				errorChannel.send(MessageBuilder.withPayload(new MessagingException(builder.build().toString(), ae)).build());
			} else
			{
				throw new RuntimeException("Error creating the mapping for event.", ae);
			}
		}
		return returnEvent;
	}
}
