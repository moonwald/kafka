package com.aldogrand.kfc.pollingmanager.rules.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;

import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.manager.impl.RuleFileSystemManagerImpl;

public class RuleFileSystemManagerImplTest {

    @Resource(name = "ruleFileSystemManager")
    RuleFileSystemManagerImpl ruleMgr;

    @Test
    public void testAddOrUpdate() {
        // Create rule schedule
        RuleSchedule ruleSchedule = createRule();

        RuleFileSystemManagerImpl ruleMgr = new RuleFileSystemManagerImpl();

        // save the rule to service
        ruleMgr.addOrUpdate(ruleSchedule);

        RuleSchedule saved = ruleMgr.get("/src/test/resources/rules/allevents-header-sample.json");

        assertEquals(ruleSchedule, saved);
    }


    @Test
    public void testRemoveByPath() {

        RuleSchedule ruleSchedule = createRule();


        RuleFileSystemManagerImpl ruleMgr = new RuleFileSystemManagerImpl();

        String path = "./src/test/resources/rules/allevents-sample.json";


        // save the rule to manager
        ruleMgr.addOrUpdate(ruleSchedule, path);

        List<RuleSchedule> allRules = ruleMgr.getAll();
        assertNotNull(allRules);

        RuleSchedule actual = allRules.get(0);

        assertEquals(ruleSchedule, actual);

        ruleMgr.remove(ruleSchedule.getRuleId());

        assertEquals(null, ruleMgr.getByPath(ruleSchedule.getRuleId()));;


    }


    @Test
    public void testGetByPath() {
        // Create rule schedule
        RuleSchedule ruleSchedule = createRule();

        RuleFileSystemManagerImpl ruleMgr = new RuleFileSystemManagerImpl();

        String path = "./src/test/resources/rules/allevents-sample.json";


        // save the rule to manager
        ruleMgr.addOrUpdate(ruleSchedule, path);

        // get
        RuleSchedule checkSchedule = ruleMgr.getByPath(ruleSchedule.getRuleId());

        assertEquals(ruleSchedule, checkSchedule);


    }



    private RuleSchedule createRule() {


        TimeUnit timeunit = java.util.concurrent.TimeUnit.MILLISECONDS;
        com.aldogrand.kfc.pollingmanager.rules.Duration duration =
                        new com.aldogrand.kfc.pollingmanager.rules.Duration(Long.valueOf(10000), timeunit);
        Rule rule = new Rule(RuleType.ALL, DataType.EVENT, duration);

        Date loadtime = new Date();
        RuleSchedule rs = new RuleSchedule("/src/test/resources/rules/allevents-header-sample.json", rule);
        rs.setLoadTime(loadtime);

        return rs;


    }






}
