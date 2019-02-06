package com.aldogrand.kfc.pollingmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aldogrand.kfc.pollingmanager.rules.Rule;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager-test.xml"})
public class JsonToRuleTransformerTest {

    @Autowired
    JsonToRuleTransformer transformer;

    private static String TEST_RESOURCES_RULES = "src/test/resources/rules";

    private String invalidTypeJson =
                    "{\"periodicity\": { \"duration\" : 20,\"unit\": \"SECONDS\"},\"ruleType\": \"ALL\",\"dataType\": \"EVENTx\","
                                    + "\"accounts\": [ \"matchbook-soccer\", \"3et-soccer\"],\"filters\": [{\"@filter\": "
                                    + "\"event-status\",\"values\": [ \"OPEN\"]},{\"@filter\": \"meta-tag\",\"key\": \"SPORT\","
                                    + "\"values\": [ \"Soccer\", \"Football\" ]}]}";

    private static final String HEADER = "path";
    private static final String HEADER_VALUE = "temp/test.file";

    final Logger logger = Logger.getLogger(JsonToRuleTransformer.class);
    ByteArrayOutputStream out;
    Layout layout;
    Appender appender;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        transformer.setObjectMapper(new ObjectMapper());

        out = new ByteArrayOutputStream();
        layout = new SimpleLayout();
        appender = new WriterAppender(layout, out);
        logger.addAppender(appender);
        logger.setLevel(Level.ERROR);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        logger.removeAppender(appender);
    }

    /**
     * Test method for AllEventsRule
     * {@link com.aldogrand.kfc.pollingmanager.service.JsonToRuleTransformer#transform(org.springframework.messaging.Message)}.
     */
    @Test
    public void testTransform_allEventsRule() throws IOException, IllegalArgumentException {
        String rulePath = TEST_RESOURCES_RULES + "/allevents-sample.json";
        Path testPath = FileSystems.getDefault().getPath(rulePath);
        Message<Rule> msg = prepareAndTransform(convertFileToString(testPath), HEADER, HEADER_VALUE);
        Rule rule = msg.getPayload();
        // assertEquals("10101", rule.getRuleId());
        assertTrue(rule instanceof Rule);
        MessageHeaders headers = msg.getHeaders();
        assertEquals(HEADER_VALUE, headers.get(HEADER));
    }

    /**
     * Test method for invalid type
     * {@link com.aldogrand.kfc.pollingmanager.service.JsonToRuleTransformer#transform(org.springframework.messaging.Message)}
     */
    @Test
    public void testTransform_invalidType() throws IOException, IllegalArgumentException {
        prepareAndTransform(invalidTypeJson, HEADER, HEADER_VALUE);

        String logMsg = out.toString();

        assertNotNull(logMsg);
        assertTrue(logMsg.startsWith("ERROR - JsonParseException when transforming: Can not construct instance of "
                        + "com.aldogrand.kfc.pollingmanager.rules.DataType from String value 'EVENTx'"));
    }

    /**
     * Test method for no pay load
     * {@link com.aldogrand.kfc.pollingmanager.service.JsonToRuleTransformer#transform(org.springframework.messaging.Message)}
     */
    @Test
    public void testTransform_noPayLoad() throws IOException, IllegalArgumentException {
        Message<Rule> result = prepareAndTransform("{}");
        assertNull(result);

        String logMsg = out.toString();

        assertNotNull(logMsg);
        assertTrue(logMsg.startsWith("ERROR - JsonParseException when transforming: Instantiation of [simple type, "
                        + "class com.aldogrand.kfc.pollingmanager.rules.Rule] value failed: A ruleType is required"));
    }

    /**
     * @return Rule that has been transformed.
     * @throws IOException
     * @throws JsonParseException
     */
    private Message<Rule> prepareAndTransform(String json, String headerName, String headerValue) throws IOException, JsonParseException {
        JsonParser parser = new JsonFactory().createParser(json);
        parser.setCodec(new ObjectMapper());
        MessageBuilder<TreeNode> builder = MessageBuilder.withPayload(parser.readValueAsTree());
        if (headerName != null && headerValue != null) {
            builder.setHeader(headerName, headerValue);
        }
        return transformer.transform(builder.build());
    }

    /**
     * @return Rule that has been transformed.
     * @throws IOException
     * @throws JsonParseException
     */
    private Message<Rule> prepareAndTransform(String json) throws IOException, JsonParseException {
        return prepareAndTransform(json, null, null);
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
