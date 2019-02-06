package com.aldogrand.kfc.producer;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.Producer;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.interfaces.KFCProducer;
import com.aldogrand.kfc.msg.events.KFCEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by aldogrand on 31/10/14.
 */
public class KFCProducerImpl implements KFCProducer {

    @Autowired
    private Producer producer;

    @Autowired
    private KFCEventFactory kFCEventFactory;

    public KFCProducerImpl () {}

    public void send (KFCEvent data) throws IOException, ProducerException {
        producer.send(kFCEventFactory.createMessage(data));
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public KFCEventFactory getKFCEventFactory() {
        return kFCEventFactory;
    }

    public void setKFCEventFactory(KFCEventFactory kFCEventFactory) {
        this.kFCEventFactory = kFCEventFactory;
    }
}
