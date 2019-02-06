package com.aldogrand.kfc.error.impl;

import org.apache.log4j.Logger;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.error.ErrorManager;
import com.aldogrand.kfc.msg.events.error.CreationError;
import com.aldogrand.kfc.msg.events.error.DeleteError;
import com.aldogrand.kfc.msg.events.error.FetchError;
import com.aldogrand.kfc.msg.events.error.MapError;
import com.aldogrand.kfc.msg.events.error.UpdateError;

/**
 * The manager able to process the error and do the proper management depending on the event type.
 */
public class ErrorManagerImpl implements ErrorManager {

    private static Logger LOG = Logger.getLogger("ErrorHandler");

	public void manageError(MessageHeaders headers, CreationError errorEvent) {
		// TODO Auto-generated method stub
		
	}

	public void manageError(MessageHeaders headers, DeleteError errorEvent) {
		// TODO Auto-generated method stub
		
	}

	public void manageError(MessageHeaders headers, FetchError errorEvent) {
		// TODO Auto-generated method stub
		
	}

	public void manageError(MessageHeaders headers, MapError errorEvent) {
		// TODO Auto-generated method stub
		
	}

	public void manageError(MessageHeaders headers, UpdateError errorEvent) {
		// TODO Auto-generated method stub
		
	}
}
