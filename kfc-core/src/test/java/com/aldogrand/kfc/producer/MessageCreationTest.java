package com.aldogrand.kfc.producer;

import com.aldogrand.sbpc.connectors.model.Event;
import com.aldogrand.sbpc.connectors.model.Market;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.KFCEventFactoryImpl;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceMarketsReceivedEvent;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.Message;

import java.util.Date;

/**
 * Created by aldogrand on 04/11/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageCreationTest {

    // Attributes
    private Event event;
    private Market market;
    private SourceEventReceivedEvent eventReceived = new SourceEventReceivedEvent();
    private SourceMarketsReceivedEvent marketReceived = new SourceMarketsReceivedEvent();
    private KFCEventFactory KFCEventFactory = new KFCEventFactoryImpl();

    @Before
    public void before() throws Exception {
        event = new Event();
        event.setId("100");
        event.setName("eventname");
        event.setStartTime(new Date());
        eventReceived.setEvent(event);

/*        market = new Market();
        market.setId("101");
        market.setName("marketName");
        market.setType(MarketType.BOTH_SCORE);
        marketReceived.setMarkets(new ArrayList<Market>().add(market));*/
    }

    @After
    public void after() throws Exception {}

    // Tests the KFCEventFactory.
    @Test
    public void kfcContentTypeMap () throws ProducerException {

        Message message =  KFCEventFactory.createMessage(eventReceived);
        Assert.assertEquals(message.getHeaders().get("contentType").toString(), "SOURCE_EVENT_RECEIVED");
/*
        message =  KFCEventFactory.createMessage(marketReceived);
        Assert.assertEquals(message.getHeaders().get("contentType").toString(), "MARKET_RECEIVED");*/
    }
}