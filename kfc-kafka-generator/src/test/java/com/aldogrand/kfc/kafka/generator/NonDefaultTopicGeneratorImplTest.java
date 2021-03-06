package com.aldogrand.kfc.kafka.generator;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.connectors.model.Account;
import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.sbpc.connectors.model.Offer;
import com.aldogrand.sbpc.connectors.model.Position;
import com.aldogrand.sbpc.connectors.model.Runner;
import com.aldogrand.sbpc.model.BettingPlatform;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.events.fetcher.AccountReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.BettingPlatformReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.ConnectorReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventIncidentReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventsClosedStatusReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.OffersReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PositionsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PricesReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SettledBetsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceMarketsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceRunnersReceivedEvent;
import com.aldogrand.kfc.msg.events.mapped.EventCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.EventMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerMappedEvent;
import com.aldogrand.kfc.msg.events.mapper.EventMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.EventMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.EventNotMappedEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.MarketNotMappedEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapFoundEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerMapNewEvent;
import com.aldogrand.kfc.msg.events.mapper.RunnerNotMappedEvent;
import com.aldogrand.kfc.msg.events.processed.EventUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.MarketUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.RunnerUpdatedEvent;
import com.aldogrand.kfc.msg.events.processor.EventProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.MarketProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.RunnerProcessedEvent;
import com.aldogrand.kfc.msg.events.raw.AccountCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.AccountUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.BettingPlatformCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.BettingPlatformUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.ConnectorCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.ConnectorUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.EventIncidentCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.EventIncidentUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.PositionCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.PositionUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceRemovedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceEventCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceEventUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceMarketCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceMarketUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceRunnerCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.SourceRunnerUpdatedEvent;
import com.aldogrand.kfc.msg.interfaces.TopicGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/kafka-non-default-generator-context-test.xml"})
public class NonDefaultTopicGeneratorImplTest {

    @Autowired
    private TopicGenerator topicGenerator;
    @Autowired
    KFCEventFactory KFCEventFactory;

    @Before
    public void before() throws RuntimeException {}

    @After
    public void after() throws RuntimeException {}

    private AccountReceivedEvent accountReceived = new AccountReceivedEvent();
    private AccountCreatedEvent accountCreated = new AccountCreatedEvent();
    private AccountUpdatedEvent accountUpdated = new AccountUpdatedEvent();

