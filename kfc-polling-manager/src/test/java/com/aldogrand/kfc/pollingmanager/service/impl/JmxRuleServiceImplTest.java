package com.aldogrand.kfc.pollingmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.jmx.manager.JmxRuleManager;
import com.aldogrand.kfc.pollingmanager.jmx.manager.impl.JmxRuleManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager.xml"})
public class JmxRuleServiceImplTest {



    @Autowired
    private JmxRuleManager ruleManager;


    @Autowired
    ApplicationContext context;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {}


    @Test
    public void testJmxEnableToMemory() throws JsonParseException, JsonMappingException, IOException, IntrospectionException,
                    InstanceNotFoundException, MalformedObjectNameException, ReflectionException {
        
        String name = "sean:name=jmxRulesInMemory";
        JmxRuleManagerImpl ruleMgr = new JmxRuleManagerImpl();

        MBeanExporter exporter = context.getBean(MBeanExporter.class);

        exporter.registerManagedResource(ruleMgr, new ObjectName(name));

        MBeanInfo mBeanInfo = ManagementFactory.getPlatformMBeanServer().getMBeanInfo(new ObjectName(name));
        assertNotNull(mBeanInfo);
        assertEquals(mBeanInfo.getClassName(), JmxRuleManagerImpl.class.getName());

        assertEquals(mBeanInfo.getConstructors().length, 0);
        assertEquals(mBeanInfo.getAttributes().length, 1);
        assertEquals(mBeanInfo.getOperations().length, 2);
        MBeanAttributeInfo mBeanAttribute = mBeanInfo.getAttributes()[0];
        assertNotNull(mBeanAttribute);


        boolean enable = false;
        boolean disable = false;
        for (MBeanOperationInfo mBeanOperation : mBeanInfo.getOperations()) {
            assertNotNull(mBeanOperation);
            if (mBeanOperation.getName().equals("enable")) {
                enable = true;
                assertEquals(mBeanOperation.getReturnType(), "void");
                assertEquals(mBeanOperation.getSignature().length, 0);
            } else if (mBeanOperation.getName().equals("disable")) {
                disable = true;
                assertEquals(mBeanOperation.getReturnType(), "void");
                assertEquals(mBeanOperation.getSignature().length, 1);
            }
        }
        assertEquals(true, enable);


        
    }

    public JmxRuleManager getRuleManager() {
        return ruleManager;
    }

    public void setRuleManager(JmxRuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }



}
