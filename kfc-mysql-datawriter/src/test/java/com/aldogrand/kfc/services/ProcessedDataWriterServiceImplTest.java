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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aldogrand.sbpc.dataaccess.EventDao;
import com.aldogrand.sbpc.dataaccess.MarketDao;
import com.aldogrand.sbpc.dataaccess.RunnerDao;
import com.aldogrand.sbpc.dataaccess.model.EventDto;
import com.aldogrand.sbpc.dataaccess.model.MarketDto;
import com.aldogrand.sbpc.dataaccess.model.RunnerDto;
import com.aldogrand.sbpc.model.Event;
import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.Market;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.Period;
import com.aldogrand.sbpc.model.ResultStatus;
import com.aldogrand.sbpc.model.Runner;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.processor.EventProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.MarketProcessedEvent;
import com.aldogrand.kfc.msg.events.processor.RunnerProcessedEvent;
import com.aldogrand.kfc.services.mysql.ProcessDataWriterService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/kfc-processdatawriter-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ProcessedDataWriterServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    private EntityManager entityManager;

	@Autowired
	private ProcessDataWriterService processDataWriterService;

    @Mock
    private KFCProducer kfcKafkaProducer;

    @Autowired
    private EventDao eventDao;
    @Autowired
    private MarketDao marketDao;
    @Autowired
    private RunnerDao runnerDao;

    private EventProcessedEvent sourceProcessedEvent = new EventProcessedEvent();
    private MarketProcessedEvent sourceProcessedMarket = new MarketProcessedEvent();
    private RunnerProcessedEvent sourceProcessedRunner = new RunnerProcessedEvent();

