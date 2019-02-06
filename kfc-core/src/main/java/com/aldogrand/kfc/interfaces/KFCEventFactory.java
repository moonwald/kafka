package com.aldogrand.kfc.interfaces;

import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.msg.events.KFCEvent;
import org.springframework.messaging.Message;

/**
 * Created by aldogrand on 28/10/14.
 */
public interface KFCEventFactory {
    public Message createMessage(KFCEvent data) throws ProducerException;
}
