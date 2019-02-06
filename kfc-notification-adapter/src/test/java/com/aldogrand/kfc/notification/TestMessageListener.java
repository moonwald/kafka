package com.aldogrand.kfc.notification;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

/**
 * <p>
 * <b>Title</b> TestMessageListener.java
 * </p>
 * com.aldogrand.kfc.notification
 * <p>
 * <b>Description</b> kfc-notification-adapter.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author aldogrand
 * @version 1.0
 */
@Component
public class TestMessageListener implements MessageListener{

    private Message message;

    public void onMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
