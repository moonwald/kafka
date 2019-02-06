package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.ConnectorDao;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> ConnectorDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link ConnectorDao}.
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
public class ConnectorDaoJpaImpl extends GenericJpaDao<ConnectorDto> implements
        ConnectorDao
{
    /**
     * Create a new {@link ConnectorDaoJpaImpl}.
     */
    public ConnectorDaoJpaImpl()
    {
        super(ConnectorDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#getConnector(java.lang.Long)
     */
    @Override
    public ConnectorDto getConnector(Long id)
    {
        assert id != null;
        
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#getConnector(java.lang.String)
     */
    @Override
    public ConnectorDto getConnector(String name)
    {
        assert name != null;
        
        return executeQuery("from Connector c where c.name = ?1", name);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public void lock(ConnectorDto connector)
    {
        writeLock(connector);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#getNumberOfConnectors()
     */
    @Override
    public int getNumberOfConnectors()
    {
        Number count = executeQuery("select count(c.id) from Connector c", Number.class);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#getConnectors(java.lang.Integer, 
     * java.lang.Integer)
     */
    @Override
    public List<ConnectorDto> getConnectors(Integer offset, Integer maxResults)
    {
        return executeQuery("from Connector c order by c.name", offset, maxResults);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#getConnectorsWithAccounts(
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<ConnectorDto> getConnectorsWithAccounts(Integer offset,
            Integer maxResults)
    {
        return executeQuery("from Connector c left join fetch c.accounts order by c.name", 
                offset, maxResults);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao
     * #getNumberOfConnectorsLastFetchedBefore(java.util.Date)
     */
    @Override
    public int getNumberOfConnectorsLastFetchedBefore(Date fetchTime)
    {
        assert fetchTime != null;
        
        Number count = executeQuery("select count(c.id) from Connector c where c.lastFetchTime < ?1", 
                Number.class, fetchTime);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao
     * #getConnectorsLastFetchedBefore(java.util.Date, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<ConnectorDto> getConnectorsLastFetchedBefore(Date fetchTime, Integer offset, 
            Integer maxResults)
    {
        assert fetchTime != null;

        return executeQuery("from Connector c where c.lastFetchTime < ?1 order by c.name", 
                offset, maxResults, fetchTime);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#getNumberOfConnectorsWithFailedFetchAttempts(int)
     */
    @Override
    public int getNumberOfConnectorsWithFailedFetchAttempts(int fetchAttempts)
    {
        Number count = executeQuery("select count(c.id) from Connector c where c.failedFetchAttempts >= ?1", 
                Number.class, fetchAttempts);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#getConnectorsWithFailedFetchAttempts(int, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<ConnectorDto> getConnectorsWithFailedFetchAttempts(
            int fetchAttempts, Integer offset, Integer maxResults)
    {
        return executeQuery("from Connector c where c.failedFetchAttempts >= ?1 order by c.name", 
                offset, maxResults, fetchAttempts);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#saveConnector(com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public ConnectorDto saveConnector(ConnectorDto connector)
    {
        return saveEntity(connector);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.ConnectorDao#deleteConnectors(java.util.List)
     */
    @Override
    public void deleteConnectors(List<ConnectorDto> connectors)
    {
        removeEntities(connectors);
    }    
}
