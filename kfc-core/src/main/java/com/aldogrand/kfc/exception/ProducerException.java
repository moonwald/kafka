package com.aldogrand.kfc.exception;

/**
 * <p>
 * <b>Title</b> Producer Exception Class
 * </p>
 * <p>
 * <b>Description</b> Kafka Producer Exception Implementation.
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
public class ProducerException extends RuntimeException {

    /**
     * Constructs the Kafka Producer Exception.<br/>
     * @params {@msg}
     */
    public ProducerException(String msg) {
        super(msg);
    }

    /**
     * Constructs the Kafka Producer Exception.<br/>
     * @params {@msg}
     * @params {@ex}
     */
    public ProducerException(Exception ex, String msg) {
        throw new RuntimeException(msg, ex);
    }

    /**
     * Constructs the Kafka Producer Exception.<br/>
     * @params {@ex}
     */
    public ProducerException(Exception ex) {
        throw new RuntimeException(ex);
    }
}
