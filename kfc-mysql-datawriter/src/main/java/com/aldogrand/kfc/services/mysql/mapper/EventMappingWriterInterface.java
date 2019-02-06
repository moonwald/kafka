/**
 * 
 */
package com.aldogrand.kfc.services.mysql.mapper;

import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.SourceEvent;

/**
 * <p>
 * <b>Title</b> EventMappingWriterInterface.java <br>
 * 
 * <pre>
 * This Service has two functions and only deals with the events mapping.
 * {@link #createEvent(SourceEvent, Event)}
 * 		1. Create new event for the source event received and map them
 * {@link #updateEvent(SourceEvent, Event)}
 * 		2. Map the source event with the target event.
 * </pre>
 * 
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
public interface EventMappingWriterInterface
{

	/**
	 * Map the source event with the target event.
	 * 
	 * @param sourceEvent
	 *            to be mapped with event
	 * @param event
	 *            existing Event to be mapped
	 * @return Event Mapped event
	 */
	public Event updateEvent(SourceEvent sourceEvent, Event event);

	/**
	 * Create new event for the source event received and map them
	 * 
	 * @param sourceEvent
	 *            to be mapped
	 * @param event
	 *            to be Created and mapped
	 * @return Event Mapped event
	 */
	public Event createEvent(SourceEvent sourceEvent, Event event);

}