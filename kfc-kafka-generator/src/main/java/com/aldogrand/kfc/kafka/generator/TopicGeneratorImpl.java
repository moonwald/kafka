package com.aldogrand.kfc.kafka.generator;

import java.util.Properties;

import com.aldogrand.kfc.msg.interfaces.TopicGenerator;

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

/**
 * <p>
 * <b>Title</b> TopicGeneratorImpl
 * </p>
 * <p>
 * <b>Description</b> Topic Generator for Kafka Implementation.
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
public class TopicGeneratorImpl implements TopicGenerator {

	private Properties topics;
	private ExpressionParser parser = new SpelExpressionParser();
	private final static String regEx = "[^a-zA-Z0-9\\._\\-]";
    public TopicGeneratorImpl ()
    {
		ApplicationContext context = new ClassPathXmlApplicationContext("kafka-generator-context.xml");
    	this.topics = (Properties) context.getBean("topics");
    }

    public TopicGeneratorImpl (Properties property)
    {
    	this.topics = property;
    }

    /**
     * Generates the kafka topic<br/>
     * @param message
     * @return java.lang.String
     */
    public String generateTopic(Message message) throws ParseException, EvaluationException, NumberFormatException, RuntimeException {
    	// Calculate the topic
    	String topic = "";

    	// Read and Process the regular expression
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("message", message);
		String contentType = message.getHeaders().get("contentType").toString();
		Expression exp = parser.parseExpression(topics.getProperty(contentType));
		topic = (String) exp.getValue(context);
		topic = topic.replaceAll(regEx, "_");
    	return topic;
    }

	public Properties getTopics() {
		return topics;
	}

	public void setTopics(Properties topics) {
		this.topics = topics;
	}
}