package com.aldogrand.kfc.pollingmanager.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.manager.impl.RuleManagerImpl;
import com.aldogrand.kfc.pollingmanager.service.RuleException;
import com.aldogrand.kfc.pollingmanager.service.RulesReaderService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager.xml"})
public class RulesLoaderFileSystemServiceImplTest {

    @Autowired
    private RulesLoaderFileSystemServiceImpl loaderService;

    @Autowired
    private RuleManagerImpl ruleManager;

    @Autowired
    private RulesReaderService rulesReader;

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


    @Test(expected = NullPointerException.class)
    public void testLoadRulesFromPathNull() {
        loaderService.loadRulesFromPath(null, true);
    }


    @Test(expected = RuleException.class)
    public void testLoadRulesFromPathRuleException() throws RuleException {
        // Given
        String filePath = RULE_PATH + "/sample.json";

        Paths.get(filePath).toAbsolutePath().toString();

        // When
        rulesReader.readAllRules(filePath);
    }


    @Test
    public void testLoadRulesFromPath() {
        String filePath = RULE_PATH + "/allevents-sample.json";

        String absolutePath = Paths.get(filePath).toAbsolutePath().toString();

        loaderService.loadRulesFromPath(filePath, true);
        RuleSchedule ruleSchedule = ruleManager.get(absolutePath);
        assertNotNull(ruleSchedule);
        assertTrue(absolutePath.equalsIgnoreCase(ruleSchedule.getRuleId()));
    }


    @Test
    public void testLoadRule() {
        String filePath = RULE_PATH + "/allevents-sample.json";

        String absolutePath = Paths.get(filePath).toAbsolutePath().toString();

        loaderService.loadRule(filePath);
        RuleSchedule ruleSchedule = ruleManager.get(absolutePath);
        assertNotNull(ruleSchedule);
        assertTrue(absolutePath.equalsIgnoreCase(ruleSchedule.getRuleId()));
    }



    @Test(expected = RuleException.class)
    public void testloadAllRulesException() throws RuleException {
        String filePath = RULE_PATH + "/allevents-sample.json";

        loaderService.loadAllRules(true);
        rulesReader.readAllRules(filePath);
    }


    @Ignore
    @Test
    public void testloadAllRules() throws RuleException {

        String filePath = RULE_PATH + "/allevents-sample.json";

        String absolutePath = Paths.get(filePath).toAbsolutePath().toString();

        loaderService.loadAllRules(true);

        List<File> allRulesFiles = rulesReader.readAllRules(absolutePath);
        assertNotNull(allRulesFiles);
        File fileExpected = allRulesFiles.get(0);
        assertNotNull(fileExpected);

    }


}
