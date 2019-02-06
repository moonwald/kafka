package com.aldogrand.kfc.lmax;

import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.lmax.disruptor.RingBuffer;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * 
 * <p>
 * <b>Title</b> KFCEventProducer.java
 * </p>
 * <p>
 * <b>Description</b> KFCEventProducer <br/>
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

public class KFCEventProducer {
	
	    private final RingBuffer<KFCLMAXEvent> ringBuffer;

	    public KFCEventProducer(RingBuffer<KFCLMAXEvent> ringBuffer)
	    {
	        this.ringBuffer = ringBuffer;
	    }

	    public void onData(Message<JsonNode> event)
	    {
	        long sequence = ringBuffer.next();  
	        try
	        {
	        	KFCLMAXEvent lmaxEvent = ringBuffer.get(sequence); 
	        	lmaxEvent.setEvent(event);
	        }
	        finally
	        {
	            ringBuffer.publish(sequence);
	        }
	    }
	
}
