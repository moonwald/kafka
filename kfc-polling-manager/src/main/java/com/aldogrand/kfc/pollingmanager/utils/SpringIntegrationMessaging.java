package com.aldogrand.kfc.pollingmanager.utils;

import static org.springframework.messaging.MessageHeaders.ERROR_CHANNEL;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;

/**
 * 
 * <p>
 * <b>Title</b> SpringIntegrationMessaging.
 * </p>
 * <p>
 * <b>Description</b> Spring Integration Messaging utils
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author Aldo Grand
 *
 */
public class SpringIntegrationMessaging {

    private static String ERROR_CHANNEL_NAME = "errorChannel";

    /**
     * Build a Spring Integration error message.
     * 
     * @param msg String message
     * @return Message
     */
    public static Message<MessagingException> buildErrorMessage(String msg) {
        MessageBuilder<String> msgBuilder = MessageBuilder.withPayload(msg).setHeader(ERROR_CHANNEL, ERROR_CHANNEL_NAME);

        Message<MessagingException> msgException =
                        MessageBuilder.withPayload(new MessagingException(msgBuilder.build().toString())).build();

        return msgException;
    }

    /**
     * buildErrorMessage.
     * @param msg String message
     * @param exception Exception
     * @return Message
     */
    public static Message<MessagingException> buildErrorMessage(String msg, Exception exception) {
        MessageBuilder<String> msgBuilder = MessageBuilder.withPayload(msg).setHeader(ERROR_CHANNEL, ERROR_CHANNEL_NAME);

        Message<MessagingException> msgException =
                        MessageBuilder.withPayload(new MessagingException(msgBuilder.build().toString(), exception)).build();

        return msgException;
    }
    
    public static Message<Rule> buildRuleMessage(Rule rule, Map<String, String> header) {
        MessageBuilder<Rule> msgBuilder = MessageBuilder.withPayload(rule).copyHeaders(header);        
        return msgBuilder.build();
    }
    
    public static Message<RuleSchedule> buildRuleScheduleMessage(RuleSchedule ruleSchedule, Map<String, String> header) {
        MessageBuilder<RuleSchedule> msgBuilder = MessageBuilder.withPayload(ruleSchedule).copyHeaders(header);        
        return msgBuilder.build();
    }
}
