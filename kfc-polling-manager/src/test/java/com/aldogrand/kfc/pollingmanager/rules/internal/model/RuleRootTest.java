package com.aldogrand.kfc.pollingmanager.rules.internal.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;

import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;

public class RuleRootTest {


    @Resource(name = "ruleRootService")
    RuleRoot service;

    @Test
    public void testAddRule() {
        // Get original rule to save
        RuleSchedule rule = createRule();

        RuleRoot service = new RuleRoot();
        // save the rule to service
        service.addRule(rule);

        // confirm rule was saved correctly

        RuleSchedule saved = service.getRule(rule.getRuleId());
        assertEquals(rule, saved);
    }


    @Test
    public void testDeleteRule() {
        // Get original rule to save
        RuleSchedule ruleSchedule = createRule();
        RuleRoot service = new RuleRoot();
        // save the rule to service
        service.addRule(ruleSchedule);

        // confirm rule was saved correctly
        List<RuleSchedule> allRules = service.getAllRules();
        assertNotNull(allRules);

        RuleSchedule actual = allRules.get(0);
        assertEquals(ruleSchedule, actual);
        
        // delete rule
        service.deleteRule(ruleSchedule.getRuleId());
        assertEquals(null, service.getRule(ruleSchedule.getRuleId()));
    }



    @Test
    public void testGetRulesByType() {
        // Get original rule to save
        RuleSchedule ruleSchedule = createRule();
        RuleSchedule ruleSchedule2 = createRule();

        RuleRoot service = new RuleRoot();
        // save the rule to service
        service.addRule(ruleSchedule);
        service.addRule(ruleSchedule2);

        ArrayList<RuleSchedule> rulesSchedule = new ArrayList<RuleSchedule>();
        rulesSchedule.add(ruleSchedule);
        rulesSchedule.add(ruleSchedule2);
        // confirm rule was saved correctly

        Map<String, RuleSchedule> rulesMap = new HashMap<String, RuleSchedule>();
        String path = "./src/test/resources/rules/allevents-sample.json";
        rulesMap.put(path, ruleSchedule);

        Map<RuleType, Map<String, RuleSchedule>> rulesByType = new HashMap<RuleType, Map<String, RuleSchedule>>();
        rulesByType.put(RuleType.ALL, rulesMap);

        List<RuleSchedule> allRules = service.getRulesByType(RuleType.ALL);
        assertNotNull(allRules);
    }

    @Ignore
    @Test
    public void testDeleteAllRules() {
        // Get original rule to save
        RuleSchedule ruleSched = createRule();
        RuleSchedule ruleSched2 = createRule();

        RuleRoot service = new RuleRoot();
        // save the rule to service
        service.addRule(ruleSched);
        service.addRule(ruleSched2);

        ArrayList<RuleSchedule> rulesSchedule = new ArrayList<RuleSchedule>();
        rulesSchedule.add(ruleSched);
        rulesSchedule.add(ruleSched2);


        Map<String, RuleSchedule> rulesById = new HashMap<String, RuleSchedule>();
        String path = "./src/test/resources/rules/allevents-sample.json";
        rulesById.put(path, ruleSched);

        Map<RuleType, Map<String, RuleSchedule>> rulesByType = new HashMap<RuleType, Map<String, RuleSchedule>>();
        rulesByType.put(RuleType.ALL, rulesById);

        List<RuleSchedule> allRules = service.getAllRules();
        assertNotNull(allRules);

        service.deleteAllRules();
        assertEquals(null, allRules);
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