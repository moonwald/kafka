/**
 *
 */
package com.aldogrand.kfc.services;

import com.aldogrand.sbpc.dataaccess.*;
import com.aldogrand.sbpc.dataaccess.model.*;
import com.aldogrand.sbpc.model.*;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.mapper.*;
import com.aldogrand.kfc.services.mysql.impl.MappingDataWriterServiceImpl;
import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * <p>
 * <b>Title</b> MappingDataWriterServiceImplTest.java
 * </p>
 * com.aldogrand.kfc.services
 * <p>
 * <b>Description</b> kfc-mysql-datawriter.
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
@ContextConfiguration(locations =
{ "classpath:/kfc-mysql-mapper-datawriter-test.xml" })
@TransactionConfiguration(transactionManager = "txManager")
@Transactional
public class MappingDataWriterServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests
{
	private static final String	CONNECTOR_NAME		= "MAP_CONNECTOR";

	private static final long	CONNECTOR_ID		= 1l;

	private static final String	SOURCE_ID			= "1111";
	private static final String	SOURCE_NAME			= "TEST_MAPPER";
	private static final String	EVENT				= "_EVENT";
	private static final String	MARKET				= "_MARKET";
	private static final String	RUNNER				= "_RUNNER";

	private Long				eventId				= null;
	private Long				sourceEventId		= null;
	private Long				marketId			= null;
	private Long				sourceMarketId		= null;
	private Long				runnerId			= null;
	private Long				sourceRunnerId		= null;
	private Long				participantId		= null;

	@PersistenceContext
	private EntityManager		entityManager;

	@Resource(name = "KFCEventFactory")
	private KFCEventFactory		KFCEventFactory;

	@Autowired
	private ConnectorDao		connectorDao;
	@Autowired
	private SourceMarketDao		sourceMarketDao;
	@Autowired
	private SourceEventDao		sourceEventDao;
	@Autowired
	private SourceRunnerDao		sourceRunnerDao;
	@Autowired
	private EventDao			eventDao;
	@Autowired
	private MarketDao			marketDao;
	@Autowired
	private RunnerDao			runnerDao;
	@Autowired
	private ParticipantDao		participantDao;
	@Autowired
	private MetaTagDao			metaTagDao;
	@Autowired
	@Qualifier("requests")
	private MessageChannel		inputChannel;

    @Autowired
    private MappingDataWriterServiceImpl mappingDataWriterService;

	private EventMapNewEvent	eventMapNewEvent	= new EventMapNewEvent();
	private MarketMapNewEvent	marketMapNewEvent	= new MarketMapNewEvent();
	private RunnerMapNewEvent	runnerMapNewEvent	= new RunnerMapNewEvent();
	private EventMapFoundEvent	eventMapFoundEvent	= new EventMapFoundEvent();
	private MarketMapFoundEvent	marketMapFoundEvent	= new MarketMapFoundEvent();
	private RunnerMapFoundEvent	runnerMapFoundEvent	= new RunnerMapFoundEvent();
	private ConnectorDto		connectorDto		= null;
	private ParticipantDto		participantDto		= null;

	@Before
	public void beforeEachTest()
	{
		connectorDto = getTestConnectorDto();
		participantDto = getTestParticipant();
	}

	@After
	public void after()
	{

	}
    @AfterClass
    public static void deleteLog() {
        File file = new File("error.log");
        file.delete();
    }

	// ##################################################//
	// Test notify and the channels //
	// ##################################################//