//    private List<Runner> listRunners = new ArrayList<Runner>();
//    private List<Market> listMarkets = new ArrayList<Market>();

    private List<RunnerDto> listRunnersDto = new ArrayList<RunnerDto>();
    private List<MarketDto> listMarketsDto = new ArrayList<MarketDto>();

    @Before
    public void beforeEachTest () {
        insertPretestData();
    }

    @AfterClass
    public static void afterClass() {
    	File file = new File("error.log");
    	file.delete();
    }
    
    @After
    public void after() {

    }
    
    @Transactional
    private void insertPretestData() {
        EventDto event = new EventDto();
        event.setName("Event Original Name");
        event.setStartTime(new java.util.Date());
        event.setEventStatus(EventStatus.OPEN);

        MarketDto market = new MarketDto();
        market.setName("Market - 2 updated");
        market.setType(MarketType.SCORE_BOTH_HALVES);
        market.setEvent(event);
        market.setHandicap(0.67);
        market.setPeriod(Period.SECOND_PERIOD);
        market.setMarketStatus(MarketStatus.CLOSED);

        listMarketsDto.add(market);

        market = new MarketDto();
        market.setName("Market - 2 updated");
        market.setType(MarketType.SCORE_BOTH_HALVES);
        market.setEvent(event);
        market.setHandicap(0.67);
        market.setPeriod(Period.SECOND_PERIOD);
        market.setMarketStatus(MarketStatus.CLOSED);

        listMarketsDto.add(market);

        RunnerDto runner = new RunnerDto();
        runner.setMarket(market);
        runner.setName("Runner - 11 updated");
        runner.setSequence(10);
        runner.setType(RunnerType.OVER);
        runner.setSide(RunnerSide.AWAY);
        runner.setHandicap(0.5);
        runner.setRotationNumber(1);
        runner.setRunnerStatus(RunnerStatus.OPEN);
        runner.setResultStatus(ResultStatus.WINNER);
        listRunnersDto.add(runner);

        runner = new RunnerDto();
        runner.setMarket(market);
        runner.setName("Runner - 12 updated");
        runner.setSequence(13);
        runner.setType(RunnerType.UNDER);
        runner.setSide(RunnerSide.HOME);
        runner.setHandicap(0.65);
        runner.setRotationNumber(2);
        runner.setRunnerStatus(RunnerStatus.OPEN);
        runner.setResultStatus(ResultStatus.LOSER);
        listRunnersDto.add(runner);

        market.setRunners(listRunnersDto);
        event.setMarkets(listMarketsDto);
        entityManager.persist(event);
    }

    @Test
    @Transactional
    public void updateRunnersTest() throws ClassNotFoundException, SQLException, InterruptedException, IOException {
        List <EventDto> eventsDto = eventDao.getEvents(0, Integer.MAX_VALUE);

        Runner runner = new Runner();
        runner.setId(eventsDto.get(0).getMarkets().get(1).getRunners().get(0).getId());
        runner.setEventId(eventsDto.get(0).getId());
        runner.setMarketId(eventsDto.get(0).getMarkets().get(0).getId());
        runner.setName("Runner - 22 Test  only Runner");
        runner.setSequence(13);
        runner.setType(RunnerType.UNDER);
        runner.setSide(RunnerSide.HOME);
        runner.setHandicap(0.65);
        runner.setRotationNumber(2);
        runner.setRunnerStatus(RunnerStatus.OPEN);
        runner.setResultStatus(ResultStatus.LOSER);

        sourceProcessedRunner.setRunner(runner);

        processDataWriterService.update(sourceProcessedRunner);

        assertEquals("Runner - 22 Test  only Runner", runnerDao.getRunner(eventsDto.get(0).getMarkets().get(1).getRunners().get(0).getId()).getName());
        eventDao.deleteEvents(eventDao.getEvents(0, Integer.MAX_VALUE));
    }

    @Test
    @Transactional
    public void updateMarketTest() throws Exception {
        System.out.println ("Test: updateMarketTest");

        List <EventDto> eventsDto = eventDao.getEvents(0, 1);

        Market market = new Market();
        market.setId(eventsDto.get(0).getMarkets().get(1).getId());
        market.setName("Market - 2 Test only Markets");
        market.setType(MarketType.SCORE_BOTH_HALVES);
        market.setEventId(eventsDto.get(0).getId());
        market.setHandicap(0.67);
        market.setPeriod(Period.SECOND_PERIOD);
        market.setMarketStatus(MarketStatus.CLOSED);

        sourceProcessedMarket.setMarket(market);
        processDataWriterService.update(sourceProcessedMarket);

        assertEquals("Market - 2 Test only Markets", marketDao.getMarket(eventsDto.get(0).getMarkets().get(1).getId()).getName());
        eventDao.deleteEvents(eventsDto);
    }

    @Test
    @Transactional
	public void updateEventTest() throws Exception {
        System.out.println ("Test: updateEventTest");

        List <EventDto> eventsDto = eventDao.getEvents(0, Integer.MAX_VALUE);

        // Event processed to be updated
        Event event = new Event ();
        event.setId(eventsDto.get(0).getId());
        event.setName("Event Name Test only event");
        event.setStartTime(new java.util.Date());
        event.setEventStatus(EventStatus.OPEN);
        sourceProcessedEvent.setEvent(event);

        // Test the Update
        processDataWriterService.update(sourceProcessedEvent);
        assertEquals("Event Name Test only event", eventDao.getEvent(eventsDto.get(0).getId()).getName());
        eventDao.deleteEvents(eventsDto);
    }
    @Test
    @Ignore
    public void errorHandlerEventProcessed() throws IOException, InterruptedException {
        EventProcessedEvent eventUpdated = new EventProcessedEvent ();
        eventUpdated.setIntegrationModuleId(null);
        eventUpdated.setEvent(null);
        processDataWriterService.update(eventUpdated);

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("EventProcessedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("EventProcessedEvent"), on(localProfile))).totalLines(), is(7));
    }
    @Test
    @Ignore
    public void errorHandlerMarketProcessed() throws IOException, InterruptedException {
        MarketProcessedEvent marketUpdated = new MarketProcessedEvent ();
        marketUpdated.setIntegrationModuleId(null);
        marketUpdated.setMarket(null);
        processDataWriterService.update(marketUpdated);

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("MarketProcessedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("MarketProcessedEvent"), on(localProfile))).totalLines(), is(7));
    }
    @Test
    @Ignore
    public void errorHandlerRunnerProcessed() throws IOException, InterruptedException {
        RunnerProcessedEvent runnerUpdated = new RunnerProcessedEvent ();
        runnerUpdated.setIntegrationModuleId(null);
        runnerUpdated.setRunner(null);
        processDataWriterService.update(runnerUpdated);

        // Checks the exception has been logged
        File file = new File("error.log");
        Assert.assertTrue(file != null && file.exists());

        // Checks the logged exception is the one expected
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("RunnerProcessedEvent")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("RunnerProcessedEvent"), on(localProfile))).totalLines(), is(7));
    }
}
