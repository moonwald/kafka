package com.aldogrand.kfc.integrationmodules.betting.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aldogrand.sbpc.model.EventStatus;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.RunnerSide;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.MarketStatus;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.MatchPhase;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.ResultStatus;

/**
 * Base class for Betgenius Data transformations and constants
 * 
 * @author aldogrand
 *
 */
public class BettingTransformationBase {

	protected static final String SOCCER = "Soccer";
	protected static final String FOOTBALL = "Football";
	
	protected static final String PRODUCT_IN_RUNNING = "In Running";
	
	//This value is simply to fit correct score runners into the existing structure. Correct score runners do not have an outcome id in BetGenius.
	protected static final int CORRECT_SCORE = 100;
	
	protected static final int DRAW = 2;
	protected static final int AWAY = 3;
	protected static final int HOME = 1;
	protected static final int UNDER = 31;
	protected static final int OVER = 30;
	protected static final int PLAYER_ONE = 25;
	protected static final int PLAYER_TWO = 26;
	protected static final int YES = 39;
	protected static final int NO = 40;
	protected static final int HOME_HOME = 10;
	protected static final int HOME_DRAW = 11;
	protected static final int HOME_AWAY = 12;
	protected static final int DRAW_HOME = 13;
	protected static final int DRAW_DRAW = 14;
	protected static final int DRAW_AWAY = 15;
	protected static final int AWAY_HOME = 16;
	protected static final int AWAY_DRAW = 17;
	protected static final int AWAY_AWAY = 18;

	// TODO replace with an XML file which contains all the details
	// and which is loaded into memory once on startup
	protected static final Map<Integer, MarketType> MARKET_TYPES = new HashMap<Integer, MarketType>();
	static {
		// Football
		MARKET_TYPES.put(2, MarketType.THREE_WAY); // Match Result
		MARKET_TYPES.put(82, MarketType.ASIAN_HANDICAP); // Asian Handicap
		MARKET_TYPES.put(105, MarketType.HANDICAP); // Handicap
		MARKET_TYPES.put(259, MarketType.TOTAL); // Over/Under
		MARKET_TYPES.put(6832, MarketType.FIRST_HALF_RESULT); // Half-time result
		MARKET_TYPES.put(7591, MarketType.SECOND_HALF_RESULT); // 2nd half result
		MARKET_TYPES.put(7079, MarketType.BOTH_SCORE); // Both teams to score
		MARKET_TYPES.put(62, MarketType.CLEAN_SHEET); // Clean sheet
		MARKET_TYPES.put(59, MarketType.SCORE_BOTH_HALVES); // Score in both halves
		MARKET_TYPES.put(60, MarketType.WIN_BOTH_HALVES); // Win both halves
		MARKET_TYPES.put(7871, MarketType.ASIAN_HANDICAP_HALF_TIME); // Half-time asian
		MARKET_TYPES.put(7075, MarketType.HANDICAP_HALF_TIME); // Half-time handicap
		MARKET_TYPES.put(14, MarketType.ANYTIME_GOALSCORER); // Anytime goalscorer
		MARKET_TYPES.put(12, MarketType.FIRST_GOALSCORER); // First goalscorer
		MARKET_TYPES.put(122, MarketType.DRAW_NO_BET); // Draw no bet
		MARKET_TYPES.put(10616, MarketType.DRAW_NO_BET_HALF_TIME); // Half-time draw no bet
		MARKET_TYPES.put(7076, MarketType.TOTAL_HALF_TIME); // Half-time over/under
		MARKET_TYPES.put(3, MarketType.HALF_FULL_TIME); // Half-time/full-time
		MARKET_TYPES.put(295, MarketType.FIRST_TEAM_SCORER); // First team to score
		MARKET_TYPES.put(25, MarketType.HIGHEST_SCORE_HALF); // Highest scoring half
		MARKET_TYPES.put(7354, MarketType.ODD_EVEN_TOTAL); // Odd or Even Total
		MARKET_TYPES.put(169, MarketType.WIN_TO_NIL); // To win to nil
		MARKET_TYPES.put(7274, MarketType.WINNING_MARGIN); // Winning Margin
		MARKET_TYPES.put(91, MarketType.CORRECT_SCORE); //Correct Score
		
		// Tennis
		MARKET_TYPES.put(7169, MarketType.DOUBLES_HANDICAP); // Doubles game handicap
		MARKET_TYPES.put(6930, MarketType.DOUBLES_MATCH); // Doubles match
		MARKET_TYPES.put(7175, MarketType.DOUBLES_SET); // Doubles set betting
		MARKET_TYPES.put(7173, MarketType.DOUBLES_FIRST_SET); // Doubles 1st Set win
		MARKET_TYPES.put(77, MarketType.FIRST_SET); // First set winner
		MARKET_TYPES.put(311, MarketType.HANDICAP); // Game handicap
		MARKET_TYPES.put(322, MarketType.TWO_WAY); // Match
		MARKET_TYPES.put(120, MarketType.SET); // Set betting
		MARKET_TYPES.put(8660, MarketType.SET_HANDICAP); // Set handicap
		MARKET_TYPES.put(6481, MarketType.TOTAL_GAMES); // Total Games Over/Under
		MARKET_TYPES.put(7643, MarketType.TOTAL_FIRST_SET); // 1st Set total games
		MARKET_TYPES.put(6599, MarketType.SECOND_SET); // Second Set Winner
		MARKET_TYPES.put(7822, MarketType.ODD_EVEN_TOTAL); // Total games Odd/Even
		MARKET_TYPES.put(7171, MarketType.TOTAL); // Total Sets
	}