	/**
	 * Test method (
	 * {@link MappingDataWriterServiceImpl#map(org.springframework.messaging.MessageHeaders, EventMapNewEvent)})
	 */
	@Test
	@Transactional
	public void testEventMapNewWithNotifyChannel()
	{
		// Populate and Load
		populateEventMapNew();
		SourceEventDto sourceEventDtoBeforeMapping = sourceEventDao.getSourceEvent(this.eventMapNewEvent.getSourceEvent().getId(), false);

		// Pre conditions
		assertNull(sourceEventDtoBeforeMapping.getEvent());

		//Method Call
		Message<KFCEvent> message = createMessage(eventMapNewEvent);
		inputChannel.send(message);

		assertNotNull(sourceEventDtoBeforeMapping.getEvent());
		assertEquals(sourceEventDtoBeforeMapping.getStatus().toString(), sourceEventDtoBeforeMapping.getEvent().getEventStatus().toString());
		assertEquals(sourceEventDtoBeforeMapping.getMarkets().size(), sourceEventDtoBeforeMapping.getEvent().getMarkets().size());
		assertEquals(sourceEventDtoBeforeMapping.getStartTime(), sourceEventDtoBeforeMapping.getEvent().getStartTime());
		assertEquals(sourceEventDtoBeforeMapping.getMetaTags().size(), sourceEventDtoBeforeMapping.getEvent().getMetaTags().size());

		// cleanup
		cleanup(sourceEventDtoBeforeMapping);
	}

	/**
	 * Test method (
	 * {@link MappingDataWriterServiceImpl#map(org.springframework.messaging.MessageHeaders, EventMapFoundEvent)})
	 */
	@Test
	@Transactional
	public void testEventMapFoundWithNotifyChannel()
	{
		// load the test data
		populateEventMapFound();
		SourceEventDto sourceEventDtoBeforeMapping = sourceEventDao.getSourceEvent(this.eventMapFoundEvent.getSourceEvent().getId(), false);
		EventDto eventDtoBeforeMapping = eventDao.getEvent(this.eventMapFoundEvent.getEvent().getId());

		// assert pre conditions
		assertNotNull(eventDtoBeforeMapping);
		assertNotNull(sourceEventDtoBeforeMapping);
		assertNull(sourceEventDtoBeforeMapping.getEvent());
		Long time = eventDtoBeforeMapping.getLastChangeTime().getTime();

		// call the method
		Message<KFCEvent> message = createMessage(eventMapFoundEvent);
		inputChannel.send(message);

		// assert post conditions
		EventDto eventDto = sourceEventDtoBeforeMapping.getEvent();
		assertNotNull(eventDto);
		assertEquals(sourceEventDtoBeforeMapping.getStatus().toString(), eventDto.getEventStatus().toString());
		assertEquals(sourceEventDtoBeforeMapping.getMarkets().size(), eventDto.getMarkets().size());
		assertEquals(sourceEventDtoBeforeMapping.getMetaTags().size(), eventDto.getMetaTags().size());
		assertTrue(time < eventDtoBeforeMapping.getLastChangeTime().getTime());

		// cleanup
		cleanup(sourceEventDtoBeforeMapping);

	}

	/**
	 * Test method (
	 * {@link MappingDataWriterServiceImpl#map(org.springframework.messaging.MessageHeaders, MarketMapNewEvent)})
	 * 
	 */
	@Test
	@Transactional
	public void testMarketMapNewWithNotifyChannel()
	{
		// load test data
		populateMarketMapNew();
		SourceMarketDto sourceMarketDtoBeforeMapping = this.sourceMarketDao.getSourceMarket(this.sourceMarketId, false);

		// assert pre condition
		assertNotNull(sourceMarketDtoBeforeMapping);
		assertNull(sourceMarketDtoBeforeMapping.getMarket());
		assertNotNull(sourceMarketDtoBeforeMapping.getSourceEvent());
		assertNotNull(sourceMarketDtoBeforeMapping.getSourceEvent().getEvent());
		// Call the actual method
		Message<KFCEvent> message = createMessage(marketMapNewEvent);
		inputChannel.send(message);

		// post condition asserts
		MarketDto marketDto = sourceMarketDtoBeforeMapping.getMarket();
		assertNotNull(marketDto);
		assertEquals(sourceMarketDtoBeforeMapping.getRunners().size(), marketDto.getRunners().size());
		assertEquals(sourceMarketDtoBeforeMapping.getMarketStatus().toString(), marketDto.getMarketStatus().toString());
		assertEquals(sourceMarketDtoBeforeMapping.getHandicap(), marketDto.getHandicap());
		assertEquals(sourceMarketDtoBeforeMapping.getSourceName(), marketDto.getName());
		assertEquals(sourceMarketDtoBeforeMapping.getType().toString(), marketDto.getType().toString());

		cleanup(sourceMarketDtoBeforeMapping);

	}

