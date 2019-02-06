package com.aldogrand.kfc.pollingmanager.integration;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import kafka.consumer.TopicFilter;
import kafka.consumer.Whitelist;
import kafka.message.MessageAndMetadata;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.aldogrand.kfc.integration.KafkaClient;
import com.aldogrand.kfc.msg.events.integration.CommandType;
import com.aldogrand.kfc.msg.producer.kafka.KafkaProducer;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.model.EventAttributesMother;
import com.aldogrand.kfc.pollingmanager.rules.Rule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager.xml"})
public class FilterToKafkaTest {

    private static final String CONNECTOR_TOPIC_REGEX = "Matchbook.DATA_REQ|ThreeEt.DATA_REQ";
    private static final String ZOOKEEPER_URI = "localhost:2181";

    @Autowired
    @Qualifier("to-filter-service")
    private MessageChannel serviceChannel;

    @Autowired
    private KafkaProducer kafkaProducer;

    private KafkaClient kafkaConsumer;
    private ExecutorService executorService;
    private BlockingQueue<MessageAndMetadata<byte[], byte[]>> receivedMessages;

    private EventAttributes eventAttributes;


    @Before
    public void setup() throws Exception {
        this.receivedMessages = new LinkedBlockingQueue<>();
        kafkaConsumer = KafkaClient.createConsumerClient(ZOOKEEPER_URI);
        executorService = Executors.newSingleThreadExecutor();
    }

    @After
    public void after() throws Exception {
        executorService.shutdownNow();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        receivedMessages.clear();
        kafkaConsumer.close();
    }

    @Test
    public void sendAllEventsMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_EVENTS_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_EVENTS_COMMAND);
    }

    @Test
    public void sendAllEventsThreeetMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createThreeetAllEventsAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_EVENTS_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_EVENTS_COMMAND);
    }

    @Test
    public void sendOneEventMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookOneEventAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_EVENT_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_EVENT_COMMAND);
    }

    @Test
    public void sendOneEventThreeetMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createThreeetOneEventAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_EVENT_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_EVENT_COMMAND);
    }

    @Test
    public void sendAllMarketsMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookAllMarketsAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_MARKETS_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_MARKETS_COMMAND);
    }

    @Test
    public void sendAllRunnersMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookAllRunnersAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_RUNNERS_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_RUNNERS_COMMAND);
    }

    @Test
    public void sendOneRunnerMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookOneRunnerAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_RUNNER_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_RUNNER_COMMAND);
    }

    @Test
    public void sendAllPricesMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookAllPricesAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_PRICES_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_PRICES_COMMAND);
    }

    @Test(expected = Exception.class)
    public void sendRefreshPricesMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookOnePriceAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_PRICES_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());
    }

    @Test
    public void sendAllPositionsMatchbookMessage() throws InterruptedException {
        // Given
        executorService.execute(createKafkaRunner(new Whitelist(CONNECTOR_TOPIC_REGEX)));
        eventAttributes = EventAttributesMother.createMatchbookAllPositionsAttributes();
        MessageBuilder<Rule> builder = MessageBuilder.withPayload(eventAttributes.getRule());
        builder.setHeader(MessageHeaders.CONTENT_TYPE, CommandType.FETCH_POSITIONS_COMMAND.toString());

        // When
        serviceChannel.send(builder.build());

        // Then
        Thread.sleep(2000);
        assertMessageType(receivedMessages.iterator().next(), CommandType.FETCH_POSITIONS_COMMAND);
    }

    private void assertMessageType(MessageAndMetadata<byte[], byte[]> message, CommandType commandType) {
        String receivedMessage = new String(message.message());
        ReadContext receivedJson = JsonPath.parse(receivedMessage);
        String contentTypeField = receivedJson.read("$.headers.contentType");
        Assert.assertEquals(commandType.toString(), contentTypeField);
    }

    private Runnable createKafkaRunner(final TopicFilter filter) {
        return new Runnable() {
            public void run() {
                Iterator<MessageAndMetadata<byte[], byte[]>> messageIterator = kafkaConsumer.receive(filter);
                while (true) {
                    if (messageIterator.hasNext()) {
                        MessageAndMetadata<byte[], byte[]> nextMessage = messageIterator.next();
                        receivedMessages.add(nextMessage);
                    }
                }
            }
        };
    }
}
