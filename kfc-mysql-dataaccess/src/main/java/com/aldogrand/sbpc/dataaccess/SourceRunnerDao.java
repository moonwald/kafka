package com.aldogrand.sbpc.dataaccess;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto;

/**
 * <p>
 * <b>Title</b> SourceRunnerDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link SourceRunnerDto}s.
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
public interface SourceRunnerDao
{
    /**
     * Get the {@link SourceRunnerDto} with the id.<br/>
     * Optionally lock the {@link SourceRunnerDto}.
     * @param id
     * @param lock
     * @return
     */
    SourceRunnerDto getSourceRunner(Long id, boolean lock);
    
    /**
     * Get the {@link SourceRunnerDto} with the connectorId and sourceId.
     * @param connectorId
     * @param sourceId
     * @return
     */
    SourceRunnerDto getSourceRunner(Long connectorId, String sourceId);

    /**
     * Get the {@link SourceRunnerDto}s with the connectorId and sourceId.
     * @param connectorId
     * @param sourceId
     * @return
     */
    List<SourceRunnerDto> getSourceRunners(Long connectorId, String sourceId);

    
    /**
     * Lock the {@link SourceRunnerDto} and refresh its state.
     * @param sourceRunner
     */
    void lock(SourceRunnerDto sourceRunner);
    
    /**
     * Get the number of {@link SourceRunnerDto}s that are not mapped to any {@link RunnerDto}.
     * @return
     */
    int getNumberOfUnmappedSourceRunners();
    
    /**
     * Get the number of {@link SourceRunnerDto}s that are not mapped to any {@link RunnerDto}
     * that are for the {@link MarketDto} and the {@link ConnectorDto} with the given ids.
     * @return
     */
    int getNumberOfUnmappedSourceRunners(Long marketId, Long connectorId);
    
    /**
     * Get the {@link SourceRunnerDto}s that are not mapped to any {@link RunnerDto}.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceRunnerDto> getUnmappedSourceRunners(Integer offset, Integer maxResults);
    
    /**
     * Get the {@link SourceRunnerDto}s that are not mapped to any {@link RunnerDto}
     * for the {@link MarketDto}.
     * @param marketId The id of the {@link MarketDto}.
     * @param connectorId If set only include {@link SourceMarketDto}s from the {@link ConnectorDto}.
     * @return
     */
    List<SourceRunnerDto> getUnmappedSourceRunners(Long marketId, Long connectorId);
    
    /**
     * Get the {@link SourceRunnerDto} for the {@link ConnectorDto} with the sourceId.
     * @param connector
     * @param sourceId
     * @return
     */
    SourceRunnerDto getRunnerMapping(ConnectorDto connector, String sourceId);
    
    /**
     * Get the {@link SourceRunnerDto} for the runner and connector.
     * @param runner
     * @param connector
     * @return
     */
    SourceRunnerDto getRunnerMapping(RunnerDto runner, ConnectorDto connector);

    /**
     * Get the {@link SourceRunnerDto} for the market with the given id from the connector
     * with the given id.
     * @param runnerId
     * @param connectorId
     * @return
     */
    SourceRunnerDto getRunnerMapping(Long runnerId, Long connectorId);
    
    /**
     * Get the number of {@link SourceRunnerDto}s for the {@link RunnerDto}.
     * @param runner
     * @return
     */
    int getNumberOfRunnerMappings(RunnerDto runner);
    
    /**
     * Get the {@link SourceRunnerDto}s for the {@link RunnerDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param runner
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceRunnerDto> getRunnerMappings(RunnerDto runner, Integer offset, 
            Integer maxResults);

    /**
     * Get the number of {@link SourceRunnerDto}s from the {@link ConnectorDto}.
     * @param connector
     * @return
     */
    int getNumberOfRunnerMappings(ConnectorDto connector);
    
    /**
     * Get the {@link SourceRunnerDto}s from the {@link ConnectorDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connector
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceRunnerDto> getRunnerMappings(ConnectorDto connector, Integer offset, 
            Integer maxResults);
    
    /**
     * Get the number of {@link SourceRunnerDto}s for {@link RunnerDto}s in the {@link MarketDto} 
     * from the {@link ConnectorDto}.
     * @param connector
     * @param market
     * @return
     */
    int getNumberOfRunnerMappings(ConnectorDto connector, MarketDto market);
    
    /**
     * Get the {@link SourceRunnerDto}s for {@link RunnerDto}s in the {@link MarketDto} 
     * from the {@link ConnectorDto}.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connector
     * @param market
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceRunnerDto> getRunnerMappings(ConnectorDto connector, MarketDto market, 
            Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link SourceRunnerDto}s for the {@link MarketDto} with 
     * the marketId and the {@link ConnectorDto} with the connectorId 
     * that were last fetched before the given {@link Date}. 
     * @param connectorId
     * @param marketId
     * @param fetchTime
     * @return
     */
    int getNumberOfSourceRunnersLastFetchedBefore(Long connectorId, Long marketId, 
            Date fetchTime);
    
    /**
     * Get the {@link SourceRunnerDto}s for the {@link MarketDto} with the marketId
     * and the {@link ConnectorDto} with the connectorId that were last fetched 
     * before the given {@link Date}.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param connectorId
     * @param marketId
     * @param fetchTime
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceRunnerDto> getSourceRunnersLastFetchedBefore(Long connectorId, 
            Long marketId, Date fetchTime, Integer offset, Integer maxResults);      

    /**
     * Get the number of {@link SourceRunnerDto}s for the {@link MarketDto} and
     * {@link ConnectorDto} with fetchAttempts or more failed fetch attempts.
     * @param connectorId
     * @param marketId
     * @param attempts
     * @return
     */
    int getNumberOfSourceRunnersWithFailedFetchAttempts(Long connectorId, 
            Long marketId, int attempts);
    
    /**
     * Get the {@link SourceRunnerDto}s for the {@link MarketDto} and {@link ConnectorDto}
     * with fetchAttempts or more failed fetch attempts.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param connectorId
     * @param marketId
     * @param attempts
     * @param offset
     * @param maxResults
     * @return
     */
    List<SourceRunnerDto> getSourceRunnersWithFailedFetchAttempts(Long connectorId, 
            Long marketId, int attempts, Integer offset, Integer maxResults); 

    /**
     * Get the {@link SourceRunnerDto} for the {@link ConnectorDto} with the modelId.
     * @param connector
     * @param connectorsId
     * @return
     */
    SourceRunnerDto getRunnerMappingFromId(ConnectorDto connector, Long modelId);
        
    /**
     * Get the {@link SourceRunnerDto}s for {@link RunnerDto}s in the {@link MarketDto} 
     * from the {@link ConnectorDto}.
     * @param market
     * @param connector
     * @return
     */
    List<SourceRunnerDto> getRunnerMappings(MarketDto market, ConnectorDto connector);
    
    /**
     * Save the {@link SourceRunnerDto} returning the updated version.
     * @param sourceRunner
     * @return
     */
    SourceRunnerDto saveSourceRunner(SourceRunnerDto sourceRunner);
    
    /**
     * Delete the {@link SourceRunnerDto}.
     * @param sourceRunner
     */
    void deleteSourceRunner(SourceRunnerDto sourceRunner);
}
