/**
 * 
 */
package com.aldogrand.kfc.msg.consumer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

import com.aldogrand.kfc.interfaces.KFCEventFactory;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * <p>
 * <b>Title</b> kfcKafkaConvertingMessageHandler.java
 * </p>
 * com.aldogrand.kfc.msg.consumer.kafka
 * <p>
 * <b>Description</b> kfc-kafka-producer.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author aldogrand
 * @version 1.0
 */
public class KFCKafkaConvertingMessageHandler extends KafkaConvertingMessageHandler<KFCEvent>
{
	private MessageChannel channel;
    
	@Autowired
    private KFCEventFactory KFCEventFactory;
	/* (non-Javadoc)
	 * @see com.aldogrand.messaging.kafka.KafkaConvertingMessageHandler#handleConvertedMessage(java.lang.String, java.lang.String, int, long, java.lang.Object)
	 */
	@Override
	protected void handleConvertedMessage( KFCEvent convertedMessage)
			throws MessagingException
	{
		channel.send(KFCEventFactory.createMessage(convertedMessage));
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
	/**
	 * @return the KFCEventFactory
	 */
	public KFCEventFactory getKFCEventFactory()
	{
		return KFCEventFactory;
	}
	/**
	 * @param KFCEventFactory the KFCEventFactory to set
	 */
	public void setKFCEventFactory(KFCEventFactory KFCEventFactory)
	{
		this.KFCEventFactory = KFCEventFactory;
	}

}
