package com.aldogrand.kfc.pollingmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.internal.model.RuleRoot;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleFileSystemManager;
import com.aldogrand.kfc.pollingmanager.service.RuleFileSystemService;
import com.aldogrand.kfc.pollingmanager.service.RulesReaderService;

/**
 *
 * <p>
 * <b>Title</b>: serviceImplTest.java
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 *
 * @author dlehane
 *
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager-test.xml"})
public class RuleServiceImplTest {


    @Autowired
    private RuleFileSystemService service;

    @Autowired
    private RuleFileSystemManager ruleManagerFileSystem;


    @Autowired
    RulesReaderService readerService;

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

    @Ignore
    @Test
    public void testAddRuleToMemory() throws JsonParseException, JsonMappingException, IOException {

        String path = "/src/test/resources/rules/allevents-sample.json";

        // Create Rule from sample JSON file
        Rule rule = createRule();
        
        // Call service to add rule to memory
        service.addRuleToMemory(rule, path);

        // Test that rule was added to memory
        List<RuleSchedule> allRulesSchedule = ruleManagerFileSystem.getAll();
        assertNotNull(allRulesSchedule);

        RuleSchedule actual = allRulesSchedule.get(0);
        assertEquals("allevents-sample", actual.getRuleId());
        assertEquals(rule, actual.getRule());
        
    }


    @Test
    public void testRemoveRuleFromMemory() throws JsonParseException, JsonMappingException, IOException {

        Rule rule = createRule();

        String path = "/src/test/resources/rules/allevents-sample.json";

        // Call service to add rule to memory
        service.addRuleToMemory(rule, path);

        // Test that rule was added to memory
        List<RuleSchedule> allRulesSchedule = ruleManagerFileSystem.getAll();

        assertNotNull(allRulesSchedule);
        RuleSchedule expected = allRulesSchedule.get(0);;

        service.removeRuleFromMemory(expected.getRuleId());

        assertEquals(true, allRulesSchedule.isEmpty());


    }

    @Ignore
    @Test
    public void testEnableRule() throws JsonParseException, JsonMappingException, IOException {

        Rule rule = createRule();

        String path = "/src/test/resources/rules/allevents-sample.json";

        RuleSchedule ruleSched = createRuleSchedule(path, rule);

        RuleRoot root = new RuleRoot();
        root.addRule(ruleSched);

        // Call service to add rule to memory
        service.addRuleToMemory(rule, path);

        // Test that rule was added to memory
        List<RuleSchedule> allRulesSchedule = ruleManagerFileSystem.getAll();
        assertNotNull(allRulesSchedule);

        RuleSchedule actual = allRulesSchedule.get(0);


        // da cambiare RuleManager
        service.enableRule(actual.getRuleId());

        // service.enableRule(path);

        assertEquals(true, ruleSched.getRule().isEnabled());

    }


    @Ignore
    @Test
    public void testDisableRule() throws JsonParseException, JsonMappingException, IOException {

        Rule rule = createRule();

        String path = "/src/test/resources/rules/allevents-sample.json";

        RuleSchedule ruleSched = createRuleSchedule(path, rule);

        RuleRoot root = new RuleRoot();
        root.addRule(ruleSched);

        // Call service to add rule to memory
        service.addRuleToMemory(rule, path);

        // Test that rule was added to memory
        List<RuleSchedule> allRulesSchedule = ruleManagerFileSystem.getAll();
        assertNotNull(allRulesSchedule);

        RuleSchedule actual = allRulesSchedule.get(0);

        // da cambiare RuleManager
        service.disableRule(actual.getRuleId());


        assertEquals(true, ruleSched.getRule().isEnabled());
    }



    private RuleSchedule createRuleSchedule(String path, Rule rule) {

        TimeUnit timeunit = java.util.concurrent.TimeUnit.MILLISECONDS;
        com.aldogrand.kfc.pollingmanager.rules.Duration duration =
                        new com.aldogrand.kfc.pollingmanager.rules.Duration(Long.valueOf(10000), timeunit);
        rule = new Rule(RuleType.ALL, DataType.EVENT, duration);



        Date loadtime = new Date();

        RuleSchedule rs = new RuleSchedule(path, rule);
        rs.setLoadTime(loadtime);

        return rs;


    }

    private Rule createRule() {

        TimeUnit timeunit = java.util.concurrent.TimeUnit.MILLISECONDS;
        Duration duration = new Duration(Long.valueOf(10000), timeunit);
        Rule rule = new Rule(RuleType.ALL, DataType.EVENT, duration);
        Date loadtime = new Date();       
        rule.setStartDate(loadtime);

        return rule;


    }

    public RuleServiceImpl getService() {
        return (RuleServiceImpl) service;
    }

    public void setService(RuleServiceImpl service) {
        this.service = (RuleFileSystemService) service;
    }

    public RulesReaderService getReaderService() {
        return readerService;
    }

    public void setReaderService(RulesReaderService readerService) {
        this.readerService = readerService;
    }

}
