package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.dataaccess.OfferDao;
import com.aldogrand.sbpc.dataaccess.model.OfferDto;
import com.aldogrand.sbpc.model.OfferStatus;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> OfferDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link OfferDto}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Ken Barry
 * @version 1.0
 */
public class OfferDaoJpaImpl extends GenericJpaDao<OfferDto>
        implements OfferDao
{
    public OfferDaoJpaImpl()
    {
        super(OfferDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getOffer(long)
     */
    @Override
    public OfferDto getOffer(long id)
    {
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getOffer(long, boolean)
     */
    @Override
    public OfferDto getOffer(long id, boolean lock)
    {
        if (lock)
        {
            return getEntityManager().find(OfferDto.class, id, LockModeType.PESSIMISTIC_WRITE);
        }
        else
        {
            return getEntity(id);
        }
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao
     * #getOffer(java.lang.Long, java.lang.String)
     */
    @Override
    public OfferDto getOffer(Long connectorId, String sourceId)
    {
        assert connectorId != null;
        assert sourceId != null;
        
        return executeQuery("from Offer o where o.connector.id = ?1 and o.sourceId = ?2", 
                connectorId, sourceId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.OfferDto)
     */
    @Override
    public void lock(OfferDto offer)
    {
        writeLock(offer);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getOffers(java.util.List)
     */
    @Override
    public List<OfferDto> getOffers(List<Long> ids)
    {
        assert ids != null;
        
        return executeQuery("from Offer o where o.id in (?1)", 0, null, ids);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao
     * #getNumberOfRunnerOffers(java.lang.Long)
     */
    @Override
    public int getNumberOfRunnerOffers(Long runnerId)
    {
        assert runnerId != null;
        
        Number count = executeQuery("select count(o.id) from Offer o where o.runner.id = ?1", 
                Number.class, runnerId);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getRunnerOffers(
     * java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<OfferDto> getRunnerOffers(Long runnerId, Integer offset,
            Integer maxResults)
    {
        assert runnerId != null;
        
        return executeQuery("from Offer o where o.runner.id = ?1 order by o.offerTime", 
                offset, maxResults, runnerId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao
     * #getNumberOfRunnerOffers(java.lang.Long, java.lang.Long)
     */
    @Override
    public int getNumberOfRunnerOffers(Long connectorId, Long runnerId)
    {
        assert connectorId != null;
        assert runnerId != null;

        return executeQuery("select count(o.id) from Offer o where o.connector.id = ?1 and o.runner.id = ?2", 
                Number.class, connectorId, runnerId).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao
     * #getRunnerOffers(java.lang.Long, java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<OfferDto> getRunnerOffers(Long connectorId, Long runnerId,
            Integer offset, Integer maxResults)
    {
        assert connectorId != null;
        assert runnerId != null;

        return executeQuery("from Offer o where o.connector.id = ?1 and o.runner.id = ?2 order by o.id", 
                offset, maxResults, connectorId, runnerId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getOffers(java.util.List, 
     * java.util.List, java.util.Date, java.util.List, java.util.List, java.util.List, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<OfferDto> getOffers(List<Long> connectorIds, List<Long> accountIds,
            Date modifiedSince, List<Long> eventIds, List<Long> marketIds,
            List<Long> runnerIds, OfferStatus status, Boolean hasBets, Integer offset, Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select distinct(o) from Offer o");
        if(hasBets != null){
        	if(hasBets == true){
        		query.append(" join o.bets b");
        	}
        	else{
        		query.append(" left outer join o.bets b");
        		query.append((where ? " and" : " where"));
                where = true;
                query.append(" b.id is null");
        	}
        }
        if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.connector.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(connectorIds);
        }
        if ((accountIds != null) && (!accountIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.account.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(accountIds);
        }
        if (modifiedSince != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.lastChangeTime >= ?");
            query.append(index);
            index ++;
            params.add(modifiedSince);
        }
        if (status != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.status = ?");
            query.append(index);
            index ++;
            params.add(status);
        }
        if ((runnerIds != null) && (!runnerIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(runnerIds);
        }
        else if ((marketIds != null) && (!marketIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(marketIds);
        } 
        else if ((eventIds != null) && (!eventIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.event.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(eventIds);
        } 
        query.append(" order by o.offerTime");
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getNumberOfOffers(
     * java.lang.Long, java.util.List, java.util.List, java.util.List)
     */
    @Override
    public int getNumberOfOffers(Long accountId, List<Long> eventIds,
            List<Long> marketIds, List<Long> runnerIds)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select count(o.id) from Offer o");
        if (accountId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.account.id = ?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(accountId);
        }
        if ((runnerIds != null) && (!runnerIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(runnerIds);
        }
        else if ((marketIds != null) && (!marketIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(marketIds);
        } 
        else if ((eventIds != null) && (!eventIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.event.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(eventIds);
        } 
        return executeQuery(query.toString(), Number.class, params.toArray()).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getOffers(java.lang.Long, 
     * java.util.List, java.util.List, java.util.List, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<OfferDto> getOffers(Long accountId, List<Long> eventIds,
            List<Long> marketIds, List<Long> runnerIds, Integer offset,
            Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from Offer o");
        if (accountId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.account.id = ?");
            query.append(index);
            index ++;
            params.add(accountId);
        }
        if ((runnerIds != null) && (!runnerIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(runnerIds);
        }
        else if ((marketIds != null) && (!marketIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(marketIds);
        } 
        else if ((eventIds != null) && (!eventIds.isEmpty()))
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.event.id in (?");
            query.append(index);
            query.append(")");
            index ++;
            params.add(eventIds);
        } 
        query.append(" order by o.offerTime");
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getNumberOfOffersLastFetchedBefore(
     * java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date)
     */
    @Override
    public int getNumberOfOffersLastFetchedBefore(Long connectorId,
            Long marketId, Long accountId, Date fetchTime)
    {
    	assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select count(o.id) from Offer o");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.bettingPlatform.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        if (accountId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.account.id = ?");
            query.append(index);
            index ++;
            params.add(accountId);
        }
        query.append((where ? " and" : " where"));
        query.append(" o.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        Number count = executeQuery(query.toString(), Number.class, params);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getOffersLastFetchedBefore(
     * java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.lang.Integer, 
     * java.lang.Integer)
     */
    @Override
    public List<OfferDto> getOffersLastFetchedBefore(Long connectorId, Long marketId, 
            Long accountId, Date fetchTime, Integer offset, Integer maxResults)
    {
    	assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from Offer o");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.bettingPlatform.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (marketId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.runner.market.id = ?");
            query.append(index);
            index ++;
            params.add(marketId);
        }
        if (accountId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" o.account.id = ?");
            query.append(index);
            index ++;
            params.add(accountId);
        }
        query.append((where ? " and" : " where"));
        query.append(" o.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        return executeQuery(query.toString(), offset, maxResults, params);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getNumberOfOffersWithFailedFetchAttempts(
     * java.lang.Long, java.lang.Long, java.lang.Long, int)
     */
    @Override
    public int getNumberOfOffersWithFailedFetchAttempts(Long connectorId,
            Long marketId, Long accountId, int attempts)
    {
        assert connectorId != null;
        assert marketId != null;
        assert accountId != null;
        
        Number count = executeQuery("select count(o.id) from Offer o where o.connector.id = ?1 and o.runner.market.id = ?2 and o.account.id = ?3 and o.failedFetchAttempts >= ?4",
                Number.class, connectorId, marketId, accountId, attempts);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#getOffersWithFailedFetchAttempts(
     * java.lang.Long, java.lang.Long, java.lang.Long, int, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<OfferDto> getOffersWithFailedFetchAttempts(Long connectorId, 
            Long marketId, Long accountId, int attempts, Integer offset, 
            Integer maxResults)
    {
        assert connectorId != null;
        assert marketId != null;
        assert accountId != null;
        
        return executeQuery("from Offer o where o.connector.id = ?1 and o.runner.market.id = ?2 and o.account.id = ?3 and o.failedFetchAttempts >= ?4 order by o.offerTime",
                offset, maxResults, connectorId, marketId, accountId, attempts);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#saveOffer(
     * com.aldogrand.sbpc.dataaccess.model.OfferDto)
     */
    @Override
    public OfferDto saveOffer(OfferDto offer)
    {
      assert offer != null;
      entityManager.persist(offer);
      return offer;
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.OfferDao#deleteOffer(
     * com.aldogrand.sbpc.dataaccess.model.OfferDto)
     */
    @Override
    public void deleteOffer(OfferDto offer)
    {
        assert offer != null;
        
        removeEntity(offer);
    }
    
	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.OfferDao#deleteOffers(java.util.Date)
	 */
	@Override
	public void deleteOffers(Date lastFetchedBefore) 
	{
	    assert lastFetchedBefore != null;
	    
		String query = "delete from Offer o where o.lastFetchTime < ?1";
		executeUpdateQuery(query, lastFetchedBefore);
	}

	@Override
	public OfferDto getOffer(Long connectorId, String sourceId, String sourceRunnerId,
			String sourceMarketId, String sourceEventId) {
		assert connectorId != null;
        assert sourceId != null;

        
        return executeQuery("select o from Offer o join o.runner.mappings as r where o.connector.id = ?1 and o.sourceId = ?2  and "
        		+ "r.sourceId = ?3 and r.sourceMarket.sourceId = ?4 and r.sourceMarket.sourceEvent.sourceId = ?5", 
                connectorId, sourceId, sourceRunnerId,sourceMarketId, sourceEventId);
	}
}
