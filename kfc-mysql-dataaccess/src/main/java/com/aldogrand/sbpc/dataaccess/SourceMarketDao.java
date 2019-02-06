package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.model.MarketStatus;

/**
 * <p>
 * <b>Title</b> SourceMarketDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link SourceMarketDto}s.
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
public interface SourceMarketDao
{
    /**
     * Get the {@link SourceMarketDto} with the id.<br/>
     * Optionally lock the {@link SourceMarketDto}.
     * @param id
     * @param lock
     * @return
     */
    SourceMarketDto getSourceMarket(Long id, boolean lock);
    
    
    /**
     * Get the {@link SourceMarketDto}s for the {@link ConnectorDto} with the connectorId
     * and market sourceId.
     * @param connectorId
     * @param sourceId
     * @return
     */
	List<SourceMarketDto> getSourceMarkets(Long connectorId, String sourceId);

	
    /**
     * Get the {@link SourceMarketDto} for the {@link ConnectorDto} with the connectorId
     * and market sourceId.
     * @param connectorId
     * @param sourceId
     * @return
     */
    SourceMarketDto getSourceMarket(Long connectorId, String sourceId);

    /**
     * Lock the {@link SourceMarketDto} refreshing its state.
     * @param sourceMarket
     */
    void lock(SourceMarketDto sourceMarket);
    
    /**
     * Get the number of {@link SourceMarketDto}s that are not mapped to any {@link MarketDto}.
     * @return
     */
    int getNumberOfUnmappedSourceMarkets();
    
    /**
     * Get the number of {@link SourceMarketDto}s that are not mapped to any {@link MarketDto}
     * that are for the {@link EventDto} and the {@link ConnectorDto} with the given ids.
     * @return
     */
    int getNumberOfUnmappedSourceMarkets(Long eventId, Long connectorId);
    
    /**
     * Get the {@link SourceMarketDto}s that are not mapped to any {@link MarketDto}.<br/>
     * Optionally limit the number of results by providing an offset and maxResults. 
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceMarketDto> getUnmappedSourceMarkets(Integer offset, Integer maxResults);
    
    /**
     * Get the {@link SourceMarketDto}s that are not mapped to any {@link MarketDto}
     * that are for the {@link EventDto} with the given id.
     * @param eventId The event id.
     * @param connectorId If set only include {@link SourceMarketDto}s from the {@link ConnectorDto}.
     * @return
     */
    List<SourceMarketDto> getUnmappedSourceMarkets(Long eventId, Long connectorId);
    
    /**
     * Get the {@link SourceMarketDto} for market and connector
     * @param market
     * @param connector
     * @return
     */
    SourceMarketDto getMarketMapping(MarketDto market, ConnectorDto connector);

    /**
     * Get the {@link SourceMarketDto} for the market with the given id from the connector
     * with the given id.
     * @param marketId
     * @param connectorId
     * @return
     */
    SourceMarketDto getMarketMapping(Long marketId, Long connectorId);
    
    /**
     * Get the number of {@link SourceMarketDto}s for the given {@link MarketDto}.
     * @param market
     * @return
     */
    int getNumberOfMarketMappings(MarketDto market);
    
    /**
     * Get the {@link SourceMarketDto}s for the {@link MarketDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param market
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceMarketDto> getMarketMappings(MarketDto market, Integer offset, 
            Integer maxResults);
    
    /**
     * Get the number of {@link SourceMarketDto}s from the {@link ConnectorDto}.
     * @param connector
     * @return
     */
    int getNumberOfMarketMappings(ConnectorDto connector);
    
    /**
     * Get the {@link SourceMarketDto}s from the {@link ConnectorDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connector
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceMarketDto> getMarketMappings(ConnectorDto connector, Integer offset, 
            Integer maxResults);
    
    /**
     * Get the number of {@link SourceMarketDto}s that are for the {@link ConnectorDto} and
     * for {@link MarketDto}s that are in the {@link EventDto}.
     * @param connector
     * @param event
     * @return
     */
    int getNumberOfMarketMappings(ConnectorDto connector, EventDto event);
    
    /**
     * Get the {@link SourceMarketDto}s that are for the {@link ConnectorDto} and
     * for {@link MarketDto}s that are in the {@link EventDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connector
     * @param event
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceMarketDto> getMarketMappings(ConnectorDto connector, EventDto event, 
            Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link SourceMarketDto}s for the {@link EventDto} and
     * {@link ConnectorDto} that were last fetched before the given {@link Date}. 
     * @param connectorId
     * @param eventId
     * @param fetchTime
     * @return
     */
    int getNumberOfSourceMarketsLastFetchedBefore(Long connectorId, Long eventId, 
            Date fetchTime);
    
    /**
     * Get the {@link SourceMarketDto}s for the {@link EventDto} and {@link ConnectorDto}
     * that were last fetched before the given {@link Date}.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param connectorId
     * @param eventId
     * @param fetchTime
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceMarketDto> getSourceMarketsLastFetchedBefore(Long connectorId, 
            Long eventId, Date fetchTime, Integer offset, Integer maxResults);    
    
    /**
     * Get the number of {@link SourceMarketDto}s for the {@link EventDto} and
     * {@link ConnectorDto} with fetchAttempts or more failed fetch attempts.
     * @param connectorId
     * @param eventId
     * @param attempts
     * @return
     */
    int getNumberOfSourceMarketsWithFailedFetchAttempts(Long connectorId, 
            Long eventId, int attempts);
    
    /**
     * Get the {@link SourceMarketDto}s for the {@link EventDto} and {@link ConnectorDto}
     * with fetchAttempts or more failed fetch attempts.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param connectorId
     * @param eventId
     * @param attempts
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceMarketDto> getSourceMarketsWithFailedFetchAttempts(Long connectorId, 
            Long eventId, int attempts, Integer offset, Integer maxResults); 
    
    /**
     * Get the {@link SourceMarketDto} for the {@link ConnectorDto} with the modelId.
     * @param connector
     * @param connectorsId
     * @return
     */
    SourceMarketDto getMarketMappingFromId(ConnectorDto connector, Long modelId);
    
    /**
     * Save the {@link SourceMarketDto} returning the updated version.
     * @param sourceMarket
     * @return
     */
    SourceMarketDto saveSourceMarket(SourceMarketDto sourceMarket);
    
    /**
     * Delete the {@link SourceMarketDto}.
     * @param sourceMarket
     */
    void deleteSourceMarket(SourceMarketDto sourceMarket);
    
    /**
     * Returns true only if all source markets for the {@link SourceEvent} are 
     * in the given status</p>
     * 
     * Returns false if no markets exist or have null statuses
     * 
     * @param sourceEventId
     * @param status
     * @return
     */
    boolean allSourceEventMarketsInStatus(long sourceEventId, MarketStatus status);
    
    /**
     * Get the non closed {@link SourceMarketDto}s for the {@link ConnectorDto}.
     * @param connectorId
     * @return
     */
    List<SourceMarketDto> getNonClosedSourceMarkets(Long connectorId, String sourceEventId );  
    
    /**
     * Get the {@link SourceMarketDto} with the sourceId.<br/>
     * Optionally lock the {@link SourceMarketDto}.
     * @param sourceId
     * @param lock
     * @return
     */
    SourceMarketDto getSourceMarket(String sourceId);
    
}
