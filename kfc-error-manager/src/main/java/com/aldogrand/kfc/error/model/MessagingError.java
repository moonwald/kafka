//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.error.model;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.msg.events.KFCErrorEvent;

/**
 * Error entity.
 */
@Document(collection="messagingErrors")
public class MessagingError {

    @Id    
    private ObjectId id;

    /**
     * Message header, may be null.
     */
    private MessageHeaders header;
   
    /**
     * Message payload, may be null.
     */
    private KFCErrorEvent payload;
       
    /**
     * Constructor
     */
    public MessagingError() {
    	
    }

    public ObjectId getObjectId() {
        return id;
    }

	public MessageHeaders getHeader() {
		return header;
	}

	public void setHeader(MessageHeaders header) {
		this.header = header;
	}

	public KFCErrorEvent getPayload() {
		return payload;
	}

	public void setPayload(KFCErrorEvent payload) {
		this.payload = payload;
	}

}
