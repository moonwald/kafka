package com.aldogrand.kfc.lmax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * 
 * <p>
 * <b>Title</b> LMAXServiceImpl.java
 * </p>
 * <p>
 * <b>Description</b> LMAXServiceImpl <br/>
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

public class LMAXServiceImpl {

	private final Logger		logger				= Logger.getLogger(getClass());
	
	private MessageChannel channel;
	private int numThreads;
	private int bufferSize = 1024;
	private Executor executor;
	private KFCEventFactory factory;
	private Disruptor<KFCLMAXEvent> disruptor;
	private RingBuffer<KFCLMAXEvent> ringBuffer;

	public LMAXServiceImpl(int numThreads, MessageChannel channel, int bufferSize){
		this.numThreads = numThreads;
		this.channel = channel;
		this.bufferSize = bufferSize;
		
		executor = Executors.newCachedThreadPool();
		factory = new KFCEventFactory();
		disruptor = new Disruptor(factory, bufferSize,executor, ProducerType.MULTI, new BlockingWaitStrategy());
		
		KFCEventHandler[] handlers = new KFCEventHandler[numThreads];
        for(int i = 0; i < numThreads; i++){
			handlers[i] = new KFCEventHandler(i,numThreads,channel);
		}

		disruptor.handleEventsWith(handlers);

		
		disruptor.start();
		
		ringBuffer = disruptor.getRingBuffer();
	}
	
	public void onData(Message<JsonNode> event)
    {
        long sequence = ringBuffer.next();  
        try
        {
        	//logger.info("onData key:"+ event.getHeaders().get("KEY")+ " T:"+Thread.currentThread().getName() );
        	KFCLMAXEvent lmaxEvent = ringBuffer.get(sequence); 
        	lmaxEvent.setEvent(event);
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }

	public MessageChannel getChannel() {
		return channel;
	}

	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}
	
	public int getNumThreads() {
		return numThreads;
	}

	public RingBuffer<KFCLMAXEvent> getRingBuffer() {
		return ringBuffer;
	}

	public int getBufferSize() {
		return bufferSize;
	}
	
	public void destroy(){
		disruptor.halt();
	}

}
