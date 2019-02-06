package com.aldogrand.kfc.integrationmodules.betting;


/**
 * Implementation of {@link Connector} that interfaces with Betgenius Event
 * Management Feed
 * 
 * @author aldogrand
 * 
 */
public class BettingEventMessageListener  {

	/*protected final Logger logger = Logger.getLogger(getClass());
	
	private BetgeniusListener betgeniusConnector;
	
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			logger.debug("Received Event Management Update from Betgenius");
			String msgText;
			try {
				msgText = ((TextMessage) message).getText();
				
				this.betgeniusConnector.onEventManagementMessage(msgText);
			} catch (JMSException e) {
				LogSF.error(
						logger,
						"JMSException parsing text from Message : {}, Exception : {}",
						ArrayUtils.toArray(message, e.getMessage()));
			}
		} else {
			LogSF.warn(
					logger,
					"Invalid message type received from betgenius for Event Management : {}",
					message);
		}

	}

	public BetgeniusListener getBetgeniusConnector() {
		return betgeniusConnector;
	}

	public void setBetgeniusConnector(BetgeniusListener betgeniusConnector) {
		this.betgeniusConnector = betgeniusConnector;
	}
	*/
}
