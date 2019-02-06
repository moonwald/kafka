//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import com.aldogrand.kfc.msg.events.fetcher.SourceMarketsReceivedEvent;
import org.springframework.integration.annotation.Headers;
import org.springframework.messaging.MessageHeaders;

public class TestEventWriter {

    private MessageHeaders headers;
    private KFCEvent payload;

    public MessageHeaders getHeaders() {
        return headers;
    }

    public KFCEvent getPayload() {
        return payload;
    }

    public void write(@Headers MessageHeaders headers, SourceEventReceivedEvent event) {
        this.headers = headers;
        this.payload = event;
    }

    public void write(@Headers MessageHeaders headers, SourceMarketsReceivedEvent event) {
        this.headers = headers;
        this.payload = event;
    }

}
