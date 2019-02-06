package com.aldogrand.kfc.interfaces;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.msg.events.KFCEvent;

import java.io.IOException;

/**
 * Created by aldogrand on 31/10/14.
 */
public interface KFCProducer {
    public void send(KFCEvent data) throws IOException, ProducerException;
}
