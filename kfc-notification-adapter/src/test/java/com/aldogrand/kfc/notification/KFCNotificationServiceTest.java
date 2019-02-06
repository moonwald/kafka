/**
 * 
 */
package com.aldogrand.kfc.notification;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import javax.jms.JMSException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.Offer;
import com.aldogrand.sbpc.model.OfferStatus;
import com.aldogrand.sbpc.model.Price;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.mapped.EventCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.EventMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.MarketMappedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerCreatedEvent;
import com.aldogrand.kfc.msg.events.mapped.RunnerMappedEvent;
import com.aldogrand.kfc.msg.events.processed.EventUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.MarketUpdatedEvent;
import com.aldogrand.kfc.msg.events.processed.RunnerUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.OfferUpdatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceCreatedEvent;
import com.aldogrand.kfc.msg.events.raw.PriceUpdatedEvent;
import com.aldogrand.kfc.notification.service.KFCNotificationService;
import com.aldogrand.kfc.notification.service.KFCNotificationService;
import com.aldogrand.kfc.notification.service.impl.KFCNotificationServiceImpl.HeaderFieldName;
import com.aldogrand.kfc.utils.general.OddsType;

/**
 * <p>
 * <b>Title</b> kfcWorkQueuePopulatorServiceTest.java
 * </p>
 * com.aldogrand.kfc.hz
 * <p>
 * <b>Description</b> kfc-hz-adapter.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:/kfc-notification-adapter.xml", "classpath:/kfc-notification-adapter-test.xml"})
public class KFCNotificationServiceTest
{
	@Autowired
	private KFCEventFactory					KFCEventFactory;

	@Autowired
	@Qualifier("input")
	private MessageChannel					inputChannel;
	
	private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TestMessageListener testMessageListener;

    @Autowired
    private KFCNotificationService kfcNotificationService;
    
	private PriceCreatedEvent	priceCreated	= new PriceCreatedEvent();
	private PriceUpdatedEvent	priceUpdated	= new PriceUpdatedEvent();
	private OfferCreatedEvent	offerCreated	= new OfferCreatedEvent();
	private OfferUpdatedEvent	offerUpdated	= new OfferUpdatedEvent();
	private RunnerUpdatedEvent	runnerUpdated	= new RunnerUpdatedEvent();
	private RunnerMappedEvent	runnerMapped	= new RunnerMappedEvent();
	private RunnerCreatedEvent	runnerCreated	= new RunnerCreatedEvent();
	private MarketUpdatedEvent	marketUpdated	= new MarketUpdatedEvent();
	private MarketMappedEvent	marketMapped	= new MarketMappedEvent();
	private MarketCreatedEvent	marketCreated	= new MarketCreatedEvent();
	private EventUpdatedEvent	eventUpdated	= new EventUpdatedEvent();
	private EventMappedEvent	eventMapped		= new EventMappedEvent();
	private EventCreatedEvent	eventCreated	= new EventCreatedEvent();

	@Before
	public void setup()
	{
		Price price = new Price();
		price.setConnectorId(4l);
		price.setBettingPlatformId(4l);
		price.setEventId(4l);
		price.setMarketId(4l);
		price.setRunnerId(4l);
		price.setId(4l);
		price.setSide(PriceSide.WIN);
		price.setSequence(4);
		price.setOddsType(OddsType.US);
		priceCreated.setPrice(price);
		
		Price price2 = new Price();
		price2.setConnectorId(4l);
		price2.setBettingPlatformId(4l);
		price2.setEventId(4l);
		price2.setMarketId(4l);
		price2.setRunnerId(4l);
		price2.setId(4l);
		price2.setSide(PriceSide.WIN);
		price2.setSequence(4);
		price2.setOddsType(OddsType.DECIMAL);
		priceUpdated.setPrice(price2);
		
		
        Offer offer = new Offer();
		offer.setConnectorId(11l);
        offer.setEventId(11l);
        offer.setMarketId(11l);
        offer.setRunnerId(11l);
        offer.setAccountId(11l);
        offer.setId(11l);
        offer.setSide(PriceSide.WIN);
        offer.setStatus(OfferStatus.MATCHED);
		offerCreated.setOffer(offer);
        
		offer = new Offer();
		offer.setConnectorId(12l);
        offer.setEventId(12l);
        offer.setMarketId(12l);
        offer.setRunnerId(12l);
        offer.setAccountId(12l);
        offer.setId(12l);
        offer.setSide(PriceSide.WIN);
        offer.setStatus(OfferStatus.MATCHED);
		offerUpdated.setOffer(offer);
		
		Runner runner = new Runner();
		runner.setEventId(1l);
		runner.setMarketId(2l);
		runner.setId(3l);
		runnerCreated.setRunner(runner);
		runnerCreated.setIntegrationModuleId("1");
		
		runner = new Runner();
		runner.setEventId(4l);
		runner.setMarketId(5l);
		runner.setId(6l);
		runnerUpdated.setRunner(runner);
		runnerUpdated.setIntegrationModuleId("2");
		
		runner = new Runner();
		runner.setEventId(7l);
		runner.setMarketId(8l);
		runner.setId(9l);
		runnerMapped.setRunner(runner);
		runnerMapped.setIntegrationModuleId("3");
		
		Market market = new Market();
		market.setId(1l);
		market.setEventId(2l);
		this.marketCreated.setIntegrationModuleId("5");
		this.marketCreated.setMarket(market);

		market = new Market();
		market.setId(2l);
		market.setEventId(3l);
		this.marketUpdated.setIntegrationModuleId("6");
		this.marketUpdated.setMarket(market);

		market = new Market();
		market.setId(3l);
		market.setEventId(4l);
		this.marketMapped.setIntegrationModuleId("7");
		this.marketMapped.setMarket(market);

		Event event = new Event();
		event.setId(1l);
		this.eventCreated.setIntegrationModuleId("8");
		this.eventCreated.setEvent(event);

		event = new Event();
		event.setId(2l);
		this.eventUpdated.setIntegrationModuleId("9");
		this.eventUpdated.setEvent(event);

		event = new Event();
		event.setId(3l);
		this.eventMapped.setIntegrationModuleId("10");
		this.eventMapped.setEvent(event);
	}
	@After
	public void destroy()
	{
		priceCreated = new PriceCreatedEvent();
		priceUpdated = new PriceUpdatedEvent();
		offerUpdated = new OfferUpdatedEvent();
		offerCreated = new OfferCreatedEvent();
		runnerUpdated = new RunnerUpdatedEvent();
		runnerMapped = new RunnerMappedEvent();
		runnerCreated = new RunnerCreatedEvent();
		marketUpdated = new MarketUpdatedEvent();
		marketMapped = new MarketMappedEvent();
		marketCreated = new MarketCreatedEvent();
		eventUpdated = new EventUpdatedEvent();
		eventMapped = new EventMappedEvent();
		eventCreated = new EventCreatedEvent();
	}
    @AfterClass
    public static void afterClass() {
    	File file = new File("error.log");
    	file.delete();
    }

	@Test
	public void testEventCreated() throws JsonProcessingException, IOException, InterruptedException, JMSException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(eventCreated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.EVENT_CREATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
		assertNotNull(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName()),eventCreated.getEvent().getId());
	}

	@Test
	@Ignore
	public void testEventUpdated() throws JsonProcessingException, IOException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(eventUpdated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.EVENT_UPDATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
		assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),eventUpdated.getEvent().getId());
	}

	@Test
	@Ignore
	public void testEventMapped() throws JsonProcessingException, IOException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(eventMapped);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.EVENT_MAPPED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
		assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),eventMapped.getEvent().getId());
	}

	@Test
	@Ignore
	public void testMarketCreated() throws JsonProcessingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(marketCreated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.MARKET_CREATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),marketCreated.getMarket().getEventId());
		assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())),marketCreated.getMarket().getId());
		assertEquals("ADDED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}

	@Test
	@Ignore
	public void testMarketUpdated() throws NumberFormatException, JMSException, JsonProcessingException, IOException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(marketUpdated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.MARKET_UPDATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),marketUpdated.getMarket().getEventId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())),marketUpdated.getMarket().getId());
        assertEquals("CHANGED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}

	@Test
	@Ignore
	public void testMarketMapped() throws JsonProcessingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(marketMapped);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.MARKET_MAPPED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),marketMapped.getMarket().getEventId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())),marketMapped.getMarket().getId());
        assertEquals("CHANGED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}

	@Test
	@Ignore
	public void testRunnerCreated() throws JsonProcessingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(runnerCreated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.RUNNER_CREATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),runnerCreated.getRunner().getEventId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())),runnerCreated.getRunner().getMarketId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.RUNNER_ID.getName())),runnerCreated.getRunner().getId());
        assertEquals("ADDED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}

	@Test
	@Ignore
	public void testRunnerUpdated() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(runnerUpdated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.RUNNER_UPDATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),runnerUpdated.getRunner().getEventId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())),runnerUpdated.getRunner().getMarketId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.RUNNER_ID.getName())),runnerUpdated.getRunner().getId());
        assertEquals("CHANGED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}

	@Test
	@Ignore
	public void testRunnerMapped() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(runnerMapped);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.RUNNER_MAPPED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())),runnerMapped.getRunner().getEventId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())),runnerMapped.getRunner().getMarketId());
        assertEquals(Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.RUNNER_ID.getName())),runnerMapped.getRunner().getId());
        assertEquals("CHANGED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}

	@Test
	@Ignore
	public void testPrices() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(priceCreated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.PRICE_CREATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(priceCreated.getPrice().getConnectorId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.CONNECTOR_ID.getName())));
        assertEquals(priceCreated.getPrice().getBettingPlatformId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.BETTING_PLATFORM_ID.getName())));
        assertEquals(priceCreated.getPrice().getEventId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())));
        assertEquals(priceCreated.getPrice().getMarketId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())));
        assertEquals(priceCreated.getPrice().getRunnerId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.RUNNER_ID.getName())));
        assertEquals(priceCreated.getPrice().getId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.PRICE_ID.getName())));
        assertEquals(priceCreated.getPrice().getSequence(),Integer.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.SEQUENCE.getName())));
        assertEquals(priceCreated.getPrice().getSide().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.SIDE.getName()));
        assertEquals(priceCreated.getPrice().getOddsType().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.ODD_TYPE.getName()));

        assertEquals("ADDED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
        
        // Message to be sent.
        message = objectMapper.writeValueAsString(priceUpdated);
		// call the method

        //Message Builder
        builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.PRICE_UPDATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(priceUpdated.getPrice().getConnectorId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.CONNECTOR_ID.getName())));
        assertEquals(priceUpdated.getPrice().getBettingPlatformId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.BETTING_PLATFORM_ID.getName())));
        assertEquals(priceUpdated.getPrice().getEventId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())));
        assertEquals(priceUpdated.getPrice().getMarketId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())));
        assertEquals(priceUpdated.getPrice().getRunnerId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.RUNNER_ID.getName())));
        assertEquals(priceUpdated.getPrice().getId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.PRICE_ID.getName())));
        assertEquals(priceUpdated.getPrice().getSequence(),Integer.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.SEQUENCE.getName())));
        assertEquals(priceUpdated.getPrice().getSide().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.SIDE.getName()));
        assertEquals(priceUpdated.getPrice().getOddsType().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.ODD_TYPE.getName()));

        assertEquals("CHANGED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}
	
	@Test
	@Ignore
	public void testOfferCreated() throws JsonProcessingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(offerCreated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.OFFER_CREATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(offerCreated.getOffer().getConnectorId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.CONNECTOR_ID.getName())));
        assertEquals(offerCreated.getOffer().getEventId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())));
        assertEquals(offerCreated.getOffer().getMarketId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())));
        assertEquals(offerCreated.getOffer().getRunnerId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.RUNNER_ID.getName())));
        assertEquals(offerCreated.getOffer().getAccountId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.ACCOUNT_ID.getName())));
        assertEquals(offerCreated.getOffer().getId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.OFFER_ID.getName())));
        assertEquals(offerCreated.getOffer().getSide().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.SIDE.getName()));
        assertEquals(offerCreated.getOffer().getStatus().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.STATUS.getName()));

        assertEquals("ADDED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}
	@Test
	@Ignore
	public void testOfferUpdated() throws JsonProcessingException, IOException, NumberFormatException, JMSException, InterruptedException
	{
        // Message to be sent.
        String message = objectMapper.writeValueAsString(offerUpdated);
		// call the method

        //Message Builder
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, EventContentType.OFFER_UPDATED);
        
        inputChannel.send(builder.build());

		// post conditions
        Thread.sleep(500l);
        assertEquals(offerUpdated.getOffer().getConnectorId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.CONNECTOR_ID.getName())));
        assertEquals(offerUpdated.getOffer().getEventId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.EVENT_ID.getName())));
        assertEquals(offerUpdated.getOffer().getMarketId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.MARKET_ID.getName())));
        assertEquals(offerUpdated.getOffer().getRunnerId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.RUNNER_ID.getName())));
        assertEquals(offerUpdated.getOffer().getAccountId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.ACCOUNT_ID.getName())));
        assertEquals(offerUpdated.getOffer().getId(),Long.valueOf(testMessageListener.getMessage().getStringProperty(HeaderFieldName.OFFER_ID.getName())));
        assertEquals(offerUpdated.getOffer().getSide().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.SIDE.getName()));
        assertEquals(offerUpdated.getOffer().getStatus().toString(),testMessageListener.getMessage().getStringProperty(HeaderFieldName.STATUS.getName()));
        
        assertEquals("CHANGED", testMessageListener.getMessage().getStringProperty(HeaderFieldName.NOTIFICATION_TYPE.getName()));
	}
	
	@Test
	@Ignore
	public void testEventCreatedERROR() throws JsonProcessingException, IOException, InterruptedException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new EventCreatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("EventCreatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("EventCreatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testEventUpdatedERROR() throws JsonProcessingException, IOException, JMSException, InterruptedException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new EventUpdatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("EventUpdatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("EventUpdatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testEventMappedERROR() throws JsonProcessingException, IOException, JMSException, InterruptedException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new EventMappedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("EventMappedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("EventMappedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testMarketCreatedERROR() throws JsonProcessingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new MarketCreatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("MarketCreatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("MarketCreatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testMarketUpdatedERROR() throws NumberFormatException, JMSException, JsonProcessingException, IOException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new MarketUpdatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("MarketUpdatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("MarketUpdatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testMarketMappedERROR() throws JsonProcessingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new MarketMappedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("MarketMappedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("MarketMappedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testRunnerCreatedERROR() throws JsonProcessingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new RunnerCreatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("RunnerCreatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("RunnerCreatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testRunnerUpdatedERROR() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new RunnerUpdatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("RunnerUpdatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("RunnerUpdatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}
	@Test
	@Ignore
	public void testRunnerMappedERROR() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new RunnerMappedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("RunnerMappedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("RunnerMappedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}

	@Test
	@Ignore
	public void testPriceCreatedERROR() throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new PriceCreatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("PriceCreatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("PriceCreatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}
	@Test
	@Ignore
	public void testOfferCreatedERROR() throws JsonProcessingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new OfferCreatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("OfferCreatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("OfferCreatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}
	@Test
	@Ignore
	public void testOfferUpdatedERROR() throws JsonProcessingException, IOException, NumberFormatException, JMSException
	{
		try{
			kfcNotificationService.notify(new MessageHeaders(null),new OfferUpdatedEvent());
		}catch(Exception e){

		}finally{
			// Checks the exception has been logged
			File file = new File("error.log");
			Assert.assertTrue(file != null && file.exists());

			// Checks the logged exception is the one expected
			Profile localProfile = ProfileBuilder.newBuilder()
					.name("OfferUpdatedEvent")
					.filePath("error.log")
					.onLocalhost()
					.build();
			assertThat(executing(grep(constantExpression("OfferUpdatedEvent"), on(localProfile))).totalLines(), is(3));
		}    
	}
}
