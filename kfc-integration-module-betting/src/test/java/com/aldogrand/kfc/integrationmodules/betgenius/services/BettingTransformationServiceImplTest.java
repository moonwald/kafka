package com.aldogrand.kfc.integrationmodules.betgenius.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.sbpc.connectors.model.Runner;
import com.aldogrand.sbpc.model.CurrentDangerballStatus;
import com.aldogrand.sbpc.model.EventPhase;
import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.IncidentType;
import com.aldogrand.sbpc.model.MetaTag;
import com.aldogrand.sbpc.model.MetaTagType;
import com.aldogrand.sbpc.model.Period;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.sbpc.model.ScoreType;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Competition;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.CreateMarket;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Event;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Market;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.MarketStatus;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.MarketType;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Region;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Round;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Season;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Selection;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Sport;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.UpdateMarket;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Updategram;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchEvent;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchPhase;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchStateFootball;
import com.aldogrand.kfc.integrationmodules.betting.model.football.Score;
import com.aldogrand.kfc.integrationmodules.betting.services.BettingTransformationBase;
import com.aldogrand.kfc.integrationmodules.betting.services.BettingTransformationService;
import com.aldogrand.kfc.integrationmodules.model.BettingTransformedData;

/**
 *
 * <p>
 * <b>Title</b>: BetgeniusTransformationServiceImplTest.java
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 *
 * @author dlehane
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/kfc-integration-module-betgenius-test.xml" })
public class BettingTransformationServiceImplTest extends BettingTransformationBase {

	private JAXBContext jaxbContext;

	private Unmarshaller unmarshaller;

	@Autowired
	private BettingTransformationService transformer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		jaxbContext = JAXBContext.newInstance(Updategram.class);
		unmarshaller = jaxbContext.createUnmarshaller();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTransformEvent_createEvent() {
		transformEvent_createEvent("createEvent-01.xml");
		transformEvent_createEvent("createEventMarket-02.xml");
		transformEvent_createEvent("createEventMarket-01.xml");
	}

	@Test
	public void testTransformEvent_updateEvent() {
		transformEvent_updateEvent("updateEvent-01.xml");
		transformEvent_updateEvent("updateEvent-02.xml");
	}

	@Test
	public void testTransformEvent_createMarket() {
		transformEvent_createMarket("createEventMarket-01.xml");
		transformEvent_createMarket("createEventMarket-02.xml");
	}

	@Test
	public void testTransformEvent_updateMarket() {
		transformEvent_updateMarket("updateMarket-01.xml");
		transformEvent_updateMarket("updateMarket-02.xml");
	}

	@Test
	public void testTransformEvent_createResult() {
		transformEvent_createResult("createResult-01.xml");
		transformEvent_createResult("createResult-02.xml");
		transformEvent_createResult("createResult-03.xml");
	}

	@Test
	public void testTransformMatchStateFootball() {
		// TODO: Don't have a valid match state football json file yet, when we do we can use it for this test.
		// transformMatchStateFootball("matchStateFootball-01.json");
		transformMatchStateFootball("");
	}

	private void transformMatchStateFootball(String file) {
		// Create destination models
		List<EventIncident> destEventIncidents = new ArrayList<EventIncident>();
		Map<Integer, EventIncident> destEventIncidentsMap = new HashMap<Integer, EventIncident>();
		MatchStateFootball srcMatchState;

		// populate source objects
		if (file != null && !file.isEmpty()) {
			srcMatchState = loadMatchState(file);
		} else {
			srcMatchState = createMatchStateFootball();
		}
		List<MatchPhase> srcMatchPhases = srcMatchState.getAllMatchPhaseDetails();
		List<MatchEvent> srcMatchEvents = srcMatchState.getMatchEvent();

		// Create a map of the phases for matching incidents against
		Map<Integer, MatchPhase> srcMapPhases = new HashMap<>();
		for (MatchPhase matchPhase : srcMatchPhases) {
			srcMapPhases.put(matchPhase.getPhase(), matchPhase);
		}

		// transform
		destEventIncidents = transformer.transformMatchState(srcMatchState);
		
		// create map by sourceEventId of EventIncidents in order to compare to source
		createDestEventIncidentMap(destEventIncidents, destEventIncidentsMap);

		for (MatchEvent srcMatchEvent : srcMatchEvents) {
			MatchPhase srcPhase = srcMapPhases.get(srcMatchEvent.getOccurredDuring());

			if (srcPhase != null && srcMatchEvent.isConfirmed()) {
				assertTransformedEventIncident(srcMatchEvent, srcMatchState, destEventIncidentsMap.get(srcMatchEvent.getId()));
			}
		}
	}
	
	private void assertTransformedEventIncident(MatchEvent srcMatchEvent, MatchStateFootball srcMatchState,
			EventIncident eventIncident) {
		assertEquals(srcMatchState.getEventId(), eventIncident.getSourceEventId());
		assertEquals(srcMatchEvent.getId(), eventIncident.getSequenceNumber());
		CurrentDangerballStatus srcStatus = CurrentDangerballStatus.getStatus(srcMatchState.getCurrentDangerState());
		assertEquals(srcStatus, eventIncident.getCurrentDangerBallStatus());
		assertEquals(EventPhase.getStatus(srcMatchEvent.getOccurredDuring()), eventIncident.getEventPhase());
		assertEquals(srcMatchEvent.getOccurredAt(), eventIncident.getElapsedTime());
		assertEquals(IncidentType.getType(srcMatchEvent.getMatchEventType()), eventIncident.getType());
		assertEquals(srcMatchEvent.getPlayerId(), eventIncident.getParticipantId());

		if (srcMatchState.getGoals() != null && srcMatchState.getGoals().isAreReliable()) {
			assertTransformedScoreGoal(srcMatchState.getGoals(), eventIncident.getScore());
		}
	}

	/**
	 * @param destEventIncidents
	 * @param destUpdateMarketMap
	 */
	private void createDestEventIncidentMap(List<EventIncident> destEventIncidents,
			Map<Integer, EventIncident> destEventIncidentsMap) {
		for (EventIncident ei : destEventIncidents) {
			destEventIncidentsMap.put(ei.getSequenceNumber(), ei);
		}
	}

	private void assertTransformedScoreGoal(Score src, com.aldogrand.sbpc.connectors.model.Score dest) {
		assertEquals(ScoreType.GOAL, dest.getType());
		assertEquals(new Integer(src.getHome()), dest.getHome());
		assertEquals(new Integer(src.getAway()), dest.getAway());
	}

	private void transformEvent_createEvent(String fileName) {
		// Create destination models
		com.aldogrand.sbpc.connectors.model.Event destCreateEvent = null;

		// populate source objects
		Updategram src = loadUpdateGram(fileName);
		Event srcCreateEvent = src.getCreateEventCommand().getEvent();

		// transform
		BettingTransformedData dest = transformer.transformEvent(src);
		destCreateEvent = dest.getTransformedCreateEvent();

		// check transformed Event
		assertTransformedEvent(srcCreateEvent, destCreateEvent);

		// check transformed MetaTags
		assertTransformedMetaTags(srcCreateEvent, destCreateEvent.getMetaTags());

		// check MatchState - for createEvent this will always be Open
		assertEquals(EventStatus.OPEN, dest.getTransformedCreateEvent().getStatus());
	}

	private void transformEvent_updateEvent(String file) {

		// Create destination models
		com.aldogrand.sbpc.connectors.model.Event destUpdateEvent = null;

		// populate source objects
		Updategram src = loadUpdateGram(file);
		Event srcUpdateEvent = src.getUpdateEventCommand().getEvent();

		// transform
		BettingTransformedData dest = transformer.transformEvent(src);
		destUpdateEvent = dest.getTransformedUpdateEvent();

		// check transformed Event
		assertTransformedEvent(srcUpdateEvent, destUpdateEvent);

		// check transformed MetaTags
		assertTransformedMetaTags(srcUpdateEvent, destUpdateEvent.getMetaTags());

		// check MatchState
		if (srcUpdateEvent.getMatchState() != null) {
			assertTransformedMatchState(srcUpdateEvent, destUpdateEvent);
		}
	}

	private void transformEvent_createMarket(String file) {
		// Create destination models
		List<com.aldogrand.sbpc.connectors.model.Market> destCreateMarkets = new ArrayList<com.aldogrand.sbpc.connectors.model.Market>();
		Map<String, com.aldogrand.sbpc.connectors.model.Market> destCreateMarketMap = new HashMap<String, com.aldogrand.sbpc.connectors.model.Market>();

		// populate source objects
		Updategram src = loadUpdateGram(file);
		List<CreateMarket> srcCreateMarkets = src.getCreateMarkets();

		// transform
		BettingTransformedData dest = transformer.transformEvent(src);
		destCreateMarkets = dest.getTransformedCreateMarkets();

		// check list of markets is present and same size
		assertNotNull(destCreateMarkets);
		assertEquals(srcCreateMarkets.size(), destCreateMarkets.size());

		// create map by id of Markets in order to compare to source
		createDestMarketMap(destCreateMarkets, destCreateMarketMap);

		// Check if each source market was transformed correctly
		for (CreateMarket srcCreateMarket : srcCreateMarkets) {
			Market srcMarket = srcCreateMarket.getMarket();
			MarketType srcMarketType = srcMarket.getMarketType();
			Long mktId = srcMarket.getId();
			com.aldogrand.sbpc.connectors.model.Market destMkt = destCreateMarketMap.get(String.valueOf(mktId));

			// check market info
			assertTransformedMarket(srcMarket, srcMarketType, destMkt);

			// check selections/runners
			assertTransformedSelectionsRunners(srcMarket, destMkt);
		}
	}

	private void transformEvent_updateMarket(String file) {
		// Create destination models
		List<com.aldogrand.sbpc.connectors.model.Market> destUpdateMarkets = new ArrayList<com.aldogrand.sbpc.connectors.model.Market>();
		Map<String, com.aldogrand.sbpc.connectors.model.Market> destUpdateMarketMap = new HashMap<String, com.aldogrand.sbpc.connectors.model.Market>();

		// populate source objects
		Updategram src = loadUpdateGram(file);
		List<UpdateMarket> srcUpdateMarkets = src.getUpdateMarkets();

		// transform
		BettingTransformedData dest = transformer.transformEvent(src);
		destUpdateMarkets = dest.getTransformedUpdateMarkets();

		// check list of markets is present and same size
		assertNotNull(destUpdateMarkets);
		assertEquals(srcUpdateMarkets.size(), destUpdateMarkets.size());

		// create map by id of Markets in order to compare to source
		createDestMarketMap(destUpdateMarkets, destUpdateMarketMap);

		// Check if each source market was transformed correctly
		for (UpdateMarket srcUpdateMarket : srcUpdateMarkets) {
			Market srcMarket = srcUpdateMarket.getMarket();
			MarketType srcMarketType = srcMarket.getMarketType();
			Long mktId = srcMarket.getId();
			com.aldogrand.sbpc.connectors.model.Market destMkt = destUpdateMarketMap.get(String.valueOf(mktId));

			// check market info
			assertTransformedMarket(srcMarket, srcMarketType, destMkt);

			// check selections/runners
			assertTransformedSelectionsRunners(srcMarket, destMkt);
		}

	}

	private void transformEvent_createResult(String file) {
		// Create destination models
		com.aldogrand.sbpc.connectors.model.Market destResultMarket = new com.aldogrand.sbpc.connectors.model.Market();

		// populate source objects
		Updategram src = loadUpdateGram(file);
		Market srcResultMarket = src.getCreateResultCommand().getMarket();

		// transform
		BettingTransformedData dest = transformer.transformEvent(src);
		destResultMarket = dest.getTransformedResultMarket();

		// check result market
		if (srcResultMarket != null) {
			assertNotNull(destResultMarket);

			// Check if each source market was transformed correctly
			MarketType srcResultMarketType = srcResultMarket.getMarketType();

			// check market info
			assertTransformedMarket(srcResultMarket, srcResultMarketType, destResultMarket);

			// check selections/runners
			assertTransformedSelectionsRunners(srcResultMarket, destResultMarket);
		}
	}

	private Updategram loadUpdateGram(String file) {
		try {
			return (Updategram) unmarshaller.unmarshal(getClass().getResourceAsStream("/testcases/" + file));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param destCreateMarkets
	 * @param destCreateMarket
	 */
	private void createDestMarketMap(List<com.aldogrand.sbpc.connectors.model.Market> destCreateMarkets,
			Map<String, com.aldogrand.sbpc.connectors.model.Market> destCreateMarket) {
		for (com.aldogrand.sbpc.connectors.model.Market destMarket : destCreateMarkets) {
			assertNotNull(destMarket);
			assertNotNull(destMarket.getId());
			destCreateMarket.put(destMarket.getId(), destMarket);
		}
	}

	/**
	 * 
	 * @param destRunners
	 * @param destRunnerMap
	 */
	private void createDestRunnerMap(List<Runner> destRunners, Map<String, Runner> destRunnerMap) {
		for (Runner runner : destRunners) {
			assertNotNull(runner);
			assertNotNull(runner.getId());
			destRunnerMap.put(runner.getId(), runner);
		}
	}

	/**
	 * check transformed event
	 * 
	 * @param srcEvent
	 * @param dest
	 * @return
	 */
	private void assertTransformedEvent(Event srcEvent, com.aldogrand.sbpc.connectors.model.Event destEvent) {
		assertNotNull(destEvent);
		assertEquals(srcEvent.getId().toString(), destEvent.getId());
		assertEquals(srcEvent.getName(), destEvent.getName());
		assertEquals(srcEvent.getStartTime().toGregorianCalendar().getTime(), destEvent.getStartTime());
	}

	/**
	 * check transformed MetaTags
	 * 
	 * @param destMetaTags
	 * @param srcEvent
	 */
	private void assertTransformedMetaTags(Event srcEvent, List<MetaTag> destMetaTags) {
		Sport srcSport = srcEvent.getSport();
		Season srcSeason = srcEvent.getSeason();
		Competition srcComp = srcSeason.getCompetition();
		Region srcRegion = srcComp.getRegion();
		Round srcRound = srcEvent.getRound();

		MetaTag metaTag = null;
		Map<MetaTagType, MetaTag> destMetaTagMap = new HashMap<MetaTagType, MetaTag>();
		populateDestMetaTagMap(destMetaTags, destMetaTagMap);

		if (srcSport != null && StringUtils.isNotBlank(srcSport.getName())) {
			// SPORT meta tag expected
			metaTag = destMetaTagMap.get(MetaTagType.SPORT);
			if (srcSport.getName().equals(FOOTBALL)) {
				assertEquals(SOCCER, metaTag.getName());
			} else {
				assertEquals(srcSport.getName(), metaTag.getName());
			}
		}

		if (srcSeason != null) {
			if (StringUtils.isNotBlank(srcSeason.getName())) {
				// Season meta tag expected
				metaTag = destMetaTagMap.get(MetaTagType.SEASON);
				assertEquals(srcSeason.getName(), metaTag.getName());
			}

			if (srcComp != null) {
				if (StringUtils.isNotBlank(srcComp.getName())) {
					// Competition meta tag expected
					metaTag = destMetaTagMap.get(MetaTagType.COMPETITION);
					assertEquals(srcComp.getName(), metaTag.getName());
				}

				if (srcRegion != null && StringUtils.isNotBlank(srcRegion.getName())) {
					// region/country meta tag expected
					metaTag = destMetaTagMap.get(MetaTagType.COUNTRY);
					assertEquals(srcRegion.getName(), metaTag.getName());
				}
			}
		}

		if (srcRound != null && StringUtils.isNotBlank(srcRound.getName())) {
			// round meta tag expected
			metaTag = destMetaTagMap.get(MetaTagType.ROUND);
			assertEquals(srcRound.getName(), metaTag.getName());
		}


	}

	/**
	 * @param destMetaTags
	 * @return
	 */
	private void populateDestMetaTagMap(List<MetaTag> metaTags, Map<MetaTagType, MetaTag> metaTagMap) {
		for (MetaTag metaTag : metaTags) {
			metaTagMap.put(metaTag.getType(), metaTag);
		}
	}

	/**
	 * check MatchState
	 * 
	 * @param destEvent
	 * @param srcEvent
	 */
	private void assertTransformedMatchState(Event srcEvent, com.aldogrand.sbpc.connectors.model.Event destEvent) {
		assertNotNull(srcEvent.getMatchState());
		assertEquals(EVENT_STATUS_MAP.get(srcEvent.getMatchState().getPhase()), destEvent.getStatus());
	}

	/**
	 * @param srcMarket
	 * @param srcMarketType
	 * @param destMkt
	 */
	private void assertTransformedMarket(Market srcMarket, MarketType srcMarketType,
			com.aldogrand.sbpc.connectors.model.Market destMkt) {
		assertNotNull(destMkt);
		assertEquals(String.valueOf(srcMarket.getId()), destMkt.getId());
		assertEquals(String.valueOf(srcMarket.getEventId()), destMkt.getEventId());
		assertEquals(srcMarket.getName(), destMkt.getName());

		// handicap
		if (srcMarket.getHandicap() == null) {
			assertEquals(new Double(0), destMkt.getHandicap());
		} else {
			assertEquals(new Double(srcMarket.getHandicap().doubleValue()), destMkt.getHandicap());
		}

		// period
		if (srcMarketType != null) {
			if (FIRST_HALF_MARKETS.contains(srcMarketType.getId())) {
				assertEquals(Period.FIRST_HALF, destMkt.getPeriod());
			} else if (SECOND_HALF_MARKETS.contains(srcMarketType.getId())) {
				assertEquals(Period.SECOND_HALF, destMkt.getPeriod());
			} else {
				assertEquals(Period.FULL_EVENT, destMkt.getPeriod());
			}
		} else {
			assertEquals(Period.UNKNOWN, destMkt.getPeriod());
		}

		// Market.type
		if (srcMarketType != null && srcMarketType.getId() != 0) {
			com.aldogrand.sbpc.model.MarketType mappedMarketType = MARKET_TYPES.get(srcMarketType.getId());

			assertNotNull(destMkt.getType());

			if (mappedMarketType == null) {
				if (srcMarketType.isHandicap()) {
					assertEquals(com.aldogrand.sbpc.model.MarketType.HANDICAP, destMkt.getType());
				} else if (srcMarketType.isOverunder()) {
					assertEquals(com.aldogrand.sbpc.model.MarketType.TOTAL, destMkt.getType());
				} else {
					assertEquals(com.aldogrand.sbpc.model.MarketType.UNKNOWN, destMkt.getType());
				}
			} else {
				if (mappedMarketType.equals(com.aldogrand.sbpc.model.MarketType.ASIAN_HANDICAP)) {
					if (srcMarket.getHandicap().doubleValue() % 0.5 == 0) {
						assertEquals(com.aldogrand.sbpc.model.MarketType.HANDICAP, destMkt.getType());
					}
				} else if (mappedMarketType.equals(com.aldogrand.sbpc.model.MarketType.TOTAL)) {
					if (srcMarket.getHandicap().doubleValue() % 0.5 != 0) {
						assertEquals(com.aldogrand.sbpc.model.MarketType.ASIAN_HANDICAP_TOTAL, destMkt.getType());
					}
				} else {
					assertEquals(mappedMarketType, destMkt.getType());
				}
			}
		}

		// MarketStatus
		if (MarketStatus.Held.equals(srcMarket.getMarketStatus())) {
			assertEquals(com.aldogrand.sbpc.model.MarketStatus.SUSPENDED, destMkt.getMarketStatus());
		} else if (MarketStatus.New.equals(srcMarket.getMarketStatus())) {
			assertEquals(com.aldogrand.sbpc.model.MarketStatus.OPEN, destMkt.getMarketStatus());
		} else if (MarketStatus.Open.equals(srcMarket.getMarketStatus())) {
			assertEquals(com.aldogrand.sbpc.model.MarketStatus.OPEN, destMkt.getMarketStatus());
		} else if (MarketStatus.Resulted.equals(srcMarket.getMarketStatus())) {
			assertEquals(com.aldogrand.sbpc.model.MarketStatus.RESULTED, destMkt.getMarketStatus());
		} else if (MarketStatus.Closed.equals(srcMarket.getMarketStatus())) {
			assertEquals(com.aldogrand.sbpc.model.MarketStatus.CLOSED, destMkt.getMarketStatus());
		} else if (MarketStatus.Suspended.equals(srcMarket.getMarketStatus())) {
			assertEquals(com.aldogrand.sbpc.model.MarketStatus.SUSPENDED, destMkt.getMarketStatus());
		}
	}

	/**
	 * @param srcMarket
	 * @param destMkt
	 */
	private void assertTransformedSelectionsRunners(Market srcMarket, com.aldogrand.sbpc.connectors.model.Market destMkt) {
		List<Selection> selections = srcMarket.getSelections();
		Map<String, Runner> destRunnerMap = new HashMap<String, Runner>();
		if (destMkt.getRunners() != null && !destMkt.getRunners().isEmpty()) {
			createDestRunnerMap(destMkt.getRunners(), destRunnerMap);
			for (Selection selection : selections) {
				Runner runner = destRunnerMap.get(String.valueOf(selection.getId()));

				assertEquals(String.valueOf(selection.getId()), runner.getId());
				assertEquals(selection.getName(), runner.getName());
				assertEquals(String.valueOf(srcMarket.getId()), runner.getMarketId());

				if (selection.getOutcome() != null || srcMarket.getName().equals("Correct Score")) {
					RunnerDetails match = null;

					if (selection.getOutcome() != null) {
						match = RUNNER_DETAILS_MAP.get(selection.getOutcome().getId());
					} else {
						match = RUNNER_DETAILS_MAP.get(CORRECT_SCORE);
					}

					if (match != null) {
						assertEquals(match.side, runner.getSide());
						assertEquals(match.type, runner.getType());
						assertEquals(Integer.valueOf(match.sequence), runner.getSequence());

						if (srcMarket.getHandicap() != null && srcMarket.getHandicap() != BigDecimal.ZERO) {
							if (runner.getSequence() == 1) {
								assertEquals(new Double(srcMarket.getHandicap().doubleValue()), runner.getHandicap());
							} else if (runner.getSequence() == 2) {
								assertEquals(new Double(srcMarket.getHandicap().negate().doubleValue()), runner.getHandicap());
							}
						}
					} else {
						assertEquals(RunnerSide.UNKNOWN, runner.getSide());
						assertEquals(RunnerType.UNKNOWN, runner.getType());
						assertEquals(new Integer(0), runner.getSequence());
					}
				}

				if (selection.getCompetitorId() != null && selection.getCompetitorId() == 0) {
					assertEquals(selection.getCompetitorId().longValue(), runner.getSourceParticipantId());
				}

				if (selection.getTradingStatus() != null) {
					assertEquals(RunnerStatus.OPEN, runner.getRunnerStatus());
				} else {
					assertEquals(RunnerStatus.UNKNOWN, runner.getRunnerStatus());
				}

				// Set Result Status
				assertEquals(RESULT_STATUS_MAP.get(selection.getResultStatus()), runner.getRunnerStatus());

				// Prices not implemented yet
			}
		}
	}

	private MatchStateFootball createMatchStateFootball() {
		MatchStateFootball msf = new MatchStateFootball();

		List<MatchPhase> matchPhases = new ArrayList<MatchPhase>();
		matchPhases.add(createMatchPhase(1));
		matchPhases.add(createMatchPhase(2));
		
		List<MatchEvent> matchEvents = new ArrayList<MatchEvent>();
		matchEvents.add(createMatchEvent(345, 2, 1, "first", true, 3, 31, 2, true));
		matchEvents.add(createMatchEvent(456, 7, 2, "second", true, 2, 32, 2, true));

		msf.setAllMatchPhaseDetails(matchPhases);
		msf.setCurrentMatchPhase(1);
		msf.setMatchPhaseIsConfirmed(true);
		msf.setCurrentDangerState(2);
		msf.setCurrentBookingState(4);
		msf.setGoals(createScore(5, 4, true));
		msf.setCorners(createScore(2, 3, true));
		msf.setYellowCards(createScore(4, 5, true));
		msf.setRedCards(createScore(6, 7, true));
		msf.setSubstitutions(createScore(8, 9, true));
		msf.setMatchEvent(matchEvents);
		msf.setCurrentHomePlayerIds(new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
		msf.setCurrentAwayPlayerIds(new ArrayList<Integer>(Arrays.asList(3, 4, 2)));
		msf.setWaitingHomePlayerIds(new ArrayList<Integer>(Arrays.asList(31, 32, 33)));
		msf.setWaitingAwayPlayerIds(new ArrayList<Integer>(Arrays.asList(41, 42, 43)));
		msf.setSecondLeg(true);
		msf.setFirstLegScore(createScore(1, 0, true));
		return msf;
	}

	private MatchEvent createMatchEvent(int id, int matchEventType, int occurredDuring, String occurredAt, boolean confirmed,
			int playerId, int subOnPlayerId, int subOffPlayerId, boolean isSubPlayerEvent) {
		MatchEvent me = new MatchEvent();

		me.setId(id);
		me.setMatchEventType(matchEventType);
		me.setOccurredDuring(occurredDuring);
		me.setOccurredAt(occurredAt);
		me.setConfirmed(confirmed);
		me.setPlayerId(playerId);
		me.setSubOnPlayerId(subOnPlayerId);
		me.setSubOffPlayerId(subOffPlayerId);
		me.setSubPlayerEvent(isSubPlayerEvent);

		return me;
	}

	private MatchPhase createMatchPhase(int phase) {
		MatchPhase mp = new MatchPhase();
		mp.setPhase(phase);
		mp.setStartTime(getXmlGregorianCalendar());
		mp.setStoppageTime("10");
		mp.setGameInPlay(true);
		mp.setStandardPhaseDuration("45");
		mp.setStartTimeOffset("0");
		mp.setExtraTime(true);
		mp.setTimePhased(true);
		mp.setBookingsAllowed(true);
		mp.setCornersAllowed(true);
		mp.setGoalsAllowed(true);
		return mp;
	}

	private Score createScore(int home, int away, boolean areReliable) {
		Score s = new Score();
		s.setHome(home);
		s.setAway(away);
		s.setAreReliable(areReliable);
		return s;
	}

	@SuppressWarnings("unused")
	private Score createScore(int home, int away, boolean areReliable, int normalTimeHome, int normalTimeAway, int extraTimeHome,
			int extraTimeAway, int penaltyHome, int penaltyAway) {
		Score s = new Score();
		s.setHome( home);
		s.setAway(away);
		s.setAreReliable(areReliable);
		s.setNormalTimeHome(normalTimeHome);
		s.setNormalTimeAway(normalTimeAway);
		s.setExtraTimeHome(extraTimeHome);
		s.setExtraTimeAway(extraTimeAway);
		s.setPenaltyHome(penaltyHome);
		s.setPenaltyAway(penaltyAway);
		return s;
	}

	private XMLGregorianCalendar getXmlGregorianCalendar() {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private MatchStateFootball loadMatchState(String file) {
		try {
			InputStream is = getClass().getResourceAsStream("/testcases/" + file);

			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(getStringFromInputStream(is), MatchStateFootball.class);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
}
