package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.EventDto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>Title</b> EventDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link EventDto}s.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public interface EventDao
{
    /**
     * Return the {@link EventDto} with the given id
     * @param id
     * @return event
     */
    EventDto getEvent(long id);

    /**
     * Lock the {@link EventDto} and refresh its state.
     * @param event
     */
    void lock(EventDto event);
    
    /**
     * Get the number of {@link EventDto}s.
     * @return
     */
    int getNumberOfEvents();
    
    /**
     * Get the {@link EventDto}s.<br/>
     * Limit the results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<EventDto> getEvents(Integer offset, Integer maxResults);

    /**
     * Get the {@link EventDto}s. Include the s with the {@link EventDto}s.<br/>
     * Limit the results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<EventDto> getEventsWithMappings(Integer offset, Integer maxResults);
    
    /**
     * Get the {@link EventDto} which have no associated s.<br/>
     * Limit the results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<EventDto> getEventsWithoutMappings(Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link EventDto}s which have no associated s
     * @return
     */
    int getNumberOfEventsWithoutMappings();
    
    /**
     * Get the {@link EventDto}s with the given ids that are mapped from the connector
     * with the connectorsId.
     * @param connectorId
     * @param ids
     * @return
     */
    List<EventDto> getEvents(long connectorId, List<Long> ids);
    
    /**
     * Get the {@link EventDto}s with the given that are mapped from the connector
     * with the connectorId.
     * @param connectorId
     * @return
     */
    List<EventDto> getEvents(long connectorId);

    /**
     * Get the number of {@link EventDto}s optionally filtered by connector ids
     * and/or modified date.
     * @param connectorIds If set only include events that are mapped from these connectors.
     * @param modifiedSince If set only include events that were modified since this date.
     * @return
     */
    int getNumberOfEvents(List<Long> connectorIds, Date modifiedSince);
    
    /**
     * Get the {@link EventDto}s optionally filtered by connector ids
     * and/or modified date.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connectorIds If set only include events that are mapped from these connectors.
     * @param modifiedSince If set only include events that were modified since this date.
     * @param metaTagIds If set only include events that have s with the given ids.
     * @param searchQuery If set only include events with names that match the query.
     * @param offset
     * @param maxResults
     * @return
     */
    List<EventDto> getEvents(List<Long> connectorIds, Date modifiedSince,
            List<Long> metaTagIds, String searchQuery, List<Long> eventIds, Integer offset, Integer maxResults);
            
    /**
     * Get the {@link EventDto}s that are mapped from the connector with the id and
     * that have the mapped ids on that connector.
     * @param connectorId
     * @param sourceIds
     * @return
     */
    List<EventDto> getMappedEvents(long connectorId, List<String> sourceIds);
    
    /**
     * Get the number of {@link EventDto}s that have either the name or a 
     * variant of the name given.
     * @param name
     * @return
     */
    int getNumberOfEvents(String name);
    
    /**
     * Get the {@link EventDto}s that are represented in the interested clients table
     * @return
     */
    List<EventDto> getInterestedEvents(Integer offset, Integer maxResults);
    
    
    /**
     * Get the {@link EventDto}s that have either one of the names or a variant of the names given.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
	List<EventDto> getCandidateEventsForMapping(List<String> eventNameVariants, Integer offset, Integer maxResults);

    /**
     * Save the {@link EventDto} returning the updated version.
     * @param event
     * @return
     */
    EventDto saveEvent(EventDto event);
    
    /**
     * Delete the {@link EventDto}.
     * @param event
     */
    void deleteEvent(EventDto event);
    
    /**
     * Delete the {@link EventDto}s.
     * @param events
     */
    void deleteEvents(List<EventDto> events);
}
