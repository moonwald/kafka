package com.aldogrand.kfc.msg.interfaces;

import org.springframework.messaging.Message;

/**
 * <p>
 * <b>Title</b> TopicGenerator Interface
 * </p>
 * <p>
 * <b>Description</b> Topic Generator for Kafka Interface.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public interface TopicGenerator {

    /**
     * Generates the kafka topic<br/>
     * @param event
     * @return java.lang.String
     */
	public String generateTopic(Message<?> event);
}
