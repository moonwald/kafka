package com.aldogrand.kfc.pollingmanager.service.generator;

import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class MessageSerializer implements Encoder<Message<?>>, Decoder<Message<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSerializer.class);
    
    public MessageSerializer(VerifiableProperties props) {
    }

    public MessageSerializer() {
    }

    private ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Override
    public byte[] toBytes(Message<?> message) {
        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (IOException ioException) {
            LOGGER.error("Could not read to bytes in MessageSerialiser", ioException);
        }
        return null;
    }

    @Override
    public Message<?> fromBytes(byte[] bytes) {
        Message<?> message = null;
        try {
            message = objectMapper.readValue(bytes, Message.class);
        } catch (IOException ioException) {
            LOGGER.error("Could not read from bytes in MessageSerialiser", ioException);
        }
        return message;
    }
}