	protected static final List<Integer> FIRST_HALF_MARKETS = Arrays.asList(
			7871, 10849, 10459, 170, 7769, 10616, 9536, 7075, 10850, 9535,
			6832, 362, 7076);

	protected static final List<Integer> SECOND_HALF_MARKETS = Arrays.asList(
			7809, 7591);

	protected static final Map<Integer, String> FOOTBALL_MATCH_EVENTS = new HashMap<Integer, String>();
	static {
		FOOTBALL_MATCH_EVENTS.put(0, "HOME_GOAL");
		FOOTBALL_MATCH_EVENTS.put(1, "AWAY_GOAL");
		FOOTBALL_MATCH_EVENTS.put(2, "HOME_CORNER");
		FOOTBALL_MATCH_EVENTS.put(3, "AWAY_CORNER");
		FOOTBALL_MATCH_EVENTS.put(4, "HOME_YELLOW_CARD");
		FOOTBALL_MATCH_EVENTS.put(5, "AWAY_YELLOW_CARD");
		FOOTBALL_MATCH_EVENTS.put(6, "HOME_RED_CARD");
		FOOTBALL_MATCH_EVENTS.put(7, "AWAY_RED_CARD");
		FOOTBALL_MATCH_EVENTS.put(8, "HOME_SUB");
		FOOTBALL_MATCH_EVENTS.put(9, "AWAY_SUB");
	}

	/**
	 * Should have all statuses for different entities in one decomposed project which can be referenced by all others, i.e. one place to add new status
	 */
	protected static final Map<MarketStatus, com.aldogrand.sbpc.model.MarketStatus> MARKET_STATUS_MAP = new HashMap<MarketStatus, com.aldogrand.sbpc.model.MarketStatus>();
	static {
		MARKET_STATUS_MAP.put(MarketStatus.Held,
				com.aldogrand.sbpc.model.MarketStatus.SUSPENDED);
		MARKET_STATUS_MAP.put(MarketStatus.New,
				com.aldogrand.sbpc.model.MarketStatus.OPEN);
		MARKET_STATUS_MAP.put(MarketStatus.Open,
				com.aldogrand.sbpc.model.MarketStatus.OPEN);
		MARKET_STATUS_MAP.put(MarketStatus.Resulted,
				com.aldogrand.sbpc.model.MarketStatus.RESULTED);
		MARKET_STATUS_MAP.put(MarketStatus.Closed,
				com.aldogrand.sbpc.model.MarketStatus.CLOSED);
		MARKET_STATUS_MAP.put(MarketStatus.Suspended,
				com.aldogrand.sbpc.model.MarketStatus.SUSPENDED);
	}

	protected static final Map<ResultStatus, com.aldogrand.sbpc.model.ResultStatus> RESULT_STATUS_MAP = new HashMap<ResultStatus, com.aldogrand.sbpc.model.ResultStatus>();
	static {
		RESULT_STATUS_MAP.put(ResultStatus.Partial,
				com.aldogrand.sbpc.model.ResultStatus.UNKNOWN);
		RESULT_STATUS_MAP.put(ResultStatus.Pushed,
				com.aldogrand.sbpc.model.ResultStatus.UNKNOWN);
		RESULT_STATUS_MAP.put(ResultStatus.None,
				com.aldogrand.sbpc.model.ResultStatus.UNKNOWN);
		RESULT_STATUS_MAP.put(ResultStatus.Loser,
				com.aldogrand.sbpc.model.ResultStatus.LOSER);
		RESULT_STATUS_MAP.put(ResultStatus.Winner,
				com.aldogrand.sbpc.model.ResultStatus.WINNER);
		RESULT_STATUS_MAP.put(ResultStatus.Placed,
				com.aldogrand.sbpc.model.ResultStatus.PLACED);
	}

