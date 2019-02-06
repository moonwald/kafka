package com.aldogrand.kfc.pollingmanager.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 *
 * <p>
 * <b>Title</b> MessageDecoder.java.
 * </p>
 * <p>
 * <b>Description</b> Transform an SI Message to Message using Jackson 2.x library (fasterxml)
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 *
 * @author dlehane
 *
 */
public class MessageDecoder {

    private ObjectMapper mapper;

    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    private static final Logger LOG = Logger.getLogger(MessageDecoder.class);

    public MessageDecoder() {
        mapper = new ObjectMapper();
    }

    /**
     * Transform an SI Message to Message using Jackson 2.x library
     * 
     * @param bytes input data
     * @return SI Message with headers configured
     */
    public Message<JsonNode> stringToJsonNode(Message<String> msg) {
        if (msg != null) {
            try {
                JsonNode payload = mapper.readTree(msg.getPayload());
                LOG.debug("Decoding consumer payload message: " + payload);

                MessageHeaders msgHeaders = msg.getHeaders();
                if (msgHeaders == null) {

                    String msgHeadersNull = "No headers given: " + payload;
                    LOG.warn(msgHeadersNull);

                    if (errorChannel != null) {
                        errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgHeadersNull));
                    }
                }

                if (payload == null) {
                    String msgNoPayloadGiven = "No payload given: " + payload;
                    LOG.error(msgNoPayloadGiven);
                    if (errorChannel != null) {
                        errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgNoPayloadGiven));
                    }
                    return null;
                }

                MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(payload);

                // Set the existing headers on the new message.
                builder.copyHeadersIfAbsent(msgHeaders);

                return builder.build();

            } catch (IOException ex) {
                String msgErrorReadinBytes = "Error reading message bytes: " + ex.getMessage();
                LOG.error(msgErrorReadinBytes);
                if (errorChannel != null) {
                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgErrorReadinBytes, ex));
                }
            }
        } else {
            String msgIsNull = "Message is null";
            LOG.error(msgIsNull);
            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgIsNull));
            }
        }
        return null;
    }

}
