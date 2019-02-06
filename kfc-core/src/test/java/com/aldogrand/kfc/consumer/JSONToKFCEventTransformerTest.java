//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-mysql-jsontransformer-test.xml"})
public class JSONToKFCEventTransformerTest {

    @Autowired
    private JSONToKFCEventTransformer transformer;

/*    @Autowired
    @Qualifier("input")
    private MessageChannel inputChannel;

    @Autowired
    @Qualifier("requests")
    private MessageChannel requestsChannel;

    @Autowired
    private TestEventWriter messageWriter;

    @Autowired
    private KafkaLocalServer kafkaLocalServer;*/

/*    @Before
    public void before() throws Exception {
        this.kafkaLocalServer.startup();
    }

    @After
    public void after() throws Exception {
        this.kafkaLocalServer.shutdown();
    }*/
    @Test
    public void mapping() {
        assertEquals(SourceEventReceivedEvent.class, transformer.getMapping(EventContentType.SOURCE_EVENT_RECEIVED));
    }

    @Test(expected = NullPointerException.class)
    public void noContentType() throws IOException,  IllegalArgumentException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"integrationModuleName\":\"m1\"}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree());
        transformer.transform(builder.build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongContentType() throws IOException,  IllegalArgumentException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"integrationModuleName\":\"m1\"}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
                .setHeader(MessageHeaders.CONTENT_TYPE, "wrongContentType");
        transformer.transform(builder.build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPayLoad() throws IOException,  IllegalArgumentException {
        JsonParser parser = new JsonFactory().createJsonParser("");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_EVENT_RECEIVED);
        transformer.transform(builder.build());

    }

    @Test
    public void transformTest() throws IOException,  IllegalArgumentException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"integrationModuleName\": \"m3\", \"markets\": [{"+
                "\"id\":234, \"name\":\"Name2\"}]}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_MARKETS_RECEIVED);
        transformer.setObjectMapper(new ObjectMapper());
        KFCEvent KFCEvent = transformer.transform(builder.build());
    }
/*

    @Test(expected = MessagingException.class)
    public void wrongContentType() throws IOException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"connector\":\"m1\"}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
            .setHeader(MessageHeaders.CONTENT_TYPE, "wrongContentType");
        inputChannel.send(builder.build());
    }

    @Test(expected = MessagingException.class)
    public void nullContentType() throws IOException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"connector\":\"m1\"}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
            .setHeader(MessageHeaders.CONTENT_TYPE, null);
        inputChannel.send(builder.build());
    }

    @Test
    public void noPayload() throws IOException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"connector\":\"m1\"}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
            .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_EVENT_RECEIVED.name());
        inputChannel.send(builder.build());
        SourceEventReceivedEvent payload = (SourceEventReceivedEvent) messageWriter.getPayload();
        assertNotNull(payload);
        assertEquals("m1", payload.getConnector());
    }

    @Test
    public void eventWithIdAndName() throws IOException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"connector\": \"m2\", \"event\": {"+
            "\"id\":123, \"name\":\"Name1\"}}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
            .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_EVENT_RECEIVED.name());
        inputChannel.send(builder.build());
        SourceEventReceivedEvent payload = (SourceEventReceivedEvent) messageWriter.getPayload();
        assertNotNull(payload);
        assertEquals("m2", payload.getConnector());
        assertEquals("123", payload.getEvent().getId());
        assertEquals("Name1", payload.getEvent().getName());
    }

    @Test
    public void marketWithIdAndName() throws IOException {
        JsonParser parser = new JsonFactory().createJsonParser("{\"connector\": \"m3\", \"markets\": [{"+
            "\"id\":234, \"name\":\"Name2\"}]}");
        parser.setCodec(new ObjectMapper());
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(parser.readValueAsTree())
            .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_MARKETS_RECEIVED.name());
        inputChannel.send(builder.build());
        SourceMarketsReceivedEvent payload = (SourceMarketsReceivedEvent) messageWriter.getPayload();
        assertNotNull(payload);
        assertEquals("m3", payload.getConnector());
        assertNotNull(payload.getMarkets().get(0));
        assertEquals("234", payload.getMarkets().get(0).getId());
        assertEquals("Name2", payload.getMarkets().get(0).getName());
    }


*/

}