	protected static final Map<Integer, RunnerDetails> RUNNER_DETAILS_MAP = new HashMap<Integer, RunnerDetails>();
	static {
		RUNNER_DETAILS_MAP.put(HOME, new RunnerDetails(RunnerSide.HOME, RunnerType.PARTICIPANT, 1));
		RUNNER_DETAILS_MAP.put(PLAYER_ONE, new RunnerDetails(RunnerSide.HOME, RunnerType.PARTICIPANT, 1));
		RUNNER_DETAILS_MAP.put(DRAW, new RunnerDetails(RunnerSide.NA, RunnerType.DRAW, 3));
		RUNNER_DETAILS_MAP.put(AWAY, new RunnerDetails(RunnerSide.AWAY, RunnerType.PARTICIPANT, 2));
		RUNNER_DETAILS_MAP.put(PLAYER_TWO, new RunnerDetails(RunnerSide.AWAY, RunnerType.PARTICIPANT, 2));
		RUNNER_DETAILS_MAP.put(OVER, new RunnerDetails(RunnerSide.NA, RunnerType.OVER, 1));
		RUNNER_DETAILS_MAP.put(UNDER, new RunnerDetails(RunnerSide.NA, RunnerType.UNDER, 2));
		RUNNER_DETAILS_MAP.put(YES, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 1));
		RUNNER_DETAILS_MAP.put(NO, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 2));
		RUNNER_DETAILS_MAP.put(HOME_HOME, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 1));
		RUNNER_DETAILS_MAP.put(HOME_DRAW, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 2));
		RUNNER_DETAILS_MAP.put(HOME_AWAY, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 3));
		RUNNER_DETAILS_MAP.put(DRAW_HOME, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 4));
		RUNNER_DETAILS_MAP.put(DRAW_DRAW, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 5));
		RUNNER_DETAILS_MAP.put(DRAW_AWAY, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 6));
		RUNNER_DETAILS_MAP.put(AWAY_HOME, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 7));
		RUNNER_DETAILS_MAP.put(AWAY_DRAW, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 8));
		RUNNER_DETAILS_MAP.put(AWAY_AWAY, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 9));
		RUNNER_DETAILS_MAP.put(CORRECT_SCORE, new RunnerDetails(RunnerSide.NA, RunnerType.PARTICIPANT, 1));
	}
	
	
	protected static final Map<MatchPhase, EventStatus> EVENT_STATUS_MAP = new HashMap<MatchPhase, EventStatus>();
	static {
		EVENT_STATUS_MAP.put(MatchPhase.FirstHalf, EventStatus.FIRST_HALF);
		EVENT_STATUS_MAP.put(MatchPhase.HalfTime, EventStatus.HALF_TIME);
		EVENT_STATUS_MAP.put(MatchPhase.SecondHalf, EventStatus.SECOND_HALF);
		EVENT_STATUS_MAP.put(MatchPhase.PostMatch, EventStatus.CLOSED);
		EVENT_STATUS_MAP.put(MatchPhase.PreMatch, EventStatus.PRE_EVENT_LIVE);
		EVENT_STATUS_MAP.put(MatchPhase.BeforeExtraTime, EventStatus.BEFORE_EXTRA_TIME);
		EVENT_STATUS_MAP.put(MatchPhase.ExtraTimeFirstHalf, EventStatus.EXTRA_TIME_FIRST_HALF);
		EVENT_STATUS_MAP.put(MatchPhase.ExtraTimeHalfTime, EventStatus.EXTRA_TIME_HALF_TIME);
		EVENT_STATUS_MAP.put(MatchPhase.ExtraTimeSecondHalf, EventStatus.EXTRA_TIME_SECOND_HALF);
		EVENT_STATUS_MAP.put(MatchPhase.BeforePenaltyShootout, EventStatus.BEFORE_PENALTY_SHOOTOUT);
		EVENT_STATUS_MAP.put(MatchPhase.PenaltyShootout, EventStatus.PENALTY_SHOOTOUT);
		EVENT_STATUS_MAP.put(MatchPhase.MatchAbandoned, EventStatus.CLOSED);
	}

	protected static class RunnerDetails {

		public final RunnerSide side;
		public final RunnerType type;
		public final int sequence;

		public RunnerDetails(RunnerSide side, RunnerType type, int sequence) {
			this.side = side;
			this.type = type;
			this.sequence = sequence;
		}
	};

}
