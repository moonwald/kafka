package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.dataaccess.EventDao;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> EventDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> A JPA implementation of {@link EventDao}. 
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
public class EventDaoJpaImpl extends GenericJpaDao<EventDto> implements EventDao
{
	/**
	 * Create a new {@link EventDaoJpaImpl}.
	 */
	public EventDaoJpaImpl()
	{
		super(EventDto.class);
	}

	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.EventDao#getEvent(long)
	 */
    @Override
    public EventDto getEvent(long id) 
    {
        return getEntity(id);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.EventDto)
     */
    @Override
    public void lock(EventDto event)
    {
        writeLock(event);
    }

	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.EventDao#getNumberOfEvents()
	 */
	@Override
	public int getNumberOfEvents()
	{
		Number count = executeQuery("select count(e.id) from Event e", Number.class);
		return count.intValue();
	}

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getEvents(java.lang.String, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<EventDto> getCandidateEventsForMapping(List<String> names, Integer offset,
            Integer maxResults)
    {
        assert names != null;
        assert !names.isEmpty();
        
        return executeQuery("select distinct e from Event e join e.nameVariants v where e.name in (?1) or v.variant in (?1)", 
                offset, maxResults, names);
    }

    
	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.EventDao#getEvents(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<EventDto> getEvents(Integer offset, Integer maxResults)
	{
		return executeQuery("from Event e order by e.name", offset, maxResults);
	}

	/* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getEventsWithMappings(
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<EventDto> getEventsWithMappings(Integer offset,
            Integer maxResults)
    {
        return executeQuery("select distinct e from Event e left join fetch e.mappings order by e.name", 
                offset, maxResults);
    }
    
	/* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getEventsWithoutMappings(
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<EventDto> getEventsWithoutMappings(Integer offset,
            Integer maxResults)
    {
        return executeQuery("select distinct e from Event e left join fetch e.mappings m where m.id = null", 
                offset, maxResults);
    }
    
	/* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.EventDao#getNumberOfEvents()
	 */
	@Override
	public int getNumberOfEventsWithoutMappings()
	{
		Number count = executeQuery("select count(distinct e.id) from Event e left join e.mappings m where m.id = null", Number.class);
		return count.intValue();
	}

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getEvents(long, java.util.List)
     */
    @Override
    public List<EventDto> getEvents(long connectorId)
    {
        
        return executeQuery("select se.event from SourceEvent se where se.connector.id = ?1 order by se.event.name",
                0, null, connectorId);
    }
    
	/* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getEvents(long, java.util.List)
     */
    @Override
    public List<EventDto> getEvents(long connectorId, List<Long> ids)
    {
        assert ids != null;
        
        return executeQuery("select se.event from SourceEvent se where se.connector.id = ?1 and se.event.id in (?2) order by se.event.name",
                0, null, connectorId, ids);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getNumberOfEvents(java.util.List, java.util.Date)
     */
    @Override
    public int getNumberOfEvents(List<Long> connectorIds, Date modifiedSince)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append("select count(se.id) from SourceEvent se where se.connector.id in (?1)");
            params.add(connectorIds);
            if (modifiedSince != null)
            {
                query.append(" and se.event.lastChangeTime >= ?2");
                params.add(modifiedSince);
            }
            query.append(" order by se.event.name");
        }
        else
        {
            query.append("select count(e.id) from Event e");
            if (modifiedSince != null)
            {
                query.append(" and e.lastChangeTime >= ?1");
                params.add(modifiedSince);
            }
            query.append(" order by e.name");
        }

        Number count = executeQuery(query.toString(), Number.class, params.toArray());
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getEvents(java.util.List, 
     * java.util.Date, java.util.List, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<EventDto> getEvents(List<Long> connectorIds, Date modifiedSince, 
            List<Long> metaTagIds, String searchQuery, List<Long> eventIds, Integer offset, Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        if ((connectorIds != null) && (!connectorIds.isEmpty()))
        {
            query.append("select distinct se.event from SourceEvent se");
            boolean where = false;
            int index = 1;
            if ((metaTagIds != null) && (!metaTagIds.isEmpty()))
            {
                query.append(" join se.event.metaTags t where t.id in (?");
                query.append(index);
                query.append(")");
                params.add(metaTagIds);
                where = true;
                index ++;
            }

            query.append(where ? " and" : " where");
            query.append(" se.connector.id in (?");
            query.append(index);
            query.append(")");
            params.add(connectorIds);
            where = true;
            index ++;
            
            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" se.event.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if (searchQuery != null)
            {
                query.append(where ? " and" : " where");
                query.append(" se.event.name like ?");
                query.append(index);
                params.add("%" + searchQuery + "%");
                where = true;
                index ++;
            }
            if (eventIds != null)
            {
            	if(!eventIds.isEmpty()){
            		query.append(where ? " and" : " where");
                    query.append(" se.event.id in (?");
                    query.append(index);
                    query.append(")");
                    params.add(eventIds);
                    where = true;
                    index ++;
            	}
            	else{
            		return null;
            	}
                
            }
            query.append(" order by se.event.name");
        }
        else
        {
            query.append("select distinct e from Event e");
            boolean where = false;
            int index = 1;
            if ((metaTagIds != null) && (!metaTagIds.isEmpty()))
            {
                query.append(" join e.metaTags t where t.id in (?");
                query.append(index);
                query.append(")");
                params.add(metaTagIds);
                where = true;
                index ++;
            }
            if (modifiedSince != null)
            {
                query.append(where ? " and" : " where");
                query.append(" e.lastChangeTime >= ?");
                query.append(index);
                params.add(modifiedSince);
                where = true;
                index ++;
            }
            if (searchQuery != null)
            {
                query.append(where ? " and" : " where");
                query.append(" e.name like ?");
                query.append(index);
                params.add("%" + searchQuery + "%");
                where = true;
                index ++;
            }
            if ((eventIds != null))
            {
            	if(!eventIds.isEmpty()){
            		 query.append(where ? " and" : " where");
                     query.append(" e.id in (?");
                     query.append(index);
                     query.append(")");
                     params.add(eventIds);
                     where = true;
                     index ++;
            	}
            	else{
            		return null;
            	}
               
            }
            query.append(" order by e.name");
        }

        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }
    
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.MarketDao
     * #getMarketsWithInterestedClient(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<EventDto> getInterestedEvents(Integer offset, Integer maxResults)
    {
    	StringBuilder queryStr = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        
        queryStr.append("select distinct e from Event e join e.markets m join m.interestedClients ic");
        
        //queryStr.append(" order by e.id asc");
        
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
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getMappedEvents(long, 
     * java.util.List)
     */
    @Override
    public List<EventDto> getMappedEvents(long connectorId,
            List<String> sourceIds)
    {
        assert sourceIds != null;
        
        return executeQuery("select se.event from SourceEvent se where se.connector.id = ?1 and se.sourceId in (?2)", 
                0, null, connectorId, sourceIds);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#getNumberOfEvents(java.lang.String)
     */
    @Override
    public int getNumberOfEvents(String name)
    {
        assert name != null;
        
        Number count = executeQuery("select count(distinct e.id) from Event e join e.nameVariants v where e.name = ?1 or v.variant = ?1", 
                Number.class, name);
        return count.intValue();
    }



    /* (non-Javadoc)
	 * @see com.aldogrand.sbpc.dataaccess.EventDao#saveEvent(
	 * com.aldogrand.sbpc.dataaccess.model.EventDto)
	 */
	@Override
	@Transactional
	public EventDto saveEvent(EventDto event)
	{
		return saveEntity(event);
	}

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#deleteEvent(
     * com.aldogrand.sbpc.dataaccess.model.EventDto)
     */
    @Override
    @Transactional
    public void deleteEvent(EventDto event)
    {
        assert event != null;
        
        removeEntity(event);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.EventDao#deleteEvents(java.util.List)
     */
    @Override
    @Transactional
    public void deleteEvents(List<EventDto> events)
    {
        assert events != null;
        
        removeEntities(events);
    }
}
