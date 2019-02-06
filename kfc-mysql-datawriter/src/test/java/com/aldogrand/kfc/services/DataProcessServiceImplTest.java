package com.aldogrand.kfc.services;


import com.aldogrand.sbpc.dataaccess.ConnectorDao;
import com.aldogrand.sbpc.dataaccess.SourceEventDao;
import com.aldogrand.sbpc.dataaccess.SourceMarketDao;
import com.aldogrand.sbpc.dataaccess.SourceRunnerDao;
import com.aldogrand.sbpc.dataaccess.model.*;
import com.aldogrand.sbpc.model.*;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.services.exception.DataProcessException;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.javaapi.*;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/kfc-data-update-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DataProcessServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Autowired
    private SourceEventDao sourceEventDao;
    @Autowired
    private SourceMarketDao sourceMarketDao;
    @Autowired
    private SourceRunnerDao sourceRunnerDao;
    @Autowired
    private ConnectorDao connectorDao;

    @Autowired
    @Qualifier("LMAXInputChannelDataProcessor")
    private MessageChannel inputChannel;

    @Autowired
    @Qualifier("errorChannel")
    private SubscribableChannel errorChannel;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Date dateNow = new Date ();

    private Boolean firstTime = true;

    @Before
    public void beforeEachTest () throws InterruptedException {
    }

    @After
    public void after() {

    }

    @AfterClass
    public static void deleteLog() {
        File file = new File("error.log");
        file.delete();
    }

    @Transactional
    private void insertRunnerTestData() {
        // Connectors initial input
        ConnectorDto connectorDto = new ConnectorDto();
        connectorDto.setId(31L);
        connectorDto.setName("Creator Connector for Runner Test");
        connectorDto.setEnabled(true);
        connectorDto.setEventContributor(true);
        entityManagerFactory.persist(connectorDto);
        entityManagerFactory.flush();

        ConnectorDto connectorDto2 = new ConnectorDto();
        connectorDto2.setId(32L);
        connectorDto2.setName("Not Creator Connector for Runner Test");
        connectorDto2.setEnabled(true);
        connectorDto2.setEventContributor(false);
        entityManagerFactory.persist(connectorDto2);
        entityManagerFactory.flush();


        // Events initial input
        EventDto event = new EventDto();
        event.setId(399990L);
        event.setName("Source Event Created Mapped and Creator for Runner Test");
        event.setStartTime(dateNow);
        event.setEventStatus(EventStatus.OPEN);
        entityManagerFactory.persist(event);
        entityManagerFactory.flush();

        SourceEventDto sourceEvent = new SourceEventDto();
        sourceEvent.setId(399990L);
        sourceEvent.setConnector(connectorDto);
        sourceEvent.setEvent(event);
        sourceEvent.setSourceId("310000");
        sourceEvent.setSourceName("Source Event Created Mapped and Creator for Runner Test");
        sourceEvent.setCreator(true);
        sourceEvent.setStartTime(dateNow);
        sourceEvent.setStatus(EventStatus.OPEN);
        entityManagerFactory.persist(sourceEvent);
        entityManagerFactory.flush();


        // Markets initial input
        MarketDto market = new MarketDto();
        market.setId(388880L);
        market.setName("Source Market Created Mapped and Creator for Runner Test");
        market.setType(MarketType.TWO_WAY);
        market.setHandicap(0.5);
        market.setPeriod(Period.FULL_EVENT);
        market.setMarketStatus(MarketStatus.OPEN);
        market.setEvent(event);

        entityManagerFactory.persist(market);
        entityManagerFactory.flush();

        SourceMarketDto sourceMarket = new SourceMarketDto();
        sourceMarket.setId(388880L);
        sourceMarket.setConnector(connectorDto);
        sourceMarket.setMarket(market);
        sourceMarket.setSourceId("310000");
        sourceMarket.setSourceName("Test Source Name");
        sourceMarket.setCreator(true);
        sourceMarket.setHandicap(0.5);
        sourceMarket.setPeriod(Period.FULL_EVENT);
        sourceMarket.setType(MarketType.TWO_WAY);
        sourceMarket.setMarketStatus(MarketStatus.OPEN);
        sourceMarket.setSourceEvent(sourceEvent);

        entityManagerFactory.persist(sourceMarket);
        entityManagerFactory.flush();

        // Runners initial input
        RunnerDto runner = new RunnerDto();
        runner.setId(77770L);
        runner.setName("Source Runner Created Mapped and Creator for Runner Test");
        runner.setSequence(10);
        runner.setType(RunnerType.OVER);
        runner.setSide(RunnerSide.UNKNOWN);
        runner.setHandicap(0.5);
        runner.setRotationNumber(1);
        runner.setRunnerStatus(RunnerStatus.OPEN);
        runner.setResultStatus(ResultStatus.WINNER);
        runner.setMarket(market);

        entityManagerFactory.persist(runner);
        entityManagerFactory.flush();

        SourceRunnerDto sourceRunner = new SourceRunnerDto();
        sourceRunner.setId(77770L);
        sourceRunner.setRunner(runner);
        sourceRunner.setConnector(connectorDto);
        sourceRunner.setSourceId("10000");
        sourceRunner.setSourceName("Source Runner Created Mapped and Creator for Runner Test");
        sourceRunner.setCreator(true);
        sourceRunner.setHandicap(0.5);
        sourceRunner.setSequence(10);
        sourceRunner.setRunnerStatus(RunnerStatus.OPEN);
        sourceRunner.setResultStatus(ResultStatus.WINNER);
        sourceRunner.setSide(RunnerSide.AWAY);
        sourceRunner.setType(RunnerType.UNKNOWN);
        sourceRunner.setSourceMarket(sourceMarket);

        entityManagerFactory.persist(sourceRunner);
        entityManagerFactory.flush();
    }

    @Transactional
    private void insertMarketTestData() {
        // Connectors initial input
        ConnectorDto connectorDto = new ConnectorDto();
        connectorDto.setId(21L);
        connectorDto.setName("Creator Connector for Market");
        connectorDto.setEnabled(true);
        connectorDto.setEventContributor(true);
        entityManagerFactory.persist(connectorDto);
        entityManagerFactory.flush();

        ConnectorDto connectorDto2 = new ConnectorDto();
        connectorDto2.setId(22L);
        connectorDto2.setName("Not Creator Connector for Market");
        connectorDto2.setEnabled(true);
        connectorDto2.setEventContributor(false);
        entityManagerFactory.persist(connectorDto2);
        entityManagerFactory.flush();


        // Events initial input
        EventDto event = new EventDto();
        event.setId(299990L);
        event.setName("Source Event Created Mapped and Creator for Market");
        event.setStartTime(dateNow);
        event.setEventStatus(EventStatus.OPEN);
        entityManagerFactory.persist(event);
        entityManagerFactory.flush();

        SourceEventDto sourceEvent = new SourceEventDto();
        sourceEvent.setId(299990L);
        sourceEvent.setConnector(connectorDto);
        sourceEvent.setEvent(event);
        sourceEvent.setSourceId("210000");
        sourceEvent.setSourceName("Source Event Created Mapped and Creator for Market");
        sourceEvent.setCreator(true);
        sourceEvent.setStartTime(dateNow);
        sourceEvent.setStatus(EventStatus.OPEN);
        entityManagerFactory.persist(sourceEvent);
        entityManagerFactory.flush();


        // Markets initial input
        MarketDto market = new MarketDto();
        market.setId(88880L);
        market.setName("Source Market Created Mapped and Creator for Market");
        market.setType(MarketType.TWO_WAY);
        market.setHandicap(0.5);
        market.setPeriod(Period.FULL_EVENT);
        market.setMarketStatus(MarketStatus.OPEN);
        market.setEvent(event);

        entityManagerFactory.persist(market);
        entityManagerFactory.flush();

        SourceMarketDto sourceMarket = new SourceMarketDto();
        sourceMarket.setId(88880L);
        sourceMarket.setConnector(connectorDto);
        sourceMarket.setMarket(market);
        sourceMarket.setSourceId("10000");
        sourceMarket.setSourceName("Test Source Name for Market");
        sourceMarket.setCreator(true);
        sourceMarket.setHandicap(0.5);
        sourceMarket.setPeriod(Period.FULL_EVENT);
        sourceMarket.setType(MarketType.TWO_WAY);
        sourceMarket.setMarketStatus(MarketStatus.OPEN);
        sourceMarket.setSourceEvent(sourceEvent);

        entityManagerFactory.persist(sourceMarket);
        entityManagerFactory.flush();
    }

    @Transactional
    private void insertEventTestData() {
        System.out.println ("1");
        // Connectors initial input
        ConnectorDto connectorDto = new ConnectorDto();
        connectorDto.setId(1L);
        connectorDto.setName("Creator Connector");
        connectorDto.setEnabled(true);
        connectorDto.setEventContributor(true);
        entityManagerFactory.persist(connectorDto);
        entityManagerFactory.flush();

        ConnectorDto connectorDto2 = new ConnectorDto();
        connectorDto2.setId(2L);
        connectorDto2.setName("Not Creator Connector");
        connectorDto2.setEnabled(true);
        connectorDto2.setEventContributor(false);
        entityManagerFactory.persist(connectorDto2);
        entityManagerFactory.flush();


        // Events initial input
        EventDto event = new EventDto();
        event.setId(99990L);
        event.setName("Source Event Created Mapped and Creator");
        event.setStartTime(dateNow);
        event.setEventStatus(EventStatus.OPEN);
        entityManagerFactory.persist(event);
        entityManagerFactory.flush();

        SourceEventDto sourceEvent = new SourceEventDto();
        sourceEvent.setId(99990L);
        sourceEvent.setConnector(connectorDto);
        sourceEvent.setEvent(event);
        sourceEvent.setSourceId("10000");
        sourceEvent.setSourceName("Source Event Created Mapped and Creator");
        sourceEvent.setCreator(true);
        sourceEvent.setStartTime(dateNow);
        sourceEvent.setStatus(EventStatus.OPEN);
        entityManagerFactory.persist(sourceEvent);
        entityManagerFactory.flush();
    }

    @Test
    @Transactional
    @Ignore
    public void processRunnersUpdatedTest() throws ClassNotFoundException, SQLException, InterruptedException, IOException, DataProcessException {
        insertRunnerTestData();
        long connectorId = connectorDao.getConnectors(0, 1).get(0).getId();

        // Message to be sent.
        String message = "{\n" +
                "        \"connector\": \"" + connectorId + "\",\n" +
                "        \"runner\": {\n" +
                "            \"id\": 77770,\n" +
                "            \"name\": \"Source Runner Created Mapped and Creator\",\n" +
                "            \"eventId\": 99990,\n" +
                "            \"eventName\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"marketId\": 88880,\n" +
                "            \"marketName\": \"Source Market Created Mapped and Creator\",\n" +
                "            \"type\": \"OVER\",\n" +
                "            \"side\": \"UNKNOWN\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"sequence\": 10,\n" +
                "            \"rotationNumber\": 1,\n" +
                "            \"runnerStatus\": \"CLOSED\",\n" +
                "            \"resultStatus\": \"WINNER\",\n" +
                "            \"prices\": [],\n" +
                "            \"mappings\": []\n" +
                "        },\n" +
                "        \"sourceRunner\": {\n" +
                "            \"id\": 77770,\n" +
                "            \"runnerId\": 77770,\n" +
                "            \"sourceMarketId\": 88880,\n" +
                "            \"sourceId\": \"10000\",\n" +
                "            \"sourceName\": \"Source Runner Created Mapped and Creator\",\n" +
                "            \"creator\":true,\n" +
                "            \"type\": \"OVER\",\n" +
                "            \"side\": \"UNKNOWN\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"sequence\": 10,\n" +
                "            \"rotationNumber\": 1,\n" +
                "            \"runnerStatus\": \"CLOSED\",\n" +
                "            \"resultStatus\": \"WINNER\",\n" +
                "            \"connectorId\":\"" + connectorId + "\"\n" +
                "        }}";

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_RUNNER_UPDATED);

        // Message is put into the channel
        inputChannel.send(builder.build());

        // Message is consume to check the test.
        String msg = readInternalMessage ("SOURCE_RUNNER_PROCESSED");
        System.out.println(msg);

        //Asserts
        assertTrue(msg.contains("SOURCE_RUNNER_PROCESSED"));
    }

    @Test
    @Transactional
    @Ignore
    public void processRunnersUpdatedNegativeTest() throws ClassNotFoundException, SQLException, InterruptedException, IOException, DataProcessException {
        long connectorId = 2L;

        String message = "{\n" +
                "        \"connector\": \"" + connectorId + "\",\n" +
                "        \"runner\": {\n" +
                "            \"id\": 77771,\n" +
                "            \"name\": \"Source Runner Created Mapped and Creator\",\n" +
                "            \"eventId\": 99990,\n" +
                "            \"eventName\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"marketId\": 88880,\n" +
                "            \"marketName\": \"Source Market Created Mapped and Creator\",\n" +
                "            \"type\": \"OVER\",\n" +
                "            \"side\": \"UNKNOWN\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"sequence\": 10,\n" +
                "            \"rotationNumber\": 1,\n" +
                "            \"runnerStatus\": \"OPEN\",\n" +
                "            \"resultStatus\": \"WINNER\",\n" +
                "            \"prices\": [],\n" +
                "            \"mappings\": []\n" +
                "        },\n" +
                "        \"sourceRunner\": {\n" +
                "            \"id\": 77771,\n" +
                "            \"runnerId\": 77770,\n" +
                "            \"sourceMarketId\": 88880,\n" +
                "            \"sourceId\": \"10000\",\n" +
                "            \"sourceName\": \"Source Runner Created Mapped and Creator\",\n" +
                "            \"creator\":true,\n" +
                "            \"type\": \"OVER\",\n" +
                "            \"side\": \"UNKNOWN\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"sequence\": 10,\n" +
                "            \"rotationNumber\": 1,\n" +
                "            \"runnerStatus\": \"OPEN\",\n" +
                "            \"resultStatus\": \"WINNER\",\n" +
                "            \"connectorId\":\"" + connectorId + "\"\n" +
                "        }}";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_RUNNER_UPDATED);
        inputChannel.send(builder.build());
        String msg = readInternalMessage ("SOURCE_RUNNER_PROCESSED");
        System.out.println(msg);
        assertFalse(msg.contains("77771"));
    }

    @Test
    @Transactional
    @Ignore
    public void processMarketsUpdatedTest() throws ClassNotFoundException, SQLException, InterruptedException, IOException, DataProcessException {

        String message = "{\n" +
                "        \"connector\": \"1\",\n" +
                "        \"market\": {\n" +
                "            \"id\": 88880,\n" +
                "            \"name\": \"Source Market Created Mapped and Creator\",\n" +
                "            \"eventId\": 99990,\n" +
                "            \"eventName\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"type\": \"TWO_WAY\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"period\": \"FULL_EVENT\",\n" +
                "            \"marketStatus\": \"CLOSED\",\n" +
                "            \"runners\": [],\n" +
                "            \"mappings\": []\n" +
                "        },\n" +
                "        \"sourceMarket\": {\n" +
                "            \"id\": 88880,\n" +
                "            \"marketId\": 88880,\n" +
                "            \"sourceEventId\": 99990,\n" +
                "            \"sourceId\": \"10000\",\n" +
                "            \"sourceName\": \"Source Market Created Mapped and Creator\",\n" +
                "            \"creator\":true,\n" +
                "            \"type\": \"TWO_WAY\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"period\": \"FULL_EVENT\",\n" +
                "            \"marketStatus\": \"CLOSED\",\n" +
                "            \"runners\": [],\n" +
                "            \"connectorId\": 1\n" +
                "        }\n" +
                "    }";

        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_MARKET_UPDATED);
        inputChannel.send(builder.build());
        String msg = readInternalMessage ("SOURCE_MARKET_PROCESSED");
        System.out.println(msg);
        assertTrue(msg.contains("SOURCE_MARKET_PROCESSED"));
    }

    @Test
    @Transactional
    @Ignore
    public void processMarketsUpdatedNegativeTest() throws ClassNotFoundException, SQLException, InterruptedException, IOException, DataProcessException {

        String message = "{\n" +
                "        \"connector\": \"2\",\n" +
                "        \"market\": {\n" +
                "            \"id\": 88881,\n" +
                "            \"name\": \"Source Market Created Mapped and Creator\",\n" +
                "            \"eventId\": 99990,\n" +
                "            \"eventName\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"type\": \"TWO_WAY\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"period\": \"FULL_EVENT\",\n" +
                "            \"marketStatus\": \"CLOSED\",\n" +
                "            \"runners\": [],\n" +
                "            \"mappings\": []\n" +
                "        },\n" +
                "        \"sourceMarket\": {\n" +
                "            \"id\": 88881,\n" +
                "            \"marketId\": 88881,\n" +
                "            \"sourceEventId\": 99990,\n" +
                "            \"sourceId\": \"10001\",\n" +
                "            \"sourceName\": \"Source Market Created Mapped and Creator\",\n" +
                "            \"creator\":true,\n" +
                "            \"type\": \"TWO_WAY\",\n" +
                "            \"handicap\": 0.5,\n" +
                "            \"period\": \"FULL_EVENT\",\n" +
                "            \"marketStatus\": \"CLOSED\",\n" +
                "            \"runners\": [],\n" +
                "            \"connectorId\": 2\n" +
                "        }\n" +
                "    }";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_MARKET_UPDATED);
        inputChannel.send(builder.build());
        String msg = readInternalMessage ("SOURCE_MARKET_PROCESSED");
        System.out.println(msg);
        assertFalse(msg.contains("88881"));
    }

    @Test
    @Transactional
    @Ignore
    public void processEventsUpdatedTest() throws ClassNotFoundException, SQLException, InterruptedException, IOException, DataProcessException {
        String message = "{\n" +
                "        \"connector\": 1,\n" +
                "        \"event\": {\n" +
                "            \"id\": \"99990\",\n" +
                "            \"name\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"startTime\": \"2014-11-29T20:00:00.000+0000\",\n" +
                "            \"metaTags\": [],\n" +
                "            \"markets\": [],\n" +
                "            \"eventStatus\": \"CLOSED\"\n" +
                "        },\n" +
                "        \"sourceEvent\": {\n" +
                "            \"id\": 99990,\n" +
                "            \"eventId\": 99990,\n" +
                "            \"sourceId\": \"10000\",\n" +
                "            \"sourceName\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"startTime\": \"2014-11-29T20:00:00.000+0000\",\n" +
                "            \"creator\":true,\n" +
                "            \"metaTags\": [],\n" +
                "            \"markets\": [],\n" +
                "            \"status\": \"CLOSED\",\n" +
                "            \"connectorId\": 1 \n" +
                "        }\n" +
                "    }";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_EVENT_UPDATED);
        inputChannel.send(builder.build());
        String msg = readInternalMessage ("SOURCE_EVENT_PROCESSED");
        System.out.println(msg);
        assertTrue(msg.contains("SOURCE_EVENT_PROCESSED"));
    }

    @Test
    @Transactional
    @Ignore
    public void processEventsUpdatedNegativeTest() throws ClassNotFoundException, SQLException, InterruptedException, IOException, DataProcessException {
        String message = "{\n" +
                "        \"connector\": 2,\n" +
                "        \"event\": {\n" +
                "            \"id\": \"99991\",\n" +
                "            \"name\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"startTime\": \"2014-11-29T20:00:00.000+0000\",\n" +
                "            \"metaTags\": [],\n" +
                "            \"markets\": [],\n" +
                "            \"eventStatus\": \"CLOSED\"\n" +
                "        },\n" +
                "        \"sourceEvent\": {\n" +
                "            \"id\": 99991,\n" +
                "            \"eventId\": 99991,\n" +
                "            \"sourceId\": \"10001\",\n" +
                "            \"sourceName\": \"Source Event Created Mapped and Creator\",\n" +
                "            \"startTime\": \"2014-11-29T20:00:00.000+0000\",\n" +
                "            \"creator\":true,\n" +
                "            \"metaTags\": [],\n" +
                "            \"markets\": [],\n" +
                "            \"status\": \"CLOSED\",\n" +
                "            \"connectorId\": 2 \n" +
                "        }\n" +
                "    }";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_EVENT_UPDATED);
        inputChannel.send(builder.build());
        String msg = readInternalMessage ("SOURCE_EVENT_PROCESSED");
        System.out.println(msg);
        assertFalse(msg.contains("99991"));
    }

    public String readInternalMessage(String topicName) throws UnsupportedEncodingException {
        SimpleConsumer consumer = null;
        try {
            consumer = new SimpleConsumer("localhost", 9092, 1000, 64 * 1024, "");
            long offSet = getLastOffset(consumer, topicName, 0, kafka.api.OffsetRequest.EarliestTime(), "");
            System.out.println (offSet);
            FetchRequest req = new FetchRequestBuilder().clientId("").addFetch(topicName, 0, offSet, 100000).build();
            ByteBufferMessageSet messageAndOffsets = consumer.fetch(req).messageSet(topicName, 0);
            ByteBuffer payload = messageAndOffsets.iterator().next().message().payload();
            byte[] bytes = new byte[payload.limit()];
            System.out.println (payload.toString());
            payload.get(bytes);
            return new String(bytes, "utf-8");
        } catch (NullPointerException np) {
            System.out.println ("Nothing to Consumer");
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (consumer !=null) {consumer.close();};
        }
    }

    public static long getLastOffset(SimpleConsumer consumer, String topic,
            int partition, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic,
                partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));
        kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest (requestInfo, kafka.api.OffsetRequest.CurrentVersion(),clientName);
        kafka.javaapi.OffsetResponse response = consumer.getOffsetsBefore(request);

        if (response.hasError()) {
            System.out
                    .println("Error fetching data Offset Data the Broker. Reason: "
                            + response.errorCode(topic, partition));
            return 0;
        }
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }

    @Test
    @Ignore
    public void errorHandlerEventUpdated () throws IOException, InterruptedException {
        // Created a badly made message.
        String message = "{\n" +
                "        \"connector\": 1,\n" +
                "        \"event\": {},\n" +
                "        \"sourceEvent\": {}\n" +
                "    }";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_EVENT_UPDATED);

        // Sends message to provoke exception
        inputChannel.send(builder.build());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("SourceEventUpdatedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("SourceEventUpdatedEvent"), on(localProfile))).totalLines(), is(6));
    }

    @Test
    @Ignore
    public void errorHandlerMarketUpdated () throws IOException, InterruptedException {
        // Created a badly made message.
        String message = "{\n" +
                "        \"connector\": 1,\n" +
                "        \"market\": {},\n" +
                "        \"sourceMarket\": {}\n" +
                "    }";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_MARKET_UPDATED);

        // Sends message to provoke exception
        inputChannel.send(builder.build());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("SourceMarketUpdatedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("SourceMarketUpdatedEvent"), on(localProfile))).totalLines(), is(6));
    }

    @Test
    @Ignore
    public void errorHandlerRunnerUpdated () throws IOException, InterruptedException {
        // Created a badly made message.
        String message = "{\n" +
                "        \"connector\": 1,\n" +
                "        \"runner\": {},\n" +
                "        \"sourceRunner\": {}\n" +
                "    }";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.SOURCE_RUNNER_UPDATED);

        // Sends message to provoke exception
        inputChannel.send(builder.build());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("SourceRunnerUpdatedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("SourceRunnerUpdatedEvent"), on(localProfile))).totalLines(), is(6));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public SourceEventDao getSourceEventDao() {
        return sourceEventDao;
    }

    public void setSourceEventDao(SourceEventDao sourceEventDao) {
        this.sourceEventDao = sourceEventDao;
    }

    public SourceMarketDao getSourceMarketDao() {
        return sourceMarketDao;
    }

    public void setSourceMarketDao(SourceMarketDao sourceMarketDao) {
        this.sourceMarketDao = sourceMarketDao;
    }

    public SourceRunnerDao getSourceRunnerDao() {
        return sourceRunnerDao;
    }

    public void setSourceRunnerDao(SourceRunnerDao sourceRunnerDao) {
        this.sourceRunnerDao = sourceRunnerDao;
    }

    public ConnectorDao getConnectorDao() {
        return connectorDao;
    }

    public void setConnectorDao(ConnectorDao connectorDao) {
        this.connectorDao = connectorDao;
    }

    public MessageChannel getInputChannel() {
      return inputChannel;
    }

    public void setInputChannel(MessageChannel inputChannel) {
      this.inputChannel = inputChannel;
    }
}