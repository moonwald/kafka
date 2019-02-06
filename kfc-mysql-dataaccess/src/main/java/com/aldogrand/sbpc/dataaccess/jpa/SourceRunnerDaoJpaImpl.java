package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import com.aldogrand.sbpc.dataaccess.SourceRunnerDao;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> SourceRunnerDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link SourceRunnerDao}.
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
public class SourceRunnerDaoJpaImpl extends GenericJpaDao<SourceRunnerDto>
        implements SourceRunnerDao
{
    public SourceRunnerDaoJpaImpl()
    {
        super(SourceRunnerDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getSourceRunner(java.lang.Long, boolean)
     */
    @Override
    public SourceRunnerDto getSourceRunner(Long id, boolean lock)
    {
        if (lock)
        {
            return getEntityManager().find(SourceRunnerDto.class, id, 
                    LockModeType.PESSIMISTIC_WRITE);
        }
        else
        {
            return getEntity(id);
        }
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getSourceRunner(java.lang.Long, java.lang.String)
     */
    @Override
    public SourceRunnerDto getSourceRunner(Long connectorId, String sourceId)
    {
        assert connectorId != null;
        assert sourceId != null;
        
        return executeQuery("from SourceRunner sr where sr.connector.id = ?1 and sr.sourceId = ?2", 
                connectorId, sourceId);
    }

    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getSourceRunner(java.lang.Long, java.lang.String)
     */
    @Override
    public List<SourceRunnerDto> getSourceRunners(Long connectorId, String sourceId)
    {
        assert connectorId != null;
        assert sourceId != null;
        
        return executeQuery("from SourceRunner sr where sr.connector.id = ?1 and sr.sourceId = ?2", (Integer)null, (Integer)null,
                connectorId, sourceId);
    }

    
    
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto)
     */
    @Override
    public void lock(SourceRunnerDto sourceRunner)
    {
        writeLock(sourceRunner);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getNumberOfUnmappedSourceRunners()
     */
    @Override
    public int getNumberOfUnmappedSourceRunners()
    {
        return executeQuery("select count(sr.id) from SourceRunner sr where sr.runner is null",
                Number.class).intValue();
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getNumberOfUnmappedSourceRunners()
     */
    @Override
    public int getNumberOfUnmappedSourceRunners(Long marketId, Long connectorId)
    {
        return executeQuery("select count(sr.id) from SourceRunner sr where sr.runner is null and sr.sourceMarket.market.id = ?1 and sr.sourceMarket.connector.id = ?2",
                Number.class, marketId, connectorId).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getUnmappedSourceRunners(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceRunnerDto> getUnmappedSourceRunners(Integer offset,
            Integer maxResults)
    {
        return executeQuery("from SourceRunner sr where sr.runner is null order by sr.sourceMarket, sr.sequence",
                offset, maxResults);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getUnmappedSourceRunners(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<SourceRunnerDto> getUnmappedSourceRunners(Long marketId,
            Long connectorId)
    {
        assert marketId != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        query.append("from SourceRunner sr where sr.runner is null and sr.sourceMarket.market.id = ?1");
        params.add(marketId);
        if (connectorId != null)
        {
            query.append(" and sr.connector.id = ?2");
            params.add(connectorId);
        }
        query.append(" order by sr.sequence");

        return executeQuery(query.toString(), 0, null, params.toArray(new Object[params.size()]));
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getRunnerMapping(com.aldogrand.sbpc.dataaccess.model.ConnectorDto, 
     * java.lang.String)
     */
    @Override
    public SourceRunnerDto getRunnerMapping(ConnectorDto connector,
            String sourceId)
    {
        assert connector != null;
        assert sourceId != null;
        
        return executeQuery("from SourceRunner r where r.connector = ?1 and r.sourceId = ?2", 
                connector, sourceId);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getRunnerMapping(
     * com.aldogrand.sbpc.dataaccess.model.RunnerDto, 
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public SourceRunnerDto getRunnerMapping(RunnerDto runner,
            ConnectorDto connector)
    {
        assert runner != null;
        assert connector != null;
        
        return executeQuery("from SourceRunner r where r.runner = ?1 and r.connector = ?2", 
                runner, connector);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getRunnerMapping(
     * java.lang.Long, java.lang.Long)
     */
    @Override
    public SourceRunnerDto getRunnerMapping(Long runnerId, Long connectorId)
    {
        assert runnerId != null;
        assert connectorId != null;
        
        return executeQuery("from SourceRunner r where r.runner.id = ?1 and r.connector.id = ?2", 
                runnerId, connectorId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getNumberOfRunnerMappings(com.aldogrand.sbpc.dataaccess.model.RunnerDto)
     */
    @Override
    public int getNumberOfRunnerMappings(RunnerDto runner)
    {
        assert runner != null;
        
        Number count = executeQuery("select count(r.id) from SourceRunner r where r.runner = ?1", 
                Number.class, runner);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getRunnerMappings(com.aldogrand.sbpc.dataaccess.model.RunnerDto, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceRunnerDto> getRunnerMappings(RunnerDto runner,
            Integer offset, Integer maxResults)
    {
        assert runner != null;
        
        return executeQuery("from SourceRunner r where r.runner = ?1 order by r.id", 
                offset, maxResults, runner);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getNumberOfRunnerMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public int getNumberOfRunnerMappings(ConnectorDto connector)
    {
        assert connector != null;
        
        Number count = executeQuery("select count(r.id) from SourceRunner r where r.connector = ?1", 
                Number.class, connector);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getRunnerMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    public List<SourceRunnerDto> getRunnerMappings(ConnectorDto connector,
            Integer offset, Integer maxResults)
    {
        assert connector != null;
        
        return executeQuery("from SourceRunner r where r.connector = ?1 order by r.id", 
                offset, maxResults, connector);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getNumberOfRunnerMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, 
     * com.aldogrand.sbpc.dataaccess.model.MarketDto)
     */
    @Override
    public int getNumberOfRunnerMappings(ConnectorDto connector,
            MarketDto market)
    {
        assert connector != null;
        assert market != null;
        
        Number count =  executeQuery("select count(r.id) from SourceRunner r where r.connector = ?1 and r.runner.market = ?2", 
                Number.class, connector, market);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getRunnerMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, 
     * com.aldogrand.sbpc.dataaccess.model.MarketDto, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceRunnerDto> getRunnerMappings(ConnectorDto connector,
            MarketDto market, Integer offset, Integer maxResults)
    {
        assert connector != null;
        assert market != null;
        
        return executeQuery("from SourceRunner r where r.connector = ?1 and r.runner.market = ?2", 
                offset, maxResults, connector, market);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getNumberOfSourceRunnersLastFetchedBefore(java.lang.Long, java.lang.Long, 
     * java.util.Date)
     */
    @Override
    public int getNumberOfSourceRunnersLastFetchedBefore(Long connectorId, 
            Long marketId, Date fetchTime)
    {
        assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select count(sr.id) from SourceRunner sr");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sr.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sr.sourceMarket.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        query.append((where ? " and" : " where"));
        query.append(" sr.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        Number count = executeQuery(query.toString(), Number.class, params.toArray());
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getSourceRunnersLastFetchedBefore(java.lang.Long, java.lang.Long, 
     * java.util.Date, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceRunnerDto> getSourceRunnersLastFetchedBefore(
            Long connectorId, Long marketId, Date fetchTime,
            Integer offset, Integer maxResults)
    {
        assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from SourceRunner sr");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sr.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sr.sourceMarket.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        query.append((where ? " and" : " where"));
        query.append(" sr.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getNumberOfSourceRunnersWithFailedFetchAttempts(java.lang.Long, 
     * java.lang.Long, int)
     */
    @Override
    public int getNumberOfSourceRunnersWithFailedFetchAttempts(
            Long connectorId, Long marketId, int attempts)
    {
        assert connectorId != null;
        assert marketId != null;
        
        Number count = executeQuery("select count(sr.id) from SourceRunner sr where sr.connector.id = ?1 and sr.sourceMarket.market.id = ?2 and sr.failedFetchAttempts >= ?3", 
                Number.class, connectorId, marketId, attempts);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #getSourceRunnersWithFailedFetchAttempts(java.lang.Long, java.lang.Long, 
     * int, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceRunnerDto> getSourceRunnersWithFailedFetchAttempts(
            Long connectorId, Long marketId, int attempts,
            Integer offset, Integer maxResults)
    {
        assert connectorId != null;
        assert marketId != null;
        
        return executeQuery("from SourceRunner sr where sr.connector.id = ?1 and sr.sourceMarket.market.id = ?2 and sr.failedFetchAttempts >= ?3 order by sr.id", 
                offset, maxResults, connectorId, marketId, attempts);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getRunnerMapping(com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.String)
     */
    @Override
    public SourceRunnerDto getRunnerMappingFromId(ConnectorDto connector,
            Long modelId)
    {
        assert connector != null;
        assert modelId != null;
        
        return executeQuery("from SourceRunner r where r.connector = ?1 and r.runner.id = ?2", 
                 connector, modelId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao#getRunnerMappings(com.aldogrand.sbpc.dataaccess.model.MarketDto, com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public List<SourceRunnerDto> getRunnerMappings(MarketDto market,
            ConnectorDto connector)
    {
        assert market != null;
        assert connector != null;
        
        return executeQuery("from SourceRunner r where r.runner.market = ?1 and r.connector = ?2", 
                0, null, market, connector);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #saveSourceRunner(com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto)
     */
    @Override
    public SourceRunnerDto saveSourceRunner(SourceRunnerDto sourceRunner)
    {
        assert sourceRunner != null;
        
        return saveEntity(sourceRunner);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceRunnerDao
     * #deleteSourceRunner(com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto)
     */
    @Override
    public void deleteSourceRunner(SourceRunnerDto sourceRunner)
    {
        assert sourceRunner != null;
        
        removeEntity(sourceRunner);
    }
}
