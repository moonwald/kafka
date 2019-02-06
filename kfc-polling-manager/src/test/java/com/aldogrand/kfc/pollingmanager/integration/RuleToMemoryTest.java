package com.aldogrand.kfc.pollingmanager.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.manager.impl.RuleManagerImpl;
import com.aldogrand.kfc.pollingmanager.service.impl.RulesLoaderFileSystemServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager.xml"})
public class RuleToMemoryTest {

    @Autowired
    private RulesLoaderFileSystemServiceImpl loaderService;

    @Autowired
    private RuleManagerImpl ruleManager;

    private static String RULE_PATH = "src/test/resources/rules";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFileToMemory() {
        String filePath = RULE_PATH + "/allevents-sample.json";

        String absolutePath = Paths.get(filePath).toAbsolutePath().toString();

        loaderService.loadRule(filePath);
        RuleSchedule ruleSchedule = ruleManager.get(absolutePath);
        assertNotNull(ruleSchedule);
        assertTrue(absolutePath.equalsIgnoreCase(ruleSchedule.getRuleId()));
    }

}
