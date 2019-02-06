package com.aldogrand.kfc.pollingmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.databind.JsonNode;

public class MessageDecoderTest {

    private MessageDecoder decoder = new MessageDecoder();

    private static final String HEADER_NAME = "header_name";

    final Logger logger = Logger.getLogger(MessageDecoder.class);
    ByteArrayOutputStream out;
    Layout layout;
    Appender appender;

    @Before
    public void setUp() throws Exception {
        out = new ByteArrayOutputStream();
        layout = new SimpleLayout();
        appender = new WriterAppender(layout, out);
        logger.addAppender(appender);
        logger.setLevel(Level.ERROR);
    }

    @After
    public void tearDown() throws Exception {
        logger.removeAppender(appender);
    }

    @Test
    public void testFromBytes_emptyString() {
        Message<JsonNode> result = decoder.stringToJsonNode(createMessage("", ""));
        assertNull(result);

        String logMsg = out.toString();

        assertNotNull(logMsg);
        assertTrue(logMsg.startsWith("ERROR - Error reading message bytes: No content to map due to end-of-input"));
    }

    @Test
    @Ignore
    public void testFromBytes_noHeadersEmptyPayload() {
        Message<JsonNode> result = decoder.stringToJsonNode(createMessage("{}", ""));
        assertNull(result);

        String logMsg = out.toString();

        assertNotNull(logMsg);
        assertTrue(logMsg.startsWith("ERROR - No payload given: {}"));
    }

    @Test
    @Ignore
    public void testFromBytes_noHeaders() {
        Message<JsonNode> result = decoder.stringToJsonNode(createMessage("{\"payload\":{}}"));
        assertEquals(2, result.getHeaders().size());
        assertNotNull(result.getHeaders().get(MessageHeaders.ID));
        assertNotNull(result.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("{}", result.getPayload().toString());
    }

    @Test
    @Ignore
    public void testFromBytes_emptyPayload() {
        Message<JsonNode> result = decoder.stringToJsonNode(createMessage("{}", ""));
        assertNull(result);

        String logMsg = out.toString();

        assertNotNull(logMsg);
        assertTrue(logMsg.startsWith("ERROR - No payload given: {}"));
    }

    @Test
    @Ignore
    public void testFromBytes_emptyHeadersEmptyPayload() {
        Message<JsonNode> message = decoder.stringToJsonNode(createMessage("{\"payload\": {}}"));
        assertEquals(2, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("{}", message.getPayload().toString());
    }

    @Test
    @Ignore
    public void testFromBytes_oneHeaderEmptyPayload() {
        String json = "{\"headers\":{\"header1\": \"someHeader\"}, \"payload\": {}}";
        Message<JsonNode> message = decoder.stringToJsonNode(createMessage(json, "header_value"));
        assertEquals(4, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("header_value", message.getHeaders().get(HEADER_NAME));
        assertEquals("someHeader", message.getHeaders().get("header1"));
        assertEquals("{}", message.getPayload().toString());
    }

    @Test
    @Ignore
    public void testFromBytes_multipleHeadersEmptyPayload() {
        String json = "{\"headers\":{\"header1\": \"someHeader\", \"abc\":\"def\"}, \"payload\": {}}";
        Message<JsonNode> message = decoder.stringToJsonNode(createMessage(json, "header_value"));
        assertEquals(5, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("header_value", message.getHeaders().get(HEADER_NAME));
        assertEquals("def", message.getHeaders().get("abc"));
        assertEquals("{}", message.getPayload().toString());
    }

    @Test
    @Ignore
    public void testFromBytes_jsonHeaderSimplePayPlusHeader() {
        String json = "{\"headers\":{\"header1\": \"someHeader\"}, \"payload\": {\"data\":\"abc\"}}";
        Message<JsonNode> message = decoder.stringToJsonNode(createMessage(json, "header_value"));
        assertEquals(4, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("someHeader", message.getHeaders().get("header1"));
        assertEquals("header_value", message.getHeaders().get(HEADER_NAME));
        assertEquals("{\"data\":\"abc\"}", message.getPayload().toString());
    }

    @Test
    @Ignore
    public void testFromBytes_jsonHeaderSimplePayload() {
        String json = "{\"headers\":{\"header1\": \"someHeader\"}, \"payload\": {\"data\":\"abc\"}}";
        Message<JsonNode> message = decoder.stringToJsonNode(createMessage(json));
        assertEquals(3, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("someHeader", message.getHeaders().get("header1"));
        assertEquals("{\"data\":\"abc\"}", message.getPayload().toString());
    }

    private Message<String> createMessage(String json) {
        MessageBuilder<String> builder = MessageBuilder.withPayload(json);

        return (Message<String>) builder.build();
    }

    private Message<String> createMessage(String json, String headerValue) {
        MessageBuilder<String> builder = MessageBuilder.withPayload(json).setHeader(HEADER_NAME, headerValue);

        return (Message<String>) builder.build();
    }

}
