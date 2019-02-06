package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.BettingPlatformDao;
import com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> BettingPlatformDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link BettingPlatformDao}.
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
public class BettingPlatformDaoJpaImpl extends GenericJpaDao<BettingPlatformDto> 
        implements BettingPlatformDao
{
    /**
     * Create a new {@link BettingPlatformDaoJpaImpl}.
     */
    public BettingPlatformDaoJpaImpl()
    {
        super(BettingPlatformDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#getBettingPlatform(long)
     */
    @Override
    public BettingPlatformDto getBettingPlatform(long id) 
    {
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#getBettingPlatform(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.String)
     */
    @Override
    public BettingPlatformDto getBettingPlatform(ConnectorDto connector,
            String bettingPlatformName)
    {
        assert connector != null;
        assert bettingPlatformName != null;
        
        return executeQuery("from BettingPlatform bp where bp.connector = ?1 and bp.name = ?2", 
                connector, bettingPlatformName);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto)
     */
    @Override
    public void lock(BettingPlatformDto bettingPlatform)
    {
        writeLock(bettingPlatform);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#getNumberOfBettingPlatforms()
     */
    @Override
    public int getNumberOfBettingPlatforms()
    {
        Number count = executeQuery("select count(bp.id) from BettingPlatform bp", 
                Number.class);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#getBettingPlatforms(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<BettingPlatformDto> getBettingPlatforms(Integer offset, Integer maxResults)
    {
        return executeQuery("from BettingPlatform bp order by bp.name", 
                offset, maxResults);
    }
    

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#getNumberOfBettingPlatforms()
     */
    @Override
    public int getNumberOfBettingPlatformsLastFetchedBefore(Date lastFetchedBefore)
    {
        Number count = executeQuery("select count(bp.id) from BettingPlatform bp where bp.lastFetchTime < ?1", 
                Number.class,lastFetchedBefore);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#getBettingPlatforms(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<BettingPlatformDto> getBettingPlatformsLastFetchedBefore(Date lastFetchedBefore, Integer offset, Integer maxResults)
    {
        return executeQuery("from BettingPlatform bp where bp.lastFetchTime < ?1 order by bp.name", 
                offset, maxResults, lastFetchedBefore);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao
     * #getNumberOfBettingPlatforms(java.util.List)
     */
    @Override
    public int getNumberOfBettingPlatforms(List<Long> connectorIds)
    {
        assert connectorIds != null;

        Number count = executeQuery("select count(bp.id) from BettingPlatform bp where bp.connector.id in (?1)", 
                Number.class, connectorIds);
        return count.intValue();
    }
    
    

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao
     * #getBettingPlatforms(java.util.List, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<BettingPlatformDto> getBettingPlatforms(
            List<Long> connectorIds, Integer offset, Integer maxResults)
    {
        assert connectorIds != null;

        return executeQuery("from BettingPlatform bp where bp.connector.id in (?1) order by bp.name", 
                offset, maxResults, connectorIds);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#saveBettingPlatform(
     * com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto)
     */
    @Override
    public BettingPlatformDto saveBettingPlatform(BettingPlatformDto bettingPlatform)
    {
        assert bettingPlatform != null;
        
        return saveEntity(bettingPlatform);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.BettingPlatformDao#deleteBettingPlatform(
     * com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto)
     */
    @Override
    public void deleteBettingPlatform(BettingPlatformDto bettingPlatform)
    {
        assert bettingPlatform != null;

        removeEntity(bettingPlatform);
    }
}
