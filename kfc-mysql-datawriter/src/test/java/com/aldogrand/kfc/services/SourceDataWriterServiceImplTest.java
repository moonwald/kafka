package com.aldogrand.kfc.services;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

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
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.connectors.model.Account;
import com.aldogrand.sbpc.connectors.model.Connector;
import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.sbpc.connectors.model.Market;
import com.aldogrand.sbpc.connectors.model.Offer;
import com.aldogrand.sbpc.connectors.model.Position;
import com.aldogrand.sbpc.connectors.model.Price;
import com.aldogrand.sbpc.connectors.model.Runner;
import com.aldogrand.sbpc.dataaccess.AccountDao;
import com.aldogrand.sbpc.dataaccess.BetDao;
import com.aldogrand.sbpc.dataaccess.BettingPlatformDao;
import com.aldogrand.sbpc.dataaccess.ConnectorDao;
import com.aldogrand.sbpc.dataaccess.EventDao;
import com.aldogrand.sbpc.dataaccess.MarketDao;
import com.aldogrand.sbpc.dataaccess.MetaTagDao;
import com.aldogrand.sbpc.dataaccess.OfferDao;
import com.aldogrand.sbpc.dataaccess.PositionDao;
import com.aldogrand.sbpc.dataaccess.PriceDao;
import com.aldogrand.sbpc.dataaccess.ReportJobInfoDao;
import com.aldogrand.sbpc.dataaccess.RunnerDao;
import com.aldogrand.sbpc.dataaccess.SettledBetDao;
import com.aldogrand.sbpc.dataaccess.SourceEventDao;
import com.aldogrand.sbpc.dataaccess.SourceEventIncidentDao;
import com.aldogrand.sbpc.dataaccess.SourceMarketDao;
import com.aldogrand.sbpc.dataaccess.SourceRunnerDao;
import com.aldogrand.sbpc.dataaccess.model.AccountDto;
import com.aldogrand.sbpc.dataaccess.model.BettingPlatformDto;
import com.aldogrand.sbpc.dataaccess.model.ConnectorDto;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.OfferDto;
import com.aldogrand.sbpc.dataaccess.model.ParticipantDto;
import com.aldogrand.sbpc.dataaccess.model.ParticipantType;
import com.aldogrand.sbpc.dataaccess.model.PositionDto;
import com.aldogrand.sbpc.dataaccess.model.PriceDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.dataaccess.model.SourceEventDto;
import com.aldogrand.sbpc.dataaccess.model.SourceMarketDto;
import com.aldogrand.sbpc.dataaccess.model.SourceRunnerDto;
import com.aldogrand.sbpc.model.Currency;
import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.MetaTag;
import com.aldogrand.sbpc.model.MetaTagType;
import com.aldogrand.sbpc.model.OfferStatus;
import com.aldogrand.sbpc.model.OfferType;
import com.aldogrand.sbpc.model.Period;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.fetcher.AccountReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.BettingPlatformReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.ConnectorReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventIncidentReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.EventsClosedStatusReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.MarketsClosedStatusReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.OffersReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PositionsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.PricesReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SettledBetsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceMarketsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceRunnersReceivedEvent;
import com.aldogrand.kfc.services.mysql.SourceDataWriterService;
import com.aldogrand.kfc.utils.general.OddsType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/kfc-mysql-datawriter-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SourceDataWriterServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	private static String bettingok_CONNECTOR_ID = "10";
	private static String SMARTODDS_CONNECTOR_ID = "3";
	
	private static String MATCHBOOK_CONNECTOR_ID = "1";
	private static String MATCHBOOK_CONNECTOR_NAME = "Matchbook";
	private static String BETTING_PLATFORM_MATCHBOOK_NAME = "Matchbook";

    @Autowired
    private DataSource dataSource;
    
    @Autowired
	private KFCEventFactory KFCEventFactory;
	
    @Autowired
	private SourceDataWriterService dataWriter;

	@Autowired
	private ConnectorDao connectorDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private BettingPlatformDao bettingPlatformDao;

	@Autowired
	private EventDao eventDao;

	@Autowired
	private SourceEventDao sourceEventDao;

	@Autowired
	private MetaTagDao metaTagDao;

	@Autowired
	private MarketDao marketDao;

	@Autowired
	private SourceMarketDao sourceMarketDao;

	@Autowired
	private RunnerDao runnerDao;

	@Autowired
	private SourceRunnerDao sourceRunnerDao;

	@Autowired
	private PriceDao priceDao;

	@Autowired
	private PositionDao positionDao;

	@Autowired
	private OfferDao offerDao;

	@Autowired
	private BetDao betDao;

	@Autowired
	private SourceEventIncidentDao sourceEventIncidentDao;

	@Autowired
	private SettledBetDao settledBetDao;

	@Autowired
	private ReportJobInfoDao reportJobInfoDao;
	
	@PersistenceContext
	private EntityManager entityManager;
		
	private List<SourceEventDto> sourceEvents = new ArrayList<SourceEventDto>();
	private List<EventDto> events = new ArrayList<EventDto>();
	private List<SourceMarketDto> sourceMarkets = new ArrayList<SourceMarketDto>();
	private List<MarketDto> markets = new ArrayList<MarketDto>();
	private List<AccountDto> accounts = new ArrayList<AccountDto>();
	private List<SourceRunnerDto> sourceRunners = new ArrayList<SourceRunnerDto>();
	private List<RunnerDto> runners = new ArrayList<RunnerDto>();
	private List<ConnectorDto> connectors = new ArrayList<ConnectorDto>();
	private List<ParticipantDto> participants = new ArrayList<ParticipantDto>();
    private ObjectMapper objectMapper;

    @Before
	@Transactional
	public void before() throws Exception {
		Date now = new Date();

		EventDto eventDto = new EventDto();
		eventDto.setEventStatus(EventStatus.PENALTY_SHOOTOUT);
		eventDto.setLastChangeTime(now);
		eventDto.setName("AldoGrand Event");
		eventDto.setStartTime(now);
		entityManager.persist(eventDto);
		events.add(eventDto);
		
		MarketDto marketDto = new MarketDto();
		marketDto.setEvent(eventDto);
		marketDto.setLastChangeTime(now);
		marketDto.setMarketStatus(MarketStatus.SUSPENDED);
		marketDto.setName("AldoGrand kfc");
		marketDto.setPeriod(Period.FULL_EVENT);
		marketDto.setType(MarketType.MULTI_RUNNER);
		entityManager.persist(marketDto);
		markets.add(marketDto);
		
		ParticipantDto participant = new ParticipantDto();
		participant.setName("Kafka");
		participant.setType(ParticipantType.INDIVIDUAL);
		entityManager.persist(participant);
		participants.add(participant);
		
		RunnerDto runnerDto = new RunnerDto();
		runnerDto.setLastChangeTime(now);
		runnerDto.setMarket(marketDto);
		runnerDto.setName("Kafka");
		runnerDto.setParticipant(participant);
		runnerDto.setRunnerStatus(RunnerStatus.OPEN);
		runnerDto.setSequence(1);
		runnerDto.setSide(RunnerSide.HOME);
		runnerDto.setType(RunnerType.PARTICIPANT);
		entityManager.persist(runnerDto);
		runners.add(runnerDto);
		
		marketDto.setRunners(runners);
		entityManager.persist(marketDto);
		
		ConnectorDto connectorDto = new ConnectorDto();		
		connectorDto.setName("Matchbook");
		connectorDto.setEnabled(true);
		connectorDto.setEventContributor(true);
		connectorDto.setOfferManagementEnabled(true);
		connectorDto.setPushSourceData(false);
		connectorDto.setCreationTime(now);
		connectorDto.setLastFetchTime(now);
		connectorDto.setLastChangeTime(now);	
		entityManager.persist(connectorDto);
		connectors.add(connectorDto);
				
		SourceEventDto sourceEventDto = new SourceEventDto();
		sourceEventDto.setConnector(connectorDto);
		sourceEventDto.setCreationTime(now);
		sourceEventDto.setCreator(true);
		sourceEventDto.setSourceId("8888889");
		sourceEventDto.setSourceName("AldoGrand Event");
		sourceEventDto.setStartTime(now);
		sourceEventDto.setStatus(EventStatus.SUSPENDED);
		sourceEventDto.setLastFetchTime(now);
		sourceEventDto.setLastChangeTime(now);
		sourceEventDto.setEvent(eventDto);
		entityManager.persist(sourceEventDto);
		sourceEvents.add(sourceEventDto);		
		
		SourceMarketDto sourceMarket = new SourceMarketDto();
		sourceMarket.setConnector(connectorDto);
		sourceMarket.setSourceId("7777779");
		sourceMarket.setSourceName("AldoGrand kfc");
		sourceMarket.setType(MarketType.MULTI_RUNNER);
		sourceMarket.setHandicap(null);
		sourceMarket.setPeriod(Period.FULL_EVENT);
		sourceMarket.setMarketStatus(MarketStatus.SUSPENDED);
		sourceMarket.setSourceEvent(sourceEventDto);
		sourceMarket.setCreationTime(now);
		sourceMarket.setLastChangeTime(now);
		sourceMarket.setLastFetchTime(now);
		sourceMarket.setCreator(true);
		entityManager.persist(sourceMarket);
		sourceMarkets.add(sourceMarket);
				
		SourceRunnerDto sourceRunner = new SourceRunnerDto();
		sourceRunner.setConnector(connectorDto);
		sourceRunner.setCreationTime(now);
		sourceRunner.setCreator(true);
		sourceRunner.setHandicap(null);
		sourceRunner.setLastChangeTime(now);
		sourceRunner.setLastFetchTime(now);
		sourceRunner.setResultStatus(null);
		sourceRunner.setRotationNumber(null);
		sourceRunner.setRunnerStatus(RunnerStatus.OPEN);
		sourceRunner.setSequence(1);
		sourceRunner.setSide(RunnerSide.HOME);
		sourceRunner.setSourceId("111");
		sourceRunner.setSourceMarket(sourceMarket);
		sourceRunner.setSourceName("Kafka");
		sourceRunner.setType(RunnerType.PARTICIPANT);
		entityManager.persist(sourceRunner);
		sourceRunners.add(sourceRunner);
		
		AccountDto account = new AccountDto();
		account.setAvailableAmount(new BigDecimal(1000));
		account.setBalance(new BigDecimal(1100));
		account.setConnector(connectorDto);
		account.setCreationTime(now);
		account.setCurrency(Currency.EUR);
		account.setLastChangeTime(now);
		account.setLastFetchTime(now);
		account.setPassword("Password");
		account.setUsername("kfc");
		entityManager.persist(account);
		accounts.add(account);
		
//		ScoreDto scoreDto = new ScoreDto();
//		scoreDto.setAway(1);
//		scoreDto.setCreationTime(now);
//		scoreDto.setHome(1);
//		scoreDto.setLastChangeTime(now);
//		scoreDto.setLastFetchTime(now);
//		scoreDto.setType(ScoreType.GAME);
//		scoreDto.setSourceEvent(sourceEventDto);
//		
//		SourceEventIncidentDto eventIncidentDto = new SourceEventIncidentDto();
//		eventIncidentDto.setDangerballStatus(CurrentDangerballStatus.AWAY_CORNER);
//		eventIncidentDto.setCreationTime(now);
//		eventIncidentDto.setElapsedTime(now);
//		eventIncidentDto.setEventPhase(EventPhase.BEFORE_EXTRA_TIME);
//		eventIncidentDto.setLastChangeTime(now);
//		eventIncidentDto.setLastFetchTime(now);
//		eventIncidentDto.setIncidentType(IncidentType.AWAY_CORNER);
//		eventIncidentDto.setScore(scoreDto);
//		eventIncidentDto.setSourceEvent(sourceEventDto);
//		entityManager.persist(eventIncidentDto);
    }

    @AfterClass
    public static void deleteLog() {
        File file = new File("error.log");
        file.delete();
    }

    @After
    public void after() throws Exception {
    	sourceEvents = new ArrayList<SourceEventDto>();
    	events = new ArrayList<EventDto>();
    	sourceMarkets = new ArrayList<SourceMarketDto>();
    	accounts = new ArrayList<AccountDto>();
    	sourceRunners = new ArrayList<SourceRunnerDto>();
    	connectors = new ArrayList<ConnectorDto>();
    	markets = new ArrayList<MarketDto>();
    	runners = new ArrayList<RunnerDto>();
    }
    
    @Transactional
	@Test
	public void testNewSourceEventReceivedEvent(){				
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);		
//		List<MetaTag> metaTags = new ArrayList<MetaTag>();
//		metaTags.add(createMetaTag("AldoGrand", MetaTagType.COMPETITION));
//		metaTags.add(createMetaTag("kfc", MetaTagType.DATE));
		
		Event event = createEvent("8888888", "Special Event", cal.getTime());
		event.setStatus(EventStatus.OPEN);
		//event.setMetaTags(metaTags);
		
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		
		SourceEventReceivedEvent eventReceived = createSourceEventReceivedEvent(connectorDto.getId(), event);
		Message<KFCEvent> msg = createMessage(eventReceived); 
		dataWriter.write(msg.getHeaders(), (SourceEventReceivedEvent)msg.getPayload());		
		
		SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(connectorDto.getId(), event.getId());
		assertEquals("Special Event", sourceEventDto.getSourceName());
		assertEquals(EventStatus.OPEN, sourceEventDto.getStatus());		
	}
		
    @Transactional
	@Test
	public void testUpdateSourceEventReceivedEvent(){				
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);
//		List<MetaTag> metaTags = new ArrayList<MetaTag>();
//		metaTags.add(createMetaTag("AldoGrand", MetaTagType.COMPETITION));
//		metaTags.add(createMetaTag("kfc", MetaTagType.DATE));
		
		Event event = createEvent("8888889", "AldoGrand Event", cal.getTime());
		event.setStatus(EventStatus.OPEN);
		//event.setMetaTags(metaTags);
		
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		
		SourceEventReceivedEvent eventReceived = createSourceEventReceivedEvent(connectorDto.getId(), event);
		Message<KFCEvent> msg = createMessage(eventReceived); 
		dataWriter.write(msg.getHeaders(), (SourceEventReceivedEvent)msg.getPayload());		
		
		SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(connectorDto.getId(), event.getId());
		assertEquals(event.getName(), sourceEventDto.getSourceName());
		assertEquals(event.getStatus(), sourceEventDto.getStatus());
		assertEquals(event.getStartTime(), sourceEventDto.getStartTime());
	}
	
	@Test
	public void testAccountReceived() {
    	ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
    	AccountDto accountDto = accountDao.getAccount(connectorDto.getId(), "kfc");

		Account account = new Account();
		account.setAvailableAmount(new BigDecimal(1000.001));
		account.setBalance(new BigDecimal(1100.001));
		account.setCurrency(Currency.EUR);
		account.setUsername("kfc");
		account.setPassword("Password");
    	
		AccountReceivedEvent accountReceived = new AccountReceivedEvent();
		accountReceived.setAccount(account);
		accountReceived.setAccountId(accountDto.getId());
		accountReceived.setIntegrationModuleId(connectorDto.getId().toString());
		
		Message<KFCEvent> msg = createMessage(accountReceived); 
		dataWriter.write(msg.getHeaders(), (AccountReceivedEvent)msg.getPayload());		
		
		accountDto = accountDao.getAccount(connectorDto.getId(), "kfc");
		assertEquals(accounts.get(0).getBalance(), accountDto.getBalance());
		assertEquals(accounts.get(0).getAvailableAmount(), accountDto.getAvailableAmount());
				
		account.setAvailableAmount(new BigDecimal(2000));
		account.setBalance(new BigDecimal(2100));
		
		msg = createMessage(accountReceived); 
		dataWriter.write(msg.getHeaders(), (AccountReceivedEvent)msg.getPayload());		
		
		accountDto = accountDao.getAccount(connectorDto.getId(), "kfc");
		assertEquals(account.getBalance(), accountDto.getBalance());
		assertEquals(account.getAvailableAmount(), accountDto.getAvailableAmount());		

		account.setBalance(null);
		msg = createMessage(accountReceived); 
		dataWriter.write(msg.getHeaders(), (AccountReceivedEvent)msg.getPayload());		
		
		accountDto = accountDao.getAccount(connectorDto.getId(), "kfc");
		assertEquals(accounts.get(0).getBalance(), accountDto.getBalance());
		
		account.setAvailableAmount(null);
		msg = createMessage(accountReceived); 
		dataWriter.write(msg.getHeaders(), (AccountReceivedEvent)msg.getPayload());		
		
		accountDto = accountDao.getAccount(connectorDto.getId(), "kfc");
		assertEquals(accounts.get(0).getAvailableAmount(), accountDto.getAvailableAmount());
		
		accountDto.setBalance(null);
		accountDto.setAvailableAmount(null);
		accountDao.updateAccount(accountDto);
		
		account.setAvailableAmount(new BigDecimal(2000));
		account.setBalance(new BigDecimal(2100));
		
		msg = createMessage(accountReceived); 
		dataWriter.write(msg.getHeaders(), (AccountReceivedEvent)msg.getPayload());		
		
		accountDto = accountDao.getAccount(connectorDto.getId(), "kfc");
		assertEquals(account.getBalance(), accountDto.getBalance());
		assertEquals(account.getAvailableAmount(), accountDto.getAvailableAmount());
		
	}
	
	@Test
	public void testBettingPlatformReceived(){
		BettingPlatformReceivedEvent bpReceived = new BettingPlatformReceivedEvent();
		bpReceived.setBettingPlatform(BETTING_PLATFORM_MATCHBOOK_NAME);
		
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		bpReceived.setIntegrationModuleId(connectorDto.getId().toString());
		
		Message<KFCEvent> msg = createMessage(bpReceived); 
		dataWriter.write(msg.getHeaders(), (BettingPlatformReceivedEvent)msg.getPayload());		
			
		BettingPlatformDto bpDto = bettingPlatformDao.getBettingPlatform(connectorDto, BETTING_PLATFORM_MATCHBOOK_NAME);
		assertEquals(BETTING_PLATFORM_MATCHBOOK_NAME, bpDto.getName());
		assertEquals(connectorDto, bpDto.getConnector());
		
		msg = createMessage(bpReceived); 
		dataWriter.write(msg.getHeaders(), (BettingPlatformReceivedEvent)msg.getPayload());
	}
	
	@Test
	public void testConnectorReceived(){
		
		Connector connector = new Connector();		
		connector.setName("Smart Odds");
		connector.setEnabled(false);
		connector.setEventContributor(false);
		connector.setOfferManagementEnabled(true);		
				
		ConnectorReceivedEvent connectorReceived = new ConnectorReceivedEvent();
		connectorReceived.setIntegrationModuleId(SMARTODDS_CONNECTOR_ID);
		connectorReceived.setConnectorModel(connector);
		
		Message<KFCEvent> msg = createMessage(connectorReceived); 
		dataWriter.write(msg.getHeaders(), (ConnectorReceivedEvent)msg.getPayload());
		ConnectorDto connectorDto = connectorDao.getConnector("Smart Odds");
		assertEquals("Smart Odds", connectorDto.getName());
		assertEquals(true, connectorDto.isOfferManagementEnabled());
		assertEquals(false, connectorDto.isEnabled());
		
		
		connector.setName(MATCHBOOK_CONNECTOR_NAME);
		connector.setEnabled(true);
		connector.setEventContributor(true);
		connector.setOfferManagementEnabled(false);
		
		connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		
		connectorReceived = new ConnectorReceivedEvent();
		connectorReceived.setIntegrationModuleId(connectorDto.getId().toString());
		connectorReceived.setConnectorModel(connector);
		
		msg = createMessage(connectorReceived); 
		dataWriter.write(msg.getHeaders(), (ConnectorReceivedEvent)msg.getPayload());
		connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		assertEquals(MATCHBOOK_CONNECTOR_NAME, connectorDto.getName());
		assertEquals(false, connectorDto.isOfferManagementEnabled());
		assertEquals(true, connectorDto.isEnabled());
	}
	
	@Test
	@Ignore
	public void testEventIncidentReceived(){
//		Date now = new Date();
//		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
//		SourceEventDto sourceEventDto = sourceEvents.get(0);
//		List<CurrentScore> currentScores = new ArrayList<CurrentScore>();
//		currentScores.add(new CurrentScore(1L, ScoreType.MATCH, 0));
//		currentScores.add(new CurrentScore(2L, ScoreType.MATCH, 0));
//		EventIncident eventIncident = new EventIncident(Long.valueOf(sourceEventDto.getSourceId()), 1, CurrentDangerballStatus.HOME_PENALTY, currentScores, 1, now, IncidentType.HOME_GOAL, 1L);
//		
//		EventIncidentReceivedEvent eventIncReceived = new EventIncidentReceivedEvent();
//		eventIncReceived.setIntegrationModuleId(connectorDto.getId().toString());
//		eventIncReceived.setEventIncident(eventIncident);
//		
//		Message<KFCEvent> msg = createMessage(eventIncReceived); 
//		dataWriter.write(msg.getHeaders(), (EventIncidentReceivedEvent)msg.getPayload());
//		
//		EventIncidentDto eventIncidentDto = eventIncidentDao.getIncident(
//				eventIncident.getSequencePhase(), 
//				eventIncident.getSequenceNumber(), eventIncident.getType(), 
//				eventIncident.getParticipantId(), eventIncident.getIncidentDateTime());
//		assertEquals(CurrentDangerballStatus.HOME_PENALTY, eventIncidentDto.getCurrentDangerballStatus());
//		assertEquals(IncidentType.HOME_GOAL, eventIncidentDto.getIncidentType());
//		assertEquals(1, eventIncidentDto.getSequenceNumber());
//		
//		// Event incident of a source event unknown
//		currentScores = new ArrayList<CurrentScore>();
//		currentScores.add(new CurrentScore(1L, ScoreType.MATCH, 1));
//		currentScores.add(new CurrentScore(2L, ScoreType.MATCH, 0));		
//		eventIncident = new EventIncident(1000L, 1, CurrentDangerballStatus.HOME_CORNER_DANGER, currentScores, 1, now, IncidentType.HOME_CORNER, 1L);
//		eventIncReceived = new EventIncidentReceivedEvent();
//		eventIncReceived.setIntegrationModuleId(connectorDto.getId().toString());
//		eventIncReceived.setEventIncident(eventIncident);
//		
//		msg = createMessage(eventIncReceived); 
//		dataWriter.write(msg.getHeaders(), (EventIncidentReceivedEvent)msg.getPayload());
//		
//		eventIncidentDto = eventIncidentDao.getIncident(eventIncident.getSequencePhase(), eventIncident.getSequenceNumber(), eventIncident.getType(), 
//				eventIncident.getParticipantId(), eventIncident.getIncidentDateTime());
//		
//		assertEquals(null, eventIncidentDto);
//		
//		eventIncident = new EventIncident(Long.valueOf(sourceEventDto.getSourceId()), 1, 
//				CurrentDangerballStatus.HOME_PENALTY, currentScores, 1, now, null, 1L);
//		
//		eventIncReceived = new EventIncidentReceivedEvent();
//		eventIncReceived.setIntegrationModuleId(connectorDto.getId().toString());
//		eventIncReceived.setEventIncident(eventIncident);
//		
//		msg = createMessage(eventIncReceived); 
//		dataWriter.write(msg.getHeaders(), (EventIncidentReceivedEvent)msg.getPayload());
	}

	@Test
	public void testEventsClosedStatusReceived(){
		List<Event> events = new ArrayList<Event>();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);