	/**
	 * Test method (
	 * {@link MappingDataWriterServiceImpl#map(org.springframework.messaging.MessageHeaders, MarketMapFoundEvent)})
	 * 
	 */
	@Test
	@Transactional
	public void testMarketMapFoundWithNotifyChannel()
	{
		// load test data
		populateMarketMapFound();
		SourceMarketDto sourceMarketDtoBeforeMapping = this.sourceMarketDao.getSourceMarket(this.sourceMarketId, false);
		MarketDto marketDtoBeforeMapping = this.marketDao.getMarket(this.marketId);
		Long time = marketDtoBeforeMapping.getLastChangeTime().getTime();

		// assert pre conditions
		assertNotNull(sourceMarketDtoBeforeMapping);
		assertNull(sourceMarketDtoBeforeMapping.getMarket());
		assertNotNull(sourceMarketDtoBeforeMapping.getSourceEvent());
		assertNotNull(sourceMarketDtoBeforeMapping.getSourceEvent().getEvent());

		// call the data writer
		Message<KFCEvent> message = createMessage(marketMapFoundEvent);
		inputChannel.send(message);

		// assert after condition
		MarketDto marketDto = sourceMarketDtoBeforeMapping.getMarket();
		assertNotNull(marketDto);
		assertEquals(sourceMarketDtoBeforeMapping.getRunners().size(), marketDto.getRunners().size());
		assertEquals(sourceMarketDtoBeforeMapping.getMarketStatus().toString(), marketDto.getMarketStatus().toString());
		assertEquals(sourceMarketDtoBeforeMapping.getHandicap(), marketDto.getHandicap());
		assertEquals(sourceMarketDtoBeforeMapping.getSourceName(), marketDto.getName());
		assertEquals(sourceMarketDtoBeforeMapping.getType().toString(), marketDto.getType().toString());
		assertTrue(time < marketDtoBeforeMapping.getLastChangeTime().getTime());

		// cleanup
		cleanup(sourceMarketDtoBeforeMapping);
	}

	/**
	 * Test method (
	 * {@link MappingDataWriterServiceImpl#map(org.springframework.messaging.MessageHeaders, RunnerMapFoundEvent)})
	 * 
	 */
	@Test
	@Transactional
	public void testRunnerMapFoundWithNotifyChannel()
	{
		// populate test data
		populateRunnerMapFound();
		SourceRunnerDto sourceRunnerDtoBeforeMapping = this.sourceRunnerDao.getSourceRunner(this.sourceRunnerId, false);
		RunnerDto runnerDtoBeforeMapping = this.runnerDao.getRunner(this.runnerId);
		Long time = runnerDtoBeforeMapping.getLastChangeTime().getTime();

		// pre conditions
		assertNull(sourceRunnerDtoBeforeMapping.getRunner());
		assertNotNull(sourceRunnerDtoBeforeMapping);
		assertNotNull(runnerDtoBeforeMapping);

		// call the method
		Message<KFCEvent> message = createMessage(runnerMapFoundEvent);
		inputChannel.send(message);

		// post conditions
		RunnerDto runnerDto = sourceRunnerDtoBeforeMapping.getRunner();
		assertNotNull(sourceRunnerDtoBeforeMapping.getRunner());
		assertEquals(sourceRunnerDtoBeforeMapping.getType().toString(), runnerDto.getType().toString());
		assertEquals(sourceRunnerDtoBeforeMapping.getHandicap(), runnerDto.getHandicap());
		assertEquals(sourceRunnerDtoBeforeMapping.getSourceName(), runnerDto.getName());
		assertTrue(time < runnerDtoBeforeMapping.getLastChangeTime().getTime());

		cleanup(sourceRunnerDtoBeforeMapping);
	}

