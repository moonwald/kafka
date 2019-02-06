package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.AccountDto;
import com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.PositionDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;

/**
 * <p>
 * <b>Title</b> PositionDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DTO) that loads and saves {@link PositionDto}s.
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
public interface PositionDao
{
    /**
     * Get the {@link PositionDto} with the given id.
     * @param id
     * @return
     */
    PositionDto getPosition(long id);
    
    /**
     * Get the {@link PositionDto} for the {@link RunnerDto} on the {@link BettingPlatformDto}
     * using the {@link AccountDto}.
     * @param runnerId
     * @param bettingPlatformId
     * @param accountId
     * @return
     */
    PositionDto getPosition(Long runnerId, Long bettingPlatformId, Long accountId);

    /**
     * Lock the {@link PositionDto} and refresh its state.
     * @param position
     */
    void lock(PositionDto position);
    
    /**
     * Get the number of {@link PositionDto}s for the {@link BettingPlatformDto}.
     * @param bettingPlatformId
     * @return
     */
    int getNumberOfPositions(Long bettingPlatformId);
    
    /**
     * Get the list of {@link PositionDto}s for the given {@link BettingPlatformDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param bettingPlatformId
     * @param offset
     * @param maxResults
     * @return
     */
    List<PositionDto> getPositions(Long bettingPlatformId, Integer offset, 
            Integer maxResults);
    
    /**
     * Get the number of {@link PositionDto}s for the {@link RunnerDto} with runnerId.
     * @param connectorId
     * @param runnerId
     * @return
     */
    int getNumberOfRunnerPositions(Long runnerId);

    /**
     * Get the {@link PositionDto}s for the {@link RunnerDto} with runnerId.
     * @param connectorId
     * @param runnerId
     * @param offset
     * @param maxResults
     * @return
     */
    List<PositionDto> getRunnerPositions(Long runnerId, Integer offset, 
            Integer maxResults);

    /**
     * Get the number of {@link PositionDto}s for the {@link RunnerDto} with runnerId
     * from the {@link ConnectorDto} with connectorId.
     * @param connectorId
     * @param runnerId
     * @return
     */
    int getNumberOfRunnerPositions(Long connectorId, Long runnerId);

    /**
     * Get the {@link PositionDto}s for the {@link RunnerDto} with runnerId
     * from the {@link ConnectorDto} with connectorId.
     * @param connectorId
     * @param runnerId
     * @param offset
     * @param maxResults
     * @return
     */
    List<PositionDto> getRunnerPositions(Long connectorId, Long runnerId, Integer offset, 
            Integer maxResults);
    
    /**
     * Get {@link PositionDto}s optionally filtering the results.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connectorIds Include only {@link PositionDto}s from the connectors.
     * @param bettingPlatformIds Include only {@link PositionDto}s from the betting platforms. 
     *      Ignore connectorIds.
     * @param accountIds Include only {@link PositionDto}s for the accounts.
     * @param modifiedSince Include only {@link PositionDto}s modified after this date.
     * @param eventIds Include only {@link PositionDto}s from the events.
     * @param marketIds Include only {@link PositionDto}s from the markets. Ignore eventIds.
     * @param runnerIds Include only {@link PositionDto}s from the runners. 
     *      Ignore eventIds and marketIds.
     * @param offset
     * @param maxResults
     * @return
     */
    List<PositionDto> getPositions(List<Long> connectorIds,
            List<Long> bettingPlatformIds, List<Long> accountIds,
            Date modifiedSince, List<Long> eventIds, List<Long> marketIds,
            List<Long> runnerIds, Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link PositionDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} that were last fetched before the date. 
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param fetchTime
     * @return
     */
    int getNumberOfPositionsLastFetchedBefore(Long connectorId, Long marketId, 
            Long accountId, Date fetchTime);
    
    /**
     * Get the {@link PositionDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} that were last fetched before the date.<br/>
     * Optionally limit the number of results by providing an offset and maxResults. 
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param fetchTime
     * @param offset
     * @param maxResults
     * @return
     */
    List<PositionDto> getPositionsLastFetchedBefore(Long connectorId, Long marketId, 
            Long accountId, Date fetchTime, Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link PositionDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} with fetchAttempts or more failed fetch attempts.
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param fetchAttempts
     * @return
     */
    int getNumberOfPositionsWithFailedFetchAttempts(Long connectorId, Long marketId, 
            Long accountId, int fetchAttempts);
    
    /**
     * Get the {@link PositionDto}s from the {@link ConnectorDto} for the
     * {@link MarketDto} and {@link AccountDto} with fetchAttempts or more failed fetch attempts.<br/>
     * Optionally limit the number of results by providing an offset and maxResults. 
     * @param connectorId
     * @param marketId
     * @param accountId
     * @param fetchAttempts
     * @param offset
     * @param maxResults
     * @return
     */
    List<PositionDto> getPositionsWithFailedFetchAttempts(Long connectorId, Long marketId, 
            Long accountId, int fetchAttempts, Integer offset, Integer maxResults);
        
    /**
     * Save the {@link PositionDto} returning the updated version.
     * @param position
     * @return
     */
    PositionDto savePosition(PositionDto position);
    
    /**
     * Delete the {@link PositionDto}.
     * @param position
     */
    void deletePosition(PositionDto position);
    
    /**
     * Delete the {@link PositionDto}s.
     * @param lastFetchedBefore
     */
    void deletePositions(Date lastFetchedBefore); 
}
