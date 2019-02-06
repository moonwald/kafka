package com.aldogrand.kfc.lmax;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.POJONode;
import org.minnal.autopojo.AutoPojoFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.aldogrand.kfc.lmax.KFCEventFactory;
import com.aldogrand.kfc.lmax.KFCEventHandler;
import com.aldogrand.kfc.lmax.KFCEventProducer;
import com.aldogrand.kfc.lmax.KFCLMAXEvent;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.fetcher.PricesReceivedEvent;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class LMAXTest {

	public static void main(String[] args) {
		// Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // The factory for the event
        KFCEventFactory factory = new KFCEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        //Disruptor<KFCLMAXEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
        //Disruptor<KFCLMAXEvent> disruptor = new Disruptor<KFCLMAXEvent>(factory,bufferSize, executor, ProducerType.MULTI, new BusySpinWaitStrategy());
        Disruptor<KFCLMAXEvent> disruptor = new Disruptor(factory, bufferSize,executor, ProducerType.SINGLE, new BlockingWaitStrategy());
        
        MessageChannel channel = mock(MessageChannel.class);
        doAnswer(new Answer<Object>() {
	        public Object answer(InvocationOnMock invocation) {
	        	Message<JsonNode> j = (Message<JsonNode>) invocation.getArguments()[0];
	        	int key = (Integer.valueOf((String) j.getHeaders().get("KEY")));
	            System.out.println("Go in with key:"+key+"  T:"+Thread.currentThread().getName());
	            return true;
	        }
	    }).when(channel).send(any(Message.class));
        
        // Connect the handler
        int numT = 7;
      
        KFCEventHandler[] handlers = new KFCEventHandler[numT];
        for(int i = 0; i < numT; i++){
			handlers[i] = new KFCEventHandler(i,numT,channel);
		}
		
		disruptor.handleEventsWith(handlers);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<KFCLMAXEvent> ringBuffer = disruptor.getRingBuffer();

        KFCEventProducer producer = new KFCEventProducer(ringBuffer);

        AutoPojoFactory f = new AutoPojoFactory();
        
        
        for (int l = 0; l < 10000; l++)
        { 
        	PricesReceivedEvent price = f.populate(PricesReceivedEvent.class);
        	ObjectMapper objMapper = new ObjectMapper();
        	objMapper.getJsonFactory();
        	JsonNode node = objMapper.convertValue(price, JsonNode.class);
        	Message<JsonNode> m = MessageBuilder.withPayload(node)
            .setHeader("KEY", String.valueOf(l))  
            .build();
            producer.onData(m);
           /* try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			
			}*/
        }
        
        try {
			Thread.sleep(1000);
			disruptor.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
	}

}
