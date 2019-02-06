package com.aldogrand.kfc.interfaces;

import com.aldogrand.kfc.exception.ProducerException;
import org.springframework.messaging.Message;
import java.io.IOException;

/**
 * 
 * @author aldogrand
 *
 */

public interface Producer {

	public void send(Message<?> message) throws ProducerException, IOException;
    public void send (Message<?> message, String topic, String key) throws ProducerException, IOException;
}
