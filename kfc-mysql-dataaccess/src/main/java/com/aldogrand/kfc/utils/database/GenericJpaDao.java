package com.aldogrand.kfc.utils.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title</b> GenericJpaDao
 * </p>
 * <p>
 * <b>Description</b> A Data Access Object (DAO) that can be used generically to
 * persist and retrieve any model entity using JPA.
 * </p>
 * <p>
 * This can be used directly as a generic DAO or it can be extended and used as 
 * the base implementation for a domain / entity specific DAO. 
 * </p>
 * <p>
 * When used as part of a container managed  (Application Server) or Spring managed
 * JPA environment the environment's default {@link EntityManager} is automatically
 * injected.
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
public class GenericJpaDao<E>
{
    protected final Logger logger = LogManager.getLogger(getClass());
    
    private final Class<E> type;
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    /**
     * Create a new {@link GenericJpaDao} that persists and retrieves entities
     * of the given type.
     * @param type
     */
    public GenericJpaDao(Class<E> type)
    {
        assert type != null;
        
        this.type = type;
    }
    
    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    /**
     * @param entityManager the entityManager to set
     */
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    /**
     * Get an entity with the primary key id.
     * @param id
     * @return
     */
    public E getEntity(Long id)
    {
        assert id != null;
        
        logger.debug("Getting " + type.getSimpleName() + " with id " + id);
        
        return entityManager.find(type, id);
    }

    /**
     * Get an entity with the primary key id.
     * @param id
     * @param lock If true lock the entity for editing.
     * @return
     */
    public E getEntity(Long id, boolean lock)
    {
        assert id != null;
        
        logger.debug("Getting " + type.getSimpleName() + " with id " + id);
        
        if (lock)
        {
            return entityManager.find(type, id, LockModeType.PESSIMISTIC_WRITE);
        }
        else
        {
            return entityManager.find(type, id);
        }
    }
    
    
    /**
     * Get an entity with the primary key id.
     * 
     * @param id
     * @param lockType type of lock to use if locking the object
     * @return
     */
    public E getEntity(Long id, LockModeType lockType)
    {
        assert id != null;
        
        logger.debug("Getting " + type.getSimpleName() + " with id " + id);
                
        if (lockType != null)
        {
            return entityManager.find(type, id, lockType);
        }
        else
        {
            return entityManager.find(type, id);
        }
    }

