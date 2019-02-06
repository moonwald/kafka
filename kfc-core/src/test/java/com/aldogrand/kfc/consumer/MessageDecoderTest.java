//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MessageDecoderTest {

    private MessageDecoder decoder = new MessageDecoder();

    @Test(expected = IllegalStateException.class)
    public void emptyString() {
        decoder.fromBytes("".getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noHeadersNoPayload() {
        decoder.fromBytes("{}".getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noHeaders() {
        decoder.fromBytes("{\"payload\":{}}".getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPayload() {
        decoder.fromBytes("{\"headers\":{}}".getBytes());
    }

    @Test
    public void emptyHeadersEmptyPayload() {
        Message message = decoder.fromBytes("{\"headers\":{}, \"payload\": {}}".getBytes());
        assertEquals(2, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals(2, message.getHeaders().size());
        assertEquals("{}", message.getPayload().toString());
    }

    @Test
    public void oneHeaderEmptyPayload() {
        String json = "{\"headers\":{\"contentType\": \"kfc/SourceEventReceivedEvent\"}, \"payload\": {}}";
        Message message = decoder.fromBytes(json.getBytes());
        assertEquals(3, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("kfc/SourceEventReceivedEvent", message.getHeaders().get(MessageHeaders.CONTENT_TYPE));
        assertEquals("{}", message.getPayload().toString());
    }

    @Test
    public void multipleHeadersEmptyPayload() {
        String json = "{\"headers\":{\"contentType\": \"kfc/SourceEventReceivedEvent\", \"abc\":\"def\"}, \"payload\": {}}";
        Message message = decoder.fromBytes(json.getBytes());
        assertEquals(4, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("kfc/SourceEventReceivedEvent", message.getHeaders().get(MessageHeaders.CONTENT_TYPE));
        assertEquals("def", message.getHeaders().get("abc"));
        assertEquals("{}", message.getPayload().toString());
    }

    @Test
    public void oneHeaderSimplePayload() {
        String json = "{\"headers\":{\"contentType\": \"kfc/SourceEventReceivedEvent\"}, \"payload\": {\"data\":\"abc\"}}";
        Message message = decoder.fromBytes(json.getBytes());
        assertEquals(3, message.getHeaders().size());
        assertNotNull(message.getHeaders().get(MessageHeaders.ID));
        assertNotNull(message.getHeaders().get(MessageHeaders.TIMESTAMP));
        assertEquals("kfc/SourceEventReceivedEvent", message.getHeaders().get(MessageHeaders.CONTENT_TYPE));
        assertEquals("{\"data\":\"abc\"}", message.getPayload().toString());
    }

}