//		List<MetaTag> metaTags = new ArrayList<MetaTag>();
//		metaTags.add(createMetaTag("AldoGrand", MetaTagType.COMPETITION));
//		metaTags.add(createMetaTag("kfc", MetaTagType.DATE));
		
		Event event = createEvent("9888889", "AldoGrand Event", cal.getTime());
		event.setStatus(EventStatus.OPEN);
		//event.setMetaTags(metaTags);
		events.add(event);
		
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		EventsClosedStatusReceivedEvent receivedEvent = new EventsClosedStatusReceivedEvent();
		receivedEvent.setIntegrationModuleId(connectorDto.getId().toString());
		receivedEvent.setEvents(events);
		
		Message<KFCEvent> msg = createMessage(receivedEvent); 
		dataWriter.write(msg.getHeaders(), (EventsClosedStatusReceivedEvent)msg.getPayload());
		SourceEventDto sourceEventDto = sourceEventDao.getSourceEvent(connectorDto.getId(), sourceEvents.get(0).getSourceId());
		assertEquals(EventStatus.CLOSED, sourceEventDto.getStatus());		
	}

	@Ignore
	@Transactional
	@Test
	public void testNewSourceMarketsReceivedEvent(){
		SourceMarketDto sourceMarketDtoTmp = sourceMarketDao.getSourceMarket(Long.valueOf(MATCHBOOK_CONNECTOR_ID), "7777770");
		assertEquals(null, sourceMarketDtoTmp);
		
		List<Market> markets = new ArrayList<Market>();
		markets.add(createMarket("7777770", "AldoGrand Re-Arch", MarketType.MULTI_RUNNER, null, Period.FULL_EVENT, MarketStatus.SUSPENDED, "8888889"));
		
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		
		SourceMarketsReceivedEvent marketReceived = createMarketReceived(connectorDto.getId(), markets);
		Message<KFCEvent> msg = createMessage(marketReceived); 
		dataWriter.write(msg.getHeaders(), (SourceMarketsReceivedEvent)msg.getPayload());		
				
		Market market = markets.get(0);
		SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorDto.getId(), "7777770");
		assertEquals(market.getName(), sourceMarketDto.getSourceName());
		assertEquals(MarketStatus.SUSPENDED, sourceMarketDto.getMarketStatus());
		assertEquals(market.getType(), sourceMarketDto.getType());
		assertEquals(market.getPeriod(), sourceMarketDto.getPeriod());
	}

	@Ignore
	@Transactional
	@Test
	public void testUpdateSourceMarketsReceivedEvent(){
		List<Market> markets = new ArrayList<Market>();
		markets.add(createMarket("7777779", "AldoGrand kfc", MarketType.MULTI_RUNNER, null, Period.FULL_EVENT, MarketStatus.OPEN, "8888889"));
		
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		
		SourceMarketsReceivedEvent marketReceived = createMarketReceived(connectorDto.getId(), markets);
		Message<KFCEvent> msg = createMessage(marketReceived); 
		dataWriter.write(msg.getHeaders(), (SourceMarketsReceivedEvent)msg.getPayload());		
		
		Market market = markets.get(0);
		SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorDto.getId(), "7777779");
		assertEquals(market.getName(), sourceMarketDto.getSourceName());
		assertEquals(market.getMarketStatus(), sourceMarketDto.getMarketStatus());
		assertEquals(market.getType(), sourceMarketDto.getType());
		assertEquals(market.getPeriod(), sourceMarketDto.getPeriod());
		
		marketReceived = createMarketReceived(connectorDto.getId(), null);
		msg = createMessage(marketReceived); 
		dataWriter.write(msg.getHeaders(), (SourceMarketsReceivedEvent)msg.getPayload());
		logger.info("TEST: Update an Markets with the same values.");
		dataWriter.write(msg.getHeaders(), (SourceMarketsReceivedEvent)msg.getPayload());
		logger.info("TEST: for Markets Done");
		
		marketReceived = createMarketReceived(1000L, null);
		msg = createMessage(marketReceived); 
		dataWriter.write(msg.getHeaders(), (SourceMarketsReceivedEvent)msg.getPayload());	
	}
	
	@Transactional
	@Test
	public void testCloseSourceMarketsReceivedEvent(){
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		List<Market> markets = new ArrayList<Market>();
	
		markets.add(createMarket("7777779", "AldoGrand kfc", MarketType.MULTI_RUNNER, null, Period.FULL_EVENT, MarketStatus.OPEN, "8888889"));

		MarketsClosedStatusReceivedEvent marketClosedReceived = createMarketClosedReceived(connectorDto.getId(), "8888889", markets);
		Message<KFCEvent> msg = createMessage(marketClosedReceived); 

		dataWriter.write(msg.getHeaders(), (MarketsClosedStatusReceivedEvent)msg.getPayload());		
		
		SourceMarketDto sourceMarketDto = sourceMarketDao.getSourceMarket(connectorDto.getId(), "7777779");		
		assertEquals(MarketStatus.SUSPENDED, sourceMarketDto.getMarketStatus());
		
		markets = new ArrayList<Market>();
		markets.add(createMarket("7777778", "AldoGrand 3ET", MarketType.MULTI_RUNNER, null, Period.FULL_EVENT, MarketStatus.CLOSED, "8888889"));

		marketClosedReceived = createMarketClosedReceived(connectorDto.getId(), "8888889", markets);
		msg = createMessage(marketClosedReceived); 
		dataWriter.write(msg.getHeaders(), (MarketsClosedStatusReceivedEvent)msg.getPayload());		
		
		sourceMarketDto = sourceMarketDao.getSourceMarket(connectorDto.getId(), "7777779");		
		assertEquals(MarketStatus.CLOSED, sourceMarketDto.getMarketStatus());
		

		marketClosedReceived = createMarketClosedReceived(1000L, "8888889", markets);
		msg = createMessage(marketClosedReceived); 

		dataWriter.write(msg.getHeaders(), (MarketsClosedStatusReceivedEvent)msg.getPayload());
	}
		
	@Test
	public void testOfferReceived(){
		Date now = new Date();
		SourceRunnerDto sourceRunner = sourceRunners.get(0);
		SourceEventDto sourceEvent = sourceEvents.get(0);
		SourceMarketDto sourceMarket = sourceMarkets.get(0);
				
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		
		BettingPlatformReceivedEvent bpReceived = new BettingPlatformReceivedEvent();
		bpReceived.setBettingPlatform(BETTING_PLATFORM_MATCHBOOK_NAME);
		bpReceived.setIntegrationModuleId(connectorDto.getId().toString());
		
		Message<KFCEvent> msg = createMessage(bpReceived); 
		dataWriter.write(msg.getHeaders(), (BettingPlatformReceivedEvent)msg.getPayload());
				
		BettingPlatformDto bpDto = bettingPlatformDao.getBettingPlatform(connectorDto, BETTING_PLATFORM_MATCHBOOK_NAME);
		OffersReceivedEvent offerReceived = new OffersReceivedEvent();
		
		offerReceived.setAccount(null);
		offerReceived.setIntegrationModuleId(null);
		offerReceived.setOffers(null);		
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());
		
		
		Account account = new Account();
		account.setAvailableAmount(new BigDecimal(1000));
		account.setBalance(new BigDecimal(1100));
		account.setCurrency(Currency.EUR);
		account.setPassword("Password");
		account.setUsername("kfc - unknown");
		
		List<Offer> offers = new ArrayList<Offer>();
		Offer offer = new Offer();
		offer.setAvailableAmount(new BigDecimal(100));
		offer.setCurrency(Currency.EUR);
		offer.setDecimalOdds(new BigDecimal(1.55));
		offer.setEventId(sourceEvent.getSourceId());
		offer.setId("123");
		offer.setMarketId(sourceMarket.getSourceId());
		offer.setMatchedAmount(new BigDecimal(100));
		offer.setOdds(new BigDecimal(-150));
		offer.setOddsType(OddsType.US);
		offer.setOfferTime(now);
		offer.setOfferType(OfferType.PUBLIC);
		offer.setSide(PriceSide.LOSE);
		offer.setStake(new BigDecimal(100));
		offer.setStatus(OfferStatus.UNKNOWN);
		offer.setRunnerId("777");
		offers.add(offer);
		
		offerReceived.setAccount(account);
		offerReceived.setIntegrationModuleId(null);
		offerReceived.setOffers(offers);		
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());

		offerReceived.setAccount(account);
		offerReceived.setIntegrationModuleId(connectorDto.getId().toString());
		offerReceived.setOffers(offers);		
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());

		offer.setRunnerId("555");
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());
		
		offer.setRunnerId(sourceRunner.getSourceId());		
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());

		// Update an Offer with the same values, it won't be sent to Kafka. (Check the debug log).
		logger.info("TEST: Update an Offer with the same values.");
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());
		logger.info("TEST: Done");

		// Mapping of the sourceRunner		
		sourceRunner.setRunner(runners.get(0));
		entityManager.persist(sourceRunner);
		
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());
		
		// Create a new offer
		account.setUsername("kfc");
		msg = createMessage(offerReceived);
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());
		OfferDto offerDto = offerDao.getOffer(connectorDto.getId(), offer.getId());
		assertEquals(OfferStatus.UNKNOWN, offerDto.getStatus());

		// Update an offer
		offer.setAvailableAmount(new BigDecimal(100));
		offer.setStatus(OfferStatus.UNMATCHED);
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());
		offerDto = offerDao.getOffer(connectorDto.getId(), offer.getId());
		assertEquals(OfferStatus.UNMATCHED, offerDto.getStatus());
		
		// Update an offer
		offer.setAvailableAmount(new BigDecimal(-1));
		offer.setStatus(OfferStatus.UNMATCHED);
		msg = createMessage(offerReceived); 
		dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());
		offerDto = offerDao.getOffer(connectorDto.getId(), offer.getId());
		assertEquals(OfferStatus.MATCHED, offerDto.getStatus());
		
		if (offerDto.getBets() != null && !offerDto.getBets().isEmpty()) {
			assertEquals(true, false);
		} else {
			assertEquals(true, true);
		}
	}

	@Test
	public void testPrices() {
		Date now = new Date();
		SourceRunnerDto sourceRunner = sourceRunners.get(0);
		SourceEventDto sourceEvent = sourceEvents.get(0);
		SourceMarketDto sourceMarket = sourceMarkets.get(0);
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		
		BettingPlatformReceivedEvent bpReceived = new BettingPlatformReceivedEvent();
		bpReceived.setBettingPlatform(BETTING_PLATFORM_MATCHBOOK_NAME);
		bpReceived.setIntegrationModuleId(connectorDto.getId().toString());
		
		Message<KFCEvent> msg = createMessage(bpReceived); 
		dataWriter.write(msg.getHeaders(), (BettingPlatformReceivedEvent)msg.getPayload());		
		BettingPlatformDto bpDto = bettingPlatformDao.getBettingPlatform(connectorDto, BETTING_PLATFORM_MATCHBOOK_NAME);
		
		// Mapping of the sourceRunner		
		sourceRunner.setRunner(runners.get(0));
		sourceRunnerDao.saveSourceRunner(sourceRunner);
		
		sourceMarket.setMarket(markets.get(0));
		sourceMarketDao.saveSourceMarket(sourceMarket);
		
		List<Price> prices = new ArrayList<Price>();
		Price price = new Price();
		price.setAvailableAmount(new BigDecimal(100));
		price.setBettingPlatformName(bpDto.getName());
		price.setCurrency(Currency.EUR);
		price.setDecimalOdds(new BigDecimal(100));
		price.setEventId(sourceEvent.getSourceId());
		price.setId("1");
		price.setMarketId(sourceMarket.getSourceId());
		price.setOdds(new BigDecimal(100));
		price.setOddsType(OddsType.DECIMAL);
		price.setRunnerId(sourceRunner.getSourceId());
		price.setSide(PriceSide.BACK);
		
		Price price2 = new Price();
		price2.setAvailableAmount(new BigDecimal(90));
		price2.setBettingPlatformName(bpDto.getName());
		price2.setCurrency(Currency.EUR);
		price2.setDecimalOdds(new BigDecimal(90));
		price2.setEventId(sourceEvent.getSourceId());
		price2.setId("2");
		price2.setMarketId(sourceMarket.getSourceId());
		price2.setOdds(new BigDecimal(90));
		price2.setOddsType(OddsType.DECIMAL);
		price2.setRunnerId(sourceRunner.getSourceId());
		price2.setSide(PriceSide.LAY);
		prices.add(price);
		prices.add(price2);
		
		PricesReceivedEvent pricesReceived = new PricesReceivedEvent();
		pricesReceived.setIntegrationModuleId(connectorDto.getId().toString());
		pricesReceived.setPrices(prices);
		
		msg = createMessage(pricesReceived); 
		dataWriter.write(msg.getHeaders(), (PricesReceivedEvent)msg.getPayload());
		List<PriceDto> pricesDto = priceDao.getRunnerPrices(connectorDto.getId(), sourceRunner.getRunner().getId(), 0, 100);
		if (pricesDto != null && !pricesDto.isEmpty()) {
			assertEquals(prices.size(), pricesDto.size());
		} else {
			assertEquals(true, false);
		}
		
		// Create new prices and delete older prices
        prices = new ArrayList<Price>();
        prices.add(price);
        price.setOdds(new BigDecimal(200));
        //price2.setOdds(new BigDecimal(120));
        
        pricesReceived.setPrices(prices);
        msg = createMessage(pricesReceived); 
        dataWriter.write(msg.getHeaders(), (PricesReceivedEvent)msg.getPayload());
        pricesDto = priceDao.getRunnerPrices(connectorDto.getId(), sourceRunner.getRunner().getId(), 0, 100);
        if (pricesDto != null && !pricesDto.isEmpty()) {
            assertEquals(prices.size(), pricesDto.size());
        } else {
            assertEquals(true, false);
        }
	}
	
	@Test
	public void testPositionReceived(){
		BettingPlatformReceivedEvent bpReceived = new BettingPlatformReceivedEvent();
		bpReceived.setBettingPlatform(BETTING_PLATFORM_MATCHBOOK_NAME);
		
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);
		bpReceived.setIntegrationModuleId(connectorDto.getId().toString());
		
		Message<KFCEvent> msg = createMessage(bpReceived); 
		dataWriter.write(msg.getHeaders(), (BettingPlatformReceivedEvent)msg.getPayload());		
			
		BettingPlatformDto bpDto = bettingPlatformDao.getBettingPlatform(connectorDto, BETTING_PLATFORM_MATCHBOOK_NAME);
		AccountDto accountDto = accountDao.getAccount(connectorDto.getId(), "kfc");
		
		Account account = new Account();
		account.setAvailableAmount(new BigDecimal(1000));
		account.setBalance(new BigDecimal(1100));
		account.setCurrency(Currency.EUR);
		account.setUsername("kfc");
		account.setPassword("Password");
		
		List<Position> positions = new ArrayList<Position>();
		Position position = new Position();
		position.setBettingPlatformName(MATCHBOOK_CONNECTOR_NAME);
		position.setCurrency(Currency.EUR);
		position.setEventId(sourceEvents.get(0).getSourceId());
		position.setMarketId(sourceMarkets.get(0).getSourceId());
		position.setRunnerId(sourceRunners.get(0).getSourceId());
		position.setValue(new BigDecimal(500));
		positions.add(position);
		
		
		PositionsReceivedEvent positionReceived = new PositionsReceivedEvent();
		positionReceived.setIntegrationModuleId(connectorDto.getId().toString());
		positionReceived.setAccount(account);
		positionReceived.setPositions(positions);
		msg = createMessage(positionReceived); 
		dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());		
		
		PositionDto positionDto = positionDao.getPosition(runners.get(0).getId(), bpDto.getId(), accountDto.getId());		
		assertEquals(null, positionDto);
		
		// Mapping of the sourceRunner
		SourceRunnerDto sourceRunner = sourceRunners.get(0);
		sourceRunner.setRunner(runners.get(0));
		entityManager.persist(sourceRunner);
		
		positionReceived = new PositionsReceivedEvent();
		positionReceived.setIntegrationModuleId(connectorDto.getId().toString());
		positionReceived.setAccount(account);
		positionReceived.setPositions(positions);
		msg = createMessage(positionReceived); 
		dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());		
		
		logger.info("TEST: Update an Positions with the same values.");
		dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());
		logger.info("TEST: for Positions Done");
		
		positionDto = positionDao.getPosition(runners.get(0).getId(), bpDto.getId(), accountDto.getId());		
		assertEquals(Currency.EUR, positionDto.getCurrency());
		assertEquals(new BigDecimal(500), positionDto.getValue());
		
		position.setCurrency(Currency.USD);
		positionReceived = new PositionsReceivedEvent();
		positionReceived.setIntegrationModuleId(connectorDto.getId().toString());
		positionReceived.setAccount(account);
		positionReceived.setPositions(positions);
		msg = createMessage(positionReceived); 
		dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());		
		
		positionDto = positionDao.getPosition(runners.get(0).getId(), bpDto.getId(), accountDto.getId());		
		assertEquals(Currency.USD, positionDto.getCurrency());
		
		position.setRunnerId("20");
		positionReceived = new PositionsReceivedEvent();
		positionReceived.setIntegrationModuleId(connectorDto.getId().toString());
		positionReceived.setAccount(account);
		positionReceived.setPositions(positions);
		msg = createMessage(positionReceived); 
		dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());
		
		position.setRunnerId(sourceRunners.get(0).getSourceId());
		account.setUsername("Doe");
		positionReceived = new PositionsReceivedEvent();
		positionReceived.setIntegrationModuleId(connectorDto.getId().toString());
		positionReceived.setAccount(account);
		positionReceived.setPositions(positions);
		msg = createMessage(positionReceived); 
		dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());
		
		account.setUsername("kfc");
		position.setBettingPlatformName("Unknown");
		positionReceived = new PositionsReceivedEvent();
		positionReceived.setIntegrationModuleId(connectorDto.getId().toString());
		positionReceived.setAccount(account);
		positionReceived.setPositions(positions);
		msg = createMessage(positionReceived); 
		dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());
	}

	@Test
	public void testSourceRunnerReceivedEvent(){
		ConnectorDto connectorDto = connectorDao.getConnector(MATCHBOOK_CONNECTOR_NAME);

		// Mapping of the sourceMarket
		SourceMarketDto sourceMarket = sourceMarkets.get(0);
		sourceMarket.setMarket(markets.get(0));
		sourceMarket = sourceMarketDao.saveSourceMarket(sourceMarket);
		entityManager.persist(sourceMarket);
		
		List<Runner> runners = new ArrayList<Runner>();
		Runner runner = new Runner();
		runner.setId(sourceRunners.get(0).getSourceId());
		runner.setEventId(sourceEvents.get(0).getSourceId());
		runner.setMarketId(sourceMarket.getSourceId());
		runner.setName("Kafka");
		runner.setRunnerStatus(RunnerStatus.CLOSED);
		runner.setSequence(1);
		runner.setSide(RunnerSide.NA);		
		runner.setType(RunnerType.UNKNOWN);
		runners.add(runner);
		
		SourceRunnersReceivedEvent srReceived = new SourceRunnersReceivedEvent();
		srReceived.setIntegrationModuleId(connectorDto.getId().toString());
		srReceived.setRunners(runners);
	
		Message<KFCEvent> msg = createMessage(srReceived); 
		dataWriter.write(msg.getHeaders(), (SourceRunnersReceivedEvent)msg.getPayload());
		
		SourceRunnerDto srDto = sourceRunnerDao.getSourceRunner(connectorDto.getId(), runner.getId());
		assertEquals(RunnerStatus.CLOSED, srDto.getRunnerStatus());
		assertEquals(RunnerType.UNKNOWN, srDto.getType());
		assertEquals(RunnerSide.NA, srDto.getSide());
		
		sourceRunnerDao.deleteSourceRunner(srDto);		
		runner = new Runner();
		runner.setId(sourceRunners.get(0).getSourceId());
		runner.setEventId(sourceEvents.get(0).getSourceId());
		runner.setMarketId(sourceMarket.getSourceId());
		runner.setName("Kafka 2");
		runner.setRunnerStatus(RunnerStatus.OPEN);
		runner.setSequence(1);
		runner.setSide(RunnerSide.HOME);		
		runner.setType(RunnerType.DRAW);
		runners.add(runner);
		
		srReceived = new SourceRunnersReceivedEvent();
		srReceived.setIntegrationModuleId(connectorDto.getId().toString());
		srReceived.setRunners(runners);	
		msg = createMessage(srReceived); 
		dataWriter.write(msg.getHeaders(), (SourceRunnersReceivedEvent)msg.getPayload());
		
		srDto = sourceRunnerDao.getSourceRunner(connectorDto.getId(), runner.getId());
		assertEquals(RunnerStatus.OPEN, srDto.getRunnerStatus());
		assertEquals(RunnerType.DRAW, srDto.getType());
		assertEquals(RunnerSide.HOME, srDto.getSide());
	}

    @Test
    @Ignore
    public void errorHandlerSourceEventReceived () throws IOException, InterruptedException {
        SourceEventReceivedEvent eventReceived = new SourceEventReceivedEvent ();
        eventReceived.setIntegrationModuleId(null);
        eventReceived.setEvent(null);
        Message<KFCEvent> msg = createMessage(eventReceived);
        dataWriter.write(msg.getHeaders(), (SourceEventReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("SourceEventReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("SourceEventReceivedEvent"), on(localProfile))).totalLines(), is(7));
    }

    @Test
    @Ignore
    public void errorHandlerSourceMarketReceived () throws IOException, InterruptedException {
        SourceMarketsReceivedEvent marketReceived = new SourceMarketsReceivedEvent ();
        marketReceived.setIntegrationModuleId(null);
        marketReceived.setMarkets(null);
        Message<KFCEvent> msg = createMessage(marketReceived);
        dataWriter.write(msg.getHeaders(), (SourceMarketsReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("SourceMarketsReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("SourceMarketsReceivedEvent"), on(localProfile))).totalLines(), is(7));
    }

    @Test
    @Ignore
    public void errorHandlerSourceRunnerReceived () throws IOException, InterruptedException {

        SourceRunnersReceivedEvent runnerReceived = new SourceRunnersReceivedEvent();
        runnerReceived.setIntegrationModuleId(null);
        runnerReceived.setRunners(null);
        Message<KFCEvent> msg = createMessage(runnerReceived);
        dataWriter.write(msg.getHeaders(), (SourceRunnersReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("SourceRunnerReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("SourceRunnerReceivedEvent"), on(localProfile))).totalLines(), is(8));
    }

    @Test
    @Ignore
    public void errorHandlerPricesReceivedEvent () throws IOException, InterruptedException {
        PricesReceivedEvent pricesReceived = new PricesReceivedEvent();
        pricesReceived.setIntegrationModuleId(null);
        Price price = null;
        List <Price> listPrice = new ArrayList<Price>();
        listPrice.add(price);
        pricesReceived.setPrices(listPrice);
        Message<KFCEvent> msg = createMessage(pricesReceived);
        dataWriter.write(msg.getHeaders(), (PricesReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("PricesReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("PricesReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerAccountReceivedEvent () throws IOException, InterruptedException {
        AccountReceivedEvent accountReceived = new AccountReceivedEvent();
        accountReceived.setIntegrationModuleId(null);
        accountReceived.setAccount(null);
        accountReceived.setAccountId(null);
        Message<KFCEvent> msg = createMessage(accountReceived);
        dataWriter.write(msg.getHeaders(), (AccountReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("AccountReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("AccountReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerConnectorReceivedEvent () throws IOException, InterruptedException {
        ConnectorReceivedEvent connectorReceived = new ConnectorReceivedEvent();
        connectorReceived.setIntegrationModuleId(null);
        Message<KFCEvent> msg = createMessage(connectorReceived);
        dataWriter.write(msg.getHeaders(), (ConnectorReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("ConnectorReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("ConnectorReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerEventIncidentReceivedEvent () throws IOException, InterruptedException {
        EventIncidentReceivedEvent eventIncidentReceived = new EventIncidentReceivedEvent();
        eventIncidentReceived.setIntegrationModuleId(null);
        eventIncidentReceived.setEventIncident(null);
        Message<KFCEvent> msg = createMessage(eventIncidentReceived);
        dataWriter.write(msg.getHeaders(), (EventIncidentReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("EventIncidentReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("EventIncidentReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerOfferReceivedEvent () throws IOException, InterruptedException {
        OffersReceivedEvent offerReceived = new OffersReceivedEvent();
        offerReceived.setIntegrationModuleId("-123");
        offerReceived.setAccount(new Account());
        List<Offer> offers = new ArrayList<Offer>();
        offers.add(new Offer());
        offerReceived.setOffers(offers);
        Message<KFCEvent> msg = createMessage(offerReceived);
        dataWriter.write(msg.getHeaders(), (OffersReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("OfferReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("OfferReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerPositionReceivedEvent () throws IOException, InterruptedException {
        PositionsReceivedEvent positionReceived = new PositionsReceivedEvent();
        positionReceived.setIntegrationModuleId("-123");
        positionReceived.setAccount(new Account());
        List<Position> positions = new ArrayList<Position>();
        positions.add(new Position());
        positionReceived.setPositions(positions);
        Message<KFCEvent> msg = createMessage(positionReceived);
        dataWriter.write(msg.getHeaders(), (PositionsReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("PositionReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("PositionReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerSettledBetsReceivedEvent () throws IOException, InterruptedException {
        SettledBetsReceivedEvent settleBetReceived = new SettledBetsReceivedEvent();
        settleBetReceived.setIntegrationModuleId("-123");
        settleBetReceived.setAccountId(-123L);
        settleBetReceived.setSettledBets(null);
        Message<KFCEvent> msg = createMessage(settleBetReceived);
        dataWriter.write(msg.getHeaders(), (SettledBetsReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("SettledBetsReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("SettledBetsReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerBettingPlatformReceivedEvent () throws IOException, InterruptedException {
        BettingPlatformReceivedEvent bettingPlatformReceived = new BettingPlatformReceivedEvent();
        bettingPlatformReceived.setIntegrationModuleId(null);
        bettingPlatformReceived.setBettingPlatform("Test Bettin Platform !!!!!");
        Message<KFCEvent> msg = createMessage(bettingPlatformReceived);
        dataWriter.write(msg.getHeaders(), (BettingPlatformReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("BettingPlatformReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("BettingPlatformReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Test
    @Ignore
    public void errorHandlerEventsClosedStatusReceivedEvent () throws IOException, InterruptedException {
        EventsClosedStatusReceivedEvent eventCloseStatusReceived = new EventsClosedStatusReceivedEvent();
        eventCloseStatusReceived.setIntegrationModuleId(null);
        Message<KFCEvent> msg = createMessage(eventCloseStatusReceived);
        dataWriter.write(msg.getHeaders(), (EventsClosedStatusReceivedEvent)msg.getPayload());

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("EventsClosedStatusReceivedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("EventsClosedStatusReceivedEvent"), on(localProfile))).totalLines(), is(10));
    }

    @Ignore
	@Test
	public void testSettledBetsReceived(){
		// TODO:
	}
	
	private Message<KFCEvent> createMessage(KFCEvent event) {
		try {
			return KFCEventFactory.createMessage(event);
		} catch (ProducerException e) {			
			e.printStackTrace();
			return null;
		}
	}
		
	private SourceEventReceivedEvent createSourceEventReceivedEvent(Long connector, Event event) {
		SourceEventReceivedEvent eventReceived = new SourceEventReceivedEvent();		        
        eventReceived.setEvent(event);
        eventReceived.setIntegrationModuleId(connector.toString());
        return eventReceived;
	}
	
	private SourceMarketsReceivedEvent createMarketReceived(Long connector, List<Market> markets) {
		SourceMarketsReceivedEvent marketReceived = new SourceMarketsReceivedEvent();
		marketReceived.setIntegrationModuleId(connector.toString());
		marketReceived.setMarkets(markets);
		return marketReceived;
	}
			
	private MarketsClosedStatusReceivedEvent createMarketClosedReceived(Long connector, String eventId, List<Market> markets) {
		MarketsClosedStatusReceivedEvent marketReceived = new MarketsClosedStatusReceivedEvent();
		marketReceived.setIntegrationModuleId(connector.toString());
		marketReceived.setMarkets(markets);
		marketReceived.setEventId(eventId);
		return marketReceived;
	}

	
	private Event createEvent(String id, String name, Date date) {
		Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setStartTime(date);
        return event;
	}
	
	private Market createMarket(String id, String name, MarketType type, Double handicap, Period period, MarketStatus status, String eventId) {
		Market market = new Market();
		market.setId(id);
		market.setName(name);
		market.setType(type);
		market.setHandicap(handicap);
		market.setMarketStatus(status);
		market.setPeriod(period);
		market.setEventId(eventId);
		return market;
	}
	
	private MetaTag createMetaTag(String name, MetaTagType type) {
		MetaTag metaTag = new MetaTag();
		metaTag.setName(name);
		metaTag.setType(type);
		return metaTag;
	}
}
