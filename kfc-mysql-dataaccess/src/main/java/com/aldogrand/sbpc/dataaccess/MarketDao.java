package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.InterestedClientDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.Period;

/**
 * <p>
 * <b>Title</b> MarketDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link MarketDto}s.
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
public interface MarketDao
{
    /**
     * Return the {@link MarketDto} with the given id
     * @param id
     * @return market
     */
    MarketDto getMarket(long id);

    /**
     * Lock the {@link MarketDto} and refresh its state.
     * @param market
     */
    void lock(MarketDto market);
    
    /**
     * Get the number of {@link MarketDto}s.
     * @return
     */
    int getNumberOfMarkets();
	
    /**
     * Get the {@link MarketDto}s.<br/>
     * Limit the results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<MarketDto> getMarkets(Integer offset, Integer maxResults);

    /**
     * Get the {@link MarketDto}s that are on the {@link ConnectorDto} with the ids.
     * @param connectorId
     * @param ids
     * @return
     */
    List<MarketDto> getMarkets(long connectorId, List<Long> ids);
    
	/**
	 * Get the number of {@link MarketDto}s with optional filtering.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connectorIds Filter to include only {@link MarketDto}s from these {@link ConnectorDto}s.
     * @param modifiedSince Filter to include only {@link MarketDto}s modified after this date.
     * @param types Filter to only include {@link MarketDto}s of these types.
     * @param periods Filter to only include {@link MarketDto}s for these periods.
     * @param eventIds Filter to only include {@link MarketDto}s for these {@link EventDto}s.
	 * @return
	 */
	int getNumberOfMarkets(List<Long> connectorIds, Date modifiedSince,
            List<MarketType> types, List<Period> periods, List<Long> eventIds);
	
	/**
	 * Get the {@link MarketDto}s with optional filtering.<br/>
	 * Limit the number of results by providing an offset and maxResults.
	 * @param connectorIds Filter to include only {@link MarketDto}s from these {@link ConnectorDto}s.
	 * @param modifiedSince Filter to include only {@link MarketDto}s modified after this date.
	 * @param types Filter to only include {@link MarketDto}s of these types.
	 * @param periods Filter to only include {@link MarketDto}s for these periods.
	 * @param eventIds Filter to only include {@link MarketDto}s for these {@link EventDto}s.
	 * @param offset
	 * @param maxResults
	 * @return
	 */
	List<MarketDto> getMarkets(List<Long> connectorIds, Date modifiedSince,
            List<MarketType> types, List<Period> periods, List<Long> eventIds,
            Integer offset, Integer maxResults);
            
    /**
     * Get the {@link MarketDto}s that are mapped from the connector with the id and
     * that have the source ids on that connector.
     * @param connectorId
     * @param sourceIds
     * @return
     */
    List<MarketDto> getMappedMarkets(long connectorId, List<String> sourceIds); 

    /**
     * Get the number of {@link MarketDto}s that have {@link InterestedClientDto}s
     * and are for {@link EventDto}s that have a start time between start and end.
     * @param start
     * @param end
     * @return
     */
    int getNumberOfMarketsWithInterestedClientsForEventsStartingBetween(
            Date start, Date end);
    
    /**
     * Get the {@link MarketDto}s that have {@link InterestedClientDto}s 
     * and are for {@link EventDto}s that have a start time between start and end. 
     * Include the {@link SourceMarketDto} data with {@link MarketDto}s.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param start
     * @param end
     * @param offset
     * @param maxResults
     * @return
     */
    List<MarketDto> getMarketsWithInterestedClientsForEventsStartingBetweenWithMappings(
            Date start, Date end, Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link MarketDto}s that have {@link InterestedClientDto}s
     * with the clientToken.
     * @param clientToken
     * @return
     */
    int getNumberOfMarketsWithInterestedClient(String clientToken);
    
    /**
     * Get the {@link MarketDto}s that have {@link InterestedClientDto}s with the 
     * clientToken.<br/>
     * Optionally limit the number of results by providing an offset and maxResults. 
     * @param clientToken
     * @param offset
     * @param maxResults
     * @return
     */
    List<MarketDto> getMarketsWithInterestedClient(String clientToken, Integer offset,
            Integer maxResults);
    
    

	/**
     * Get the {@link MarketDto}s that have {@link InterestedClientDto}s
	 * @return
	 */
	List<MarketDto> getInterestedMarkets();
    
    /**
     * Save the {@link MarketDto} returning the updated version.
     * @param market
     * @return
     */
    MarketDto saveMarket(MarketDto market);
    
    /**
     * Delete the {@link MarketDto}.
     * @param market
     */
    void deleteMarket(MarketDto market);

}
