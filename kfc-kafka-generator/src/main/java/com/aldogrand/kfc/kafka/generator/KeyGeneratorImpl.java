package com.aldogrand.kfc.kafka.generator;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.messaging.Message;

import com.aldogrand.kfc.msg.interfaces.KeyGenerator;

/**
 * <p>
 * <b>Title</b> KeyGeneratorImpl
 * </p>
 * <p>
 * <b>Description</b> Partition Key Generator for Kafka Implementation.
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

public class KeyGeneratorImpl implements KeyGenerator {

	private Properties keyDefinitions;

	private ExpressionParser parser = new SpelExpressionParser();

    public KeyGeneratorImpl ()
    {
		ApplicationContext context = new ClassPathXmlApplicationContext("kafka-generator-context.xml");
    	this.keyDefinitions = (Properties) context.getBean("keyDefinitions");
    }

    public KeyGeneratorImpl (Properties property)
    {
    	this.keyDefinitions = property;
    }

    /**
     * Generates the partition Key<br/>
     * @param message
     * @return null
     */
    public String generateKey(Message message) throws ParseException, EvaluationException, NumberFormatException, RuntimeException {
    	// Calculate the partition
    	String key = "";

    	// Read and Process the regular expression
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("message", message);
		String contentType = message.getHeaders().get("contentType").toString();
		Expression exp = parser.parseExpression(keyDefinitions.getProperty(contentType));
		key = String.valueOf(exp.getValue(context));

    	return key;
    }

	public Properties getKeyDefinitions() {
		return keyDefinitions;
	}

	public void setKeyDefinitions(Properties keyDefinitions) {
		this.keyDefinitions = keyDefinitions;
	}
}
