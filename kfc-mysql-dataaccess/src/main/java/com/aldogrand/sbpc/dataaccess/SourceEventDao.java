package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.SourceEventDto;

/**
 * <p>
 * <b>Title</b> SourceEventDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link SourceEventDto}s.
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
public interface SourceEventDao
{
    /**
     * Get the {@link SourceEventDto} with the id.<br/>
     * Optionally lock the {@link SourceEventDto}.
     * @param id
     * @param lock
     * @return
     */
    SourceEventDto getSourceEvent(Long id, boolean lock);

    /**
     * Get the {@link SourceEventDto} for the {@link ConnectorDto} with the connectorId
     * and the sourceId.
     * @param connectorId
     * @param sourceId
     * @return
     */
    SourceEventDto getSourceEvent(Long connectorId, String sourceId);

    /**
     * Lock the {@link SourceEventDto} refreshing its state.
     * @param sourceEvent
     */
    void lock(SourceEventDto sourceEvent);
    
    /**
     * Get the number of {@link SourceEventDto}s that are not mapped to any {@link EventDto}.
     * @return
     */
    int getNumberOfUnmappedSourceEvents();
    
    /**
     * Get the {@link SourceEventDto}s that are not mapped to any {@link EventDto}.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceEventDto> getUnmappedSourceEvents(Integer offset, Integer maxResults);
    
    /**
     * Get the {@link SourceEventDto}s that are not mapped to any {@link EventDto} 
     * optionally filtering by connector and meta tags.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param connectorId If set only return {@link SourceEventDto}s from the connector.
     * @param metaTagIds If set only return {@link SourceEventDto}s that have all 
     *              the {@link MetaTagDto}s listed.
     * @param searchQuery If set only return {@link SourceEventDto}s with names that
     *              match the query.
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceEventDto> getUnmappedSourceEvents(Long connectorId, List<Long> metaTagIds,
            String searchQuery, Integer offset, Integer maxResults);
    
    /**
     * Get the {@link SourceEventDto} for the {@link EventDto} and the {@link ConnectorDto}.
     * @param event
     * @param connector
     * @return
     */
    SourceEventDto getEventMapping(EventDto event, ConnectorDto connector);

    /**
     * Get the {@link SourceEventDto} for the event with the given id from the connector
     * with the given id.
     * @param eventId
     * @param connectorId
     * @return
     */
    SourceEventDto getEventMapping(Long eventId, Long connectorId);
    
    /**
     * Get the number of {@link SourceEventDto}s for the {@link ConnectorDto}. 
     * @param connector
     * @return
     */
    int getNumberOfEventMappings(ConnectorDto connector);
    
    /**
     * Get the {@link SourceEventDto}s for the {@link ConnectorDto}.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param connector
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceEventDto> getEventMappings(ConnectorDto connector, Integer offset, Integer maxResults);

    /**
     * Get the number of {@link SourceEventDto}s for the given {@link EventDto}.
     * @param event
     * @return
     */
    int getNumberOfEventMappings(EventDto event);
    
    /**
     * Get the {@link SourceEventDto}s for the {@link EventDto}.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param event
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceEventDto> getEventMappings(EventDto event, Integer offset, Integer maxResults);

    /**
     * Get the number of {@link SourceEventDto}s for the {@link ConnectorDto}
     * that were last fetched before the given {@link Date}. 
     * @param connectorId
     * @param fetchTime
     * @return
     */
    int getNumberOfSourceEventsLastFetchedBefore(Long connectorId, Date fetchTime);
    
    /**
     * Get the {@link SourceEventDto}s for the {@link ConnectorDto} that were 
     * last fetched before the given {@link Date}.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param connectorId
     * @param fetchTime
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceEventDto> getSourceEventsLastFetchedBefore(Long connectorId, 
            Date fetchTime, Integer offset, Integer maxResults);

    /**
     * Get the number of {@link SourceEventDto}s for the {@link ConnectorDto}
     * with the connectorId and fetchAttempts or more failed fetch attempts.
     * @param connectorId
     * @param attempts
     * @return
     */
    int getNumberOfSourceEventsWithFailedFetchAttempts(Long connectorId, 
            int attempts);
    
    /**
     * Get the {@link SourceEventDto}s for the {@link ConnectorDto} with the connectorId
     * and fetchAttempts or more failed fetch attempts.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param connectorId
     * @param attempts
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceEventDto> getSourceEventsWithFailedFetchAttempts(Long connectorId, 
            int attempts, Integer offset, Integer maxResults); 
    
    
    
    
    /**
     * Get the {@link SourceEventDto} for the {@link ConnectorDto} with the modelId.
     * @param connector
     * @param connectorsId
     * @return
     */
    SourceEventDto getEventMappingFromId(ConnectorDto connector, Long modelId);

    /**
     * Get the {@link SourceEventDto}s for the {@link ConnectorDto} with the given sourceIds.
     * @param connector
     * @param sourceIds
     * @return
     */
    List<SourceEventDto> getEventMappings(ConnectorDto connector, List<String> sourceIds);

    
    /**
     * Save the {@link SourceEventDto} returning the updated version.
     * @param sourceEvent
     * @return
     */
    SourceEventDto saveSourceEvent(SourceEventDto sourceEvent);
    
    /**
     * Delete the {@link SourceEventDto}.
     * @param sourceEvent
     */
    void deleteSourceEvent(SourceEventDto sourceEvent);
    

	/**
	 * Get list of source events for the given connectorId and sourceId 
	 * @param connectorId
	 * @param sourceId
	 * @return
	 */
	List<SourceEventDto> getSourceEvents(Long connectorId, String sourceId);
	
	/**
	 * Get list of source events for a given connectorId
	 * @param connectorId
	 * @return
	 * */ 
	List<SourceEventDto> getNonClosedSourceEvents(Long connectorId);
	
}