	/**
	 * Test method (
	 * {@link MappingDataWriterServiceImpl#map(org.springframework.messaging.MessageHeaders, RunnerMapNewEvent)})
	 * 
	 */
	@Test
	@Transactional
	public void testRunnerMapNewWithNotifyChannel()
	{
		// populate Test Data
		populateRunnerMapNew();

		// pre conditions check
		SourceRunnerDto sourceRunnerDtoBeforeMapping = this.sourceRunnerDao.getSourceRunner(this.sourceRunnerId, false);
		assertNull(sourceRunnerDtoBeforeMapping.getRunner());
		assertNotNull(sourceRunnerDtoBeforeMapping);

		// call the actual method
		Message<KFCEvent> message = createMessage(runnerMapNewEvent);
		inputChannel.send(message);

		// post conditions check
		RunnerDto runnerDto = sourceRunnerDtoBeforeMapping.getRunner();
		assertNotNull(runnerDto);
		assertEquals(sourceRunnerDtoBeforeMapping.getType().toString(), runnerDto.getType().toString());
		assertEquals(sourceRunnerDtoBeforeMapping.getHandicap(), runnerDto.getHandicap());
		assertEquals(sourceRunnerDtoBeforeMapping.getSourceName(), runnerDto.getName());

		cleanup(sourceRunnerDtoBeforeMapping);
	}

	/**
	 * Test method (
	 * {@link MappingDataWriterServiceImpl#map(org.springframework.messaging.MessageHeaders, MarketMapNewEvent)})
	 * 
	 */
	@Test
	@Transactional
	public void testRunnerMapNewWithParticipantWithNotifyChannel()
	{
		// populate the test data
		populateRunnerMapNewPtcpnts();

		// pre conditions
		SourceRunnerDto sourceRunnerDtoBeforeMapping = this.sourceRunnerDao.getSourceRunner(this.sourceRunnerId, false);
		assertNull(sourceRunnerDtoBeforeMapping.getRunner());
		assertNotNull(sourceRunnerDtoBeforeMapping);

		// method call
		Message<KFCEvent> message = createMessage(runnerMapNewEvent);
		inputChannel.send(message);

		// post condition check
		RunnerDto runnerDto = sourceRunnerDtoBeforeMapping.getRunner();
		assertNotNull(runnerDto);
		assertNotNull(sourceRunnerDtoBeforeMapping.getRunner());
		assertEquals(sourceRunnerDtoBeforeMapping.getType().toString(), runnerDto.getType().toString());
		assertEquals(sourceRunnerDtoBeforeMapping.getHandicap(), runnerDto.getHandicap());
		assertEquals(sourceRunnerDtoBeforeMapping.getSourceName(), runnerDto.getName());

		// cleanup
		cleanup(sourceRunnerDtoBeforeMapping);
	}


