package com.aldogrand.kfc.lmax;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.lmax.disruptor.EventHandler;

/**
 * 
 * <p>
 * <b>Title</b> KFCEventHandler.java
 * </p>
 * <p>
 * <b>Description</b> KFCEventHandler <br/>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */

public class KFCEventHandler implements EventHandler<KFCLMAXEvent> {
	
	private final Logger logger = LogManager.getLogger(getClass());

	int numConsumers;
	int consumerNum;
	MessageChannel channel;
	
	KFCEventHandler(int consumerNum, int numConsumers, MessageChannel channel){
		this.numConsumers = numConsumers;
		this.consumerNum = consumerNum;
		this.channel = channel;
	}

	@Override
	public void onEvent(KFCLMAXEvent event, long sequence, boolean endOfBatch) throws Exception {
		Message<JsonNode> ev = (Message<JsonNode>) event.getEvent();
		int key = ((String) ev.getHeaders().get("KEY")).hashCode();
		key = Math.abs(key);
		
		if ( key % numConsumers == consumerNum){
			logger.debug("contentType:"+ev.getHeaders().get("contentType")+",seq:"+sequence + ",consumer:"+consumerNum+ ",key:"+((Message<JsonNode>) event.getEvent()).getHeaders().get("KEY")+   ",thread:"+Thread.currentThread().getName() );
			try{
				channel.send(event.getEvent());
			}catch(Exception e){
				logger.error("h:"+((Message<JsonNode>) event.getEvent()).getHeaders().get("KEY")+"T:"+Thread.currentThread().getName()+ "  " +e);
			}
		}
	}

}
