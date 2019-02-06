package com.aldogrand.kfc.integrationmodules.betting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.sbpc.connectors.model.Market;
import com.aldogrand.sbpc.connectors.model.Runner;
import com.aldogrand.kfc.integrationmodules.IntegrationModuleServiceInfo;
import com.aldogrand.kfc.integrationmodules.betting.msg.events.BettingUpdategramReceivedEvent;
import com.aldogrand.kfc.integrationmodules.betting.services.BettingTransformationService;
import com.aldogrand.kfc.integrationmodules.model.BettingTransformedData;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceMarketsReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceRunnersReceivedEvent;

/**
 * Implementation of  interfaces with Betgenius Event
 * Management Feeds
 * 
 * @author aldogrand, aldogrand
 * 
 *
 */
public class BettingConnector {

	private final Logger	logger	= Logger.getLogger(getClass());

	private BettingTransformationService betgeniusTransformationService;
	
	private boolean enabled;
	
	@Autowired
	@Qualifier("errorChannel")
	private MessageChannel	errorChannel;

	@Autowired
	private KFCProducer kafkaProducer;
	
	@Autowired
	private IntegrationModuleServiceInfo integrationModuleServiceInfo;

	/**
	 * Process the message received from Betgenius PreMatch end-point
	 * @param headers Header of the Message
	 * @param updateGram Payload of the Message
	 * @throws Exception
	 */
	public void process(@Headers MessageHeaders headers, @Payload BettingUpdategramReceivedEvent updateGram) throws Exception{		
		if (!enabled) {
			logger.debug("Betgenius Connector is not enabled");
			return;
		}
		try {
			BettingTransformedData transformedData = this.betgeniusTransformationService
					.transformEvent(updateGram.getUpdategram());
			logger.debug("Notifying listeners of event update.");
			sendToKafka(transformedData);
		
		} catch (Exception e) {
			
		}
	}	

	/**
	 * sendToKafka for create/update of data received from
	 * Betgenius
	 * 
	 * @param transformedData
	 *            Transformed Betgenius data
	 * @throws DataUpdateException
	 */
	public void sendToKafka(BettingTransformedData transformedData)
			throws Exception {
		// Create Event
		if (transformedData.getTransformedCreateEvent() != null) {
			sendSourceEvent(transformedData.getTransformedCreateEvent());
		}

		// Update Event
		if (transformedData.getTransformedUpdateEvent() != null) {
			sendSourceEvent(transformedData.getTransformedUpdateEvent());
		}

		// Create Markets
		if (CollectionUtils.isNotEmpty(transformedData
				.getTransformedCreateMarkets())) {
			sendSourceMarkets(transformedData.getTransformedCreateMarkets());

		}

		// Update Markets
		if (CollectionUtils.isNotEmpty(transformedData
				.getTransformedUpdateMarkets())) {
			sendSourceMarkets(transformedData.getTransformedUpdateMarkets());
		}

		// Create Runners
		if (CollectionUtils.isNotEmpty(transformedData
				.getTransformedCreateRunners())) {
			for (Runner runner : transformedData.getTransformedCreateRunners()) {
				sendSourceRunners(runner);
			}
		}

		// Update Runners
		if (CollectionUtils.isNotEmpty(transformedData
				.getTransformedUpdateRunners())) {
			for (Runner runner : transformedData.getTransformedUpdateRunners()) {
				sendSourceRunners(runner);
			}
		}

		// Add update of prices if we decide it is needed in the future

		// Update result
		if (transformedData.getTransformedResultMarket() != null) {
			List<Market> markets = new ArrayList<Market>();
			markets.add(transformedData.getTransformedResultMarket());
			sendSourceMarkets(markets);
		}
	}

	/**
	 * Send source runner event
	 * 
	 * @param runner Runner data
	 * @throws IOException
	 */
	private void sendSourceRunners(Runner runner) throws IOException {
		List<Runner> runners = new ArrayList<Runner>();
		runners.add(runner);
		SourceRunnersReceivedEvent sourceRunner = new SourceRunnersReceivedEvent();
		sourceRunner.setRunners(runners);
		sourceRunner.setIntegrationModuleId(getIntegrationModuleId());
		sourceRunner.setIntegrationModuleName(getIntegrationModuleName());
		kafkaProducer.send(sourceRunner);
	}
	
	/**
	 * Send source markets
	 * 
	 * @param markets List of markets
	 * @throws IOException
	 */
	private void sendSourceMarkets(List<Market> markets)
			throws IOException {
		SourceMarketsReceivedEvent sourceMarkets = new SourceMarketsReceivedEvent();
		sourceMarkets.setMarkets(markets);
		sourceMarkets.setIntegrationModuleId(getIntegrationModuleId());
		sourceMarkets.setIntegrationModuleName(getIntegrationModuleName());
		kafkaProducer.send(sourceMarkets);
	}

	/**
	 * Send source event
	 * 
	 * @param event Event data
	 * @throws IOException
	 */
	private void sendSourceEvent(Event event)
			throws IOException {
		SourceEventReceivedEvent sourceEvent = new SourceEventReceivedEvent();
		sourceEvent.setEvent(event);
		sourceEvent.setIntegrationModuleId(getIntegrationModuleId());
		sourceEvent.setIntegrationModuleName(getIntegrationModuleName());
		
		kafkaProducer.send(sourceEvent);
	}

	private String getIntegrationModuleName() {
		return integrationModuleServiceInfo.getIntegrationModuleName();
	}

	private String getIntegrationModuleId() {
		return integrationModuleServiceInfo.getIntegrationModuleId();
	}
		
	/**
	 * Notify all registered listeners of the {@link EventIncident}
	 * 
	 * @param transformedFootballIncidents
	 */
	private void notifyListenersFootballIncidents(
			List<EventIncident> transformedFootballIncidents) {
		if (CollectionUtils.isNotEmpty(transformedFootballIncidents)) {
			
		}
	}
	

	/**
	 * Retrieves the service for transforming Betgenius data to internal model
	 * 
	 * @return
	 */
	public BettingTransformationService getBetgeniusTransformationService() {
		return betgeniusTransformationService;
	}

	/**
	 * Sets the service for transforming Betgenius data to internal model
	 * 
	 * @param betgeniusMappingService
	 */
	public void setBetgeniusTransformationService(
			BettingTransformationService betgeniusTransformationService) {
		this.betgeniusTransformationService = betgeniusTransformationService;
	}

	/**
	 * @return true if connector is enabled to recieve Betgenius data
	 * 
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Set enabled to true if connector to receive Betgenius data
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	public KFCProducer getKafkaProducer() {
		return kafkaProducer;
	}

	public void setKafkaProducer(KFCProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	public IntegrationModuleServiceInfo getIntegrationModuleServiceInfo() {
		return integrationModuleServiceInfo;
	}

	public void setIntegrationModuleServiceInfo(
			IntegrationModuleServiceInfo integrationModuleServiceInfo) {
		this.integrationModuleServiceInfo = integrationModuleServiceInfo;
	}
}
