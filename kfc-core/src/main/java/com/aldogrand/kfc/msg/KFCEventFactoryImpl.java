package com.aldogrand.kfc.msg;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.exception.ProducerException;
import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.events.KFCEvent;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aldogrand on 28/10/14.
 */
public class KFCEventFactoryImpl implements KFCEventFactory {

    private static final String CONTENT_TYPE = "contentType";

	public Message createMessage(KFCEvent event) throws ProducerException {
    	Map <String, Object> header = new HashMap <String, Object>();
        header.put(CONTENT_TYPE, event.getClass().getAnnotation(ContentType.class).value());
        Message <KFCEvent> message = new GenericMessage<KFCEvent>(event, header);

        return message;
    }
}