    /**
     * Execute a named query passing in the optional parameters and return a single value.
     * @param queryName
     * @param parameters
     * @return
     */
    public E executeNamedQuery(String queryName, Object... parameters)
    {
        assert queryName != null;
        
        logger.debug("Executing named query " + queryName);

        final TypedQuery<E> query = entityManager.createNamedQuery(queryName, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute a named query passing in the optional parameters and return a single value.
     * @param queryName
     * @param lock If true lock the returned value for update.
     * @param parameters
     * @return
     */
    public E executeNamedQuery(String queryName, boolean lock, Object... parameters)
    {
        assert queryName != null;
        
        logger.debug("Executing named query " + queryName);

        final TypedQuery<E> query = entityManager.createNamedQuery(queryName, type);
        if (lock)
        {
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute a named query passing in the return type, optional parameters and return a single value.
     * @param queryName
     * @param type
     * @param parameters
     * @return
     */
    public <T> T executeNamedQuery(String queryName, Class<T> type,
            Object... parameters)
    {
        assert queryName != null;
        assert type != null;
        logger.debug("Executing named query " + queryName);

        final TypedQuery<T> query = entityManager.createNamedQuery(queryName, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute a named query passing in the return type, optional parameters and return a single value.
     * @param queryName
     * @param type
     * @param lock If true lock the returned value for update.
     * @param parameters
     * @return
     */
    public <T> T executeNamedQuery(String queryName, Class<T> type, boolean lock,
            Object... parameters)
    {
        assert queryName != null;
        assert type != null;
        
        logger.debug("Executing named query " + queryName);

        final TypedQuery<T> query = entityManager.createNamedQuery(queryName, type);
        if (lock)
        {
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute a named query passing in the optional parameters and return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param queryName
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public List<E> executeNamedQuery(String queryName, Integer offset,
            Integer maxResults, Object... parameters)
    {
        assert queryName != null;
        
        logger.debug("Executing named query " + queryName);

        final TypedQuery<E> query = entityManager.createNamedQuery(queryName, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            query.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /**
     * Execute a named query passing in the optional parameters and return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param queryName
     * @param lock If true lock the returned values for update.
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public List<E> executeNamedQuery(String queryName, boolean lock, Integer offset,
            Integer maxResults, Object... parameters)
    {
        assert queryName != null;
        
        logger.debug("Executing named query " + queryName);

        final TypedQuery<E> query = entityManager.createNamedQuery(queryName, type);
        if (lock)
        {
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            query.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }
    
    /**
     * Execute a named query passing in the return type, optional parameters and 
     * return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param queryName
     * @param type
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public <T> List<T> executeNamedQuery(String queryName, Class<T> type,
            Integer offset, Integer maxResults, Object... parameters)
    {
        assert queryName != null;
        assert type != null;
        
        logger.debug("Executing named query " + queryName);

        final TypedQuery<T> query = entityManager.createNamedQuery(queryName, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            query.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }
    
    /**
     * Execute a named query passing in the return type, optional parameters and 
     * return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param queryName
     * @param type
     * @param lock If true lock the returned values for update.
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public <T> List<T> executeNamedQuery(String queryName, Class<T> type, boolean lock,
            Integer offset, Integer maxResults, Object... parameters)
    {
        assert queryName != null;
        assert type != null;
        
        logger.debug("Executing named query " + queryName);

        final TypedQuery<T> query = entityManager.createNamedQuery(queryName, type);
        if (lock)
        {
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            query.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /**
     * Execute the given named query that does not return any results passing the 
     * optional parameters.
     * @param queryName
     * @param parameters
     */
    public void executeNamedUpdateQuery(String queryName, Object... parameters)
    {
        assert queryName != null;
        
        logger.debug("Executing named query " + queryName);
        
        Query query = entityManager.createNamedQuery(queryName);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                query.setParameter(i + 1, parameters[i]);
            }
        }
        query.executeUpdate();
    }

    /**
     * Execute the given query passing in the optional parameters and return a single value.
     * @param query
     * @param parameters
     * @return
     */
    public E executeQuery(String query, Object... parameters)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);
        
        final TypedQuery<E> jpaQuery = entityManager.createQuery(query, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
    
    /**
     * Execute the given query passing in the optional parameters and return a single value.
     * @param query
     * @param lock If true lock the returned value for update.
     * @param parameters
     * @return
     */
    public E executeQuery(String query, boolean lock, Object... parameters)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);
        
        final TypedQuery<E> jpaQuery = entityManager.createQuery(query, type);
        if (lock)
        {
            jpaQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute the given query passing in the optional parameters and return a single value.<br/>
     * Cache the query result in the query cache
     * @param query
     * @param parameters
     * @return
     */
    public E executeCachedQuery(String query, Object... parameters)
    {
        assert query != null;
        
        logger.debug("Executing cached query " + query);
        
        final TypedQuery<E> jpaQuery = entityManager.createQuery(query, type);
        jpaQuery.setHint("org.hibernate.cacheable", Boolean.TRUE); // cache the query result
        
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute the given query passing in the return type, optional parameters and return a single value.
     * @param query
     * @param type
     * @param parameters
     * @return
     */
    public <T> T executeQuery(String query, Class<T> type, Object... parameters)
    {
        assert query != null;
        assert type != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute the given query passing in the return type, optional parameters and return a single value.
     * @param query
     * @param type
     * @param lock If true lock the returned value for update.
     * @param parameters
     * @return
     */
    public <T> T executeQuery(String query, Class<T> type, boolean lock, Object... parameters)
    {
        assert query != null;
        assert type != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query, type);
        if (lock)
        {
            jpaQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
    
    /**
     * Execute the given query passing in the return type, optional parameters and return a single value.<br/>
     * Cache the query result in the query cache.
     * @param query
     * @param type
     * @param parameters
     * @return
     */
    public <T> T executeCachedQuery(String query, Class<T> type, 
            Object... parameters)
    {
        assert query != null;
        assert type != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query, type);
        jpaQuery.setHint("org.hibernate.cacheable", Boolean.TRUE); // cache the query result
        
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute the given {@link CriteriaQuery} and return a single value.
     * @param query
     * @return
     */
    public <T> T executeQuery(CriteriaQuery<T> query)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query);
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute the given {@link CriteriaQuery} and return a single value.
     * @param query
     * @param lock If true lock the returned value for update.
     * @return
     */
    public <T> T executeQuery(CriteriaQuery<T> query, boolean lock)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query);
        if (lock)
        {
            jpaQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }
    
    /**
     * Execute the given {@link CriteriaQuery} and return a single value.<br/>
     * Cache the query result in the query cache.
     * @param query
     * @return
     */
    public <T> T executeCachedQuery(CriteriaQuery<T> query)
    {
        assert query != null;
        
        logger.debug("Executing cached query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query);
        jpaQuery.setHint("org.hibernate.cacheable", Boolean.TRUE); // cache the query result
        
        try
        {
            return jpaQuery.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    /**
     * Execute the given query passing in the optional parameters and return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public List<E> executeQuery(String query, Integer offset,
            Integer maxResults, Object... parameters)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<E> jpaQuery = entityManager.createQuery(query, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }

    /**
     * Execute the given query passing in the optional parameters and return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param lock If true lock the returned values for update.
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public List<E> executeQuery(String query, boolean lock, Integer offset,
            Integer maxResults, Object... parameters)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<E> jpaQuery = entityManager.createQuery(query, type);
        if (lock)
        {
            jpaQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }
    
    /**
     * Execute the given query passing in the optional parameters and return a list of results.<br/>
     * Cache the query result in the query cache.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public List<E> executeCachedQuery(String query, Integer offset, 
            Integer maxResults, Object... parameters)
    {
        assert query != null;
        
        logger.debug("Executing cached query " + query);

        final TypedQuery<E> jpaQuery = entityManager.createQuery(query, type);
        jpaQuery.setHint("org.hibernate.cacheable", Boolean.TRUE); // cache the query result
        
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }

    /**
     * Execute the given query passing in the return type, optional parameters and 
     * return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param type
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public <T> List<T> executeQuery(String query, Class<T> type,
            Integer offset, Integer maxResults, Object... parameters)
    {
        assert query != null;
        assert type != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query, type);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }

    /**
     * Execute the given query passing in the return type, optional parameters and 
     * return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param type
     * @param lock If true lock the returned values for update.
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public <T> List<T> executeQuery(String query, Class<T> type, boolean lock,
            Integer offset, Integer maxResults, Object... parameters)
    {
        assert query != null;
        assert type != null;
        
        logger.debug("Executing query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query, type);
        if (lock)
        {
            jpaQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }
    
    /**
     * Execute the given query passing in the return type, optional parameters and 
     * return a list of results.<br/>
     * Cache the query result in the query cache.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param type
     * @param offset
     * @param maxResults
     * @param parameters
     * @return
     */
    public <T> List<T> executeCachedQuery(String query, Class<T> type,
            Integer offset, Integer maxResults, Object... parameters)
    {
        assert query != null;
        assert type != null;
        
        logger.debug("Executing cached query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query, type);
        jpaQuery.setHint("org.hibernate.cacheable", Boolean.TRUE); // cache the query result
        
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }

    /**
     * Execute the given {@link CriteriaQuery} and return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param offset
     * @param maxResults
     * @return
     */
    public <T> List<T> executeQuery(CriteriaQuery<T> query, Integer offset, 
            Integer maxResults)
    {
        assert query != null;
        
        logger.debug("Executing criteria query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query);
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }

    /**
     * Execute the given {@link CriteriaQuery} and return a list of results.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param lock If true lock the returned values for update.
     * @param offset
     * @param maxResults
     * @return
     */
    public <T> List<T> executeQuery(CriteriaQuery<T> query, boolean lock, Integer offset, 
            Integer maxResults)
    {
        assert query != null;
        
        logger.debug("Executing criteria query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query);
        if (lock)
        {
            jpaQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        }
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }
    
    /**
     * Execute the given {@link CriteriaQuery} and return a list of results.<br/>
     * Cache the query result in the query cache.<br/>
     * The offset and maxResults can be used to optionally limit the number of results.
     * @param query
     * @param offset
     * @param maxResults
     * @return
     */
    public <T> List<T> executeCachedQuery(CriteriaQuery<T> query,
            Integer offset, Integer maxResults)
    {
        assert query != null;
        
        logger.debug("Executing cached criteria query " + query);

        final TypedQuery<T> jpaQuery = entityManager.createQuery(query);
        jpaQuery.setHint("org.hibernate.cacheable", Boolean.TRUE); // cache the query result
        
        if ((offset != null) && (offset != 0))
        {
            jpaQuery.setFirstResult(offset);
        }
        if (maxResults != null)
        {
            jpaQuery.setMaxResults(maxResults);
        }
        return jpaQuery.getResultList();
    }

    /**
     * Execute the given query that does not return any results passing the 
     * optional parameters.
     * @param query
     * @param parameters
     */
    public void executeUpdateQuery(String query, Object... parameters)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);
        
        final Query jpaQuery = entityManager.createQuery(query);
        if (parameters != null)
        {
            for (int i = 0 ; i < parameters.length ; i ++)
            {
                jpaQuery.setParameter(i + 1, parameters[i]);
            }
        }
        jpaQuery.executeUpdate();
    }

    /**
     * Execute the given {@link CriteriaQuery} that does not return any results.
     * @param query
     */
    public void executeUpdateQuery(CriteriaQuery<?> query)
    {
        assert query != null;
        
        logger.debug("Executing query " + query);
        
        final Query jpaQuery = entityManager.createQuery(query);
        jpaQuery.executeUpdate();
    }

    /**
     * Create a read lock on the entity and refresh its state.
     * @param entity
     */
    public void readLock(E entity)
    {
        entityManager.refresh(entity, LockModeType.PESSIMISTIC_READ);
    }
    
    /**
     * Create a write lock on the entity and refresh its state.
     * @param entity
     */
    public void writeLock(E entity)
    {
        entityManager.refresh(entity, LockModeType.PESSIMISTIC_WRITE);
    }
    
    /**
     * Save or update the entity returning the updated version.
     * @param entity
     * @return
     */
    public E saveEntity(E entity)
    {
        assert entity != null;
        
        logger.debug("Saving " + entity);
        
        return entityManager.merge(entity);
    }

    /**
     * Save a list of entities returning the list of updated versions.
     * @param entities
     * @return
     */
    public List<E> saveEntities(List<E> entities)
    {
        assert entities != null;
        
        logger.debug("Saving entities");
        
        final List<E> merged = new ArrayList<E>();
        for (E entity : entities)
        {
            merged.add(entityManager.merge(entity));
        }
        return merged;
    }

    /**
     * Remove the entity.
     * @param entity
     */
    public void removeEntity(E entity)
    {
        assert entity != null;
        
        logger.debug("Removing entity " + entity);

        entityManager.remove(entity);
    }

    /**
     * Remove the entities.
     * @param entities
     */
    public void removeEntities(List<E> entities)
    {
        assert entities != null;
        
        logger.debug("Removing entities");

        for (E entity : entities)
        {
            entityManager.remove(entity);
        }
    }
}
