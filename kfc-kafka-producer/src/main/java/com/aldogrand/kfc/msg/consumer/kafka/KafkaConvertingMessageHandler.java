package com.aldogrand.kfc.msg.consumer.kafka;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConverter;


import com.aldogrand.kfc.consumer.JSONToKFCEventTransformer;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> KafkaConvertingMessageHandler
 * </p>
 * <p>
 * <b>Description</b> A {@link MessageHandler} that converts the message to its original application type
 * and also provides information about the Kafka topic that the message was consumed from.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * @author Aldo Grand
 * @version 1.0
 */
public abstract class KafkaConvertingMessageHandler<T> implements MessageHandler
{
    protected final Logger logger = LogManager.getLogger(getClass());
    
    private MessageConverter messageConverter;
    
    private JSONToKFCEventTransformer transformer;
    
    /**
     * @return the messageConverter
     */
    public MessageConverter getMessageConverter()
    {
        return messageConverter;
    }

    /**
     * @param messageConverter the messageConverter to set
     */
    public void setMessageConverter(MessageConverter messageConverter)
    {
        this.messageConverter = messageConverter;
    }

    /* (non-Javadoc)
     * @see org.springframework.messaging.MessageHandler#handleMessage(org.springframework.messaging.Message)
     */
    
	@Override
    public void handleMessage(Message<?> message) throws MessagingException
    {
        logger.debug("Handling {}.", message);


        try
		{
			handleConvertedMessage(transformer.transform((Message<JsonNode>) message));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    /**
     * Handle the converted message
     * @param topicName
     * @param partitionKey
     * @param partitionNumber
     * @param sequenceNumber
     * @param convertedMessage
     * @throws MessagingException
     */
    protected abstract void handleConvertedMessage(KFCEvent convertedMessage) throws MessagingException;

	
	/**
	 * @return the transformer
	 */
	public JSONToKFCEventTransformer getTransformer()
	{
		return transformer;
	}

	
	/**
	 * @param transformer the transformer to set
	 */
	public void setTransformer(JSONToKFCEventTransformer transformer)
	{
		this.transformer = transformer;
	}
}
