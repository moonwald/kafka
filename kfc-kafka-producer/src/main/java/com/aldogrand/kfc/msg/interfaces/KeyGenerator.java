package com.aldogrand.kfc.msg.interfaces;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.messaging.Message;

/**
 * <p>
 * <b>Title</b> KeyGenerator Interface
 * </p>
 * <p>
 * <b>Description</b> Partition Key Generator for Kafka Interface.
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
public interface KeyGenerator {

    /**
     * Generates the partition Key<br/>
     * @param message
     * @return java.lang.String
     * @throws Exception 
     * @throws NumberFormatException 
     * @throws EvaluationException 
     * @throws ParseException 
     */
	public String generateKey(Message<?> message) throws ParseException, EvaluationException, NumberFormatException, RuntimeException;
}
