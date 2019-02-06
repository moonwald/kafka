package com.aldogrand.kfc.utils.configuration;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * <b>Title</b> SystemPropertyConfiguratorBean
 * </p>
 * <p>
 * <b>Description</b> A Spring Bean that takes {@link Properties} values and sets
 * them as System properties. This is used for modules that are only configurable
 * by System properties. This should be added to a Spring {@link ApplicationContext}
 * before any beans that require System property configuration so that the properties
 * are set before the beans are initialized.
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
public class SystemPropertyConfiguratorBean implements InitializingBean
{
    private Properties properties;
    
    /**
     * @return the properties
     */
    public Properties getProperties()
    {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet()
    {
        if (properties != null)
        {
            for (Object key : properties.keySet())
            {
                final String name = (String) key;
                final String value = properties.getProperty(name);
                if ((value != null) && (value.length() > 0))
                {
                    System.setProperty(name, value);
                }
            }
        }
    }
}
