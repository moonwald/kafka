package com.aldogrand.kfc.integrationmodules.betting.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.Producer;

/**
 * Service to send message to kafka
 * 
 * <p>
 * <b>Title</b> BetgeniusKafkaSenderService
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2012
 * </p>
 * @author Aldo Grand
 *
 */
public class BettingKafkaSenderService {

	private final Logger logger = LogManager.getLogger(getClass());
	
    @Autowired
    private Producer producer;
        
    private String betgeniusContentType;

	public void sendContent(String content, String topic, String key) {
		try {
			Map <String, Object> header = new HashMap <String, Object>();
	        header.put("topic", topic);
	        header.put("key", key);
	        header.put("contentType", betgeniusContentType);
			Message<String> message = new GenericMessage<String>(content, header);
			
			producer.send(message, topic, key);			
		} catch (ProducerException pe) {
			logger.error(String.format("Error sending Betgenius kafka-message %s", content), pe);			
			
		} catch (IOException e) {
			logger.error(String.format("Error sending Betgenius kafka-message %s", content), e);			
		}	
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public String getBetgeniusContentType() {
		return betgeniusContentType;
	}

	public void setBetgeniusContentType(String betgeniusContentType) {
		this.betgeniusContentType = betgeniusContentType;
	}
}
