package com.aldogrand.kfc.integrationmodules.betting.inplay.listener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.aldogrand.kfc.integrationmodules.betting.BettingListener;

/**
 * betting connector for all in-play feeds
 * 
 * @author aldogrand
 *
 */
public class BettingFootballMessageListener implements MessageListener {

	private final Logger logger = LogManager.getLogger(getClass());

	private BettingListener bettingInPlayConnector;

	@Override
	public void onMessage(Message message) {
	  logger.debug("Football InPlay received: " + message.toString());
      String inplayJson = new String(message.getBody());
      this.bettingInPlayConnector.onInPlayFootballMessage(inplayJson);
	}

	public BettingListener getBettingInPlayConnector() {
		return bettingInPlayConnector;
	}

	public void setBettingInPlayConnector(
			BettingListener betgeniusInPlayConnector) {
		this.bettingInPlayConnector = betgeniusInPlayConnector;
	}
}
