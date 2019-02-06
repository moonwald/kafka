package com.aldogrand.kfc.integrationmodules.betting.inplay.listener;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.aldogrand.kfc.integrationmodules.betting.BettingListener;

/**
 * Betgenius connector for all in-play feeds
 * 
 * @author aldogrand
 *
 */
public class BettingTennisMessageListener implements MessageListener {

	private final Logger logger = LogManager.getLogger(getClass());

	private BettingListener betgeniusInPlayConnector;

	@Override
	public void onMessage(Message message) {
		String inplayJson = new String(message.getBody());
		this.betgeniusInPlayConnector.onInPlayTennisMessage(inplayJson);
	}

	public BettingListener getBetgeniusInPlayConnector() {
		return betgeniusInPlayConnector;
	}

	public void setBetgeniusInPlayConnector(
			BettingListener betgeniusInPlayConnector) {
		this.betgeniusInPlayConnector = betgeniusInPlayConnector;
	}


}
