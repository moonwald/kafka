package com.aldogrand.kfc.pollingmanager.acceptance;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.aldogrand.kfc.integration.KafkaClient;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PollingManagerSteps {

    private static final String TOPIC_REGEX = "Matchbook.DATA_REQ";
    private static final int TOPIC_READ_TIMEOUT = 5;
    private static final int TOPIC_READ_RETRIES = 4;

    private static final Logger LOG = LoggerFactory.getLogger(PollingManagerSteps.class);

    private KafkaClient kafkaConsumer;
    private ExecutorService executorService;
    private BlockingQueue<MessageAndMetadata<byte[], byte[]>> receivedMessages;
    private ReadContext receivedJson;

    private static String RULE_PATH = "/acceptance/allevents-sample.json";
    private static final String XD_RULE_PATH = "C:/Temp/allevents-sample.json";
    
    @Autowired
    @Qualifier("to-filter-service")
    private MessageChannel serviceChannel;


    @After
    public void after() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        receivedMessages.clear();
        kafkaConsumer.close();
        receivedJson = null;
    }

    @Given("^zookeeper runs at (.+)")
    public void theZookeeperRunsAt(String zookeeperUri) throws Throwable {
        theZookeeperSetup(zookeeperUri, new Whitelist(TOPIC_REGEX));
    }

    private void theZookeeperSetup(String zookeeperUri, final TopicFilter filter) {
        if (this.receivedMessages != null) {
            throw new IllegalStateException("the zookeeper is already configured");
        }

        this.receivedMessages = new LinkedBlockingQueue<>();
        kafkaConsumer = KafkaClient.createConsumerClient(zookeeperUri);
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(createKafkaRunner(filter));
    }

    private Runnable createKafkaRunner(final TopicFilter filter) {
        return new Runnable() {
            public void run() {
                Iterator<MessageAndMetadata<byte[], byte[]>> msgIterator = kafkaConsumer.receive(filter);
                while (true) {
                    try {
                        if (msgIterator.hasNext()) {
                            MessageAndMetadata<byte[], byte[]> nextMessage = msgIterator.next();
                            receivedMessages.add(nextMessage);
                        }
                    } catch (Exception ex) {
                        LOG.error("Error reading Kafka", ex);
                    }
                }
            }
        };
    }

    @Given("^kafka receiver is up and running$")
    public void kafkaReceiverIsUpAndRunning() throws Throwable {
        Thread.sleep(1000);
    }

    @When("^getallevents rule is requested by rulesservice$")
    public void getAllsEventsRuleRequested() throws Throwable {
        URL resource = this.getClass().getResource(RULE_PATH);
        String file = resource.getFile();
        Path sourcePath = Paths.get(file.substring(1));
        Path destinationPath = Paths.get(XD_RULE_PATH);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        Thread.sleep(1000);
    }

    @Then("there should be a message (\\w+) sent to topic (.+)")
    public void thereShouldBeMsgSentToTopic(String contentType, String topicName) throws Exception {
        if (this.receivedMessages == null) {
            throw new IllegalStateException("Kafka consumer not configured, use \"the zookeeper runs at ...\" keyword first");
        }

        for (int i = 0; i < TOPIC_READ_RETRIES; i++) {
            MessageAndMetadata<byte[], byte[]> msg = this.receivedMessages.poll(TOPIC_READ_TIMEOUT, TimeUnit.SECONDS);
            if (msg != null) {
                String receivedMessage = new String(msg.message());
                LOG.info("Received at " + msg.topic() + ": " + receivedMessage);
                if (topicName.equals(msg.topic())) {
                    this.receivedJson = JsonPath.parse(receivedMessage);
                    String contentTypeField = this.receivedJson.read("$.headers.contentType");
                    if (contentType.equals(contentTypeField)) {
                        return;
                    }
                }
            }
        }

        throw new IllegalStateException("Message " + contentType + " not received at " + topicName);
    }

    @Then("^the message header (.+) should be (.+)$")
    public void theMessageHeaderShouldBe(String propertyJsonPath, String propertyValue) throws Exception {
        checkReceivedMessages();

        Assert.assertEquals(propertyValue, String.valueOf(this.receivedJson.read("$.headers." + propertyJsonPath)));
    }

    private void checkReceivedMessages() {
        if (this.receivedMessages == null) {
            throw new IllegalStateException("Kafka consumer not configured, use \"the zookeeper runs at ...\" keyword first");
        }
        if (this.receivedJson == null) {
            throw new IllegalStateException("No message received, use \"there should be a message ... sent to topic ...\" keyword first");
        }
    }
}
