package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.RunnerDto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>Title</b> RunnerDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s.
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
public interface RunnerDao
{
    /**
     * Get the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto} with the provided id
     * @param id
     * @return the runner
     */
    RunnerDto getRunner(long id);

    /**
     * Get the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto} with the provided id. Include the {@link com.aldogrand.sbpc.dataaccess.model.PriceDto}
     * data with the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}.
     * @param id
     * @return the runner
     */
    RunnerDto getRunnerWithPrices(long id);

    /**
     * Lock the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto} and refresh its state.
     * @param runner
     */
    void lock(RunnerDto runner);
    
    /**
     * Get the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s with the ids from the {@link com.aldogrand.sbpc.dataaccess.model.ConnectorDto}.
     * @param connectorId
     * @param ids
     * @return
     */
    List<RunnerDto> getRunners(long connectorId, List<Long> ids);
    
    /**
     * Get the number of {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s in the {@link com.aldogrand.sbpc.dataaccess.model.MarketDto}.
     * @param marketId
     * @return
     */
    int getNumberOfRunners(Long marketId);
    
    /**
     * Get a list of {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s in {@link com.aldogrand.sbpc.dataaccess.model.MarketDto}.<br/>
     * Optionally limit the results by providing an offset and maxResults.
     * @param marketId
     * @param offset
     * @param maxResults
     * @return
     */
    List<RunnerDto> getRunners(Long marketId, Integer offset, Integer maxResults);

    /**
     * Get the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s that are mapped from the connector with the id and
     * that have the source ids on that connector.
     * @param connectorId
     * @param sourceIds
     * @return
     */
    List<RunnerDto> getMappedRunners(long connectorId, List<String> sourceIds);
    
    /**
     * Get {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s optionally applying filters.<br/>
     * Limit the number of results by providing an offset and maxResults.
     * @param connectorIds Only include {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s from these {@link com.aldogrand.sbpc.dataaccess.model.ConnectorDto}s.
     * @param modifiedSince Only include {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s modified since this date.
     * @param eventIds Only include {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s for these {@link com.aldogrand.sbpc.dataaccess.model.EventDto}s.
     * @param marketIds Only include {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}s for these {@link com.aldogrand.sbpc.dataaccess.model.MarketDto}s. If set ignore eventIds.
     * @param offset
     * @param maxResults
     * @return
     */
    List<RunnerDto> getRunners(List<Long> connectorIds, Date modifiedSince,
                               List<Long> eventIds, List<Long> marketIds, Integer offset, Integer maxResults);
    
    /**
     * Save the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto} returning the updated version.
     * @param runner
     * @return
     */
    RunnerDto saveRunner(RunnerDto runner);

    /**
     * Delete the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}.
     * @param runner
     */
    void deleteRunner(RunnerDto runner);

    /**
     * Get the {@link com.aldogrand.sbpc.dataaccess.model.RunnerDto}.
     * @param id
     * @param marketId
     * @return
     */
    RunnerDto getRunner(Long id, Long marketId);
}
