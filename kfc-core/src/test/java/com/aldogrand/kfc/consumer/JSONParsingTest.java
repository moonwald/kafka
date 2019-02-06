//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.consumer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Test JSON parsing performance on small file.
 */
public class JSONParsingTest {

    private JsonParser parser;

    public static final int COUNT = 1000000;

    @Test
    public void small() throws IOException {
        parser = new JsonFactory().createJsonParser(getClass().getResource("/JSONParsingTest.json"));
        parser.setCodec(new ObjectMapper());
        long start = System.currentTimeMillis();
        parser.readValueAsTree();
        long diff = System.currentTimeMillis() - start;
        assertTrue(diff < 50);
    }

}
