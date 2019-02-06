package com.aldogrand.kfc.pollingmanager.service.generator;

import org.springframework.messaging.Message;

import com.aldogrand.kfc.msg.events.integration.Command;
import com.aldogrand.kfc.msg.interfaces.TopicGenerator;

public class EventTopicGenerator implements TopicGenerator {

    public static final String DATA_REQUEST = ".DATA_REQ";

    public EventTopicGenerator() {}

    @Override
    public String generateTopic(Message<?> message) {
        Command<?> command = (Command<?>) message.getPayload();
        String integrationModuleName = command.getIntegrationModuleName();
        return integrationModuleName + DATA_REQUEST;
    }
}
