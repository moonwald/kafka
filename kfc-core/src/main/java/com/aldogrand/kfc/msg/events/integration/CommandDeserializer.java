package com.aldogrand.kfc.msg.events.integration;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aldogrand.kfc.msg.events.KFCEvent;

public class CommandDeserializer {

    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <C> KFCEvent transform(Message<JsonNode> message) throws IOException {
        return objectMapper.readValue(message.getPayload().toString(), new TypeReference<Command<C>>() {});
    }
}
