package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.apache.commons.collections.CollectionUtils;

import com.aldogrand.sbpc.dataaccess.SourceMarketDao;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> SourceMarketDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link SourceMarketDao}.
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
public class SourceMarketDaoJpaImpl extends GenericJpaDao<SourceMarketDto>
        implements SourceMarketDao
{
    public SourceMarketDaoJpaImpl()
    {
        super(SourceMarketDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getSourceMarket(
     * java.lang.Long, boolean)
     */
    @Override
    public SourceMarketDto getSourceMarket(Long id, boolean lock)
    {
        if (lock)
        {
            return getEntityManager().find(SourceMarketDto.class, id, 
                    LockModeType.PESSIMISTIC_WRITE);
        }
        else
        {
            return getEntity(id);
        }
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getSourceMarket(java.lang.Long, java.lang.String)
     */
    @Override
    public SourceMarketDto getSourceMarket(Long connectorId,
            String sourceId)
    {
        assert connectorId != null;
        assert sourceId != null;
        
        return executeQuery("from SourceMarket sm where sm.connector.id = ?1 and sm.sourceId = ?2", 
                connectorId, sourceId);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getSourceMarket(java.lang.Long, java.lang.String)
     */
    @Override
    public List<SourceMarketDto> getSourceMarkets(Long connectorId,
            String sourceId)
    {
        assert connectorId != null;
        assert sourceId != null;
        
        return executeQuery("from SourceMarket sm where sm.connector.id = ?1 and sm.sourceId = ?2", 
                (Integer)null, (Integer)null, connectorId, sourceId);
    }
    
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.SourceMarketDto)
     */
    @Override
    public void lock(SourceMarketDto sourceMarket)
    {
        writeLock(sourceMarket);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getNumberOfUnmappedSourceMarkets()
     */
    @Override
    public int getNumberOfUnmappedSourceMarkets()
    {
        return executeQuery("select count(sm.id) from SourceMarket sm where sm.market is null", 
                Number.class).intValue();
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getNumberOfUnmappedSourceMarkets()
     */
    @Override
    public int getNumberOfUnmappedSourceMarkets(Long eventId, Long connectorId)
    {
        return executeQuery("select count(sm.id) from SourceMarket sm where sm.market is null and sm.sourceEvent.event.id = ?1 and sm.sourceEvent.connector.id = ?2", 
                Number.class, eventId, connectorId).intValue();
    
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getUnmappedSourceMarkets(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceMarketDto> getUnmappedSourceMarkets(Integer offset,
            Integer maxResults)
    {
        return executeQuery("from SourceMarket sm where sm.market is null order by sm.sourceEvent, sm.id", 
                offset, maxResults);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getUnmappedSourceMarkets(
     * java.lang.Long, java.lang.Long)
     */
    @Override
    public List<SourceMarketDto> getUnmappedSourceMarkets(Long eventId,
            Long connectorId)
    {
        assert eventId != null;

        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        query.append("from SourceMarket sm where sm.market is null and sm.sourceEvent.event.id = ?1");
        params.add(eventId);
        if (connectorId != null)
        {
            query.append(" and sm.connector.id = ?2");
            params.add(connectorId);
        }
        query.append(" order by sm.sourceName, sm.handicap");
        return executeQuery(query.toString(), 0, null, params.toArray(new Object[params.size()]));
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getMarketMapping(
     * com.aldogrand.sbpc.dataaccess.model.MarketDto, 
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public SourceMarketDto getMarketMapping(MarketDto market,
            ConnectorDto connector)
    {
        assert market != null;
        assert connector != null;
        
        return executeQuery("from SourceMarket m where m.market = ?1 and m.connector = ?2", 
                market, connector);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getMarketMapping(
     * java.lang.Long, java.lang.Long)
     */
    @Override
    public SourceMarketDto getMarketMapping(Long marketId, Long connectorId)
    {
        assert marketId != null;
        assert connectorId != null;
        
        return executeQuery("from SourceMarket m where m.market.id = ?1 and m.connector.id = ?2", 
                marketId, connectorId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getNumberOfMarketMappings(
     * com.aldogrand.sbpc.dataaccess.model.MarketDto)
     */
    @Override
    public int getNumberOfMarketMappings(MarketDto market)
    {
        assert market != null;

        Number count = executeQuery("select count(m.id) from SourceMarket m where m.market = ?1", 
                Number.class, market);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getMarketMappings(
     * com.aldogrand.sbpc.dataaccess.model.MarketDto, java.lang.Integer, 
     * java.lang.Integer)
     */
    @Override
    public List<SourceMarketDto> getMarketMappings(MarketDto market, Integer offset, 
            Integer maxResults)
    {
        assert market != null;

        return executeQuery("from SourceMarket m where m.market = ?1 order by m.id", 
                offset, maxResults, market);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getNumberOfMarketMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public int getNumberOfMarketMappings(ConnectorDto connector)
    {
        assert connector != null;
        
        Number count = executeQuery("select count(mm.id) from SourceMarket mm where mm.connector = ?1", 
                Number.class, connector);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getMarketMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.Integer, 
     * java.lang.Integer)
     */
    @Override
    public List<SourceMarketDto> getMarketMappings(ConnectorDto connector,
            Integer offset, Integer maxResults)
    {
        assert connector != null;
        
        return executeQuery("from SourceMarket mm where mm.connector = ?1 order by mm.id", 
                offset, maxResults, connector);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getNumberOfMarketMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, 
     * com.aldogrand.sbpc.dataaccess.model.EventDto)
     */
    @Override
    public int getNumberOfMarketMappings(ConnectorDto connector, EventDto event)
    {
        assert connector != null;
        assert event != null;
        
        Number count = executeQuery("select count(m.id) from SourceMarket m where m.connector = ?1 and m.market.event = ?2", 
                Number.class, connector, event);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getMarketMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, 
     * com.aldogrand.sbpc.dataaccess.model.EventDto, java.lang.Integer, 
     * java.lang.Integer)
     */
    @Override
    public List<SourceMarketDto> getMarketMappings(ConnectorDto connector,
            EventDto event, Integer offset, Integer maxResults)
    {
        assert connector != null;
        assert event != null;
        
        return executeQuery("from SourceMarket m where m.connector = ?1 and m.market.event = ?2", 
                offset, maxResults, connector, event);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getNumberOfSourceMarketsLastFetchedBefore(java.lang.Long, java.lang.Long, 
     * java.util.Date)
     */
    @Override
    public int getNumberOfSourceMarketsLastFetchedBefore(Long connectorId, 
            Long eventId, Date fetchTime)
    {
    	assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select count(sm.id) from SourceMarket sm");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sm.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (eventId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sm.sourceEvent.event.id = ?");
            query.append(index);
            index ++;
            params.add(eventId);
        }
        query.append((where ? " and" : " where"));
        query.append(" sm.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        Number count = executeQuery(query.toString(), Number.class, params.toArray());
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getSourceMarketsLastFetchedBefore(java.lang.Long, java.lang.Long, 
     * java.util.Date, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceMarketDto> getSourceMarketsLastFetchedBefore(
            Long connectorId, Long eventId, Date fetchTime,
            Integer offset, Integer maxResults)
    {
        assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from SourceMarket sm");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sm.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        if (eventId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" sm.sourceEvent.event.id = ?");
            query.append(index);
            index ++;
            params.add(eventId);
        }
        query.append((where ? " and" : " where"));
        query.append(" sm.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getNumberOfSourceMarketsWithFailedFetchAttempts(java.lang.Long, java.lang.Long, int)
     */
    @Override
    public int getNumberOfSourceMarketsWithFailedFetchAttempts(Long connectorId, 
            Long eventId, int attempts)
    {
        assert connectorId != null;
        assert eventId != null;
        
        return executeQuery("select count(sm.id) from SourceMarket sm where sm.connector.id = ?1 and sm.sourceEvent.event.id = ?2 and sm.failedFetchAttempts >= ?3", 
                Number.class, connectorId, eventId, attempts).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #getSourceMarketsWithFailedFetchAttempts(java.lang.Long, java.lang.Long, 
     * int, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceMarketDto> getSourceMarketsWithFailedFetchAttempts(
            Long connectorId, Long eventId, int attempts, Integer offset, 
            Integer maxResults)
    {
        assert connectorId != null;
        assert eventId != null;
        
        return executeQuery("from SourceMarket sm where sm.connector.id = ?1 and sm.sourceEvent.event.id = ?2 and sm.failedFetchAttempts >= ?3 order by sm.id", 
               offset, maxResults, connectorId, eventId, attempts);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #saveSourceMarket(com.aldogrand.sbpc.dataaccess.model.SourceMarketDto)
     */
    @Override
    public SourceMarketDto saveSourceMarket(SourceMarketDto sourceMarket)
    {
        return saveEntity(sourceMarket);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao
     * #deleteSourceMarket(com.aldogrand.sbpc.dataaccess.model.SourceMarketDto)
     */
    @Override
    public void deleteSourceMarket(SourceMarketDto sourceMarket)
    {
        removeEntity(sourceMarket);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceMarketDao#getMarketMapping(com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.String)
     */
    @Override
    public SourceMarketDto getMarketMappingFromId(ConnectorDto connector,
    		Long modelId)
    {
        assert connector != null;
        assert modelId != null;
        
        return executeQuery("from SourceMarket m where m.connector = ?1 and m.market.id = ?2", 
                connector, modelId);
    }
    
    /*
	 * (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.MarketDao#areAllEventMarketsInStatuses(long, com.aldogrand.sbpc.model.MarketStatus)
	 */
	@Override
	public boolean allSourceEventMarketsInStatus(long sourceEventId, MarketStatus status) {
		StringBuilder queryString = new StringBuilder("from SourceMarket sm where sm.sourceEvent.id = ? and status not in (?) or status is null");
		List<SourceMarketDto> markets = executeQuery(queryString.toString(), 0, 1, sourceEventId, status.toString());
		
		return CollectionUtils.isEmpty(markets) ? true : false;
	}


	@Override
	public SourceMarketDto getSourceMarket(String sourceId) {
		assert sourceId != null;
		return executeQuery("from SourceMarket sm where source_id = ?",sourceId);
		
	}

	@Override
	public List<SourceMarketDto> getNonClosedSourceMarkets(Long connectorId,
			String sourceEventId) {
		assert connectorId != null;
		assert sourceEventId != null;
		
		return executeQuery("from SourceMarket sm where sm.connector.id = ? and status != 'CLOSED' and sm.sourceEvent.id in (select id from SourceEvent se where se.sourceId = ?)",(Integer)null, (Integer)null, connectorId,sourceEventId );
	}  

}
