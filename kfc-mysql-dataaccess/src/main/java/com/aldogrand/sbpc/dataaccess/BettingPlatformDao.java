package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;

/**
 * <p>
 * <b>Title</b> BettingPlatformDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link BettingPlatformDto}s.
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
public interface BettingPlatformDao
{
    /**
     * Get the {@link BettingPlatformDto} with the given id
     * @param id
     * @return
     */
    BettingPlatformDto getBettingPlatform(long id);

    /**
     * Get the {@link BettingPlatformDto} provided by the {@link ConnectorDto} that
     * has the name given.
     * @param connector The {@link ConnectorDto} that provides the {@link BettingPlatformDto}.
     * @param bettingPlatformName The name of the {@link BettingPlatformDto}. Unique within the {@link ConnectorDto}.
     * @return The named {@link BettingPlatformDto} or null if there is none.
     */
    BettingPlatformDto getBettingPlatform(ConnectorDto connector, String bettingPlatformName);

    /**
     * Lock the {@link BettingPlatformDto} and refresh its state.
     * @param bettingPlatform
     */
    void lock(BettingPlatformDto bettingPlatform);
    
    /**
     * Get the number of {@link BettingPlatformDto}s.
     * @return
     */
    int getNumberOfBettingPlatforms();
    
    /**
     * Get the number of {@link BettingPlatformDto}s with a last_fetched_time before the provided time
     * @params lastFetchedBefore
     * @return
     */
    int getNumberOfBettingPlatformsLastFetchedBefore(Date lastFecthedBefore);

    /**
     * Get the {@link BettingPlatformDto}s<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<BettingPlatformDto> getBettingPlatforms(Integer offset,
            Integer maxResults);
    
    /**
     * Get the {@link BettingPlatformDto}s with a last fetch time before the provided time<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param lastFetchedBefore
     * @param offset
     * @param maxResults
     * @return
     */
    List<BettingPlatformDto> getBettingPlatformsLastFetchedBefore(Date lastFetchedBefore, Integer offset,
            Integer maxResults);

    /**
     * Get the number of {@link BettingPlatformDto}s for the {@link ConnectorDto}s
     * with the ids given.
     * @param connectorIds
     * @return
     */
    int getNumberOfBettingPlatforms(List<Long> connectorIds);
    
    /**
     * Get the {@link BettingPlatformDto}s for the {@link ConnectorDto}s with the
     * ids given.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param connectorIds
     * @param offset
     * @param maxResults
     * @return
     */
    List<BettingPlatformDto> getBettingPlatforms(List<Long> connectorIds, Integer offset, Integer maxResults);
    
    /**
     * Save the {@link BettingPlatformDto} and return the updated version.
     * @param bettingPlatform
     * @return
     */
    BettingPlatformDto saveBettingPlatform(BettingPlatformDto bettingPlatform);

    /**
     * Delete the {@link BettingPlatformDto}.
     * @param bettingPlatform
     */
    void deleteBettingPlatform(BettingPlatformDto bettingPlatform);
}
