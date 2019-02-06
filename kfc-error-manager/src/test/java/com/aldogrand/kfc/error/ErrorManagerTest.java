//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.error;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-error-manager-test.xml"})
public class ErrorManagerTest {

    private static Logger LOG = Logger.getLogger("ErrorManager");

    @Autowired
    private ErrorManager errorManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @AfterClass
    public static void deleteLog() {
        File file = new File("error.log");
        file.delete();
    }

    @Test
    @Ignore
    public void saveFullMessageException() throws IOException {
    	
    }

    @Test
    @Ignore
    public void savePlainException() {
    	
    }
}
