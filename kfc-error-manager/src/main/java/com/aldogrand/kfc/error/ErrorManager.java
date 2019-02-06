package com.aldogrand.kfc.error;

import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.msg.events.error.CreationError;
import com.aldogrand.kfc.msg.events.error.DeleteError;
import com.aldogrand.kfc.msg.events.error.FetchError;
import com.aldogrand.kfc.msg.events.error.MapError;
import com.aldogrand.kfc.msg.events.error.UpdateError;

/**
 * The manager able to process the error and do the proper management depending on the event type.
 */
public interface ErrorManager {

    /**
     * Process a creation error message
     * 
     * @param headers Spring integration message headers
     * @param errorEvent kfc error event in creation process
     */
    public void manageError(@Headers MessageHeaders headers, @Payload CreationError errorEvent);
    
    /**
     * 
     * @param headers Spring integration message headers
     * @param errorEvent kfc error event in delete process
     */
    public void manageError(@Headers MessageHeaders headers, @Payload DeleteError errorEvent);
    
    /**
     * 
     * @param headers Spring integration message headers
     * @param errorEvent kfc error event in fetching process
     */
    public void manageError(@Headers MessageHeaders headers, @Payload FetchError errorEvent);
    
    /**
     * 
     * @param headers Spring integration message headers
     * @param errorEvent kfc error event in mapping process
     */
    public void manageError(@Headers MessageHeaders headers, @Payload MapError errorEvent);
    
    /**
     * 
     * @param headers Spring integration message headers
     * @param errorEvent kfc error event in update process
     */
    public void manageError(@Headers MessageHeaders headers, @Payload UpdateError errorEvent);
}
