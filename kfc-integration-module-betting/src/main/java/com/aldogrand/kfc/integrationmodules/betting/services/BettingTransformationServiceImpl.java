package com.aldogrand.kfc.integrationmodules.betting.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.sbpc.connectors.model.Participant;
import com.aldogrand.sbpc.connectors.model.Price;
import com.aldogrand.sbpc.connectors.model.Runner;
import com.aldogrand.sbpc.connectors.model.Score;
import com.aldogrand.sbpc.model.CurrentDangerballStatus;
import com.aldogrand.sbpc.model.EventPhase;
import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.IncidentType;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.MetaTag;
import com.aldogrand.sbpc.model.MetaTagType;
import com.aldogrand.sbpc.model.Period;
import com.aldogrand.sbpc.model.PositionType;
import com.aldogrand.sbpc.model.PriceSide;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.sbpc.model.ScoreType;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Competition;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.CreateMarket;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Event;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Market;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Player;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Product;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Selection;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Team;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.UpdateMarket;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Updategram;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchEvent;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchPhase;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchStateFootball;
import com.aldogrand.kfc.integrationmodules.betting.model.tennis.GameScore;
import com.aldogrand.kfc.integrationmodules.betting.model.tennis.MatchScore;
import com.aldogrand.kfc.integrationmodules.betting.model.tennis.MatchStateTennis;
import com.aldogrand.kfc.integrationmodules.betting.model.tennis.SetScore;
import com.aldogrand.kfc.integrationmodules.model.BettingTransformedData;
import com.aldogrand.kfc.integrationmodules.model.Incident;
import com.aldogrand.kfc.utils.general.OddsConverter;
import com.aldogrand.kfc.utils.general.OddsType;
import com.aldogrand.kfc.utils.general.kfcUtility;

/**
 * Builds internal connector model representation of received Betgenius data
 * 
 * @author aldogrand
 * 
 */
