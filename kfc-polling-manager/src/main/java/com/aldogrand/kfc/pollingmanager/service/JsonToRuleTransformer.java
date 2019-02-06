package com.aldogrand.kfc.pollingmanager.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;


public class JsonToRuleTransformer {

    private ObjectMapper objectMapper;

    public static final String HEADERS = "headers";
    public static final String PAYLOAD = "payload";

    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    private static final Logger LOG = Logger.getLogger(JsonToRuleTransformer.class);

    /**
     * Set object mapper for parsing JSON data.
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Perform transformation of incoming JSON data into a Rule.
     * 
     * @param message SI message with JsonNode payload
     * @return SI Message containing transformed Rule
     * @throws IOException
     */
    public Message<Rule> transform(Message<TreeNode> message) {
        if (message != null && message.getPayload() != null) {
            String msg = "Transforming the Rule message ::" + message;
            LOG.debug(msg);

            try {
                Rule rule;


                TreeNode payload = message.getPayload();
                if (payload == null) {
                    LOG.error("payload is null.");
                    String msgPayloadNull = String.format("payload is null.");
                    if (errorChannel != null) {

                        errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgPayloadNull));

                    }

                }
                MessageHeaders msgHeaders = message.getHeaders();
                if (msgHeaders == null) {
                    String msgHeadersNull = String.format("headers is null.");
                    LOG.error(msgHeadersNull);


                    if (errorChannel != null) {

                        errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgHeadersNull));

                    }


                }

                rule = objectMapper.readValue(payload.toString(), Rule.class);
                MessageBuilder<Rule> builder = MessageBuilder.withPayload(rule);
                builder.copyHeaders(msgHeaders);

                return builder.build();

            } catch (JsonParseException ex) {
                String msgJsonParseException = "JsonParseException when transforming: " + ex.getMessage();
                LOG.error(msgJsonParseException);


                if (errorChannel != null) {
                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgJsonParseException, ex));

                }


            } catch (JsonMappingException ex) {
                String msgJsonMappingException = "JsonParseException when transforming: " + ex.getMessage();
                LOG.error(msgJsonMappingException);


                if (errorChannel != null) {
                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgJsonMappingException, ex));

                }
            } catch (IOException ex) {
                String msgIoException = "JsonParseException when transforming: " + ex.getMessage();
                LOG.error(msgIoException);
                if (errorChannel != null) {
                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgIoException, ex));

                }

            }

        } else {
            String msg = "Message/payload is null; message: " + message;
            LOG.error(msg);
            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg));

            }
        }
        return null;
    }

}