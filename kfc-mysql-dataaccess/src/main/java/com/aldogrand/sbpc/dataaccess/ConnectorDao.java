package com.aldogrand.sbpc.dataaccess;

import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>Title</b> ConnectorDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that loads and saves {@link ConnectorDto}s.
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
public interface ConnectorDao
{
    /**
     * Get the {@link ConnectorDto} with the given id.
     * @param id
     * @return
     */
    ConnectorDto getConnector(Long id);

    /**
     * Get the {@link ConnectorDto} with the given name.
     * @param name
     * @return
     */
    ConnectorDto getConnector(String name);

    /**
     * Create a write lock on the {@link ConnectorDto} and refresh its state.
     * @param connector
     */
    void lock(ConnectorDto connector);
    
    /**
     * Get the number of {@link ConnectorDto}s.
     * @return
     */
    int getNumberOfConnectors();
    
    /**
     * Get {@link ConnectorDto}s optionally limiting the results by providing an offset
     * and maxResults. 
     * @param offset
     * @param maxResults
     * @return
     */
    List<ConnectorDto> getConnectors(Integer offset, Integer maxResults);

    /**
     * Get {@link ConnectorDto}s with the  data included.<br/>
     * Optionally limit the results by providing an offset and maxResults. 
     * @param offset
     * @param maxResults
     * @return
     */
    List<ConnectorDto> getConnectorsWithAccounts(Integer offset, Integer maxResults);
    
    /**
     * Get the number of {@link ConnectorDto}s that were last fetched before fetchTime.
     * @param fetchTime
     * @return
     */
    int getNumberOfConnectorsLastFetchedBefore(Date fetchTime);
    
    /**
     * Get the {@link ConnectorDto}s that were last fetched before the given time.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param fetchTime
     * @param offset
     * @param maxResults
     * @return
     */
    List<ConnectorDto> getConnectorsLastFetchedBefore(Date fetchTime, Integer offset, 
            Integer maxResults);
    
    /**
     * Get the number of {@link ConnectorDto}s with fetchAttempts or more failed
     * fetch attempts.
     * @param fetchAttempts
     * @return
     */
    int getNumberOfConnectorsWithFailedFetchAttempts(int fetchAttempts);
    
    /**
     * Get the {@link ConnectorDto}s with fetchAttempts or more failed fetch attempts.<br/>
     * Optionally limit the number of results by providing an offset and maxResults.
     * @param fetchAttempts
     * @param offset
     * @param maxResults
     * @return
     */
    List<ConnectorDto> getConnectorsWithFailedFetchAttempts(int fetchAttempts, 
            Integer offset, Integer maxResults);
    
    /**
     * Save the {@link ConnectorDto} returning the updated version. 
     * @param connector
     * @return
     */
    ConnectorDto saveConnector(ConnectorDto connector);
    
    /**
     * Delete the {@link ConnectorDto}s.
     * @param connectors
     */
    void deleteConnectors(List<ConnectorDto> connectors);
}
