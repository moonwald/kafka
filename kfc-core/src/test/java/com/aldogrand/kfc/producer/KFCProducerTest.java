package com.aldogrand.kfc.producer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.Producer;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.EventContentType;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.commands.integrationmodules.Account;
import com.aldogrand.kfc.msg.commands.integrationmodules.Connection;
import com.aldogrand.kfc.msg.commands.integrationmodules.FetchEventsCommand;
import com.aldogrand.kfc.msg.commands.integrationmodules.Proxy;
import com.aldogrand.kfc.msg.commands.integrationmodules.RequestConnection;
import com.aldogrand.kfc.msg.commands.integrationmodules.filters.EventsFilter;

/**
 * Created by aldogrand on 04/11/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/kfc-core-context-test.xml"})
public class KFCProducerTest {

    @Autowired
    Producer producer;

    @Autowired
    KFCProducer KFCProducer;


    // Attributes
    private Event event;
    private SourceEventReceivedEvent eventReceived = new SourceEventReceivedEvent();

    // Attributes for the Event.
    private String eventId = "100";
    private String eventName = "Event name";
    private Date eventTime = new Date();

    @Before
    public void before() throws Exception {
        event = new Event();
        event.setId(eventId);
        event.setName(eventName);
        event.setStartTime(eventTime);
        eventReceived.setEvent(event);
    }

    @After
    public void after() throws Exception {}

    @Test
    public void KFCProducerContructorTest() throws ProducerException, IOException {
        KFCProducer KFCProducer = new KFCProducerImpl();
        Mockito.spy(KFCProducer);
    }

    // Tests the KFCEventFactory and KFCProducer.
    @Test
    public void KFCProducerTest() throws Exception {
        // Checks that the send method of the interface KFCProducer is invoked.
        KFCProducer.send(eventReceived);
    }

/*    @Test(expected = ProducerException.class)
    public void KFCProducerExceptionTest () throws IOException, ProducerException {
        // Lets make the KFCProducer fail miserably ..... and get the ProducerException.
        eventReceived = null;
        KFCProducer.send(eventReceived);
    }*/
    
    @Test
    public void sendFetchEventsCommand() throws Exception {

    	MBConnection connection = new MBConnection("mb","pass",null);
    	MBFilter filter = new MBFilter();
    	MBFetchEventsCommand command = new MBFetchEventsCommand(connection, null, filter);
    	
    	final List<Message> m = new ArrayList<Message>();
    	
    	doAnswer(new Answer<Object>() {
	        public Object answer(InvocationOnMock invocation) {
	            Object[] args = invocation.getArguments();
	            m.add((Message) args[0]);
	            return "called with arguments: " + args;
	        }
	    }).when(producer).send(any(Message.class));
    	
    	KFCProducer.send(command);
    	
    	assertTrue(m.get(0).getPayload() instanceof MBFetchEventsCommand);
    
    }
    
    
}

@ContentType(EventContentType.FETCH_EVENTS_COMMAND)
class MBFetchEventsCommand extends FetchEventsCommand{

	public MBFetchEventsCommand(MBConnection connection, Account account, MBFilter filter) {
		super(connection, account, filter);
	}
}

class MBConnection extends RequestConnection{

	public MBConnection(String username, String sessionToken, Proxy proxy) {
		super(username, sessionToken, proxy);
	}
}

class MBFilter extends EventsFilter{

	public MBFilter() {
	}
}