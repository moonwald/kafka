//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.datawriter;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Tests the locally (embeddded) running kafka server by sending and receiving a message to/from test topic.
 * The server is running locally in the test VM.
 */
@RunWith(MockitoJUnitRunner.class)
public class KafkaLocalServerTest {

    private KafkaLocalServer kafka;

    public static final String CLIENT_ID = "test-client";

    @Before
    public void before() throws Exception {
        kafka = new KafkaLocalServer();
        kafka.startup();
    }

    @After
    public void after() throws Exception {
        kafka.shutdown();
    }

    @Test
    @Ignore
    public void testSendReceive() throws Exception {
        sendMessage(new KeyedMessage<String, String>("test", "message1"));
        assertEquals("message1", readMessage("test"));
    }

    private String readMessage(String topicName) throws UnsupportedEncodingException {
        SimpleConsumer consumer = new SimpleConsumer(kafka.getHost(), kafka.getPort(), 100000, 64 * 1024, CLIENT_ID);
        FetchRequest req = new FetchRequestBuilder().clientId(CLIENT_ID).addFetch(topicName, 0, 0, 100000).build();
        ByteBufferMessageSet messageAndOffsets = consumer.fetch(req).messageSet(topicName, 0);
        ByteBuffer payload = messageAndOffsets.iterator().next().message().payload();
        byte[] bytes = new byte[payload.limit()];
        payload.get(bytes);
        return new String(bytes, "utf-8");
    }

    private void sendMessage(KeyedMessage<String, String> message) throws Exception {
        Properties props = new Properties();
        props.put("metadata.broker.list", kafka.getBrokerList());
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));
        producer.send(message);
    }

}

