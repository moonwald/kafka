package com.aldogrand.kfc.msg.producer.kafka;

import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.Producer;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.KFCEventFactoryImpl;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.interfaces.KeyGenerator;
import com.aldogrand.kfc.msg.interfaces.TopicGenerator;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.consumer.SimpleConsumer;
import kafka.message.ByteBufferMessageSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Properties;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/producer-test.xml"})
public class KafkaProducerTest {
	
	public static final String CLIENT_ID = "test-client";


    @Autowired
    Producer producer;
    @Autowired
    TopicGenerator topicGenerator;
    @Autowired
    KeyGenerator keyGenerator;

    @Autowired
    private KafkaLocalServer kafkaLocalServer;

    // Attributes
    private Event event;
    private KFCEventFactory kfcMessage;
    private SourceEventReceivedEvent eventReceived = new SourceEventReceivedEvent();

    // Attributes for the Event.
    private String eventId = "100";
    private String eventName = "Event name";
    private Date eventTime = new Date();

    @Before
    public void before() throws Exception {
        this.kafkaLocalServer.startup();

        event = new Event();
        event.setId(eventId);
        event.setName(eventName);
        event.setStartTime(eventTime);
        eventReceived.setEvent(event);

        kfcMessage = new KFCEventFactoryImpl();
    }

    @After
    public void after() throws Exception {
        this.kafkaLocalServer.shutdown();
    }

    @Test
    @Ignore
    public void producerContructorTest() throws ProducerException, IOException {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "0:20002");
        properties.put("serializer.class", "com.aldogrand.kfc.msg.producer.kafka.kfcMessageSerializer");
        properties.put("key.serializer.class", "kafka.serializer.StringEncoder");
        properties.put("producer.type", "sync");
        properties.put("partitioner.class", "com.aldogrand.kfc.msg.producer.kafka.DefaultPartitioner");
        properties.put("request.required.acks", "1");
        Producer producer = new KafkaProducer(topicGenerator, keyGenerator, properties);
        Mockito.spy(producer);
    }

    @Test (expected = NullPointerException.class)
    @Ignore
    public void producerPropertiesNullContructorTest() throws ProducerException, IOException {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "0:20002");
        properties.put("serializer.class", "com.aldogrand.kfc.msg.producer.kafka.kfcMessageSerializer");
        properties.put("key.serializer.class", "kafka.serializer.StringEncoder");
        properties.put("producer.type", "sync");
        properties.put("partitioner.class", "com.aldogrand.kfc.msg.producer.kafka.DefaultPartitioner");
        properties.put("request.required.acks", "1");
        Producer producer = new KafkaProducer(topicGenerator, keyGenerator, null);
    }

    @Test
    @Ignore
    public void createKeyedMessageTest() throws ProducerException, IOException {
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);
        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(new String("topic-1"));
        Mockito.when(keyGenerator.generateKey(m)).thenReturn(new String("0"));
        producer.send(m, topicGenerator.generateTopic(m), keyGenerator.generateKey(m));
        assertTrue(true);
    }

    @Test
    @Ignore
    public void createNumberKeyedMessageTest() throws ProducerException, IOException {
    	eventReceived.getEvent().setId("123455677890887657689711418");
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);
        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(new String("topic-1"));
        Mockito.when(keyGenerator.generateKey(m)).thenReturn(new String("0"));
        producer.send(m, topicGenerator.generateTopic(m), keyGenerator.generateKey(m));
        assertTrue(true);
    }
    
    @Test
    @Ignore
    public void createDoubleMessageTest() throws ProducerException, IOException {
    	eventReceived.getEvent().setId("123455677890887.6576897114180000000000000000");
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);
        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(new String("topic-1"));
        Mockito.when(keyGenerator.generateKey(m)).thenReturn(new String("0"));
        producer.send(m, topicGenerator.generateTopic(m), keyGenerator.generateKey(m));
        assertTrue(true);
    }
    
    @Test
    @Ignore
    public void createStringMessageTest() throws ProducerException, IOException {
    	eventReceived.getEvent().setId("yuewqqtrgqwgfboiqcunhrfxzqhng");
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);
        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(new String("topic-1"));
        Mockito.when(keyGenerator.generateKey(m)).thenReturn(new String("0"));
        producer.send(m, topicGenerator.generateTopic(m), keyGenerator.generateKey(m));
        assertTrue(true);
    }
    
    @Test
    @Ignore
    public void createWeirdStringMessageTest() throws ProducerException, IOException {
    	eventReceived.getEvent().setId("/*-+,./,><><';:{:}{}@]'|'\'__--==++##~~''@@;;::/?.>,<)(*&^%$£!¬1	11`!3!");
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);
        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(new String("topic-1"));
        Mockito.when(keyGenerator.generateKey(m)).thenReturn(new String("0"));
        producer.send(m, topicGenerator.generateTopic(m), keyGenerator.generateKey(m));
        assertTrue(true);
    }
    
    @Test (expected = RuntimeException.class)
    @Ignore
    public void producerExceptionSendXTests () throws ProducerException, IOException {
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);
        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(new String("topic-1"));
        producer.send(m, topicGenerator.generateTopic(m), keyGenerator.generateKey(m));
    }

	@Test
	@Ignore
	public void sendTest() throws Exception {
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);

        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(new String("topic-1"));
        Mockito.when(keyGenerator.generateKey(m)).thenReturn(new String("0"));

        producer.send(m);

        String msg = readInternalMessage("topic-1");
        // Check that we received a message (not null) with the simple consumer, so it has consume the produce message.
        Assert.notNull(msg);
    }

    @Test (expected = RuntimeException.class)
    @Ignore
    public void sendFailTest() throws Exception {
        Message<KFCEvent> m = kfcMessage.createMessage(eventReceived);

        Mockito.when(topicGenerator.generateTopic(m)).thenReturn(null);
        Mockito.when(keyGenerator.generateKey(m)).thenReturn(new String("0"));

        producer.send(null);
    }

    public String readInternalMessage(String topicName) throws UnsupportedEncodingException {
        try {
            SimpleConsumer consumer = new SimpleConsumer("localhost", 20002, 1000, 64 * 1024, CLIENT_ID);
            FetchRequest req = new FetchRequestBuilder().clientId(CLIENT_ID).addFetch(topicName, 0, 0, 100000).build();
            ByteBufferMessageSet messageAndOffsets = consumer.fetch(req).messageSet(topicName, 0);
            ByteBuffer payload = messageAndOffsets.iterator().next().message().payload();
            byte[] bytes = new byte[payload.limit()];
            payload.get(bytes);
            return new String(bytes, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
