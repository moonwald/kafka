//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.*;


/**
 * Test JSON parsing performance on small file.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-mysql-jsontransformer-test.xml"})
public class SplitterTest {

    private Splitter splitter;

    @Before
    public void before() {
        splitter = new Splitter();
    }

//    @Test(expected = IllegalArgumentException.class)
    public void unknown() {
        Properties data = new Properties();
        data.put("1", new Object());
//        splitter.splitKafkaMessage(data);
    }

    @Test(expected = NullPointerException.class)
    public void nullMessage() {
        List<Message> messages = splitter.splitKafkaMessage(null);
    }

    @Test
    public void expected() throws IOException {
        Map topics = new HashMap();
        Map partitions = new HashMap();
        topics.put("test", partitions);
        List messages = new ArrayList();
        partitions.put("1", messages);
        messages.add(MessageBuilder.withPayload("ABC").build());
        messages.add(MessageBuilder.withPayload("DEF").build());

        List<Message> extractedMessages = splitter.splitKafkaMessage(topics);
        assertThat(extractedMessages, IsIterableContainingInAnyOrder.<Message> containsInAnyOrder(
            hasProperty("payload", is("ABC")), hasProperty("payload", is("DEF"))));
    }

}

