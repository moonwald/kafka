package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.PriceDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.model.PriceSide;

/**
 * <p>
 * <b>Title</b> PriceDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link PriceDto}s.
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
public interface PriceDao
{
    /**
     * Get the {@link PriceDto} with the id.
     * @param id
     * @return
     */
    PriceDto getPrice(long id);
    
    /**
     * Lock the {@link PriceDto} and refresh its state.
     * @param price
     */
    void lock(PriceDto price);
    
    /**
     * Get the {@link PriceDto} from the {@link BettingPlatformDto} for the 
     * {@link RunnerDto} on the {@link PriceSide} with the given sequence.
     * @param bettingPlatformId
     * @param runnerId
     * @param side
     * @param sequence The order of the {@link PriceDto} where 1 is best, 2 is seconds best, etc.
     * @return
     */
    PriceDto getPrice(Long bettingPlatformId, Long runnerId, PriceSide side, Integer sequence);
    
    /**
     * Get the {@link PriceDto}s with the ids.
     * @param ids
     * @return
     */
    List<PriceDto> getPrices(List<Long> ids);
    
    /**
     * Get the number of {@link PriceDto}s for the given {@link BettingPlatformDto}.
     * @param bettingPlatformId
     * @return
     */
    int getNumberOfPrices(Long bettingPlatformId);
    
    /**
     * Get the list of {@link PriceDto}s for the given {@link BettingPlatformDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param bettingPlatformId
     * @param offset
     * @param maxResults
     * @return
     */
    List<PriceDto> getPrices(Long bettingPlatformId, Integer offset, 
            Integer maxResults);

    /**
     * Get the number of {@link PriceDto}s for the {@link RunnerDto} with runnerId
     * from the {@link ConnectorDto} with connectorId.
     * @param connectorId
     * @param runnerId
     * @return
     */
    int getNumberOfRunnerPrices(Long connectorId, Long runnerId);

    /**
     * Get the {@link PriceDto}s for the {@link RunnerDto} with runnerId
     * from the {@link ConnectorDto} with connectorId.
     * @param connectorId
     * @param runnerId
     * @param offset
     * @param maxResults
     * @return
     */
    List<PriceDto> getRunnerPrices(Long connectorId, Long runnerId, Integer offset, 
            Integer maxResults);
    
    /**
     * Get the {@link PriceDto}s applying the optional parameters.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connectorIds Only include {@link PriceDto}s from these {@link ConnectorDto}s.
     * @param bettingPlatformIds Only include {@link PriceDto}s from these {@link BettingPlatformDto}s. 
     *      Ignore the connectorIds.
     * @param modifiedSince Only include {@link PriceDto}s modified since this date.
     * @param eventIds Only include {@link PriceDto}s for these {@link EventDto}s.
     * @param marketIds Only include {@link PriceDto}s for these {@link MarketDto}s. 
     *      Ignore the eventIds.
     * @param runnerIds Only include {@link PriceDto}s for these {@link RunnerDto}s. 
     *      Ignore the eventIds and marketIds.
     * @param depth Only include {@link PriceDto}s with a sequence greater than or equal to this.
     * @param sides Only include {@link PriceDto}s for these {@link PriceSide}s.
     * @param offset
     * @param maxResults
     * @return
     */
    List<PriceDto> getPrices(List<Long> connectorIds,
            List<Long> bettingPlatformIds, Date modifiedSince,
            List<Long> eventIds, List<Long> marketIds, List<Long> runnerIds,
            Integer depth, List<PriceSide> sides, Integer offset, Integer maxResults);
            
    /**
     * Get the number of {@link PriceDto}s for the {@link MarketDto} and
     * {@link ConnectorDto} that were last fetched before the given {@link Date}. 
     * @param connectorId
     * @param marketId
     * @param fetchTime
     * @return
     */
    int getNumberOfPricesLastFetchedBefore(Long connectorId, Long marketId, 
            Date fetchTime);
    
    /**
     * Get the {@link PriceDto}s for the {@link MarketDto} and {@link ConnectorDto}
     * that were last fetched before the given {@link Date}.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param connectorId
     * @param marketId
     * @param fetchTime
     * @param offset
     * @param maxResults
     * @return
     */
    List<PriceDto> getPricesLastFetchedBefore(Long connectorId, 
            Long marketId, Date fetchTime, Integer offset, Integer maxResults);  

    /**
     * Get the number of {@link PriceDto}s for the {@link MarketDto} and
     * {@link ConnectorDto} with fetchAttempts or more failed fetch attempts.
     * @param connectorId
     * @param marketId
     * @param attempts
     * @return
     */
    int getNumberOfPricesWithFailedFetchAttempts(Long connectorId, 
            Long marketId, int attempts);
    
    /**
     * Get the {@link PriceDto}s for the {@link MarketDto} and {@link ConnectorDto}
     * with fetchAttempts or more failed fetch attempts.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param connectorId
     * @param marketId
     * @param attempts
     * @param offset
     * @param maxResults
     * @return
     */
    List<PriceDto> getPricesWithFailedFetchAttempts(Long connectorId, 
            Long marketId, int attempts, Integer offset, Integer maxResults); 
    
    /**
     * Gets a list of prices on a runner with the betting platform type of G Feed
     * @param runnerdto
     * @param bettingPlatformId
     * @return
     */
    List<PriceDto> getGFeedPrices(RunnerDto runnerDto, Long bettingPlatformId);
    
    /**
     * Gets a list of prices on a market with the betting platform type of G Feed
     * @param marketDto
     * @param bettingPlatformId
     * @return
     */
    List<PriceDto> getGFeedPrices(MarketDto marketDto, Long bettingPlatformId);
    
    /**
     * Get the latest price for a specific runner
     * @param runnerDto
     * @param bettingPlatformId
     * @return
     */
    PriceDto getLatestPriceForRunner(RunnerDto runnerDto, Long bettingPlatformId);
    
    /**
     * Save the {@link PriceDto} returning the updated version.
     * @param price
     * @return
     */
    PriceDto savePrice(PriceDto price);
    
    /**
     * Delete the {@link PriceDto}.
     * @param price
     */
    void deletePrice(PriceDto price);   
    
    /**
     * Delete the {@link PriceDto}s.
     * @param prices
     */
    void deletePrices(List<PriceDto> prices);  
    
    /**
     * Delete the {@link PriceDto}s.
     * @param lastFetchedBefore
     */
    void deletePrices(Date lastFetchedBefore, String ignoreConnectors);
}
