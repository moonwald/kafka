package com.aldogrand.kfc.integrationmodules.betting.inplay;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.aldogrand.sbpc.connectors.model.EventIncident;
import com.aldogrand.kfc.integrationmodules.IntegrationModuleServiceInfo;
import com.aldogrand.kfc.integrationmodules.betting.BettingListener;
import com.aldogrand.kfc.integrationmodules.betting.model.eventdata.Updategram;
import com.aldogrand.kfc.integrationmodules.betting.model.football.MatchStateFootball;
import com.aldogrand.kfc.integrationmodules.betting.model.tennis.MatchStateTennis;
import com.aldogrand.kfc.integrationmodules.betting.services.BettingTransformationService;
import com.aldogrand.kfc.integrationmodules.model.BettingTransformedData;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.fetcher.EventIncidentReceivedEvent;

public class BettingInPlayConnector implements BettingListener {

	private static String BETTING_CONNECTOR_NOT_ENABLED = "Betting InPlay Connector is not enabled";

	private Logger logger = LogManager.getLogger(getClass());

	private JAXBContext jaxbContext;

	private BettingTransformationService bettingTransformationService;

	private boolean enabled;
	
	private IntegrationModuleServiceInfo integrationModuleServiceInfo;

	@Autowired
	private KFCProducer kafkaProducer;
	
	@Override
	public void onEventManagementMessage(String message) {
//		if (!enabled) {
//			logger.debug(BETTING_CONNECTOR_NOT_ENABLED);
//			return;
//		}
//		
//		try {
//			Unmarshaller unmarshaller = getUnmarshaller(Updategram.class);
//			Updategram updateGram = (Updategram) unmarshaller
//					.unmarshal(new StringReader(message));
//
//			BettingTransformedData transformedData = this.bettingTransformationService
//					.transformEvent(updateGram);
//			logger.debug("Notifying listeners of event update");
//			
//		} catch (JAXBException e) {
//			logger.error(String.format("JAXBException parsing message : %s, Exception : %s", message, e.getMessage()));
//		} 
	}

	@Override
	public void onInPlayFootballMessage(String message) {
		if (!enabled) {
			logger.debug(BETTING_CONNECTOR_NOT_ENABLED);
			return;
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			MatchStateFootball matchState = mapper.readValue(message, MatchStateFootball.class);

			// Transform the betting in-play feed
			List<EventIncident> transformedFootballIncidents = this.bettingTransformationService
					.transformMatchState(matchState);
			
			// send to kafka for each EventIncident
			sendToKafka(transformedFootballIncidents, message);
			
		} catch (JsonParseException e) {
			logger.error(String.format("JsonParseException parsing message : %s, Exception : %s", message, e.getMessage()));			
		} catch (JsonMappingException e) {
			logger.error(String.format("JsonMappingException parsing message : %s, Exception : %s", message, e.getMessage()));			
		} catch (IOException e) {
			logger.error(String.format("IOException parsing message : %s, Exception : %s", message, e.getMessage()));
		}
	}

	@Override
	public void onInPlayTennisMessage(String message) {
		// TODO Auto-generated method stub
		if (!enabled) {
			logger.debug(BETTING_CONNECTOR_NOT_ENABLED);
			return;
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			MatchStateTennis matchState = mapper.readValue(message, MatchStateTennis.class);

			// Transform the BETTING in-play feed
			List<EventIncident> transformedTennisIncidents = this.bettingTransformationService
					.transformMatchState(matchState);
			
			// send to kafka for each EventIncident
			sendToKafka(transformedTennisIncidents, message);
			
		} catch (JsonParseException e) {
			logger.error(String.format("JsonParseException parsing message : %s, Exception : %s", message, e.getMessage()));			
		} catch (JsonMappingException e) {
			logger.error(String.format("JsonMappingException parsing message : %s, Exception : %s", message, e.getMessage()));			
		} catch (IOException e) {
			logger.error(String.format("IOException parsing message : %s, Exception : %s", message, e.getMessage()));
		}
	}
	
	/**
	 * Send a list of event incidents to kafka topic.
	 * 
	 * @param eventIncidents List of event incidents
	 * @param message Original in-play message
	 */
	private void sendToKafka(List<EventIncident> eventIncidents, String message) {
		try {
			for (final EventIncident eventIncident : eventIncidents) {
				EventIncidentReceivedEvent eventIncidentReceived = new EventIncidentReceivedEvent();
				eventIncidentReceived.setIntegrationModuleId(integrationModuleServiceInfo.getIntegrationModuleId());
				eventIncidentReceived.setIntegrationModuleName(integrationModuleServiceInfo.getIntegrationModuleName());
				eventIncidentReceived.setEventIncident(eventIncident);
				kafkaProducer.send(eventIncidentReceived);
			}
		} catch (IOException e) {
			logger.error(String.format("IOException parsing message : %s, Exception : %s", message, e.getMessage()));
		}	
	}
	
	private Unmarshaller getUnmarshaller(Class<?>... clazz)
			throws JAXBException {
		if (jaxbContext == null) {
			synchronized (this) {
				if (jaxbContext == null) {
					jaxbContext = JAXBContext.newInstance(clazz);
				}
			}
		}
		// Create a new unmarshaller as they are not thread safe.
		return jaxbContext.createUnmarshaller();
	}

	public BettingTransformationService getBettingTransformationService() {
		return bettingTransformationService;
	}

	public void setBettingTransformationService(
			BettingTransformationService bettingTransformationService) {
		this.bettingTransformationService = bettingTransformationService;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public IntegrationModuleServiceInfo getIntegrationModuleServiceInfo() {
		return integrationModuleServiceInfo;
	}

	public void setIntegrationModuleServiceInfo(
			IntegrationModuleServiceInfo integrationModuleServiceInfo) {
		this.integrationModuleServiceInfo = integrationModuleServiceInfo;
	}

	public KFCProducer getKafkaProducer() {
		return kafkaProducer;
	}

	public void setKafkaProducer(KFCProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

}
