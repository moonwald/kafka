package com.aldogrand.kfc.pollingmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.service.RuleException;
import com.aldogrand.kfc.pollingmanager.service.RulesReaderService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager.xml"})
public class RulesReaderServiceTest {

    @Autowired
    private RulesReaderService rulesReader;

    private static String TEST_RESOURCES_RULES = "src/test/resources/rules";


    @Test(expected = RuleException.class)
    public void readAllRulestestRuleException() throws RuleException {
        // Given
        String filePath = TEST_RESOURCES_RULES + "/sample.json";

        Path testPath = FileSystems.getDefault().getPath(filePath);
       
        // When
        rulesReader.readAllRules(testPath.toAbsolutePath().toString());


    }

    @Test
    public void readAllRulestest() {
        Path testPath = FileSystems.getDefault().getPath(TEST_RESOURCES_RULES);
        List<File> rules = new ArrayList<File>();
        try {
            rules = rulesReader.readAllRules(testPath.toAbsolutePath().toString());
        } catch (RuleException e) {
            e.printStackTrace();
            fail("Error reading rules. Path: " + testPath);
        }

        for (File file : rules) {
            assertEquals(true, file.isFile());
        }
    }

    @Ignore
    @Test(expected = RuleException.class)
    public void readRuletestRuleException() {
        // Given
        String filePath = "picture.jpg";

        Path testPath = FileSystems.getDefault().getPath(filePath);

        // When

        rulesReader.readRule(testPath.toAbsolutePath().toString());

    }

    @Test
    public void readRuletest() {
        String rulePath = TEST_RESOURCES_RULES + "/allevents-sample.json";
        Path testPath = FileSystems.getDefault().getPath(rulePath);
        File rule = rulesReader.readRule(testPath.toAbsolutePath().toString());


        String content = convertFileToString(rule.toPath());

        String dataType = DataType.EVENT.toString();
        assertEquals(true, StringUtils.contains(content, dataType));

        String ruleType = RuleType.ALL.toString();
        assertEquals(true, StringUtils.contains(content, ruleType));
    }

    public RulesReaderService getRulesReader() {
        return rulesReader;
    }

    public void setRulesReader(RulesReaderService rulesReader) {
        this.rulesReader = rulesReader;
    }

    /**
     * Method to read the file content as String.
     * 
     * @param file File path
     * @return Content of the file as string
     */
    private String convertFileToString(Path file) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException x) {
            return null;
        }

        return builder.toString();
    }

}
