package com.aldogrand.kfc.utils.database;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.jmx.StatisticsService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * <b>Title</b> HibernateJmxRegistrationBean
 * </p>
 * <p>
 * <b>Description</b> A bean that registers an {@link HibernateEntityManagerFactory} 
 * with an {@link MBeanServer} so that it can be monitored via JMX.
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
@SuppressWarnings("deprecation")
public class HibernateJmxRegistrationBean implements InitializingBean, DisposableBean
{
    private final Logger logger = Logger.getLogger(getClass());
    
    private MBeanServer mbeanServer;
    private HibernateEntityManagerFactory hibernateEntityManagerFactory;
    private String factoryName;
    
    /**
     * @return the mbeanServer
     */
    public MBeanServer getMbeanServer()
    {
        return mbeanServer;
    }

    /**
     * @param mbeanServer the mbeanServer to set
     */
    public void setMbeanServer(MBeanServer mbeanServer)
    {
        this.mbeanServer = mbeanServer;
    }

    /**
     * @return the hibernateEntityManagerFactory
     */
    public HibernateEntityManagerFactory getHibernateEntityManagerFactory()
    {
        return hibernateEntityManagerFactory;
    }

    /**
     * @param hibernateEntityManagerFactory the hibernateEntityManagerFactory to set
     */
    public void setHibernateEntityManagerFactory(
            HibernateEntityManagerFactory hibernateEntityManagerFactory)
    {
        this.hibernateEntityManagerFactory = hibernateEntityManagerFactory;
    }

    /**
     * Name that is used to register the {@link HibernateEntityManagerFactory}.
     * @return the factoryName
     */
    public String getFactoryName()
    {
        return factoryName;
    }

    /**
     * Name that is used to register the {@link HibernateEntityManagerFactory}.
     * @param factoryName the factoryName to set
     */
    public void setFactoryName(String factoryName)
    {
        this.factoryName = factoryName;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws IllegalStateException, JMException
    {
        if (mbeanServer == null)
        {
            throw new IllegalStateException("MBeanServer cannot be null. Set MBeanServer to register Hibernate Entity Manager Factory on");
        }
        
        if (hibernateEntityManagerFactory == null)
        {
            throw new IllegalStateException("hibernateEntityManagerFactory cannot be null. Set hibernateEntityManagerFactory to enable registration for statistics");
        }

        logger.debug("Registering Hibernate Entity Manager Factory for statistics monitoring via JMX");

        final SessionFactory sessionFactory = hibernateEntityManagerFactory.getSessionFactory();
        final StatisticsService statsService = new StatisticsService();
        statsService.setSessionFactory(sessionFactory);
        
        final ObjectName mbeanName = new ObjectName("hibernate:type=statistics,sessionFactory=" 
                + (factoryName != null ? factoryName : "unnamed"));
        if (!mbeanServer.isRegistered(mbeanName))
        {
            mbeanServer.registerMBean(statsService, mbeanName);
        }
        else
        {
            mbeanServer.unregisterMBean(mbeanName);
            mbeanServer.registerMBean(statsService, mbeanName);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws JMException
    {
        logger.debug("Unregistering Hibernate Entity Manager Factory for statistics monitoring via JMX");

        final ObjectName mbeanName = new ObjectName("hibernate:type=statistics,sessionFactory=" 
                + (factoryName != null ? factoryName : "unnamed"));
        if (mbeanServer.isRegistered(mbeanName))
        {
            mbeanServer.unregisterMBean(mbeanName);
        }
    }    
}
