package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.dataaccess.MarketDao;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.Period;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> MarketDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link MarketDao}.
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
public class MarketDaoJpaImpl extends GenericJpaDao<MarketDto> implements MarketDao
{
    /**
     * Create a new {@link MarketDaoJpaImpl}.
     */
    public MarketDaoJpaImpl()
    {
        super(MarketDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#getMarket(long)
     */
    @Override
    public MarketDto getMarket(long id) 
    {
        return getEntity(id);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.MarketDto)
     */
    @Override
    public void lock(MarketDto market)
    {
        writeLock(market);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#getNumberOfMarkets()
     */
    @Override
    public int getNumberOfMarkets()
    {
        Number count = executeQuery("select count(m.id) from Market m", Number.class);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#getMarkets(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<MarketDto> getMarkets(Integer offset, Integer maxResults)
    {
        return executeQuery("from Market m order by m.name", offset, maxResults);
    }

	/* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#getMarkets(long, java.util.List)
     */
    @Override
    public List<MarketDto> getMarkets(long connectorId, List<Long> ids)
    {
        assert ids != null;

        return executeQuery("select sm.market from SourceMarket sm where sm.connector.id = ?1 and sm.market.id in (?2) order by sm.market.name", 
                0, null, connectorId, ids);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#getNumberOfMarkets(
     * java.util.List, java.util.Date, java.util.List, java.util.List, java.util.List)
     */
    @Override
    public int getNumberOfMarkets(List<Long> connectorIds, Date modifiedSince,
            List<MarketType> types, List<Period> periods, List<Long> eventIds)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append("select count(sm.market) from SourceMarket sm");
            boolean where = false;
            int index = 1;
            query.append(where ? " and" : " where");
            query.append(" sm.connector.id in (?");
            query.append(index);
            query.append(")");
            params.add(connectorIds);
            where = true;
            index ++;

            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if ((types != null) && (!types.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.type in (?");
                query.append(index);
                query.append(")");
                params.add(types);
                where = true;
                index ++;
            }
            if ((periods != null) && (!periods.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.period in (?");
                query.append(index);
                query.append(")");
                params.add(periods);
                where = true;
                index ++;
            }
            if ((eventIds != null) && (!eventIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.event.id in (?");
                query.append(index);
                query.append(")");
                params.add(eventIds);
                where = true;
                index ++;
            }
            query.append(" order by sm.market.name");
        }
        else
        {
            query.append("select count(m.id) from Market m");
            boolean where = false;
            int index = 1;
            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" m.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if ((types != null) && (!types.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" m.type in (?");
                query.append(index);
                query.append(")");
                params.add(types);
                where = true;
                index ++;
            }
            if ((periods != null) && (!periods.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" m.period in (?");
                query.append(index);
                query.append(")");
                params.add(periods);
                where = true;
                index ++;
            }
            if ((eventIds != null) && (!eventIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" m.event.id in (?");
                query.append(index);
                query.append(")");
                params.add(eventIds);
                where = true;
                index ++;
            }
            query.append(" order by m.name");
        }

        Number count = executeQuery(query.toString(), Number.class, params.toArray());
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#getMarkets(java.util.List, 
     * java.util.Date, java.util.List, java.util.List, java.util.List, java.lang.Integer, 
     * java.lang.Integer)
     */
    @Override
    public List<MarketDto> getMarkets(List<Long> connectorIds,
            Date modifiedSince, List<MarketType> types, List<Period> periods,
            List<Long> eventIds, Integer offset, Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append("select sm.market from SourceMarket sm");
            boolean where = false;
            int index = 1;
            query.append(where ? " and" : " where");
            query.append(" sm.connector.id in (?");
            query.append(index);
            query.append(")");
            params.add(connectorIds);
            where = true;
            index ++;

            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if ((types != null) && (!types.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.type in (?");
                query.append(index);
                query.append(")");
                params.add(types);
                where = true;
                index ++;
            }
            if ((periods != null) && (!periods.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.period in (?");
                query.append(index);
                query.append(")");
                params.add(periods);
                where = true;
                index ++;
            }
            if ((eventIds != null) && (!eventIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" sm.market.event.id in (?");
                query.append(index);
                query.append(")");
                params.add(eventIds);
                where = true;
                index ++;
            }
            query.append(" order by sm.market.name");
        }
        else
        {
            query.append("from Market m");
            boolean where = false;
            int index = 1;
            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" m.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if ((types != null) && (!types.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" m.type in (?");
                query.append(index);
                query.append(")");
                params.add(types);
                where = true;
                index ++;
            }
            if ((periods != null) && (!periods.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" m.period in (?");
                query.append(index);
                query.append(")");
                params.add(periods);
                where = true;
                index ++;
            }
            if ((eventIds != null) && (!eventIds.isEmpty()))
            {
                query.append(where ? " and" : " where");
                query.append(" m.event.id in (?");
                query.append(index);
                query.append(")");
                params.add(eventIds);
                where = true;
                index ++;
            }
            query.append(" order by m.name");
        }

        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#getMappedMarkets(long, 
     * java.util.List)
     */
    @Override
    public List<MarketDto> getMappedMarkets(long connectorId,
            List<String> sourceIds)
    {
        assert sourceIds != null;
        
        return executeQuery("select sm.market from SourceMarket sm where sm.connector.id = ?1 and sm.sourceId in (?2)", 
                0, null, connectorId, sourceIds);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao
     * #getNumberOfMarketsWithInterestedClientsForEventsStartingBetween(
     * java.util.Date, java.util.Date)
     */
    @Override
    public int getNumberOfMarketsWithInterestedClientsForEventsStartingBetween(Date start, Date end)
    {
        StringBuilder queryStr = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        queryStr.append("select count(distinct m) from Market m join m.interestedClients");
        boolean where = false; // Has where clause already been added
        int i = 1;
        if (start != null)
        {
            queryStr.append(where ? " and" : " where");
            where = true;
            queryStr.append(" m.event.startTime >= ?");
            queryStr.append(i);
            params.add(start);
            i ++;
        }
        if (end != null)
        {
            queryStr.append(where ? " and" : " where");
            where = true;
            queryStr.append(" m.event.startTime <= ?");
            queryStr.append(i);
            params.add(end);
            i ++;
        }
        
        if (params.isEmpty())
        {
            return executeQuery(queryStr.toString(), Number.class).intValue();
        }
        else
        {
            return executeQuery(queryStr.toString(), Number.class, 
                    params.toArray(new Object[params.size()])).intValue();
        }
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao
     * #getMarketsWithInterestedClientsForEventsStartingBetweenWithMappings(
     * java.util.Date, java.util.Date, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<MarketDto> getMarketsWithInterestedClientsForEventsStartingBetweenWithMappings(
            Date start, Date end, Integer offset, Integer maxResults)
    {
        StringBuilder queryStr = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        queryStr.append("select distinct m from Market m left join fetch m.mappings join m.interestedClients");
        boolean where = false; // Has where clause already been added
        int i = 1;
        if (start != null)
        {
            queryStr.append(where ? " and" : " where");
            where = true;
            queryStr.append(" m.event.startTime >= ?");
            queryStr.append(i);
            params.add(start);
            i ++;
        }
        if (end != null)
        {
            queryStr.append(where ? " and" : " where");
            where = true;
            queryStr.append(" m.event.startTime <= ?");
            queryStr.append(i);
            params.add(end);
            i ++;
        }
        queryStr.append(" order by m.id asc");
        
        if (params.isEmpty())
        {
            return executeQuery(queryStr.toString(), offset, maxResults);
        }
        else
        {
            return executeQuery(queryStr.toString(), offset, maxResults, 
                    params.toArray(new Object[params.size()]));
        }
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao
     * #getNumberOfMarketsWithInterestedClient(java.lang.String)
     */
    @Override
    public int getNumberOfMarketsWithInterestedClient(String clientToken)
    {
        assert clientToken != null;
        
        return executeQuery("select count(distinct m.id) from Market m join m.interestedClients ic where ic.clientToken = ?1", 
                Number.class, clientToken).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao
     * #getMarketsWithInterestedClient(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<MarketDto> getMarketsWithInterestedClient(String clientToken,
            Integer offset, Integer maxResults)
    {
    	StringBuilder queryStr = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        queryStr.append("select distinct m from Market m join m.interestedClients ic");
        boolean where = false; // Has where clause already been added
        int i = 1;
        if (clientToken != null)
        {
            queryStr.append(where ? " and" : " where");
            where = true;
            queryStr.append(" ic.clientToken = ?");
            queryStr.append(i);
            params.add(clientToken);
            i ++;
        }
        queryStr.append(" order by m.id asc");
        
        if (params.isEmpty())
        {
            return executeQuery(queryStr.toString(), offset, maxResults);
        }
        else
        {
            return executeQuery(queryStr.toString(), offset, maxResults, 
                    params.toArray(new Object[params.size()]));
        }
    }

    

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao
     * #getMarketsWithInterestedClient(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<MarketDto> getInterestedMarkets()
    {
        return executeQuery("select m from Market m join m.interestedClients ic", 
                0, null);
    }
    

    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#saveMarket(
     * com.aldogrand.sbpc.dataaccess.model.MarketDto)
     */
    @Override
    public MarketDto saveMarket(MarketDto market)
    {
        assert market != null;
        
        return saveEntity(market);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao#deleteMarket(
     * com.aldogrand.sbpc.dataaccess.model.MarketDto)
     */
	@Override
	public void deleteMarket(MarketDto market)
	{
	    assert market != null;
	    
		removeEntity(market);
	}
}
