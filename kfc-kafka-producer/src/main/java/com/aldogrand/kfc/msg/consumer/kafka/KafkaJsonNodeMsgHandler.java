package com.aldogrand.kfc.msg.consumer.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

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
public class KafkaJsonNodeMsgHandler<T> implements MessageHandler
{
    protected final Logger logger = LogManager.getLogger(getClass());
    
    private MessageChannel channel;
    

    /* (non-Javadoc)
     * @see org.springframework.messaging.MessageHandler#handleMessage(org.springframework.messaging.Message)
     */
    
	
    public void handleMessage(Message<?> message) throws MessagingException
    {
        logger.debug("Handling {}.", message);
        channel.send((Message<JsonNode>) message);
    }


	/**
	 * @return the channel
	 */
	public MessageChannel getChannel()
	{
		return channel;
	}


	/**
	 * @param channel the channel to set
	 */
	public void setChannel(MessageChannel channel)
	{
		this.channel = channel;
	}
}
