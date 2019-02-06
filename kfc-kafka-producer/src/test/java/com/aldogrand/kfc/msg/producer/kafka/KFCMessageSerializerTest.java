package com.aldogrand.kfc.msg.producer.kafka;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.events.fetcher.SourceEventReceivedEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KFCMessageSerializerTest {

    @Mock
    KFCMessageSerializer kfcDecoder;
    @Mock
    KFCEventFactory kfcMessage;
    @Mock
    SourceEventReceivedEvent eventReceived;

    @Before
    public void before() throws Exception {}

    @After
    public void after() throws Exception {}

    @Test
    public void toBytesTest() throws IOException, ProducerException {
        //kfcDecoder.fromBytes(kfcEncoder.toBytes(kfcMessage.createMessage(eventReceived)));
        KFCMessageSerializer kfcEncoder = mock(KFCMessageSerializer.class);
        when(kfcEncoder.toBytes(kfcMessage.createMessage(eventReceived))).thenReturn(new byte[100]);
        kfcEncoder.toBytes(kfcMessage.createMessage(eventReceived));

        Mockito.verify(kfcEncoder).toBytes(kfcMessage.createMessage(eventReceived));
        //Mockito.verify(kfcDecoder).fromBytes(kfcEncoder.toBytes(kfcMessage.createMessage(eventReceived)));
    }
}
