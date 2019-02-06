//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Decoding of Kafka's bytes into a SI Message, JSON is expected with two root elements: 'header' and 'payload'.
 * IllegalArgumentException is thrown if the structure does not follow this pattern.
 * Please note: only text header values are supported at the moment.
 */
public class MessageDecoder implements Decoder<Message<JsonNode>> {

    private ObjectMapper mapper;
    private Set<String> ignoredHeaders;

    public static final String HEADERS = "headers";
    public static final String PAYLOAD = "payload";

    private static final Logger LOG = Logger.getLogger(MessageDecoder.class);

    public MessageDecoder(VerifiableProperties props){
    	this();
    }
    public MessageDecoder() {
        mapper = new ObjectMapper();
        ignoredHeaders = new HashSet<>();
        ignoredHeaders.add(MessageHeaders.ID);
        ignoredHeaders.add(MessageHeaders.TIMESTAMP);
    }

    /**
     * Perform decofing of bytes to SI Message,
     * @param bytes input data
     * @return SI Message with headers configured
     */
    @Override
    public Message fromBytes(byte[] bytes) {
        try {
            String content = new String(bytes);
            LOG.debug("Decoding consumer message: "+content);
            
            JsonNode node = mapper.readTree(content);
            JsonNode headers = node.get(HEADERS);
            //LOG.info("MD key:"+headers.get("KEY")+" T:"+Thread.currentThread().getName());
            if (headers == null) {
                throw new IllegalArgumentException("No headers given: "+node);
            }
            JsonNode payload = node.get(PAYLOAD);
            if (payload == null) {
                throw new IllegalArgumentException("No payload given: "+node);
            }

            MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(payload);
            Iterator<Map.Entry<String, JsonNode>> fields = headers.getFields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (!ignoredHeaders.contains(field.getKey())) {
                    builder.setHeader(field.getKey(), field.getValue().getTextValue());
                }
            }

            return builder.build();

        } catch (IOException ex) {
            throw new IllegalStateException("Error reading message bytes", ex);
        }
    }

}