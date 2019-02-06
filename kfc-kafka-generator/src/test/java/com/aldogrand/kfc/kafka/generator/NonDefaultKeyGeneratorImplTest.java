package com.aldogrand.kfc.kafka.generator;

import java.util.ArrayList;
import java.util.List;

import com.aldogrand.sbpc.connectors.model.Account;
import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.sbpc.connectors.model.Offer;
import com.aldogrand.sbpc.connectors.model.Position;
import com.aldogrand.sbpc.connectors.model.Runner;
import com.aldogrand.sbpc.model.BettingPlatform;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.events.fetcher.BettingPlatformReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.ConnectorReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventIncidentReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventsClosedStatusReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.OffersReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PositionsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PricesReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SettledBetsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.AccountReceivedEvent;
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
import com.aldogrand.kfc.msg.interfaces.KeyGenerator;
import com.aldogrand.kfc.msg.interfaces.TopicGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import scala.Array;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/kafka-non-default-generator-context-test.xml"})
public class NonDefaultKeyGeneratorImplTest {

    @Autowired
    private KeyGenerator keyEventGenerator;
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
    public void keyGeneratorAccountReceivedTest() throws RuntimeException {
        Account account;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	account = new Account();
            accountReceived.setAccountId(12345L);
            accountReceived.setIntegrationModuleId("1");
            accountReceived.setAccount(account);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(accountReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the accountReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Account Created Test.
    @Test
    public void keyGeneratorAccountCreatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.Account account;
    	String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	account = new com.aldogrand.sbpc.model.Account();
        	accountCreated.setIntegrationModuleId("1");
        	accountCreated.setAccount(account);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(accountCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the accountCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Account Updated Test.
    @Test
    public void keyGeneratorAccountUpdatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.Account account;
    	String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	account = new com.aldogrand.sbpc.model.Account();
        	accountUpdated.setIntegrationModuleId("1");
        	accountUpdated.setAccount(account);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(accountUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the accountUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private PositionsReceivedEvent positionReceived = new PositionsReceivedEvent();
    private PositionCreatedEvent positionCreated = new PositionCreatedEvent();
    private PositionUpdatedEvent positionUpdated = new PositionUpdatedEvent();

    // Position Received Test.
    @Test
    public void keyGeneratorPositionReceivedTest() throws RuntimeException {
    	List<Position> positions = new ArrayList<Position>();
        Position position;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	position = new Position();
        	position.setEventId("12345");
        	positions.add(position);
        	positionReceived.setPositions(positions);
        	positionReceived.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(positionReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the positionReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Position Created Test.
    @Test
    public void keyGeneratorPositionCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Position position;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	position = new com.aldogrand.sbpc.model.Position();
        	position.setEventId(12345L);
        	positionCreated.setPosition(position);
        	positionCreated.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(positionCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the positionCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Position Updated Test.
    @Test
    public void keyGeneratorPositionUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Position position;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	position = new com.aldogrand.sbpc.model.Position();
        	position.setEventId(12345L);
        	positionUpdated.setPosition(position);
        	positionUpdated.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(positionUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the positionUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private BettingPlatformReceivedEvent bettingPlatformReceived = new BettingPlatformReceivedEvent();
    private BettingPlatformCreatedEvent bettingPlatformCreated = new BettingPlatformCreatedEvent();
    private BettingPlatformUpdatedEvent bettingPlatformUpdated = new BettingPlatformUpdatedEvent();

    // BettingPlatform Received Test.
    @Test
    public void keyGeneratorBettingPlatformReceivedTest() throws RuntimeException {
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	bettingPlatformReceived.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(bettingPlatformReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the bettingPlatformReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // BettingPlatform Created Test.
    @Test
    public void keyGeneratorBettingPlatformCreatedTest() throws ParseException, EvaluationException, NumberFormatException, ProducerException, RuntimeException {
    	BettingPlatform bettingPlatform;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	bettingPlatform = new BettingPlatform();
        	bettingPlatform.setId(12345L);
        	bettingPlatform.setConnectorId(1L);
        	bettingPlatformCreated.setIntegrationModuleId("1");
        	bettingPlatformCreated.setBettingPlatform(bettingPlatform);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(bettingPlatformCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the bettingPlatformCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // BettingPlatform Updated Test.
    @Test
    public void keyGeneratorBettingPlatformUpdatedTest() throws ParseException, EvaluationException, NumberFormatException, ProducerException, RuntimeException {
    	BettingPlatform bettingPlatform;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	bettingPlatform = new BettingPlatform();
        	bettingPlatform.setId(12345L);
        	bettingPlatform.setConnectorId(1L);
        	bettingPlatformUpdated.setIntegrationModuleId("1");
        	bettingPlatformUpdated.setBettingPlatform(bettingPlatform);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(bettingPlatformUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the bettingPlatformUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private SettledBetsReceivedEvent settleBetReceived = new SettledBetsReceivedEvent();
    // BettingPlatform Created Test.
    @Test
    public void keyGeneratorSettledBetsReceivedTest() throws ParseException, EvaluationException, NumberFormatException, ProducerException, RuntimeException {
    	String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	settleBetReceived.setIntegrationModuleId("1");
        	settleBetReceived.setAccountId(12345L);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(settleBetReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the settleBetReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    private ConnectorReceivedEvent connectorReceived = new ConnectorReceivedEvent();
    private ConnectorCreatedEvent connectorCreated = new ConnectorCreatedEvent();
    private ConnectorUpdatedEvent connectorUpdated = new ConnectorUpdatedEvent();

    // Connector Received Test.
    @Test
    public void keyGeneratorConnectorReceivedTest() throws RuntimeException {
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	connectorReceived.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(connectorReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the connectorReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Connector Created Test.
    @Test
    public void keyGeneratorConnectorCreatedTest() throws RuntimeException {
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	connectorCreated.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(connectorCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the connectorCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Connector Updated Test.
    @Test
    public void keyGeneratorConnectorUpdatedTest() throws RuntimeException {
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	connectorUpdated.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(connectorUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the connectorUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private OffersReceivedEvent offerReceived = new OffersReceivedEvent();
    private OfferCreatedEvent offerCreated = new OfferCreatedEvent();
    private OfferUpdatedEvent offerUpdated = new OfferUpdatedEvent();

    // Offer Received Test.
    @Test
    public void keyGeneratorOfferReceivedTest() throws RuntimeException {
    	List<Offer> offers = new ArrayList<Offer>();
        Offer offer;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	offer = new Offer();
        	offer.setEventId("12345");
        	offers.add(offer);
        	offerReceived.setIntegrationModuleId("1");
        	offerReceived.setOffers(offers);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(offerReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the offerReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Offer Created Test.
    @Test
    public void keyGeneratorOfferCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Offer offer;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	offer = new com.aldogrand.sbpc.model.Offer();
        	offer.setEventId(12345L);
        	offerCreated.setIntegrationModuleId("1");
        	offerCreated.setOffer(offer);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(offerCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the offerCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Offer Updated Test.
    @Test
    public void keyGeneratorOfferUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Offer offer;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	offer = new com.aldogrand.sbpc.model.Offer();
        	offer.setEventId(12345L);
        	offerUpdated.setIntegrationModuleId("1");
        	offerUpdated.setOffer(offer);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(offerUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the offerUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private EventIncidentReceivedEvent eventIncReceived = new EventIncidentReceivedEvent();
    private EventIncidentCreatedEvent eventIncCreated = new EventIncidentCreatedEvent();
    private EventIncidentUpdatedEvent eventIncUpdated = new EventIncidentUpdatedEvent();

    // EventIncident Received Test.
    @Test
    public void keyGeneratorEventIncidentReceivedTest() throws RuntimeException {
    	EventIncident eventIncident;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	eventIncident = new EventIncident(12345L, i, null, null, null);
        	eventIncident.setSourceEventId(12345L);
        	eventIncReceived.setIntegrationModuleId("1");
        	eventIncReceived.setEventIncident(eventIncident);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventIncReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventIncReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // EventIncident Created Test.
    @Test
    public void keyGeneratorEventIncidentCreatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.EventIncident eventIncident;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	eventIncident = new com.aldogrand.sbpc.model.EventIncident();
        	eventIncident.setEventId(12345L);
        	eventIncCreated.setIntegrationModuleId("1");
        	eventIncCreated.setEventIncident(eventIncident);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventIncCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventIncCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // EventIncident Updated Test.
    @Test
    public void keyGeneratorEventIncidentUpdatedTest() throws RuntimeException {
    	com.aldogrand.sbpc.model.EventIncident eventIncident;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	eventIncident = new com.aldogrand.sbpc.model.EventIncident();
        	eventIncident.setEventId(12345L);
        	eventIncUpdated.setIntegrationModuleId("1");
        	eventIncUpdated.setEventIncident(eventIncident);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventIncUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventIncUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private EventsClosedStatusReceivedEvent eventClosedReceived = new EventsClosedStatusReceivedEvent();

    // EventsClosedStatus Received Test.
    @Test
    public void keyGeneratorEventsClosedStatusReceivedTest() throws RuntimeException {
    	Event event;
    	List <Event> events = new ArrayList<Event>();
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();

        event = new Event ();
        event.setId("12345");
        events.add(event);
    			
        for (int i = 0; i < 1; i++) {
        	eventClosedReceived.setIntegrationModuleId("1");
        	eventClosedReceived.setEvents(events);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventClosedReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventClosedReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    private PricesReceivedEvent priceReceived = new PricesReceivedEvent();
    private PriceCreatedEvent priceCreated = new PriceCreatedEvent();
    private PriceRemovedEvent priceUpdated = new PriceRemovedEvent();

    // Prices Received Test.
    @Test
    public void keyGeneratorPricesReceivedTest() throws RuntimeException {

        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) {
        	priceReceived.setIntegrationModuleId("1");

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(priceReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the priceReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Price Created Test.
    @Test
    public void keyGeneratorPriceCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Price price;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	price = new com.aldogrand.sbpc.model.Price();
        	price.setEventId(12345L);
        	priceCreated.setIntegrationModuleId("1");
        	priceCreated.setPrice(price);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(priceCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the priceCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Price Updated Test.
    @Test
    public void keyGeneratorPriceUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Price price;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	price = new com.aldogrand.sbpc.model.Price();
        	price.setEventId(12345L);
        	priceUpdated.setIntegrationModuleId("1");
        	priceUpdated.setPrice(price);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(priceUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the priceUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private EventCreatedEvent eventCreated = new EventCreatedEvent();
    private EventMappedEvent eventMapped = new EventMappedEvent();
    private EventMapFoundEvent eventMapFound = new EventMapFoundEvent();
    private EventMapNewEvent eventMapNew = new EventMapNewEvent();
    private EventNotMappedEvent eventNotMapped = new EventNotMappedEvent();
    private EventUpdatedEvent eventUpdated = new EventUpdatedEvent();

    // Event Created Test.
    @Test
    public void keyGeneratorEventCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventCreated.setIntegrationModuleId("1");
        	eventCreated.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Mapped Test.
    @Test
    public void keyGeneratorEventMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventMapped.setIntegrationModuleId("1");
        	eventMapped.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventMapped key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Map Found Test.
    @Test
    public void keyGeneratorEventMapFoundTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventMapFound.setIntegrationModuleId("1");
        	eventMapFound.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventMapFound));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventMapFound key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Map New Test.
    @Test
    public void keyGeneratorEventMapNewTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventMapNew.setIntegrationModuleId("1");
        	eventMapNew.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventMapNew));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventMapNew key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void keyGeneratorEventNotMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventNotMapped.setIntegrationModuleId("1");
        	eventNotMapped.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventNotMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventNotMapped key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void keyGeneratorEventUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	eventUpdated.setIntegrationModuleId("1");
        	eventUpdated.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(eventUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the eventUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private SourceEventReceivedEvent sourceEventReceived = new SourceEventReceivedEvent();
    private SourceEventCreatedEvent sourceEventCreated = new SourceEventCreatedEvent();
    private EventProcessedEvent sourceEventProcessed = new EventProcessedEvent();
    private SourceEventUpdatedEvent sourceEventUpdated = new SourceEventUpdatedEvent();

    // Source Event Received Test.
    @Test
    public void keyGeneratorSourceEventReceivedTest() throws RuntimeException {
        Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new Event();
        	event.setId("12345");
        	sourceEventReceived.setIntegrationModuleId("1");
        	sourceEventReceived.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceEventReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Event Created Test.
    @Test
    public void keyGeneratorSourceEventCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	sourceEventCreated.setIntegrationModuleId("1");
        	sourceEventCreated.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceEventCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Event Processed Test.
    @Test
    public void keyGeneratorSourceEventProcessedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	sourceEventProcessed.setIntegrationModuleId("1");
        	sourceEventProcessed.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceEventProcessed));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventProcessed key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Event Updated Test.
    @Test
    public void keyGeneratorSourceEventUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Event event;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	event = new com.aldogrand.sbpc.model.Event();
        	event.setId(12345L);
        	sourceEventUpdated.setIntegrationModuleId("1");
        	sourceEventUpdated.setEvent(event);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceEventUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceEventUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private MarketCreatedEvent marketCreated = new MarketCreatedEvent();
    private MarketMappedEvent marketMapped = new MarketMappedEvent();
    private MarketMapFoundEvent marketMapFound = new MarketMapFoundEvent();
    private MarketMapNewEvent marketMapNew = new MarketMapNewEvent();
    private MarketNotMappedEvent marketNotMapped = new MarketNotMappedEvent();
    private MarketUpdatedEvent marketUpdated = new MarketUpdatedEvent();

    // Market Created Test.
    @Test
    public void keyGeneratorMarketCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketCreated.setIntegrationModuleId("1");
        	marketCreated.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(marketCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Market Mapped Test.
    @Test
    public void keyGeneratorMarketMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketMapped.setIntegrationModuleId("1");
        	marketMapped.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(marketMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketMapped key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Market Map Found Test.
    @Test
    public void keyGeneratorMarketMapFoundTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketMapFound.setIntegrationModuleId("1");
        	marketMapFound.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(marketMapFound));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketMapFound key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Map New Test.
    @Test
    public void keyGeneratorMarketMapNewTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketMapNew.setIntegrationModuleId("1");
        	marketMapNew.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(marketMapNew));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketMapNew key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void keyGeneratorMarketNotMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketNotMapped.setIntegrationModuleId("1");
        	marketNotMapped.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(marketNotMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketNotMapped key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Event Not Mapped Test.
    @Test
    public void keyGeneratorMarketUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	marketUpdated.setIntegrationModuleId("1");
        	marketUpdated.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(marketUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the marketUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private SourceMarketsReceivedEvent sourceMarketReceived = new SourceMarketsReceivedEvent();
    private SourceMarketCreatedEvent sourceMarketCreated = new SourceMarketCreatedEvent();
    private MarketProcessedEvent sourceMarketProcessed = new MarketProcessedEvent();
    private SourceMarketUpdatedEvent sourceMarketUpdated = new SourceMarketUpdatedEvent();

    // Source Market Received Test.
    @Test
    public void keyGeneratorSourceMarketsReceivedTest() throws RuntimeException {
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        
    	sourceMarketReceived.setIntegrationModuleId("1");
    	
        for (int i = 0; i < 1; i++) {
            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceMarketReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Market Created Test.
    @Test
    public void keyGeneratorSourceMarketCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	sourceMarketCreated.setIntegrationModuleId("1");
        	sourceMarketCreated.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceMarketCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Market Processed Test.
    @Test
    public void keyGeneratorSourceMarketProcessedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	sourceMarketProcessed.setIntegrationModuleId("1");
        	sourceMarketProcessed.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceMarketProcessed));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketProcessed key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Event Updated Test.
    @Test
    public void keyGeneratorSourceMarketUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Market market;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	market = new com.aldogrand.sbpc.model.Market();
        	market.setEventId(12345L);
        	sourceMarketUpdated.setIntegrationModuleId("1");
        	sourceMarketUpdated.setMarket(market);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceMarketUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceMarketUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private RunnerCreatedEvent runnerCreated = new RunnerCreatedEvent();
    private RunnerMappedEvent runnerMapped = new RunnerMappedEvent();
    private RunnerMapFoundEvent runnerMapFound = new RunnerMapFoundEvent();
    private RunnerMapNewEvent runnerMapNew = new RunnerMapNewEvent();
    private RunnerNotMappedEvent runnerNotMapped = new RunnerNotMappedEvent();
    private RunnerUpdatedEvent runnerUpdated = new RunnerUpdatedEvent();

    // Runner Created Test.
    @Test
    public void keyGeneratorRunnerCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerCreated.setIntegrationModuleId("1");
        	runnerCreated.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(runnerCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Runner Mapped Test.
    @Test
    public void keyGeneratorRunnerMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerMapped.setIntegrationModuleId("1");
        	runnerMapped.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(runnerMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerMapped key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Runner Map Found Test.
    @Test
    public void keyGeneratorRunnerMapFoundTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerMapFound.setIntegrationModuleId("1");
        	runnerMapFound.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(runnerMapFound));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerMapFound key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Runner Map New Test.
    @Test
    public void keyGeneratorRunnerMapNewTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerMapNew.setIntegrationModuleId("1");
        	runnerMapNew.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(runnerMapNew));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerMapNew key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Runner Not Mapped Test.
    @Test
    public void keyGeneratorRunnerNotMappedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerNotMapped.setIntegrationModuleId("1");
        	runnerNotMapped.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(runnerNotMapped));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerNotMapped key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Runner Not Mapped Test.
    @Test
    public void keyGeneratorRunnerUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	runnerUpdated.setIntegrationModuleId("1");
        	runnerUpdated.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(runnerUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the runnerUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }

    private SourceRunnersReceivedEvent sourceRunnerReceived = new SourceRunnersReceivedEvent();
    private SourceRunnerCreatedEvent sourceRunnerCreated = new SourceRunnerCreatedEvent();
    private RunnerProcessedEvent sourceRunnerProcessed = new RunnerProcessedEvent();
    private SourceRunnerUpdatedEvent sourceRunnerUpdated = new SourceRunnerUpdatedEvent();

    // Source Runner Received Test.
    @Test
    public void keyGeneratorSourceRunnertReceivedTest() throws RuntimeException {
    	List<Runner> runners = new ArrayList<Runner>();
    	Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new Runner();
        	runner.setEventId("12345");
        	runners.add(runner);
        	sourceRunnerReceived.setIntegrationModuleId("1");
        	sourceRunnerReceived.setRunners(runners);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceRunnerReceived));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerReceived key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Runner Created Test.
    @Test
    public void keyGeneratorSourceRunnerCreatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	sourceRunnerCreated.setIntegrationModuleId("1");
        	sourceRunnerCreated.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceRunnerCreated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerCreated key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Runner Processed Test.
    @Test
    public void keyGeneratorSourceRunnerProcessedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	sourceRunnerProcessed.setIntegrationModuleId("1");
        	sourceRunnerProcessed.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceRunnerProcessed));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerProcessed key: " + keyGenerated + "==>" + diff + " ms");
    }

    // Source Runner Updated Test.
    @Test
    public void keyGeneratorSourceRunnerUpdatedTest() throws RuntimeException {
        com.aldogrand.sbpc.model.Runner runner;
        String keyGenerated = "";

        long diff = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
        	runner = new com.aldogrand.sbpc.model.Runner();
        	runner.setEventId(12345L);
        	sourceRunnerUpdated.setIntegrationModuleId("1");
        	sourceRunnerUpdated.setRunner(runner);

            keyGenerated = keyEventGenerator.generateKey(KFCEventFactory.createMessage(sourceRunnerUpdated));
        }
        diff = System.currentTimeMillis() - start;
        System.out.println("Time to create the sourceRunnerUpdated key: " + keyGenerated + "==>" + diff + " ms");
    }
}
