package com.aldogrand.sbpc.dataaccess.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.dataaccess.SourceEventDao;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.SourceEventDto;
import com.aldogrand.kfc.utils.database.GenericJpaDao;

/**
 * <p>
 * <b>Title</b> SourceEventDaoJpaImpl
 * </p>
 * <p>
 * <b>Description</b> JPA implementation of {@link SourceEventDao}.
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
@Transactional
public class SourceEventDaoJpaImpl extends GenericJpaDao<SourceEventDto>
        implements SourceEventDao
{
    public SourceEventDaoJpaImpl()
    {
        super(SourceEventDto.class);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getSourceEvent(java.lang.Long, boolean)
     */
    @Override
    public SourceEventDto getSourceEvent(Long id, boolean lock)
    {
        if (lock)
        {
            return getEntityManager().find(SourceEventDto.class, id,    
                    LockModeType.PESSIMISTIC_WRITE);
        }
        else
        {
            return getEntity(id);
        }
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getSourceEvent(java.lang.Long, java.lang.String)
     */
    @Override
    public SourceEventDto getSourceEvent(Long connectorId, String sourceId)
    {
        assert connectorId != null;
        assert sourceId != null;
        
        return executeQuery("from SourceEvent se where se.connector.id = ?1 and se.sourceId = ?2", 
                connectorId, sourceId);
    }


    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getSourceEvent(java.lang.Long, java.lang.String)
     */
    @Override
    public List<SourceEventDto> getSourceEvents(Long connectorId, String sourceId)
    {
        assert connectorId != null;
        assert sourceId != null;
        
        return executeQuery("from SourceEvent se where se.connector.id = ?1 and se.sourceId = ?2", (Integer)null, (Integer)null, connectorId, sourceId);
    }
    
    
    
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #lock(com.aldogrand.sbpc.dataaccess.model.SourceEventDto)
     */
    @Override
    public void lock(SourceEventDto sourceEvent)
    {
        writeLock(sourceEvent);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getNumberOfUnmappedSourceEvents()
     */
    @Override
    public int getNumberOfUnmappedSourceEvents()
    {
        return executeQuery("select count(se.id) from SourceEvent se where se.event is null", 
                Number.class).intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getUnmappedSourceEvents(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceEventDto> getUnmappedSourceEvents(Integer offset,
            Integer maxResults)
    {
        return executeQuery("from SourceEvent se where se.event is null order by se.id", 
                offset, maxResults);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getUnmappedSourceEvents(java.lang.Long, java.util.List, java.lang.String, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceEventDto> getUnmappedSourceEvents(Long connectorId,
            List<Long> metaTagIds, String searchQuery, Integer offset, Integer maxResults)
    {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        int paramCount = 1;
        
        query.append("select se from SourceEvent se");
        if ((metaTagIds != null) && (!metaTagIds.isEmpty()))
        {
            for (int i = 1 ; i < metaTagIds.size() ; i ++)
            {
                query.append(" join se.metaTags t");
                query.append(i);
            }
        }
        query.append(" where se.event is null");
        if (connectorId != null)
        {
            query.append(" and se.connector.id = ?");
            query.append(paramCount);
            paramCount ++;
            params.add(connectorId);
        }
        if ((metaTagIds != null) && (!metaTagIds.isEmpty()))
        {
            for (int i = 1 ; i < metaTagIds.size() ; i ++)
            {
                query.append(" and t");
                query.append(i);
                query.append(".id = ?");
                query.append(paramCount);
                paramCount ++;
                params.add(metaTagIds.get(i - 1));
            }
        }
        if (searchQuery != null)
        {
            query.append(" and se.sourceName like ?");
            query.append(paramCount);
            paramCount ++;
            params.add("%" + searchQuery + "%");
        }
        query.append(" order by se.sourceName");
        
        return executeQuery(query.toString(), offset, maxResults, 
                params.toArray(new Object[params.size()]));
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getEventMapping(
     * com.aldogrand.sbpc.dataaccess.model.EventDto, 
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public SourceEventDto getEventMapping(EventDto event,
            ConnectorDto connector)
    {
        assert event != null;
        assert connector != null;
        
        return executeQuery("from SourceEvent se where se.event = ?1 and se.connector = ?2", 
                event, connector);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getSourceEvent(java.lang.Long, java.lang.Long)
     */
    @Override
    public SourceEventDto getEventMapping(Long eventId, Long connectorId)
    {
        assert eventId != null;
        assert connectorId != null;
        
        return executeQuery("from SourceEvent se where se.event.id = ?1 and se.connector.id = ?2", 
                eventId, connectorId);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getNumberOfEventMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto)
     */
    @Override
    public int getNumberOfEventMappings(ConnectorDto connector)
    {
        assert connector != null;
        
        Number count = executeQuery("select count(m.id) from SourceEvent se where se.connector = ?1", 
                Number.class, connector);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getEventMappings(
     * com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceEventDto> getEventMappings(ConnectorDto connector,
            Integer offset, Integer maxResults)
    {
        assert connector != null;
        
        return executeQuery("from SourceEvent se where se.connector = ?1 order by se.id asc", 
                offset, maxResults, connector);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getNumberOfEventMappings(
     * com.aldogrand.sbpc.dataaccess.model.EventDto)
     */
    @Override
    public int getNumberOfEventMappings(EventDto event)
    {
        assert event != null;
        
        Number count = executeQuery("select count(se.id) from SourceEvent se where se.event = ?1", 
                Number.class, event);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getEventMappings(
     * com.aldogrand.sbpc.dataaccess.model.EventDto)
     */
    @Override
    public List<SourceEventDto> getEventMappings(EventDto event, Integer offset, Integer maxResults)
    {
        assert event != null;

        return executeQuery("from SourceEvent se where se.event = ?1 order by se.id", 
                offset, maxResults, event);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getNumberOfSourceEventsLastFetchedBefore(java.lang.Long, java.util.Date)
     */
    @Override
    public int getNumberOfSourceEventsLastFetchedBefore(Long connectorId, Date fetchTime)
    {
    	assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("select count(se.id) from SourceEvent se");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" se.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        query.append((where ? " and" : " where"));
        query.append(" se.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        Number count = executeQuery(query.toString(), Number.class, params.toArray());
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getSourceEventsLastFetchedBefore(java.lang.Long, java.util.Date, 
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<SourceEventDto> getSourceEventsLastFetchedBefore(
            Long connectorId, Date fetchTime, Integer offset,
            Integer maxResults)
    {
    	assert fetchTime != null;
        
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        boolean where = false;
        int index = 1;
        
        query.append("from SourceEvent se");
        if (connectorId != null)
        {
            query.append((where ? " and" : " where"));
            where = true;
            query.append(" se.connector.id = ?");
            query.append(index);
            index ++;
            params.add(connectorId);
        }
        query.append((where ? " and" : " where"));
        query.append(" se.lastFetchTime < ?");
        query.append(index);
        params.add(fetchTime);
        
        return executeQuery(query.toString(), offset, maxResults, params.toArray());
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getNumberOfSourceEventsWithFailedFetchAttempts(java.lang.Long, int)
     */
    @Override
    public int getNumberOfSourceEventsWithFailedFetchAttempts(
            Long connectorId, int attempts)
    {
        assert connectorId != null;
        
        Number count = executeQuery("select count(se.id) from SourceEvent se where se.connector.id = ?1 and se.failedFetchAttempts >= ?2", 
                Number.class, connectorId, attempts);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getSourceEventsWithFailedFetchAttempts(java.lang.Long, int, java.lang.Integer, 
     * java.lang.Integer)
     */
    @Override
    public List<SourceEventDto> getSourceEventsWithFailedFetchAttempts(
            Long connectorId, int attempts, Integer offset, Integer maxResults)
    {
        assert connectorId != null;
        
        return executeQuery("from SourceEvent se where se.connector.id = ?1 and se.failedFetchAttempts >= ?2 order by se.sourceName asc", 
                offset, maxResults, connectorId, attempts);
    }


    
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getEventMapping(com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.lang.String)
     */
    @Override
    public SourceEventDto getEventMappingFromId(ConnectorDto connector,
    		Long modelId)
    {
        assert connector != null;
        assert modelId != null;
        
        return executeQuery("from SourceEvent se where se.connector = ?1 and se.event.id = ?2", 
               connector, modelId);
    }
        
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao#getEventMappings(com.aldogrand.sbpc.dataaccess.model.ConnectorDto, java.util.List)
     */
    @Override
    public List<SourceEventDto> getEventMappings(ConnectorDto connector,
            List<String> connectorsEventIds)
    {
        assert connector != null;
        assert connectorsEventIds != null;
        
        return executeQuery("from SourceEvent se where se.connector = ?1 and se.sourceId in (?2)", 
                0, null, connector, connectorsEventIds);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #saveSourceEvent(com.aldogrand.sbpc.dataaccess.model.SourceEventDto)
     */
    @Override
    public SourceEventDto saveSourceEvent(SourceEventDto sourceEvent)
    {
        return saveEntity(sourceEvent);
    }

    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #deleteSourceEvent(com.aldogrand.sbpc.dataaccess.model.SourceEventDto)
     */
    @Override
    @Transactional
    public void deleteSourceEvent(SourceEventDto sourceEvent)
    {
        assert sourceEvent != null;
        
        removeEntity(sourceEvent);
    }
    
    /* (non-Javadoc)
     * @see com.aldogrand.sbpc.dataaccess.SourceEventDao
     * #getSourceEvents(java.lang.Long)
     */
	@Override
	public List<SourceEventDto> getNonClosedSourceEvents(Long connectorId) {
		assert connectorId != null;
        
        return executeQuery("from SourceEvent se where se.connector.id = ?1 and se.status != 'CLOSED'",(Integer)null, (Integer)null, connectorId );
	}
}
