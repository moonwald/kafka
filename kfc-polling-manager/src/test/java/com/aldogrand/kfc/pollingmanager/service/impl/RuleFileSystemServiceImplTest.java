package com.aldogrand.kfc.pollingmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

// aldoAldoGrand@bitbucket.org/xcl-mb/kfc.git
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleFileSystemManager;
import com.aldogrand.kfc.pollingmanager.service.RuleFileSystemService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager-test.xml"})
public class RuleFileSystemServiceImplTest {

    @Autowired
    private RuleFileSystemService ruleFileSystemService;

    @Autowired
    private RuleFileSystemManager ruleManagerFileSystem;


    @Test
    public void addRuleToMemoryFromPathTest() {
        RuleSchedule ruleSchedule = createRuleSchedule();
        ruleFileSystemService.addRuleToMemory(ruleSchedule.getRule(), ruleSchedule.getRuleId());
        
        List<RuleSchedule> allRulesSchedule = ruleManagerFileSystem.getAll();
        assertNotNull(allRulesSchedule);
        RuleSchedule actual = allRulesSchedule.get(0);
        assertNotNull(actual);

        assertEquals(ruleSchedule.getRuleId(), ruleSchedule.getRuleId());
    }


    @Test
    public void addRuleToMemoryTest() {

        Rule rule = createRule();
        String path = "/src/test/resources/rules/allevents-sample.json";
       
        ruleFileSystemService.addRuleToMemory(rule, path);


        List<RuleSchedule> allRulesSchedule = ruleManagerFileSystem.getAll();
        assertNotNull(allRulesSchedule);
        RuleSchedule actual = allRulesSchedule.get(0);
        assertNotNull(actual);

        assertEquals(rule, actual.getRule());

    }

    @Ignore
    @Test
    public void removeRuleFromMemoryDeletePathTest() {

        Rule rule = createRule();
        String path = "/src/test/resources/rules/allevents-sample.json";

        // RuleSchedule ruleSchedule = new RuleSchedule(path, rule);

        // ruleManagerFileSystem.addOrUpdate(ruleSchedule, path);


        ruleFileSystemService.addRuleToMemory(rule, path);

        List<RuleSchedule> allRulesSchedule = ruleManagerFileSystem.getAll();
        assertNotNull(allRulesSchedule);
        RuleSchedule actual = allRulesSchedule.get(0);
        assertNotNull(actual);

        ruleFileSystemService.removeRuleFromMemory(path);

        // / assertEquals(null, actual);

        assertEquals(true, allRulesSchedule.isEmpty());;



    }

    @Ignore
    @Test
    public void removeRuleFromMemoryTest() {

        String path = "/src/test/resources/rules/allevents-sample.json";

        ruleManagerFileSystem.removeByPath(path);
        ruleFileSystemService.removeRuleFromMemory(path);

    }

    @Ignore
    @Test
    public void enableRuleInMemoryTest() {

    }

    @Ignore
    @Test
    public void disableRuleInMemoryTest() {
        fail("Not yet implemented");
    }

    @SuppressWarnings("unused") // this is expected to be used
    private Message<Rule> createMessage(Rule rule, Map<String, String> header) {
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(rule).copyHeaders(header);

        return (Message<Rule>) builder.build();
    }

    public RuleFileSystemService getRuleFileSystemService() {
        return ruleFileSystemService;
    }

    public void setRuleFileSystemService(RuleFileSystemService ruleFileSystemService) {
        this.ruleFileSystemService = ruleFileSystemService;
    }

    public RuleFileSystemManager getRuleManagerFileSystem() {
        return ruleManagerFileSystem;
    }

    public void setRuleManagerFileSystem(RuleFileSystemManager ruleManagerFileSystem) {
        this.ruleManagerFileSystem = ruleManagerFileSystem;
    }

    private RuleSchedule createRuleSchedule() {        
        Rule rule = createRule();
        RuleSchedule rs = new RuleSchedule("/src/test/resources/rules/allevents-header-sample.json", rule);
        return rs;
    }


    private Rule createRule() {
        TimeUnit timeunit = java.util.concurrent.TimeUnit.MILLISECONDS;
        Duration duration = new Duration(Long.valueOf(10000), timeunit);
        Rule rule = new Rule(RuleType.ALL, DataType.EVENT, duration);
        rule.setStartDate(new Date());
        return rule;
    }
}
