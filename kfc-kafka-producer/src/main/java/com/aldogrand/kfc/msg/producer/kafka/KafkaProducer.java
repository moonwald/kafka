package com.aldogrand.kfc.msg.producer.kafka;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.Producer;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.interfaces.KeyGenerator;
import com.aldogrand.kfc.msg.interfaces.TopicGenerator;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.log4j.Logger;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.Properties;

/**
 * <p>
 * <b>Title</b> KafkaProducer
 * </p>
 * <p>
 * <b>Description</b> Kafka Producer Implementation.
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
public class KafkaProducer implements Producer {

	private final Logger		logger				= Logger.getLogger(getClass());

    private KeyGenerator keyGenerator;

    private TopicGenerator topicGenerator;

    protected kafka.javaapi.producer.Producer<String, Object> producer;

    private Properties properties;

    /**
     * Constructs the Kafka Producer.<br/>
     * @params {@topicGenerator}
     * @params {@keyGenerator}
     * @params {@properties}
     * @exception java.io.IOException
     */
    public KafkaProducer(TopicGenerator topicGenerator, KeyGenerator keyGenerator, Properties properties) {
        try {
            this.setProperties(properties);
            this.setKeyGenerator(keyGenerator);
            this.setTopicGenerator(topicGenerator);
            ProducerConfig config = new ProducerConfig(getProperties());
            producer = new kafka.javaapi.producer.Producer<String, Object>(config);
        } catch (NullPointerException ne) {
            throw new NullPointerException();
        }
    }

    /**
     * Hidden implementation for a send method for the Kafka Producer.<br/>
     * @params {@topic}
     * @params {@key}
     * @params {@message}
     * @exception com.aldogrand.kfc.exception.ProducerException
     */
    @Override
    public void send (Message message, String topic, String key) throws ProducerException {
        try {
        // CreateKeyedMessage
        KeyedMessage<String, Object> data = createKeyedMessage (new StringBuilder(topic), new StringBuilder(key), message);

        // send
        producer.send(data);
        } catch (RuntimeException pe) {
            throw new ProducerException(pe);
        }
    }

    /**
     * Implementation for a send method for the Kafka Producer.<br/>
     * @params {@message}
     * @exception com.aldogrand.kfc.exception.ProducerException
     */
    @Override
    public void send (Message message) throws ProducerException {
        StringBuilder topic = new StringBuilder("");
        StringBuilder key = new StringBuilder("");

        try {
            // CreateKeyedMessage
            topic = new StringBuilder(topicGenerator.generateTopic(message));
            key = new StringBuilder(keyGenerator.generateKey(message));
            Message m = MessageBuilder.withPayload(message.getPayload())
            		.setHeader("KEY", key)
            		.setHeader("TOPIC", topic)
            .copyHeaders(message.getHeaders())
            .build();
            KeyedMessage<String, Object> data = createKeyedMessage(topic, key, m);

            // send
            producer.send(data);
        } catch (RuntimeException pe) {
        	logger.error(message);
            throw new ProducerException(pe);
        }
    }

    /**
     * Keyed Message creation method for the message to be sent to the Kafka bus..<br/>
     * @params {@topicName}
     * @params {@partitionKey}
     * @params {@message}
     * @return KeyedMessage<String, Object>
     * @exception com.aldogrand.kfc.exception.ProducerException
     */
    private KeyedMessage<String, Object> createKeyedMessage(StringBuilder topicName, StringBuilder partitionKey, Message message) {
        KeyedMessage<String, Object> kMessage = new KeyedMessage<String, Object>(topicName.toString(), partitionKey.toString(), message);
        return kMessage;
    }

    /**
     * Gets the Link{@KeyGenerator}<br/>
     * @return KeyGenerator
     */
    public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    /**
     * Sets the Link{@KeyGenerator<br/>
     * @param keyGenerator
     */
    public void setKeyGenerator(KeyGenerator keyGenerator) throws NullPointerException {
        this.keyGenerator = keyGenerator;
    }

    /**
     * Gets the Link{@TopicGenerator}<br/>
     * @return TopicGenerator
     */
    public TopicGenerator getTopicGenerator() {
        return topicGenerator;
    }

    /**
     * Sets the Link{@TopicGenerator<br/>
     * @param topicGenerator
     */
    public void setTopicGenerator(TopicGenerator topicGenerator) throws NullPointerException {
        this.topicGenerator = topicGenerator;
    }

    /**
     * Gets the Link{@Properties}<br/>
     * @return Properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Sets the Link{@Properties<br/>
     * @param properties
     */
    public void setProperties(Properties properties) throws NullPointerException {
        this.properties = properties;
    }
}
