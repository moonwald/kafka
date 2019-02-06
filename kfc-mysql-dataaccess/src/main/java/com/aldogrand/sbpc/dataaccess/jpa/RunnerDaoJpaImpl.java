package com.aldogrand.sbpc.dataaccess.jpa;

import com.aldogrand.sbpc.dataaccess.RunnerDao;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>Title</b> RunnerDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link com.aldogrand.sbpc.dataaccess.RunnerDao}.
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
public class RunnerDaoJpaImpl extends GenericJpaDao<RunnerDto> implements
        RunnerDao
{
    /**
     * Create a new {@link com.aldogrand.sbpc.dataaccess.jpa.RunnerDaoJpaImpl}.
     */
    public RunnerDaoJpaImpl()
    {
        super(RunnerDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getRunner(long)
     */
	@Override
	public RunnerDto getRunner(long id) 
	{
		return getEntity(id);
	}
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getRunnerWithPrices(long)
     */
    @Override
    public RunnerDto getRunnerWithPrices(long id)
    {
        return executeQuery("from Runner r left join fetch r.prices where r.id = ?1", id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.RunnerDto)
     */
    @Override
    public void lock(RunnerDto runner)
    {
        writeLock(runner);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getRunners(long, java.util.List)
     */
    @Override
    public List<RunnerDto> getRunners(long connectorId, List<Long> ids)
    {
        assert ids != null;

        return executeQuery("select rm.runner from RunnerMapping rm where rm.connector.id = ?1 and rm.runner.id in (?2)", 
                0, null, connectorId, ids);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getNumberOfRunners(java.lang.Long)
     */
    @Override
    public int getNumberOfRunners(Long marketId)
    {
        assert marketId != null;
        
        Number count = executeQuery("select count(r.id) from Runner r where r.market.id = ?1", 
                Number.class, marketId);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getRunners(java.lang.Long, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<RunnerDto> getRunners(Long marketId, Integer offset, Integer maxResults)
    {
        assert marketId != null;
        
        return executeQuery("from Runner r where r.market.id = ?1 order by r.name", 
                offset, maxResults, marketId);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getMappedRunners(long, 
     * java.util.List)
     */
    @Override
    public List<RunnerDto> getMappedRunners(long connectorId,
            List<String> sourceIds)
    {
        assert sourceIds != null;

        return executeQuery("select sr.runner from SourceRunner sr where sr.connector.id = ?1 and sm.sourceId in (?2)", 
                0, null, connectorId, sourceIds);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getRunners(java.util.List, 
     * java.util.Date, java.util.List, java.util.List, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<RunnerDto> getRunners(List<Long> connectorIds,
            Date modifiedSince, List<Long> eventIds, List<Long> marketIds,
            Integer offset, Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append("select sr.runner from SourceRunner sr");
            boolean where = false;
            int index = 1;
            query.append(where ? " and" : " where");
            query.append(" sr.connector.id in (?");
            query.append(index);
            query.append(")");
            params.add(connectorIds);
            where = true;
            index ++;

            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" sr.runner.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if ((marketIds != null) && (!marketIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sr.runner.market.id in (?");
                query.append(index);
                query.append(")");
                params.add(marketIds);
                where = true;
                index ++;
            }
            else if ((eventIds != null) && (!eventIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sr.runner.market.event.id in (?");
                query.append(index);
                query.append(")");
                params.add(eventIds);
                where = true;
                index ++;
            }
            query.append(" order by sr.runner.market, sr.runner.sequence");
        }
        else
        {
            query.append("from Runner r");
            boolean where = false;
            int index = 1;
            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" r.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if ((marketIds != null) && (!marketIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" r.market.id in (?");
                query.append(index);
                query.append(")");
                params.add(marketIds);
                where = true;
                index ++;
            }
            else if ((eventIds != null) && (!eventIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" r.market.event.id in (?");
                query.append(index);
                query.append(")");
                params.add(eventIds);
                where = true;
                index ++;
            }
            query.append(" order by r.market, r.sequence");
        }

        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#saveRunner(
     * com.aldogrand.sbpc.dataaccess.model.RunnerDto)
     */
    @Override
    public RunnerDto saveRunner(RunnerDto runner)
    {
        return saveEntity(runner);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#deleteRunner(
     * com.aldogrand.sbpc.dataaccess.model.RunnerDto)
     */
    @Override
    public void deleteRunner(RunnerDto runner)
    {
        removeEntity(runner);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.RunnerDao#getRunners(java.lang.Long,java.lang.Long)
     */
    @Override
    public RunnerDto getRunner(Long id, Long marketId)
    {
        assert marketId != null;
        assert id != null;

        return executeQuery("from Runner r where r.market.id = ?1 and r.id = ?2 order by r.name", marketId, id);
    }
}
