//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.annotation.ContentType;
import com.aldogrand.kfc.msg.events.KFCEvent;

/**
 * Transforms incoming message with JSON content into KFCEvent-based data.
 */
public class JSONToKFCEventTransformer {

    private ObjectMapper objectMapper;
    private Map<String, Class> deserializationMap;

    private static final Logger LOG = Logger.getLogger(JSONToKFCEventTransformer.class);

    /**
     * Provide mapping info.
     * @param key the content type constant
     * @return respective class mapped to given content type key
     */
    public Class<? extends KFCEvent> getMapping(String key) {
        return deserializationMap.get(key);
    }

    /**
     * Set object mapper for parsing JSON data.
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Set mapping of content type key and respective event class.
     */
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws Exception {
        this.deserializationMap = new HashMap<String, Class>();

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ContentType.class);
        LOG.info("Registering content types:");
        for (Object bean : beans.values()) {
            ContentType annotation = (bean instanceof Advised) ?
                ((Advised) bean).getTargetSource().getTarget().getClass().getAnnotation(ContentType.class) :
                    bean.getClass().getAnnotation(ContentType.class);
            LOG.info(" - " + bean.getClass().getName());
            deserializationMap.put(annotation.value(), bean.getClass());
        }
    }

    /**
     * Perform transformation of incoming JSON data into object form.
     * @param message SI message with JsonNode payload
     * @return list of mapped messages
     */
    public KFCEvent transform(Message<JsonNode> message) throws IOException {
        String contentTypeName = (String) message.getHeaders().get(MessageHeaders.CONTENT_TYPE).toString();
        if (contentTypeName == null) {
            throw new IllegalArgumentException("no content type given in message "+message);
        }
//        EventContentType contentType = EventContentType.valueOf(contentTypeName);
        Class<? extends KFCEvent> eventClass = getMapping(contentTypeName);
        if (eventClass == null) {
            throw new IllegalArgumentException("unknown content type: "+contentTypeName);
        }
        LOG.debug("Transforming the message ::"+message);
        
        return objectMapper.readValue(message.getPayload(), eventClass);
    }

}
