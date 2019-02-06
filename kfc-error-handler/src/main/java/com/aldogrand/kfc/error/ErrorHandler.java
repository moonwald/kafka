//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.error;

import org.apache.log4j.Logger;
import org.springframework.messaging.MessagingException;

/**
 * The handler able to save given MessagingException instances into database.
 */
public class ErrorHandler {

    private static Logger LOG = Logger.getLogger(ErrorHandler.class.getName());

    /**
     * Save given exception into database, @see MessagingError for details about mapping of fields.
     * @param exception given exception
     */
    public void handleError(MessagingException exception) {
        LOG.error("Error processing message: " + exception.getMessage(), exception);
    }
}
