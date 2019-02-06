//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.error;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-error-handler-test.xml"})
public class ErrorHandlerTest {

    private static Logger LOG = Logger.getLogger("ErrorHandler");

    @Autowired
    private ErrorHandler errorHandler;

    private ObjectMapper objectMapper = new ObjectMapper();

    @AfterClass
    public static void deleteLog() {
        File file = new File("error.log");
        file.delete();
    }

    @Ignore
    @Test
    public void saveFullMessageException() throws IOException {
        String message = "{\"connector\": \"m2\", \"body\": {\"id\":123, \"name\":\"Name1\"}}";
        MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
                .setHeader(MessageHeaders.CONTENT_TYPE, "TEST");

        errorHandler.handleError(new MessagingException(builder.build(), "desc", new Exception()));
        File file = new File("error.log");

        assertTrue(file != null && file.exists());
        Profile localProfile = ProfileBuilder.newBuilder()
            .name("Test saveFullMessageException")
            .filePath("error.log")
            .onLocalhost()
            .build();
        assertThat(executing(grep(constantExpression("desc"), on(localProfile))).totalLines(), is(2));
    }

    @Ignore
    @Test
    public void savePlainException() {
        errorHandler.handleError(new MessagingException("ERROR"));
        File file = new File("error.log");

        assertTrue(file != null && file.exists());
        Profile localProfile = ProfileBuilder.newBuilder()
                .name("Test saveFullMessageException")
                .filePath("error.log")
                .onLocalhost()
                .build();
        assertThat(executing(grep(constantExpression("ERROR"), on(localProfile))).totalLines(), is(2));        
    }
}
