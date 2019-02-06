package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.PositionDao;
import com.aldogrand.sbpc.dataaccess.model.PositionDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> PositionDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link PositionDto}.
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
public class PositionDaoJpaImpl extends GenericJpaDao<PositionDto> implements
        PositionDao
{
    /**
     * Create a new {@link PositionDaoJpaImpl}.
     */
    public PositionDaoJpaImpl()
    {
        super(PositionDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getPosition(long)
     */
    @Override
    public PositionDto getPosition(long id)
    {
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getPosition(
     * java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    public PositionDto getPosition(Long runnerId, Long bettingPlatformId, Long accountId)
    {
        assert runnerId != null;
        assert bettingPlatformId != null;
        assert accountId != null;
        
        return executeQuery("from Position p where p.runner.id = ?1 and p.bettingPlatform.id = ?2 and p.account.id = ?3", 
                runnerId, bettingPlatformId, accountId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.PositionDto)
     */
    @Override
    public void lock(PositionDto position)
    {
        writeLock(position);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getNumberOfPositions(
     * java.lang.Long)
     */
    @Override
    public int getNumberOfPositions(Long bettingPlatformId)
    {
        assert bettingPlatformId != null;
        
        Number count = executeQuery("select count(p.id) from Position p where p.bettingPlatform.id = ?1", 
                Number.class, bettingPlatformId);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getPositions(
     * java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PositionDto> getPositions(Long bettingPlatformId,
            Integer offset, Integer maxResults)
    {
        assert bettingPlatformId != null;
        
        return executeQuery("from Position p where p.bettingPlatform.id = ?1 order by p.runner.id, p.account.id", 
                offset, maxResults, bettingPlatformId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao
     * #getNumberOfRunnerPositions(java.lang.Long)
     */
    @Override
    public int getNumberOfRunnerPositions(Long runnerId)
    {
        assert runnerId != null;

        return executeQuery("select count(p.id) from Position p where p.runner.id = ?1", 
                Number.class, runnerId).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao
     * #getRunnerPositions(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PositionDto> getRunnerPositions(Long runnerId, Integer offset,
            Integer maxResults)
    {
        assert runnerId != null;

        return executeQuery("from Position p where p.runner.id = ?1 order by p.id", 
                offset, maxResults, runnerId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao
     * #getNumberOfRunnerPositions(java.lang.Long, java.lang.Long)
     */
    @Override
    public int getNumberOfRunnerPositions(Long connectorId, Long runnerId)
    {
        assert connectorId != null;
        assert runnerId != null;

        return executeQuery("select count(p.id) from Position p where p.bettingPlatform.connector.id = ?1 and p.runner.id = ?2", 
                Number.class, connectorId, runnerId).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getRunnerPositions(
     * java.lang.Long, java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PositionDto> getRunnerPositions(Long connectorId, Long runnerId,
            Integer offset, Integer maxResults)
    {
        assert connectorId != null;
        assert runnerId != null;

        return executeQuery("from Position p where p.bettingPlatform.connector.id = ?1 and p.runner.id = ?2 order by p.id", 
                offset, maxResults, connectorId, runnerId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getPositions(
     * java.util.List, java.util.List, java.util.List, java.util.Date, java.util.List,
     * java.util.List, java.util.List, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PositionDto> getPositions(List<Long> connectorIds,
            List<Long> bettingPlatformIds, List<Long> accountIds,
            Date modifiedSince, List<Long> eventIds, List<Long> marketIds,
            List<Long> runnerIds, Integer offset, Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from Position p");
        if ((bettingPlatformIds != null) && (!bettingPlatformIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(bettingPlatformIds);
        }
        else if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.connector.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(connectorIds);
        }
        if ((accountIds != null) && (!accountIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.account.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(accountIds);
        }
        if (modifiedSince != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.lastChangeTime >= ?");
            query.append(index);
            index ++;
            params.add(modifiedSince);
        }
        if ((runnerIds != null) && (!runnerIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(runnerIds);
        }
        else if ((marketIds != null) && (!marketIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(marketIds);
        } 
        else if ((eventIds != null) && (!eventIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.event.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(eventIds);
        } 
        query.append(" order by p.runner.id, p.bettingPlatform.id, p.account.id");
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao
     * #getNumberOfPositionsLastFetchedBefore(java.lang.Long, java.lang.Long, 
     * java.lang.Long, java.util.Date)
     */
    @Override
    public int getNumberOfPositionsLastFetchedBefore(Long connectorId,
            Long marketId, Long accountId, Date fetchTime)
    {
        assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select count(p.id) from Position p");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        if (accountId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.account.id = ?");
            query.append(index);
            index ++;
            params.add(accountId);
        }
        query.append((where ? " and" : " where"));
        query.append(" p.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);

        Number count = executeQuery(query.toString(), Number.class, params);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getPositionsLastFetchedBefore(
     * java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PositionDto> getPositionsLastFetchedBefore(Long connectorId, 
            Long marketId, Long accountId, Date fetchTime, Integer offset, 
            Integer maxResults)
    {
        assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from Position p");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.bettingPlatform.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.runner.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        if (accountId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" p.account.id = ?");
            query.append(index);
            index ++;
            params.add(accountId);
        }
        query.append((where ? " and" : " where"));
        query.append(" p.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);

        return executeQuery(query.toString(), offset, maxResults, params);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao
     * #getNumberOfPositionsWithFailedFetchAttempts(java.lang.Long, java.lang.Long, 
     * java.lang.Long, int)
     */
    @Override
    public int getNumberOfPositionsWithFailedFetchAttempts(Long connectorId, 
            Long marketId, Long accountId, int fetchAttempts)
    {
        assert connectorId != null;
        assert marketId != null;
        assert accountId != null;

        Number count = executeQuery("select count(p.id) from Position p where p.bettingPlatform.connector.id = ?1 and p.runner.market.id = ?2 and p.account.id = ?3 and p.failedFetchAttempts >= ?4", 
                Number.class, connectorId, marketId, accountId, fetchAttempts);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#getPositionsWithFailedFetchAttempts(
     * java.lang.Long, java.lang.Long, java.lang.Long, int, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<PositionDto> getPositionsWithFailedFetchAttempts(
            Long connectorId, Long marketId, Long accountId,
            int fetchAttempts, Integer offset, Integer maxResults)
    {
        assert connectorId != null;
        assert marketId != null;
        assert accountId != null;

        return executeQuery("from Position p where p.bettingPlatform.connector.id = ?1 and p.runner.market.id = ?2 and p.account.id = ?3 and p.failedFetchAttempts >= ?4", 
                offset, maxResults, connectorId, marketId, accountId, fetchAttempts);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#savePosition(
     * com.aldogrand.sbpc.dataaccess.model.PositionDto)
     */
    @Override
    public PositionDto savePosition(PositionDto position)
    {
        return saveEntity(position);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.PositionDao#deletePosition(
     * com.aldogrand.sbpc.dataaccess.model.PositionDto)
     */
    @Override
    public void deletePosition(PositionDto position)
    {
        removeEntity(position);
    }
    
	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.PositionDao#deletePositions(java.util.Date)
	 */
	@Override
	public void deletePositions(Date lastFetchedBefore) 
	{
	    assert lastFetchedBefore != null;
	    
		String query = "delete from Position p where p.lastFetchTime < ?1";
		executeUpdateQuery(query, lastFetchedBefore);
	}
}