    @Test
    @Ignore
    public void errorHandlerEventMapFoundEvent () throws IOException, InterruptedException {
        EventMapFoundEvent mapEventFound = new EventMapFoundEvent ();
        mapEventFound.setIntegrationModuleId(null);
        mapEventFound.setEvent(new Event());
        Message<KFCEvent> msg = createMessage(mapEventFound);
        mappingDataWriterService.map(msg.getHeaders(), (EventMapFoundEvent) msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("EventMapFoundEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("EventMapFoundEvent"), on(localProfile))).totalLines(), is(3));
    }

    @Test
    @Ignore
    public void errorHandlerEventMapNewEvent () throws IOException, InterruptedException {
        EventMapNewEvent mapEventNew = new EventMapNewEvent ();
        mapEventNew.setIntegrationModuleId(null);
        mapEventNew.setEvent(new Event());
        Message<KFCEvent> msg = createMessage(mapEventNew);
        mappingDataWriterService.map(msg.getHeaders(), (EventMapNewEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("EventMapNewEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("EventMapNewEvent"), on(localProfile))).totalLines(), is(3));
    }

    @Test
    @Ignore
    public void errorHandlerMarketMapFoundEvent () throws IOException, InterruptedException {
        MarketMapFoundEvent mapMarketFound = new MarketMapFoundEvent ();
        mapMarketFound.setIntegrationModuleId(null);
        mapMarketFound.setMarket(new Market());
        Message<KFCEvent> msg = createMessage(mapMarketFound);
        mappingDataWriterService.map(msg.getHeaders(), (MarketMapFoundEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("MarketMapFoundEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("MarketMapFoundEvent"), on(localProfile))).totalLines(), is(3));
    }

    @Test
    @Ignore
    public void errorHandlerMarketMapNewEvent () throws IOException, InterruptedException {
        MarketMapNewEvent mapMarketNew = new MarketMapNewEvent ();
        mapMarketNew.setIntegrationModuleId(null);
        mapMarketNew.setMarket(new Market());
        Message<KFCEvent> msg = createMessage(mapMarketNew);
        mappingDataWriterService.map(msg.getHeaders(), (MarketMapNewEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("MarketMapNewEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("MarketMapNewEvent"), on(localProfile))).totalLines(), is(3));
    }

    @Test
    @Ignore
    public void errorHandlerRunnerMapFoundEvent () throws IOException, InterruptedException {
        RunnerMapFoundEvent mapRunnerFound = new RunnerMapFoundEvent ();
        mapRunnerFound.setIntegrationModuleId(null);
        mapRunnerFound.setRunner(new Runner());
        Message<KFCEvent> msg = createMessage(mapRunnerFound);
        mappingDataWriterService.map(msg.getHeaders(), (RunnerMapFoundEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("RunnerMapFoundEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("RunnerMapFoundEvent"), on(localProfile))).totalLines(), is(3));
    }

    @Test
    @Ignore
    public void errorHandlerRunnerMapNewEvent () throws IOException, InterruptedException {
        RunnerMapNewEvent mapRunnerNew = new RunnerMapNewEvent ();
        mapRunnerNew.setIntegrationModuleId(null);
        mapRunnerNew.setRunner(new Runner());
        Message<KFCEvent> msg = createMessage(mapRunnerNew);
        mappingDataWriterService.map(msg.getHeaders(), (RunnerMapNewEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("RunnerMapNewEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("RunnerMapNewEvent"), on(localProfile))).totalLines(), is(3));
    }

	/**
	 * @param sourceRunnerDtoBeforeMapping
	 */
	private void cleanup(SourceRunnerDto sourceRunnerDtoBeforeMapping)
	{
		SourceMarketDto sourceMarketDto = sourceRunnerDtoBeforeMapping.getSourceMarket();
		this.runnerDao.deleteRunner(sourceRunnerDtoBeforeMapping.getRunner());
		this.sourceRunnerDao.deleteSourceRunner(sourceRunnerDtoBeforeMapping);
		cleanup(sourceMarketDto);
	}

	/**
	 * @param sourceMarketDtoBeforeMapping
	 */
	private void cleanup(SourceMarketDto sourceMarketDtoBeforeMapping)
	{
		SourceEventDto sourceEventDto = sourceMarketDtoBeforeMapping.getSourceEvent();
		this.marketDao.deleteMarket(sourceMarketDtoBeforeMapping.getMarket());
		this.sourceMarketDao.deleteSourceMarket(sourceMarketDtoBeforeMapping);
		cleanup(sourceEventDto);
	}

	/**
	 * @param sourceEventDtoBeforeMapping
	 */
	private void cleanup(SourceEventDto sourceEventDtoBeforeMapping)
	{
		this.eventDao.deleteEvent(sourceEventDtoBeforeMapping.getEvent());
		this.sourceEventDao.deleteSourceEvent(sourceEventDtoBeforeMapping);
		initialiseIds();
	}

	/**
     *
     */
	private void initialiseIds()
	{
		this.eventId = null;
		this.sourceEventId = null;
		this.marketId = null;
		this.sourceMarketId = null;
		this.runnerId = null;
		this.sourceRunnerId = null;
		this.participantId = null;
	}

	/**
     *
     */
	private void populateEventMapFound()
	{
		this.eventMapFoundEvent.setIntegrationModuleId(CONNECTOR_NAME);
		SourceEventDto sourceEventDto = getTestSourceEvent(false);
		SourceEvent sourceEvent = ModelFactory.createSourceEvent(sourceEventDto);
		EventDto eventDto = getTestEvent();
		Event event = ModelFactory.createEvent(eventDto, true);
		this.eventMapFoundEvent.setEvent(event);
		this.eventMapFoundEvent.setSourceEvent(sourceEvent);
	}

	private void populateEventMapNew()
	{
		this.eventMapNewEvent.setIntegrationModuleId(CONNECTOR_NAME);
		SourceEvent sourceEvent = ModelFactory.createSourceEvent(getTestSourceEvent(false));
		List<String> nameVarients = new ArrayList<String>();
		nameVarients.add(SOURCE_NAME + EVENT);
		sourceEvent.setNameVariants(nameVarients);
		this.eventMapNewEvent.setSourceEvent(sourceEvent);
	}

	private void populateMarketMapNew()
	{
		SourceMarketDto sourceMarketDto = getTestSourceMarket(false);
		SourceMarket sourceMarket = ModelFactory.createSourceMarket(sourceMarketDto);
		this.marketMapNewEvent.setIntegrationModuleId(CONNECTOR_NAME);
		this.marketMapNewEvent.setSourceMarket(sourceMarket);
	}

	/**
     *
     */
	private void populateMarketMapFound()
	{
		SourceMarketDto sourceMarketDto = getTestSourceMarket(false);
		SourceMarket sourceMarket = ModelFactory.createSourceMarket(sourceMarketDto);
		Market market = ModelFactory.createMarket(getTestMarket(), true);
		this.marketMapFoundEvent.setIntegrationModuleId(CONNECTOR_NAME);
		this.marketMapFoundEvent.setSourceMarket(sourceMarket);
		this.marketMapFoundEvent.setMarket(market);
	}

	/**
     *
     */
	private void populateRunnerMapFound()
	{
		this.runnerMapFoundEvent.setIntegrationModuleId(connectorDto.getId().toString());
		this.runnerMapFoundEvent.setSourceRunner(ModelFactory.createSourceRunner(getTestSourceRunner()));
		this.runnerMapFoundEvent.setRunner(ModelFactory.createRunner(getTestRunner(), true));
	}

	/**
     *
     */
	private void populateRunnerMapNew()
	{
		this.runnerMapNewEvent.setIntegrationModuleId(connectorDto.getId().toString());
		this.runnerMapNewEvent.setSourceRunner(ModelFactory.createSourceRunner(getTestSourceRunner()));
	}

	/**
   	 * 
   	 */
	private void populateRunnerMapNewPtcpnts()
	{
		this.runnerMapNewEvent.setIntegrationModuleId(connectorDto.getId().toString());
		this.runnerMapNewEvent.setSourceRunner(ModelFactory.createSourceRunner(getTestSourceRunnerParticipant()));
	}

	/**
     *
     */
	private EventDto getTestEvent()
	{
		EventDto event = null;
		if (this.eventId != null)
		{
			event = eventDao.getEvent(this.eventId);
		} else
		{
			event = new EventDto();
			event.setName(SOURCE_NAME + EVENT);
			event.setStartTime(new Date());
			event.setLastChangeTime(new Date());
			event.setEventStatus(EventStatus.FIRST_HALF);
			// event.setMetaTags(getTestMetatags());

			entityManager.persist(event);
		}
		this.eventId = event.getId();
		return event;
	}

	/**
     * @param withMapping
	 * @return
	 */
	private SourceEventDto getTestSourceEvent(boolean withMapping)
	{
		SourceEventDto sourceEventDto = null;
		if (this.sourceEventId != null)
		{
			sourceEventDto = sourceEventDao.getSourceEvent(this.sourceEventId, false);
		} else
		{
			sourceEventDto = new SourceEventDto();
			sourceEventDto.setSourceId(SOURCE_ID);
			sourceEventDto.setSourceName(SOURCE_NAME + EVENT);
			sourceEventDto.setCreator(true);
			sourceEventDto.setCreationTime(new Date());
			sourceEventDto.setStartTime(new Date());
			sourceEventDto.setStatus(EventStatus.FIRST_HALF);
			sourceEventDto.setConnector(connectorDto);
			// sourceEventDto.setMetaTags(getTestMetatags());
			if (withMapping)
			{
				sourceEventDto.setEvent(getMappedEvent());
			}
			entityManager.persist(sourceEventDto);
		}
		this.sourceEventId = sourceEventDto.getId();
		return sourceEventDto;
	}

	/**
	 * @return
	 */
	private EventDto getMappedEvent()
	{
		return getTestEvent();
	}

	/**
	 * @return
	 */
	private SourceMarketDto getTestSourceMarket(boolean withMapping)
	{
		SourceMarketDto sourceMarketDto = null;
		if (this.sourceMarketId != null)
		{
			sourceMarketDto = sourceMarketDao.getSourceMarket(this.sourceMarketId, false);
		} else
		{
			sourceMarketDto = new SourceMarketDto();
			sourceMarketDto.setConnector(connectorDto);
			sourceMarketDto.setSourceId(SOURCE_ID);
			sourceMarketDto.setSourceName(SOURCE_NAME + MARKET);
			sourceMarketDto.setCreator(true);
			sourceMarketDto.setType(MarketType.HANDICAP);
			sourceMarketDto.setMarketStatus(MarketStatus.OPEN);
			sourceMarketDto.setPeriod(Period.FIRST_HALF);
			sourceMarketDto.setHandicap(0.67);
			if (withMapping)
			{
				sourceMarketDto.setMarket(getTestMarket());
			}
			sourceMarketDto.setSourceEvent(getTestSourceEvent(true));
			sourceMarketDto.setCreator(true);
			sourceMarketDto.setCreationTime(new Date());
			sourceMarketDto.setCreationTime(new Date());
			entityManager.persist(sourceMarketDto);
		}
		this.sourceMarketId = sourceMarketDto.getId();
		return sourceMarketDto;
	}

	/**
	 * @return
	 */
	private MarketDto getTestMarket()
	{

		MarketDto marketDto = null;
		if (this.marketId != null)
		{
			marketDto = marketDao.getMarket(this.marketId);
		} else
		{
			marketDto = new MarketDto();
			marketDto.setEvent(getTestEvent());
			marketDto.setName(SOURCE_NAME + MARKET);
			marketDto.setType(MarketType.HANDICAP);
			marketDto.setEvent(getTestEvent());
			marketDto.setHandicap(0.67);
			marketDto.setPeriod(Period.FIRST_HALF);
			marketDto.setMarketStatus(MarketStatus.OPEN);
			marketDto.setLastChangeTime(new Date());
			entityManager.persist(marketDto);
		}
		this.marketId = marketDto.getId();
		return marketDto;
	}

	/**
	 * @return
	 */
	private SourceRunnerDto getTestSourceRunner()
	{
		SourceRunnerDto sourceRunnerDto = null;
		if (this.sourceRunnerId != null)
		{
			sourceRunnerDto = this.sourceRunnerDao.getSourceRunner(this.sourceRunnerId, false);
		} else
		{
			sourceRunnerDto = new SourceRunnerDto();
			sourceRunnerDto.setConnector(connectorDto);
			sourceRunnerDto.setSourceMarket(getTestSourceMarket(true));
			sourceRunnerDto.setSourceName(SOURCE_NAME + RUNNER);
			sourceRunnerDto.setSourceId(SOURCE_ID);
			sourceRunnerDto.setType(RunnerType.PARTICIPANT);
			sourceRunnerDto.setSide(RunnerSide.HOME);
			sourceRunnerDto.setSequence(13);
			sourceRunnerDto.setHandicap(0.65);
			sourceRunnerDto.setRotationNumber(2);
			sourceRunnerDto.setRunnerStatus(RunnerStatus.OPEN);
			sourceRunnerDto.setResultStatus(ResultStatus.LOSER);
			entityManager.persist(sourceRunnerDto);
		}
		this.sourceRunnerId = sourceRunnerDto.getId();
		return sourceRunnerDto;
	}

	/**
	 * @return
	 */
	private SourceRunnerDto getTestSourceRunnerParticipant()
	{
		SourceRunnerDto sourceRunnerDto = null;
		if (this.sourceRunnerId != null)
		{
			sourceRunnerDto = this.sourceRunnerDao.getSourceRunner(this.sourceRunnerId, false);
		} else
		{
			sourceRunnerDto = new SourceRunnerDto();
			sourceRunnerDto.setConnector(connectorDto);
			sourceRunnerDto.setSourceMarket(getTestSourceMarket(true));
			sourceRunnerDto.setSourceName(SOURCE_NAME + RUNNER + "Ptipnts");
			sourceRunnerDto.setSourceId(SOURCE_ID);
			sourceRunnerDto.setType(RunnerType.PARTICIPANT);
			sourceRunnerDto.setSide(RunnerSide.HOME);
			sourceRunnerDto.setSequence(13);
			sourceRunnerDto.setHandicap(0.65);
			sourceRunnerDto.setRotationNumber(2);
			sourceRunnerDto.setRunnerStatus(RunnerStatus.OPEN);
			sourceRunnerDto.setResultStatus(ResultStatus.LOSER);
			entityManager.persist(sourceRunnerDto);
		}
		this.sourceRunnerId = sourceRunnerDto.getId();
		return sourceRunnerDto;
	}

	/**
     *
     */
	private ParticipantDto getTestParticipant()
	{
		ParticipantDto participantDto = null;
		if (participantId != null)
		{
			participantDto = participantDao.getParticipant(participantId);
		} else
		{
			participantDto = new ParticipantDto();
			participantDto.setName(SOURCE_NAME + RUNNER);
			NameVariantDto nameVariantDto = new NameVariantDto();
			nameVariantDto.setVariant(SOURCE_NAME + RUNNER);
			participantDto.getNameVariants().add(nameVariantDto);
			nameVariantDto = new NameVariantDto();
			nameVariantDto.setVariant(SOURCE_NAME + RUNNER.toLowerCase());
			participantDto.getNameVariants().add(nameVariantDto);
			participantDto.setType(ParticipantType.TEAM);
			entityManager.persist(participantDto);
		}
		participantId = participantDto.getId();
		return participantDto;
	}

	/**
	 * @return
	 */
	private RunnerDto getTestRunner()
	{
		RunnerDto runnerDto = null;
		if (this.runnerId != null)
		{
			runnerDto = this.runnerDao.getRunner(this.runnerId);
		} else
		{
			runnerDto = new RunnerDto();
			runnerDto.setMarket(getTestMarket());
			runnerDto.setName(SOURCE_NAME + RUNNER);
			runnerDto.setType(RunnerType.PARTICIPANT);
			runnerDto.setSide(RunnerSide.HOME);
			runnerDto.setParticipant(participantDto);
			runnerDto.setSequence(13);
			runnerDto.setHandicap(0.65);
			runnerDto.setRotationNumber(2);
			runnerDto.setRunnerStatus(RunnerStatus.OPEN);
			runnerDto.setResultStatus(ResultStatus.LOSER);
			entityManager.persist(runnerDto);
		}
		this.runnerId = runnerDto.getId();
		return runnerDto;
	}

	/**
	 * @return
	 */
	private ConnectorDto getTestConnectorDto()
	{
		ConnectorDto connectorDto = connectorDao.getConnector(CONNECTOR_NAME);

		if (connectorDto == null)
		{
			connectorDto = new ConnectorDto();
			connectorDto.setName(CONNECTOR_NAME);
			connectorDto.setId(CONNECTOR_ID);
			connectorDto.setEnabled(true);
			connectorDto.setEventContributor(true);
			entityManager.persist(connectorDto);
		}
		return connectorDto;
	}

	private Message<KFCEvent> createMessage(KFCEvent event)
	{
		try
		{
			return KFCEventFactory.createMessage(event);
		} catch (ProducerException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
