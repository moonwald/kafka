package com.aldogrand.kfc.utils.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters.deMorganRewriter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;

/**
 * <p>
 * <b>Title</b> IsolationAwareHibernateJpaDialect
 * </p>
 * <p>
 * <b>Description</b> An extension of the {@link HibernateJpaDialect} that supports
 * the use of Isolation levels with Spring Transactions.
 * </p>
 * <p>
 * This should be configured in the Spring {@link LocalContainerEntityManagerFactoryBean}.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2013
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public class IsolationAwareHibernateJpaDialect extends HibernateJpaDialect
{
    /**
     * <p>
     * <b>Title</b> IsolationAwareSessionTransactionData
     * </p>
     * <p>
     * <b>Description</b> A container for data about a Transaction.
     * </p>
     * <p>
     * <b>Company</b> AldoGrand Consultancy Ltd
     * </p>
     * <p>
     * <b>Copyright</b> Copyright (c) 2013
     * </p>
     * @author Aldo Grand
     * @version 1.0
     */
    private static class IsolationAwareSessionTransactionData 
    {
        private final Object sessionTransactionData;
        private final Integer previousIsolationLevel;
        private final Connection connection;
       
        public IsolationAwareSessionTransactionData(Object sessionTransactionData, 
                Integer previousIsolationLevel , Connection connection) 
        {
            this.sessionTransactionData = sessionTransactionData;
            this.previousIsolationLevel = previousIsolationLevel;
            this.connection = connection;
        }

        /**
         * @return the sessionTransactionData
         */
        public Object getSessionTransactionData()
        {
            return sessionTransactionData;
        }

        /**
         * @return the previousIsolationLevel
         */
        public Integer getPreviousIsolationLevel()
        {
            return previousIsolationLevel;
        }

        /**
         * @return the connection
         */
        public Connection getConnection()
        {
            return connection;
        }                
    }
    
    private static final long serialVersionUID = -3406090186193945745L;

    private final Logger logger = LogManager.getLogger(getClass());
    
    /* (non-Javadoc)
     * @see org.springframework.orm.jpa.vendor.HibernateJpaDialect#beginTransaction(
     * javax.persistence.EntityManager, org.springframework.transaction.TransactionDefinition)
     */
    @Override
    public Object beginTransaction(final EntityManager entityManager,
            final TransactionDefinition definition) throws PersistenceException,
            SQLException, TransactionException
    {
        logger.debug("Beginning transaction.");
        
        Session session = getSession(entityManager);
        if (definition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) 
        {
            session.getTransaction().setTimeout(definition.getTimeout());
        }
       
        return session.doReturningWork(new ReturningWork<IsolationAwareSessionTransactionData>()
        {
            /* (non-Javadoc)
             * @see org.hibernate.jdbc.ReturningWork#execute(java.sql.Connection)
             */
            public IsolationAwareSessionTransactionData execute(Connection connection) throws SQLException
            {
                Integer previousIsolationLevel = null;
                if ((definition != null) 
                        && (definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT))
                {
                    Integer currentIsolation = connection.getTransactionIsolation();
                    if (currentIsolation != definition.getIsolationLevel()) 
                    {
                    	logger.debug("Changing isolation level from " + currentIsolation + " to " + definition.getIsolationLevel() +".");
                                                
                        previousIsolationLevel = currentIsolation;
                        connection.setTransactionIsolation(definition.getIsolationLevel());
                    }
                }
                
                entityManager.getTransaction().begin();
                Object transactionData = prepareTransaction(entityManager, 
                        definition.isReadOnly(), definition.getName());
                 
                return new IsolationAwareSessionTransactionData(transactionData, 
                        previousIsolationLevel, connection);
            }
        });
    }

    /* (non-Javadoc)
     * @see org.springframework.orm.jpa.vendor.HibernateJpaDialect
     * #cleanupTransaction(java.lang.Object)
     */
    @Override
    public void cleanupTransaction(Object transactionData)
    {
        logger.debug("Cleaning up after transaction");
        
        Object sessionTransactionData;
        if (transactionData instanceof IsolationAwareSessionTransactionData)
        {
            IsolationAwareSessionTransactionData isolationAwareTransactionData = 
                    (IsolationAwareSessionTransactionData) transactionData;
            
            Integer previousIsolationLevel = isolationAwareTransactionData.getPreviousIsolationLevel();
            if (previousIsolationLevel != null) 
            {
                try
                {
                	logger.debug("Re-setting isolation level to " + previousIsolationLevel + ".");
                    
                    isolationAwareTransactionData.getConnection().setTransactionIsolation(
                            previousIsolationLevel);
                }
                catch (Throwable t)
                {
                }
            }
            
            sessionTransactionData = isolationAwareTransactionData.getSessionTransactionData();
        }
        else
        {
            sessionTransactionData = transactionData;
        }
        
        super.cleanupTransaction(sessionTransactionData);
    }
}
