package com.aldogrand.kfc.integrationmodules.betting.services;

import java.util.List;

import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Updategram;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchStateFootball;
import com.aldogrand.kfc.integrationmodules.betting.model.tennis.MatchStateTennis;
import com.aldogrand.kfc.integrationmodules.model.BettingTransformedData;

/**
 * Builds internal connector model representation of received Betgenius
 * data
 * 
 * @author aldogrand
 *
 */
public interface BettingTransformationService {

	/**
	 * Transform the Betgenius event data format into the internal sbpc model
	 * and trigger processing
	 * 
	 * @param updateGram
	 */
	public BettingTransformedData transformEvent(Updategram updateGram);
	
	/**
	 * Transform the Betgenius football Match State format into the internal sbpc model
	 * and trigger processing
	 * 
	 * @param footbal
	 * @return
	 */
	public List<EventIncident> transformMatchState(MatchStateFootball footbal);
	
	/**
	 * Transform the Betgenius tennis Match State format into the internal sbpc model
	 * and trigger processing
	 * 
	 * @param tennis
	 * @return
	 */
	public List<EventIncident> transformMatchState(MatchStateTennis tennis);
	
}