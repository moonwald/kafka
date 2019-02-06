package com.aldogrand.kfc.integrationmodules.model;

import java.util.List;

import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.sbpc.connectors.model.Market;
import com.aldogrand.sbpc.connectors.model.Runner;

/**
 * Encapsulating all transformed data received from
 * Betgenius for event management
 * 
 * @author aldogrand
 *
 */
public class BettingTransformedData {

	private Event transformedCreateEvent;
	private Event transformedUpdateEvent;
	private List<Market> transformedCreateMarkets;
	private List<Market> transformedUpdateMarkets;
	List<Runner> transformedCreateRunners;
	List<Runner> transformedUpdateRunners;
	private Market transformedResultMarket;

	public BettingTransformedData(
			Event transformedCreateEvent,
			Event transformedUpdateEvent,
			List<Market> transformedCreateMarkets,
			List<Market> transformedUpdateMarkets,
			List<Runner> transformedCreateRunners,
			List<Runner> transformedUpdateRunners,
			Market transformedResultMarket) {
		this.transformedCreateEvent = transformedCreateEvent;
		this.transformedUpdateEvent = transformedUpdateEvent;
		this.transformedCreateMarkets = transformedCreateMarkets;
		this.transformedUpdateMarkets = transformedUpdateMarkets;
		this.transformedCreateRunners = transformedCreateRunners;
		this.transformedUpdateRunners = transformedUpdateRunners;
		this.transformedResultMarket = transformedResultMarket;
	}

	/**
	 * 
	 * @return new event from Betgenius which 
	 * needs to be created
	 */
	public Event getTransformedCreateEvent() {
		return transformedCreateEvent;
	}

	/**
	 * Sets the transformedCreateEvent
	 * 
	 * @param transformedCreateEvent
	 */
	public void setTransformedCreateEvent(Event transformedCreateEvent) {
		this.transformedCreateEvent = transformedCreateEvent;
	}

	/**
	 * 
	 * @return {@link Event} detail from Betgenius which 
	 * needs to be updated
	 */
	public Event getTransformedUpdateEvent() {
		return transformedUpdateEvent;
	}

	/**
	 * Sets the transformedUpdateEvent
	 * 
	 * @param transformedUpdateEvent
	 */
	public void setTransformedUpdateEvent(Event transformedUpdateEvent) {
		this.transformedUpdateEvent = transformedUpdateEvent;
	}

	/**
	 * 
	 * @return {@link Market} detail from Betgenius which 
	 * needs to be created
	 */
	public List<Market> getTransformedCreateMarkets() {
		return transformedCreateMarkets;
	}

	/**
	 * Sets the transformedCreateMarkets
	 * 
	 * @param transformedCreateMarkets
	 */
	public void setTransformedCreateMarkets(List<Market> transformedCreateMarkets) {
		this.transformedCreateMarkets = transformedCreateMarkets;
	}

	/**
	 * 
	 * @return {@link Market} detail from Betgenius which 
	 * needs to be updated
	 */
	public List<Market> getTransformedUpdateMarkets() {
		return transformedUpdateMarkets;
	}

	/**
	 * Sets the transformedUpdateMarkets
	 * 
	 * @param transformedUpdateMarkets
	 */
	public void setTransformedUpdateMarkets(List<Market> transformedUpdateMarkets) {
		this.transformedUpdateMarkets = transformedUpdateMarkets;
	}

	/**
	 * 
	 * @return {@link Runner} detail from Betgenius which 
	 * needs to be created
	 */
	public List<Runner> getTransformedCreateRunners() {
		return transformedCreateRunners;
	}

	/**
	 * Sets the transformedCreateRunners
	 * 
	 * @param transformedCreateRunners
	 */
	public void setTransformedCreateRunners(List<Runner> transformedCreateRunners) {
		this.transformedCreateRunners = transformedCreateRunners;
	}

	/**
	 * 
	 * @return {@link Runner} detail from Betgenius which 
	 * needs to be updated
	 */
	public List<Runner> getTransformedUpdateRunners() {
		return transformedUpdateRunners;
	}

	/**
	 * Sets the transformedUpdateRunners
	 * 
	 * @param transformedUpdateRunners
	 */
	public void setTransformedUpdateRunners(List<Runner> transformedUpdateRunners) {
		this.transformedUpdateRunners = transformedUpdateRunners;
	}
	
	/**
	 * 
	 * @return {@link Market} result data from Betgenius
	 * which needs to be updated
	 */
	public Market getTransformedResultMarket() {
		return transformedResultMarket;
	}

	/**
	 * Sets the transformedResultMarket
	 * 
	 * @param transformedResultMarket
	 */
	public void setTransformedResultMarket(Market transformedResultMarket) {
		this.transformedResultMarket = transformedResultMarket;
	}

}