    // Account Received Test.
    @Test
    public void topicGeneratorAccountReceivedTest() throws RuntimeException {
        Account account;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	account = new Account();
            accountReceived.setAccountId(12345L);
            accountReceived.setIntegrationModuleId("1");
            accountReceived.setAccount(account);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(accountReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the accountReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Account Created Test.
    @Test
    public void topicGeneratorAccountCreatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.Account account;
    	String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	account = new com.aldogrand.sbpc.model.Account();
        	accountCreated.setIntegrationModuleId("1");
        	accountCreated.setAccount(account);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(accountCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the accountCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Account Updated Test.
    @Test
    public void topicGeneratorAccountUpdatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.Account account;
    	String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	account = new com.aldogrand.sbpc.model.Account();
        	accountUpdated.setIntegrationModuleId("1");
        	accountUpdated.setAccount(account);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(accountUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the accountUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private PositionsReceivedEvent positionReceived = new PositionsReceivedEvent();
    private PositionCreatedEvent positionCreated = new PositionCreatedEvent();
    private PositionUpdatedEvent positionUpdated = new PositionUpdatedEvent();

    // Position Received Test.
    @Test
    public void topicGeneratorPositionReceivedTest() throws RuntimeException {
    	List<Position> positions = new ArrayList<Position>();
        Position position;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	position = new Position();
        	position.setEventId("12345");
        	positions.add(position);
        	positionReceived.setPositions(positions);
        	positionReceived.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(positionReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the positionReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Position Created Test.
    @Test
    public void topicGeneratorPositionCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Position position;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	position = new com.aldogrand.sbpc.model.Position();
        	position.setEventId(12345L);
        	positionCreated.setPosition(position);
        	positionCreated.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(positionCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the positionCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Position Updated Test.
    @Test
    public void topicGeneratorPositionUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Position position;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	position = new com.aldogrand.sbpc.model.Position();
        	position.setEventId(12345L);
        	positionUpdated.setPosition(position);
        	positionUpdated.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(positionUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the positionUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private BettingPlatformReceivedEvent bettingPlatformReceived = new BettingPlatformReceivedEvent();
    private BettingPlatformCreatedEvent bettingPlatformCreated = new BettingPlatformCreatedEvent();
    private BettingPlatformUpdatedEvent bettingPlatformUpdated = new BettingPlatformUpdatedEvent();

    // BettingPlatform Received Test.
    @Test
    public void topicGeneratorBettingPlatformReceivedTest() throws RuntimeException {
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	bettingPlatformReceived.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(bettingPlatformReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the bettingPlatformReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // BettingPlatform Created Test.
    @Test
    public void topicGeneratorBettingPlatformCreatedTest() throws ParseException, EvaluationException, NumberFormatException, ProducerException, RuntimeException {
    	BettingPlatform bettingPlatform;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	bettingPlatform = new BettingPlatform();
        	bettingPlatform.setId(12345L);
        	bettingPlatform.setConnectorId(1L);
        	bettingPlatformCreated.setIntegrationModuleId("1");
        	bettingPlatformCreated.setBettingPlatform(bettingPlatform);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(bettingPlatformCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the bettingPlatformCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // BettingPlatform Updated Test.
    @Test
    public void topicGeneratorBettingPlatformUpdatedTest() throws ParseException, EvaluationException, NumberFormatException, ProducerException, RuntimeException {
    	BettingPlatform bettingPlatform;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	bettingPlatform = new BettingPlatform();
        	bettingPlatform.setId(12345L);
        	bettingPlatform.setConnectorId(1L);
        	bettingPlatformUpdated.setIntegrationModuleId("1");
        	bettingPlatformUpdated.setBettingPlatform(bettingPlatform);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(bettingPlatformUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the bettingPlatformUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private SettledBetsReceivedEvent settleBetReceived = new SettledBetsReceivedEvent();
    // BettingPlatform Created Test.
    @Test
    public void topicGeneratorSettledBetsReceivedTest() throws ParseException, EvaluationException, NumberFormatException, ProducerException, RuntimeException {
    	String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	settleBetReceived.setIntegrationModuleId("1");
        	settleBetReceived.setAccountId(12345L);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(settleBetReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the settleBetReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    private ConnectorReceivedEvent connectorReceived = new ConnectorReceivedEvent();
    private ConnectorCreatedEvent connectorCreated = new ConnectorCreatedEvent();
    private ConnectorUpdatedEvent connectorUpdated = new ConnectorUpdatedEvent();

    // Connector Received Test.
    @Test
    public void topicGeneratorConnectorReceivedTest() throws RuntimeException {
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	connectorReceived.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(connectorReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the connectorReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Connector Created Test.
    @Test
    public void topicGeneratorConnectorCreatedTest() throws RuntimeException {
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	connectorCreated.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(connectorCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the connectorCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Connector Updated Test.
    @Test
    public void topicGeneratorConnectorUpdatedTest() throws RuntimeException {
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	connectorUpdated.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(connectorUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the connectorUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private OffersReceivedEvent offerReceived = new OffersReceivedEvent();
    private OfferCreatedEvent offerCreated = new OfferCreatedEvent();
    private OfferUpdatedEvent offerUpdated = new OfferUpdatedEvent();

    // Offer Received Test.
    @Test
    public void topicGeneratorOfferReceivedTest() throws RuntimeException {
    	List<Offer> offers = new ArrayList<Offer>();
        Offer offer;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	offer = new Offer();
        	offer.setEventId("12345");
        	offers.add(offer);
        	offerReceived.setIntegrationModuleId("1");
        	offerReceived.setOffers(offers);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(offerReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the offerReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Offer Created Test.
    @Test
    public void topicGeneratorOfferCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Offer offer;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	offer = new com.aldogrand.sbpc.model.Offer();
        	offer.setEventId(12345L);
        	offerCreated.setIntegrationModuleId("1");
        	offerCreated.setOffer(offer);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(offerCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the offerCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Offer Updated Test.
    @Test
    public void topicGeneratorOfferUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Offer offer;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	offer = new com.aldogrand.sbpc.model.Offer();
        	offer.setEventId(12345L);
        	offerUpdated.setIntegrationModuleId("1");
        	offerUpdated.setOffer(offer);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(offerUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the offerUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private EventIncidentReceivedEvent eventIncReceived = new EventIncidentReceivedEvent();
    private EventIncidentCreatedEvent eventIncCreated = new EventIncidentCreatedEvent();
    private EventIncidentUpdatedEvent eventIncUpdated = new EventIncidentUpdatedEvent();

    // EventIncident Received Test.
    @Test
    public void topicGeneratorEventIncidentReceivedTest() throws RuntimeException {
    	EventIncident eventIncident;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	eventIncident = new EventIncident(12345L, i, null, null, null);
        	eventIncident.setSourceEventId(12345L);
        	eventIncReceived.setIntegrationModuleId("1");
        	eventIncReceived.setEventIncident(eventIncident);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventIncReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventIncReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // EventIncident Created Test.
    @Test
    public void topicGeneratorEventIncidentCreatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.EventIncident eventIncident;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	eventIncident = new com.aldogrand.sbpc.model.EventIncident();
        	eventIncident.setEventId(12345L);
        	eventIncCreated.setIntegrationModuleId("1");
        	eventIncCreated.setEventIncident(eventIncident);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventIncCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventIncCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // EventIncident Updated Test.
    @Test
    public void topicGeneratorEventIncidentUpdatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.EventIncident eventIncident;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	eventIncident = new com.aldogrand.sbpc.model.EventIncident();
        	eventIncident.setEventId(12345L);
        	eventIncUpdated.setIntegrationModuleId("1");
        	eventIncUpdated.setEventIncident(eventIncident);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventIncUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventIncUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private EventsClosedStatusReceivedEvent eventClosedReceived = new EventsClosedStatusReceivedEvent();

    // EventsClosedStatus Received Test.
    @Test
    public void topicGeneratorEventsClosedStatusReceivedTest() throws RuntimeException {
    	Event event;
    	List <Event> events = new ArrayList<Event>();
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();

        event = new Event ();
        event.setId("12345");
        events.add(event);
    			
        for (int i = 0; i < 1; i++) {
        	eventClosedReceived.setIntegrationModuleId("1");
        	eventClosedReceived.setEvents(events);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventClosedReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventClosedReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    private PricesReceivedEvent priceReceived = new PricesReceivedEvent();
    private PriceCreatedEvent priceCreated = new PriceCreatedEvent();
    private PriceRemovedEvent priceUpdated = new PriceRemovedEvent();

    // Prices Received Test.
    @Test
    public void topicGeneratorPricesReceivedTest() throws RuntimeException {

        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) {
        	priceReceived.setIntegrationModuleId("1");

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(priceReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the priceReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Price Created Test.
    @Test
    public void topicGeneratorPriceCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Price price;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	price = new com.aldogrand.sbpc.model.Price();
        	price.setEventId(12345L);
        	priceCreated.setIntegrationModuleId("1");
        	priceCreated.setPrice(price);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(priceCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the priceCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Price Updated Test.
    @Test
    public void topicGeneratorPriceUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Price price;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	price = new com.aldogrand.sbpc.model.Price();
        	price.setEventId(12345L);
        	priceUpdated.setIntegrationModuleId("1");
        	priceUpdated.setPrice(price);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(priceUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the priceUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private EventCreatedEvent eventCreated = new EventCreatedEvent();
    private EventMappedEvent eventMapped = new EventMappedEvent();
    private EventMapFoundEvent eventMapFound = new EventMapFoundEvent();
    private EventMapNewEvent eventMapNew = new EventMapNewEvent();
    private EventNotMappedEvent eventNotMapped = new EventNotMappedEvent();
    private EventUpdatedEvent eventUpdated = new EventUpdatedEvent();

    // Event Created Test.
    @Test
    public void topicGeneratorEventCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventCreated.setIntegrationModuleId("1");
        	eventCreated.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Mapped Test.
    @Test
    public void topicGeneratorEventMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventMapped.setIntegrationModuleId("1");
        	eventMapped.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventMapped key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Map Found Test.
    @Test
    public void topicGeneratorEventMapFoundTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventMapFound.setIntegrationModuleId("1");
        	eventMapFound.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventMapFound));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventMapFound key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Map New Test.
    @Test
    public void topicGeneratorEventMapNewTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventMapNew.setIntegrationModuleId("1");
        	eventMapNew.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventMapNew));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventMapNew key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void topicGeneratorEventNotMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventNotMapped.setIntegrationModuleId("1");
        	eventNotMapped.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventNotMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventNotMapped key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void topicGeneratorEventUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventUpdated.setIntegrationModuleId("1");
        	eventUpdated.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(eventUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private SourceEventReceivedEvent sourceEventReceived = new SourceEventReceivedEvent();
    private SourceEventCreatedEvent sourceEventCreated = new SourceEventCreatedEvent();
    private EventProcessedEvent sourceEventProcessed = new EventProcessedEvent();
    private SourceEventUpdatedEvent sourceEventUpdated = new SourceEventUpdatedEvent();

    // Source Event Received Test.
    @Test
    public void topicGeneratorSourceEventReceivedTest() throws RuntimeException {
        Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new Event();
        	event.setId("12345");
        	sourceEventReceived.setIntegrationModuleId("1");
        	sourceEventReceived.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceEventReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Event Created Test.
    @Test
    public void topicGeneratorSourceEventCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	sourceEventCreated.setIntegrationModuleId("1");
        	sourceEventCreated.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceEventCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Event Processed Test.
    @Test
    public void topicGeneratorSourceEventProcessedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	sourceEventProcessed.setIntegrationModuleId("1");
        	sourceEventProcessed.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceEventProcessed));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventProcessed key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Event Updated Test.
    @Test
    public void topicGeneratorSourceEventUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	sourceEventUpdated.setIntegrationModuleId("1");
        	sourceEventUpdated.setEvent(event);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceEventUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private MarketCreatedEvent marketCreated = new MarketCreatedEvent();
    private MarketMappedEvent marketMapped = new MarketMappedEvent();
    private MarketMapFoundEvent marketMapFound = new MarketMapFoundEvent();
    private MarketMapNewEvent marketMapNew = new MarketMapNewEvent();
    private MarketNotMappedEvent marketNotMapped = new MarketNotMappedEvent();
    private MarketUpdatedEvent marketUpdated = new MarketUpdatedEvent();

    // Market Created Test.
    @Test
    public void topicGeneratorMarketCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketCreated.setIntegrationModuleId("1");
        	marketCreated.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(marketCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Market Mapped Test.
    @Test
    public void topicGeneratorMarketMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketMapped.setIntegrationModuleId("1");
        	marketMapped.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(marketMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketMapped key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Market Map Found Test.
    @Test
    public void topicGeneratorMarketMapFoundTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketMapFound.setIntegrationModuleId("1");
        	marketMapFound.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(marketMapFound));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketMapFound key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Map New Test.
    @Test
    public void topicGeneratorMarketMapNewTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketMapNew.setIntegrationModuleId("1");
        	marketMapNew.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(marketMapNew));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketMapNew key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void topicGeneratorMarketNotMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketNotMapped.setIntegrationModuleId("1");
        	marketNotMapped.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(marketNotMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketNotMapped key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void topicGeneratorMarketUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketUpdated.setIntegrationModuleId("1");
        	marketUpdated.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(marketUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private SourceMarketsReceivedEvent sourceMarketReceived = new SourceMarketsReceivedEvent();
    private SourceMarketCreatedEvent sourceMarketCreated = new SourceMarketCreatedEvent();
    private MarketProcessedEvent sourceMarketProcessed = new MarketProcessedEvent();
    private SourceMarketUpdatedEvent sourceMarketUpdated = new SourceMarketUpdatedEvent();

    // Source Market Received Test.
    @Test
    public void topicGeneratorSourceMarketsReceivedTest() throws RuntimeException {
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        
    	sourceMarketReceived.setIntegrationModuleId("1");
    	
        for (int i = 0; i < 1; i++) {
            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceMarketReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Market Created Test.
    @Test
    public void topicGeneratorSourceMarketCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	sourceMarketCreated.setIntegrationModuleId("1");
        	sourceMarketCreated.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceMarketCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Market Processed Test.
    @Test
    public void topicGeneratorSourceMarketProcessedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	sourceMarketProcessed.setIntegrationModuleId("1");
        	sourceMarketProcessed.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceMarketProcessed));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketProcessed key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Event Updated Test.
    @Test
    public void topicGeneratorSourceMarketUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	sourceMarketUpdated.setIntegrationModuleId("1");
        	sourceMarketUpdated.setMarket(market);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceMarketUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private RunnerCreatedEvent runnerCreated = new RunnerCreatedEvent();
    private RunnerMappedEvent runnerMapped = new RunnerMappedEvent();
    private RunnerMapFoundEvent runnerMapFound = new RunnerMapFoundEvent();
    private RunnerMapNewEvent runnerMapNew = new RunnerMapNewEvent();
    private RunnerNotMappedEvent runnerNotMapped = new RunnerNotMappedEvent();
    private RunnerUpdatedEvent runnerUpdated = new RunnerUpdatedEvent();

    // Runner Created Test.
    @Test
    public void topicGeneratorRunnerCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerCreated.setIntegrationModuleId("1");
        	runnerCreated.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(runnerCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Runner Mapped Test.
    @Test
    public void topicGeneratorRunnerMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerMapped.setIntegrationModuleId("1");
        	runnerMapped.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(runnerMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerMapped key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Runner Map Found Test.
    @Test
    public void topicGeneratorRunnerMapFoundTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerMapFound.setIntegrationModuleId("1");
        	runnerMapFound.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(runnerMapFound));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerMapFound key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Runner Map New Test.
    @Test
    public void topicGeneratorRunnerMapNewTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerMapNew.setIntegrationModuleId("1");
        	runnerMapNew.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(runnerMapNew));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerMapNew key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Runner Not Mapped Test.
    @Test
    public void topicGeneratorRunnerNotMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerNotMapped.setIntegrationModuleId("1");
        	runnerNotMapped.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(runnerNotMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerNotMapped key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Runner Not Mapped Test.
    @Test
    public void topicGeneratorRunnerUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerUpdated.setIntegrationModuleId("1");
        	runnerUpdated.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(runnerUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }

    private SourceRunnersReceivedEvent sourceRunnerReceived = new SourceRunnersReceivedEvent();
    private SourceRunnerCreatedEvent sourceRunnerCreated = new SourceRunnerCreatedEvent();
    private RunnerProcessedEvent sourceRunnerProcessed = new RunnerProcessedEvent();
    private SourceRunnerUpdatedEvent sourceRunnerUpdated = new SourceRunnerUpdatedEvent();

    // Source Runner Received Test.
    @Test
    public void topicGeneratorSourceRunnertReceivedTest() throws RuntimeException {
    	List<Runner> runners = new ArrayList<Runner>();
    	Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new Runner();
        	runner.setEventId("12345");
        	runners.add(runner);
        	sourceRunnerReceived.setIntegrationModuleId("1");
        	sourceRunnerReceived.setRunners(runners);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceRunnerReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerReceived key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Runner Created Test.
    @Test
    public void topicGeneratorSourceRunnerCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	sourceRunnerCreated.setIntegrationModuleId("1");
        	sourceRunnerCreated.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceRunnerCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerCreated key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Runner Processed Test.
    @Test
    public void topicGeneratorSourceRunnerProcessedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	sourceRunnerProcessed.setIntegrationModuleId("1");
        	sourceRunnerProcessed.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceRunnerProcessed));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerProcessed key: " + topicGenerated + "==>" + diff + " ms");
    }

    // Source Runner Updated Test.
    @Test
    public void topicGeneratorSourceRunnerUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String topicGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	sourceRunnerUpdated.setIntegrationModuleId("1");
        	sourceRunnerUpdated.setRunner(runner);

            topicGenerated = topicGenerator.generateTopic(KFCEventFactory.createMessage(sourceRunnerUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerUpdated key: " + topicGenerated + "==>" + diff + " ms");
    }
}
