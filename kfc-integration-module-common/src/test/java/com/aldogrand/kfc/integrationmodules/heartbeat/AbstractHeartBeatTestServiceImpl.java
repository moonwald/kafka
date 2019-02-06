package com.aldogrand.kfc.integrationmodules.heartbeat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.integrationmodules.IntegrationModuleServiceInfo;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * 
 * @author aldogrand
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={ "classpath:/kfc-integration-module-common-test.xml" })
public class AbstractHeartBeatTestServiceImpl {

	@Autowired
	HeartBeat heartBeat;
	
	@Autowired
	KFCProducer producer;
	
	@Autowired
	IntegrationModuleServiceInfo serviceInfo;
	
	@Test
	public void heartBeatServiceTest() throws Exception{
		
		final List<KFCEvent> events = new ArrayList<KFCEvent>();
		
		doAnswer(new Answer<Object>() {
	        public Object answer(InvocationOnMock invocation) {
	            Object[] args = invocation.getArguments();
	            events.add((KFCEvent) args[0]);
	            return "called with arguments: " + args;
	        }
	    }).when(producer).send(any(KFCEvent.class));
	
		//It is started through the application context
		//heartBeat.start(1000);
		Thread.sleep(2000);
		int size = events.size();
		assertTrue(size > 0);
				
		heartBeat.changeInterval(10);
		Thread.sleep(1000);
		size = events.size();
		assertTrue(size > 10);
		
		heartBeat.stop();
		size = events.size();
		Thread.sleep(1000);
		assertEquals(size, events.size());
		
		heartBeat.start(10);
		size = events.size();
		Thread.sleep(1000);
		assertTrue(events.size() > size);
		
	}
	
	@Test
	public void heartBeatServiceCorrectMsgSent() throws Exception{
		
		final List<KFCEvent> events = new ArrayList<KFCEvent>();
		
		doAnswer(new Answer<Object>() {
	        public Object answer(InvocationOnMock invocation) {
	            Object[] args = invocation.getArguments();
	            events.add((KFCEvent) args[0]);
	            return "called with arguments: " + args;
	        }
	    }).when(producer).send(any(KFCEvent.class));
	
		//It is started through the application context
		//heartBeat.start(1000);
		Thread.sleep(2000);

		assertEquals(events.get(0).getIntegrationModuleId(), serviceInfo.getIntegrationModuleId());
		assertEquals(events.get(0).getIntegrationModuleName(), serviceInfo.getIntegrationModuleName());
	}
}
