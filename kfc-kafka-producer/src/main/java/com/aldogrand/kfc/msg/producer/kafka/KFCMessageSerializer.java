package com.aldogrand.kfc.msg.producer.kafka;

import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.messaging.Message;

import java.io.IOException;

/**
 * <p>
 * <b>Title</b> kfcMessageSerializer
 * </p>
 * <pre>
 * This serialiser has two constructor but they don't do anything no implementation.
 * 1. {@link #kfcMessageSerializer(VerifiableProperties)} is used by the kafka to initialise the encoder and decoder
 * 2. {@link #kfcMessageSerializer()} can be used by the outer world to initialize the serializer
 * </pre>
 * <p>
 * <b>Description</b> Serializer / Deserializer for Kafka.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public class KFCMessageSerializer implements Encoder<Message<?>>, Decoder<Message <?>> {


	/**
	 * for kafka
	 * @param props
	 */
	public KFCMessageSerializer(VerifiableProperties props) {}
	/**
	 * default constructor for outer world
	 */
	public KFCMessageSerializer(){}
	
	/**
	 * Making ObjectMapper instance variable for the following reasons.
	 * 1. ObjectMapper is thread safe.
	 * 2. It is a strong dependency and we can not have a single constructor to inject it using spring. as one constructor is already being used by kafka.
	 * 3. Did not make it static as it might slow down the system for serialization and de-serialization
	 * having another constructor with Object is of no use and will cause confusion.
	 */
    private ObjectMapper objectMapper = new ObjectMapper().configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

    /**
     * Serialize the {@link Message} sent to the kafka bus.. The serialization is an array of byte that assembles a Json.<br/>
     * @param message
     * @return byte[]
     * @exception java.io.IOException
     */
    @Override
    public byte[] toBytes(Message message) {
        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * Deserialize the {@link Message} grab from the kafka bus..<br/>
     * @param bytes
     * @return {@link Message}
     * @exception java.io.IOException
     */
    @Override
    public Message fromBytes(byte[] bytes) {
        Message message = null;
        try {
            message = objectMapper.readValue(bytes, Message.class);
        } catch (IOException e) {
        }
        return message;
    }
}
