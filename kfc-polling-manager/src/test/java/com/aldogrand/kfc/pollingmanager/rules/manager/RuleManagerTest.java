package com.aldogrand.kfc.pollingmanager.rules.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.aldogrand.kfc.pollingmanager.service.RuleFileSystemService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager-test.xml"})
public class RuleManagerTest {

    @Autowired
    private RuleFileSystemService ruleFileSystemService;

    @Autowired
    private RuleFileSystemManager ruleMgr;


    @Test
    public void testAddOrUpdate() {
        // Get original rule to save
        RuleSchedule rule = createRuleSchedule();

        // save the rule to memory
        ruleMgr.addOrUpdate(rule);

        // confirm rule was saved correctly
        RuleSchedule saved = ruleMgr.get(rule.getRuleId());
        assertEquals(rule, saved);
    }


    @Test
    public void testGet() {
        // Get original rule to save
        RuleSchedule rule = createRuleSchedule();

        // save the rule to memory
        ruleMgr.addOrUpdate(rule);

        // confirm we can get the saved rule
        RuleSchedule saved = ruleMgr.get(rule.getRuleId());
        assertEquals(rule, saved);
    }


    @Test
    public void testGetAll() {
        // Get original rules to save
        List<RuleSchedule> rules = createRules();

        // save the rules to memory
        ruleMgr.addOrUpdate(rules.get(0));
        ruleMgr.addOrUpdate(rules.get(1));
        ruleMgr.addOrUpdate(rules.get(2));

        // confirm rule was saved correctly
        List<RuleSchedule> savedRules = ruleMgr.getAll();
        assertEquals(4, savedRules.size());

        HashMap<String, RuleSchedule> savedMap = new HashMap<String, RuleSchedule>();
        for (RuleSchedule saved : savedRules) {
            savedMap.put(saved.getRuleId(), saved);
        }
        assertEquals(rules.get(0), savedMap.get(rules.get(0).getRuleId()));
        assertEquals(rules.get(1), savedMap.get(rules.get(1).getRuleId()));
        assertEquals(rules.get(2), savedMap.get(rules.get(2).getRuleId()));

    }


    @Test
    public void testRemove() {
        // Get original rule to save
        RuleSchedule rule = createRuleSchedule();

        // save the rule to memory
        ruleMgr.addOrUpdate(rule);

        // confirm rule was saved correctly
        RuleSchedule saved = ruleMgr.get(rule.getRuleId());
        assertEquals(rule, saved);

        // remove the rule
        ruleMgr.remove(rule.getRuleId());

        // confirm rule was deleted
        RuleSchedule savedAgain = ruleMgr.get(rule.getRuleId());
        assertNull(savedAgain);
    }


    @Test
    public void testRemoveAll() {
        // Get original rules to save
        List<RuleSchedule> rules = createRules();

        // save the rules to memory
        ruleMgr.addOrUpdate(rules.get(0));
        ruleMgr.addOrUpdate(rules.get(1));
        ruleMgr.addOrUpdate(rules.get(2));

        // confirm rule was saved correctly
        List<RuleSchedule> savedRules = ruleMgr.getAll();
        assertEquals(3, savedRules.size());

        // remove all the rules
        ruleMgr.removeAll();

        // confirm all rules were deleted
        List<RuleSchedule> savedRulesAgain = ruleMgr.getAll();
        assertTrue(savedRulesAgain.isEmpty());
    }


   
    private List<RuleSchedule> createRules() {
        List<RuleSchedule> rules = new ArrayList<>();

        RuleSchedule rule1 = createRuleSchedule("ruleId-1");
        RuleSchedule rule2 = createRuleSchedule("ruleId-2");
        RuleSchedule rule3 = createRuleSchedule("ruleId-3");

        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);
        return rules;
    }
    

    private RuleSchedule createRuleSchedule() {
        Rule rule = createRule();
        RuleSchedule rs = new RuleSchedule("/src/test/resources/rules/allevents-header-sample.json", rule);
        return rs;
    }

    private RuleSchedule createRuleSchedule(String path) {
        Rule rule = createRule();
        RuleSchedule rs = new RuleSchedule(path, rule);
        return rs;
    }


    private Rule createRule() {
        TimeUnit timeunit = java.util.concurrent.TimeUnit.MILLISECONDS;
        Duration duration = new Duration(Long.valueOf(10000), timeunit);
        Rule rule = new Rule(RuleType.ALL, DataType.EVENT, duration);
        rule.setStartDate(new Date());
        return rule;
    }

    @Ignore
    @Test
    public void testEnable_Disable() {
        // Get original rule to save
        RuleSchedule rule = createRuleSchedule();

        // save the rule to memory
        ruleMgr.addOrUpdate(rule);

        // enable the rule
        ruleMgr.enable(rule.getRuleId());

        // confirm rule was enabled
        RuleSchedule saved = ruleMgr.get(rule.getRuleId());
        assertTrue(saved.getRule().isEnabled());

        // disable the rule
        ruleMgr.disable(rule.getRuleId());

        // confirm rule was disabled
        saved = ruleMgr.get(rule.getRuleId());
        assertFalse(saved.getRule().isEnabled());
    }



    public RuleFileSystemService getRuleFileSystemService() {
        return ruleFileSystemService;
    }

    public void setRuleFileSystemService(RuleFileSystemService ruleFileSystemService) {
        this.ruleFileSystemService = ruleFileSystemService;
    }

    public RuleFileSystemManager getRuleManagerFileSystem() {
        return ruleMgr;
    }

    public void setRuleManagerFileSystem(RuleFileSystemManager ruleManagerFileSystem) {
        this.ruleMgr = ruleManagerFileSystem;
    }

}