public class BettingTransformationServiceImpl extends BettingTransformationBase implements
		BettingTransformationService {

	private final Logger logger = Logger.getLogger(getClass());
	
	private static String CURRENT_SCORE_TEXT_VALUE = "Correct Score";
	private boolean processUnconfirmedIncidents;
	

	@Override
	public BettingTransformedData transformEvent(Updategram updateGram) {
		if (updateGram == null) {
			return null;
		}
		logger.debug("Betgenius Event data transformation : START");
		// Create all holders for transformation types
		com.aldogrand.sbpc.connectors.model.Event transformedCreateEvent = null;
		com.aldogrand.sbpc.connectors.model.Event transformedUpdateEvent = null;
		List<com.aldogrand.sbpc.connectors.model.Market> transformedCreateMarkets = new ArrayList<com.aldogrand.sbpc.connectors.model.Market>();
		List<com.aldogrand.sbpc.connectors.model.Market> transformedUpdateMarkets = new ArrayList<com.aldogrand.sbpc.connectors.model.Market>();
		List<Runner> transformedCreateRunners = new ArrayList<Runner>();
		List<Runner> transformedUpdateRunners = new ArrayList<Runner>();
		List<Price> transformedPrices = new ArrayList<Price>();
		com.aldogrand.sbpc.connectors.model.Market transformedResultMarket = null;

		// Create event
		if (updateGram.getCreateEventCommand() != null
				&& updateGram.getCreateEventCommand().getEvent() != null) {
			transformedCreateEvent = transformEvent(updateGram
					.getCreateEventCommand().getEvent(), updateGram.getProduct());
			// Create with an OPEN status and set to closed once all 
			// markets for the source close
			transformedCreateEvent.setStatus(EventStatus.OPEN);
		}

		// Update event
		if (updateGram.getUpdateEventCommand() != null
				&& updateGram.getUpdateEventCommand().getEvent() != null) {
			transformedUpdateEvent = transformEvent(updateGram
					.getUpdateEventCommand().getEvent(), updateGram.getProduct());
		}

		// Create Markets
		if (CollectionUtils.isNotEmpty(updateGram.getCreateMarkets())) {
			for (CreateMarket createMarket : updateGram.getCreateMarkets()) {
				transformedCreateMarkets.add(transformMarket(createMarket
						.getMarket()));
				transformRunners(createMarket.getMarket(),
						transformedCreateRunners, transformedPrices,
						createMarket.getMarket().getId());
			}
		}

		// Update Markets
		if (CollectionUtils.isNotEmpty(updateGram.getUpdateMarkets())) {
			for (UpdateMarket updateMarket : updateGram.getUpdateMarkets()) {
				transformedUpdateMarkets.add(transformMarket(updateMarket
						.getMarket()));
				transformRunners(updateMarket.getMarket(),
						transformedUpdateRunners, transformedPrices,
						updateMarket.getMarket().getId());
				// iterate over all the prices setting Market level details
			}
		}

		// Create Result
		if (updateGram.getCreateResultCommand() != null
				&& updateGram.getCreateResultCommand().getMarket() != null) {
			Market marketResult = updateGram
					.getCreateResultCommand().getMarket();
			transformedResultMarket = transformMarket(marketResult);
			transformRunners(marketResult,
					transformedUpdateRunners, transformedPrices,
					marketResult.getId());
		}
		
		logger.debug("Betgenius Event data transformation : COMPLETE");
		return new BettingTransformedData(transformedCreateEvent,
				transformedUpdateEvent, transformedCreateMarkets,
				transformedUpdateMarkets, transformedCreateRunners,
				transformedUpdateRunners, transformedResultMarket);
	}


	/**
	 * Transform Betgenius {@link Event} to integration module model
	 * {@link com.aldogrand.sbpc.connectors.model.Event}
	 * 
	 * @param event Betgenius event model
	 * @return Integration module event model 
	 */
	private com.aldogrand.sbpc.connectors.model.Event transformEvent(
			final Event event, final Product product) {
		com.aldogrand.sbpc.connectors.model.Event transformedEvent = new com.aldogrand.sbpc.connectors.model.Event();
		transformedEvent.setId(String.valueOf(event.getId()));
		transformedEvent.setName(event.getName());
		transformedEvent.setStartTime(event.getStartTime().toGregorianCalendar().getTime());

		// TODO (in future): transform participants. Now participants not being currently catered for		

		List<MetaTag> metaTags = new ArrayList<MetaTag>();
		if (event.getSport() != null
				&& StringUtils.isNotBlank(event.getSport().getName())) {
			metaTags.add(kfcUtility.createMetaTag(MetaTagType.SPORT, event
					.getSport().getName().equals(FOOTBALL) ? SOCCER : event
							.getSport().getName()));
		}

		if (event.getSeason() != null) {
			if (StringUtils.isNotBlank(event.getSeason().getName())) {
				metaTags.add(kfcUtility.createMetaTag(MetaTagType.SEASON,
						event.getSeason().getName()));
			}

			if (event.getSeason().getCompetition() != null) {
				Competition comp = event.getSeason().getCompetition();
				if (StringUtils.isNotBlank(comp.getName())) {
					metaTags.add(kfcUtility.createMetaTag(
							MetaTagType.COMPETITION, comp.getName()));
				}

				if (comp.getRegion() != null
						&& StringUtils.isNotBlank(comp.getRegion().getName())) {
					metaTags.add(kfcUtility.createMetaTag(
							MetaTagType.COUNTRY, comp.getRegion().getName()));
				}
			}
		}

		if (event.getRound() != null
				&& StringUtils.isNotBlank(event.getRound().getName())) {
			metaTags.add(kfcUtility.createMetaTag(MetaTagType.ROUND, event
					.getRound().getName()));
		}

		transformedEvent.setMetaTags(metaTags);
		
		// Check if any Match State is supplied and set as Event Status if so
		if (event.getMatchState() != null) {
			transformedEvent.setStatus(EVENT_STATUS_MAP.get(event.getMatchState().getPhase()));
			
			if (com.aldogrand.kfc.integrationmodules.betting.model.eventdata.MatchPhase.PreMatch.equals(event.getMatchState().getPhase())
					&& PRODUCT_IN_RUNNING.equals(product.getName())) {
				// Game has been selected for in-running 
				transformedEvent.setStatus(EventStatus.PRE_EVENT_LIVE);
			}
		}

		return transformedEvent;
	}

	/**
	 * Transform Betgenius {@link Market} to a Betgenius integration module model
	 * {@link com.aldogrand.sbpc.connectors.model.Market}
	 * 
	 * @param market Betgenius model
	 * @return Betgenius integration module model
	 */
	private com.aldogrand.sbpc.connectors.model.Market transformMarket(
			Market market) {
		com.aldogrand.sbpc.connectors.model.Market mappedMarket = new com.aldogrand.sbpc.connectors.model.Market();
		mappedMarket.setId(String.valueOf(market.getId()));
		mappedMarket.setName(market.getName());
		mappedMarket.setEventId(String.valueOf(market.getEventId()));
		mappedMarket.setHandicap(market.getHandicap() == null ? 0 : market
				.getHandicap().doubleValue());
		Period period = null;
		if (market.getMarketType() != null && market.getMarketType().getId() != 0) {
			if (FIRST_HALF_MARKETS.contains(market.getMarketType().getId())) {
				period = Period.FIRST_HALF;
			} else if (SECOND_HALF_MARKETS.contains(market.getMarketType().getId())) {
				period = Period.SECOND_HALF;
			} else {
				period = Period.FULL_EVENT;
			}
		} else {
			period = Period.UNKNOWN;
		}
		mappedMarket.setPeriod(period); 

		MarketType type = null;
		if (market.getMarketType() != null && market.getMarketType().getId() != 0) {
			type = MARKET_TYPES.get(market.getMarketType().getId());
			
			if (type == null) {
				if (market.getMarketType().isHandicap()) {
					type = MarketType.HANDICAP;
				} else if (market.getMarketType().isOverunder()) {
					type = MarketType.TOTAL;
				}
			}
			else{
				//Separate Asian Handicaps from Handicaps (BetGenius returns default of Asian Handicap)
				if(type.equals(MarketType.ASIAN_HANDICAP)){
					if(market.getHandicap().doubleValue() % 0.5 == 0){
						type = MarketType.HANDICAP;
					}
				}
				//Separate Asian Handicap Totals from Totals (BetGenius returns default of Total)
				else if(type.equals(MarketType.TOTAL)){
					if(market.getHandicap().doubleValue() % 0.5 != 0){
						type = MarketType.ASIAN_HANDICAP_TOTAL;
					}
				}
			}
			mappedMarket.setType(type != null ? type : MarketType.UNKNOWN);
		}
		
		// Set status
		mappedMarket.setMarketStatus(MARKET_STATUS_MAP.get(market.getMarketStatus()));
	      
		return mappedMarket;
	}

	/**
	 * Transform Collection of Betgenius {@link Selection}  
	 * to a Betgenius integration module model {@link Runner}
	 * 
	 * TODO Transform prices contained within the Selections
	 * 
	 * @param selections
	 * @param transformedCreateRunners
	 * @param transformedPrices
	 * @param marketId
	 */
	private void transformRunners(Market market,
			List<Runner> transformedCreateRunners,
			List<Price> transformedPrices, Long marketId) {
		Integer sequence = 0;
		List<Selection> selections = market.getSelections();
		for (Selection selection : selections) {
			sequence++;
			Runner runner = new Runner();
			runner.setId(String.valueOf(selection.getId()));
			runner.setMarketId(String.valueOf(marketId));
			runner.setName(selection.getName());
			runner.setEventId(String.valueOf(market.getEventId()));
			
			if (selection.getOutcome() != null || market.getName().equals(CURRENT_SCORE_TEXT_VALUE)) {
				RunnerDetails match = null;
				
				if(selection.getOutcome() != null){
					match = RUNNER_DETAILS_MAP.get(selection.getOutcome().getId());
				}
				else{
					match = RUNNER_DETAILS_MAP.get(CORRECT_SCORE);
				}
				
				if (match != null) {
					runner.setSide(match.side);
					runner.setType(match.type);
					runner.setSequence(match.sequence);
					// Set handicaps on runners
					if (market.getHandicap() != null && market.getHandicap() != BigDecimal.ZERO) {
						if (runner.getSequence() == 1) { // home or over
							runner.setHandicap(market.getHandicap().doubleValue());
						} else if (runner.getSequence() == 2) {
							runner.setHandicap(market.getHandicap().negate().doubleValue());
						}
					}
				} else {
					runner.setSide(RunnerSide.UNKNOWN);
					runner.setType(RunnerType.UNKNOWN);
					runner.setSequence(0);
				}
			}
			
			// Set the participant where provided
			if (selection.getCompetitorId() != null && selection.getCompetitorId() == 0) {
				runner.setSourceParticipantId(selection.getCompetitorId().longValue());
			}
			
			// Set Runner Status
			if (selection.getTradingStatus() != null) {
				// Won't mark runner as closed when suspended by betgenius as suspending handled 
				// at the market level
				runner.setRunnerStatus(RunnerStatus.OPEN);
			} else {
				runner.setRunnerStatus(RunnerStatus.UNKNOWN);
			}
			
			// Set Result Status
			runner.setResultStatus(RESULT_STATUS_MAP.get(selection.getResultStatus()));			
			transformedCreateRunners.add(runner);
			
			// TODO (next phase?): Get prices from Betgenius
			// Now leaving prices for now as not needed
		}

	}
	
	
	/*
	 *  Below not currently in use as initial Betgenius integration not including Prices, 
	 *  Participants or in-play MatchEvents
	 */

	/**
	 * Create price from {@link Selection} data
	 * 
	 * @param selection
	 * @return
	 */
	private Price transformCorePriceData(Selection selection) {
		Price price = new Price();
		// price.setEventId(event.getId().toString());
		// price.setMarketId(mbMarket.getId().toString());
		price.setRunnerId(String.valueOf(selection.getId()));
		// price.setBettingPlatformName(getName()); from connector
		OddsType oddsType = null;
		// Only need to do decimal if always provided
		if (selection.getEuropean() != null
				&& selection.getEuropean().doubleValue() != 0) {
			oddsType = OddsType.DECIMAL;
			price.setDecimalOdds(selection.getEuropean());
			price.setOdds((BigDecimal)OddsConverter.convertFromDecimalOdds(selection.getEuropean(), OddsType.FRACTIONAL));
		} else if (selection.getDenominator() != 0
				&& selection.getNumerator() != 0) {
			oddsType = OddsType.FRACTIONAL;
			BigDecimal calculatedPrice = new BigDecimal(selection.getNumerator()).divide(new BigDecimal(selection.getDenominator()));
			price.setOdds(calculatedPrice);
			price.setDecimalOdds(OddsConverter.convertToDecimalOdds(calculatedPrice, OddsType.DECIMAL));
		} else {
			return null;
		}
		price.setOddsType(oddsType);

		// price.setAvailableAmount(mbPrice.getAvailableAmount());
		// price.setCurrency(currency);
		price.setSide(PriceSide.WIN); // Is it just a win for a sportsbook always?
		return price;
	}

	/**
	 * Build a Collection of {@link Participant} of types Team and Player
	 * 
	 * @param event
	 * @return
	 */
	private List<com.aldogrand.sbpc.connectors.model.Participant> transformParticipants(
			final Event event) {
		List<com.aldogrand.sbpc.connectors.model.Participant> transformedParticipants = new ArrayList<com.aldogrand.sbpc.connectors.model.Participant>();
		List<Team> teams = event.getTeams();
		List<Player> players = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(teams)) {
			for (Team team : teams) {
				com.aldogrand.sbpc.connectors.model.Team transformedTeam = new com.aldogrand.sbpc.connectors.model.Team();
				transformedTeam.setId(String.valueOf(team.getId()));
				transformedTeam.setName(team.getName());
				transformedTeam.setPositionType(PositionType.fromString(team
						.getPosition()));
				transformedParticipants.add(transformedTeam);
	
				players = team.getPlayers();
				for (Player player : players) {
					com.aldogrand.sbpc.connectors.model.Player transformedPlayer = new com.aldogrand.sbpc.connectors.model.Player();
					transformedPlayer.setId(String.valueOf(player.getId()));
					transformedPlayer.setName(team.getName());
					transformedPlayer.setLastName(player.getLastName());
					transformedPlayer.setFirstName(player.getFirstName());
					transformedParticipants.add(transformedPlayer);
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(event.getPlayers())) {
			players.addAll(event.getPlayers());
		}
		
		// transform all received players
		for (Player player : players) {
			com.aldogrand.sbpc.connectors.model.Player transformedPlayer = new com.aldogrand.sbpc.connectors.model.Player();
			transformedPlayer.setId(String.valueOf(player.getId()));
			transformedPlayer.setName(player.getName());
			transformedPlayer.setLastName(player.getLastName());
			transformedPlayer.setFirstName(player.getFirstName());
			transformedParticipants.add(transformedPlayer);
		}
		
		return transformedParticipants;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.integrationmodules.betgenius.services.BetgeniusTransformationService#transformMatchState(com.aldogrand.kfc.integrationmodules.betgenius.model.football.MatchStateFootball)
	 */
	@Override 
	public List<EventIncident> transformMatchState(MatchStateFootball football) {
		final long sourceEventId = football.getEventId();
		List<EventIncident> incidents = new ArrayList<EventIncident>();
		
		// Create a map of the phases for matching incidents against
		Map<Integer, MatchPhase> mapPhases = new HashMap<>();
		List<MatchPhase> matchPhases = football.getAllMatchPhaseDetails();
		for (MatchPhase matchPhase : matchPhases) {
			mapPhases.put(matchPhase.getPhase(), matchPhase);
		}

		Score score = null;
		if (football.getGoals() != null && football.getGoals().isAreReliable()) {
			score =
				new Score(
					ScoreType.GOAL,
					football.getGoals().getHome(),
					football.getGoals().getAway()
				);
		}
		
		List<MatchEvent> matchEvents = football.getMatchEvent();

		EventPhase status = EventPhase.getStatus(football.getCurrentMatchPhase());

		for (MatchEvent matchEvent : matchEvents) {
 			MatchPhase phase = mapPhases.get(matchEvent.getOccurredDuring());
 			// Need to calculate the time the incident happen to verify if already processed
 			if (phase != null && matchEvent.isConfirmed()) { 				
 				EventIncident incident =
 					new EventIncident(
 						sourceEventId,
 						matchEvent.getId(),
 						CurrentDangerballStatus.getStatus(football.getCurrentDangerState()), 
 						score,
 						EventPhase.getStatus(matchEvent.getOccurredDuring()),
 						matchEvent.getOccurredAt(),
 						IncidentType.getType(matchEvent.getMatchEventType()),
 						matchEvent.getPlayerId()
 					);

 				incidents.add(incident);
 			}
		}		

		//create an incident to persist a danger ball. 
		incidents.add(
			new EventIncident(
				sourceEventId,
				0,
				CurrentDangerballStatus.getStatus(football.getCurrentDangerState()),
				score,
				status
			)
		);	

		return incidents;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.aldogrand.kfc.integrationmodules.betgenius.services.BetgeniusTransformationService#transformMatchState(com.aldogrand.kfc.integrationmodules.betgenius.model.tennis.MatchStateTennis)
	 */
	@Override
	public List<EventIncident> transformMatchState(MatchStateTennis tennis) {
		List<Incident> incidents = new ArrayList<Incident>();
		List<Score> scores = new ArrayList<>();
		final Date msgReceivedTime = tennis.getMsgTimestampUTC().toGregorianCalendar().getTime();
		
		// Current Game Score
		final MatchScore currentMatchScore = tennis.getCurrentMatchScore();
		if (currentMatchScore.getGameScore() != null) {
			GameScore gameScore = currentMatchScore.getGameScore();
			scores.add(
				new Score(
					ScoreType.GAME,
					gameScore.getCompOneScore(),
					gameScore.getCompTwoScore()
				)
			);
		}
		
		// Current Set Score
		if (currentMatchScore.getSetScore() != null) {
			SetScore setScore = currentMatchScore.getSetScore();
			scores.add(
				new Score(
					ScoreType.SET,
					setScore.getCompOneScore(),
					setScore.getCompTwoScore()
				)
			);
		}
		
		// Current Match Score
		if (currentMatchScore.getScoreInSets() != null) {
			SetScore currentScoreInSets = currentMatchScore.getScoreInSets();
			scores.add(
				new Score(
					ScoreType.MATCH,
					currentScoreInSets.getCompOneScore(),
					currentScoreInSets.getCompTwoScore()
				)
			);
		}
		
		// Decide on which Incident to send, priority being Match, Set and then Game Note must also ensure incident occurred
//		IncidentType type = currentMatchScore.isMatchComplete() ? IncidentType.MATCH_COMPLETE : currentMatchScore.getSetScore().isComplete() ? IncidentType.SET_COMPLETE : IncidentType.GAME_COMPLETE;
//		Incident incident = new Incident(tennis.getEventId(), msgReceivedTime, type, 0, scores);
//		incidents.add(incident);

		// Always send an incident of the point as well
		//		tennis.getMatchScoreHistory().getGameScore().get
		
		ArrayList<EventIncident> result = new ArrayList<EventIncident>();
		// DOUBT: creating one incident per score seems not to be correct
		for (int i = 1; i <= scores.size(); i++) {
			result.add(
				new EventIncident(
					tennis.getEventId(),
					i,
					null,
					scores.get(i - 1)
				)
			);
		}

		return result;
	}
	
	
	/**
	 * @return true if we are to process unconfirmed (except for corners)
	 * incidents from betgenius for speed reasons
	 * 
	 */
	public boolean isProcessUnconfirmedIncidents() {
		return processUnconfirmedIncidents;
	}

	/**
	 * Set processUnconfirmedIncidents to true if 
	 * unconfirmed incidents are to be processed
	 */
	public void setProcessUnconfirmedIncidents(boolean processUnconfirmedIncidents) {
		this.processUnconfirmedIncidents = processUnconfirmedIncidents;
	}
	
}
