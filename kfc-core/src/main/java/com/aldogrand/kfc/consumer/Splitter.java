//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.messaging.Message;

/**
 * <p>
 * <b>Title</b> Splitter.java
 * </p>
 * com.aldogrand.kfc.consumer
 * <p>
 * <b>Description</b>
 * <pre>
 * This class is to split the kafka messages produced by spring inbound-channel-adapter.
 * this class expects the output of inbound-channel-adapter is as follows.
 * Map<String, Map<Integer, List<Message>>>
 * 
 * It is a java.util.Map that contains the topic string consumed as the key and another Map as the value. 
 * The inner map's key is the stream (partition) number and value is a list of message payloads.
 * 
 *  1. Map of (key, value) with key as Partition and value as another map.
 *  	1.1. value = Map of (key, value) key as partition number and value as Message list.
 *  		1.1.1. value = list of messages 
 * </pre>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author aldogrand
 * @version 1.0
 */
public class Splitter {

    private static final Logger LOG = Logger.getLogger(Splitter.class);

    /**
     * Populate the list of messages with messages read by consumer from topic and partition combination.
     * 
     * @param kafkaMessage
     * @return
     */
    public List<Message> splitKafkaMessage(Map<String, Map<Integer, List<Message>>> kafkaMessage) {
        LOG.debug("Splitting Kafka message " + kafkaMessage);
//        LOG.info("Splitting Kafka message ");
        ArrayList<Message> messages = new ArrayList<Message>();
        extractPartitionMap(kafkaMessage, messages);
        return messages;
    }

    /**
     * Iterate over the values of Topic map and extract the partition map.
     * call {@link #extractMessageList(Map, List)} to get the list of messages. 
     * 
     * @param message Map of messages with key as topic name.
     * @param messages list of messages where all the messages will be added from topic
     */
    private void extractPartitionMap(Map<String, Map<Integer, List<Message>>> message, List<Message> messages) {
    	for (Map<Integer, List<Message>> value : message.values()) {
    		extractMessageList(value, messages);
    	}
    }
    
    /**
     * Extract the messages from the topic map and add them onto the list passed in as agrument.
     * 
     * @param message Map of messages with key as partition number
     * @param messages list of messages where all the messages will be added from partition
     */
    private void extractMessageList(Map<Integer, List<Message>> message , List<Message> messages) {
    	for (List<Message> value : message.values()) {
    		messages.addAll(value);
    	}
    }
}
