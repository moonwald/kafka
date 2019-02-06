package com.aldogrand.kfc.commands.matchbook;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.commands.content.MbCommandContentType;
import com.aldogrand.kfc.commands.integrationmodules.guessing.MBFetchEventCommand;
import com.aldogrand.kfc.commands.integrationmodules.guessing.MBFetchEventsCommand;
import com.aldogrand.kfc.commands.integrationmodules.guessing.MBFetchMarketCommand;
import com.aldogrand.kfc.commands.integrationmodules.guessing.MBFetchMarketsCommand;
import com.aldogrand.kfc.commands.integrationmodules.guessing.MBFetchPositionsCommand;
import com.aldogrand.kfc.commands.integrationmodules.guessing.MBFetchRunnerCommand;
import com.aldogrand.kfc.commands.integrationmodules.guessing.MBLoginCommand;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBEventFilter;
import com.aldogrand.kfc.commands.integrationmodules.guessing.filters.MBRunnerFilter;
import com.aldogrand.kfc.consumer.JSONToKFCEventTransformer;
import com.aldogrand.kfc.msg.events.KFCEvent;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-mysql-jsontransformer-test.xml"})
public class MBCommandsTransformationTest {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	MBFetchRunnerCommand mbFetchRunnerCommand = new MBFetchRunnerCommand();
	MBFetchEventCommand mbFetchEventCommand = new MBFetchEventCommand();
	
	@Before
	public void setup(){
		mbFetchRunnerCommand.setIntegrationModuleId("1");
		mbFetchRunnerCommand.setIntegrationModuleName("MatchBook");
		mbFetchRunnerCommand.setMbRunnerFilter(new MBRunnerFilter());
		
		mbFetchEventCommand.setIntegrationModuleId("1");
		mbFetchEventCommand.setIntegrationModuleName("MatchBook");
		mbFetchEventCommand.setFilter(new MBEventFilter());

	}
    @Autowired
    private JSONToKFCEventTransformer transformer;

    @Test
    public void mapping() {
        assertEquals(MBFetchRunnerCommand.class, transformer.getMapping(MbCommandContentType.MB_FETCH_RUNNER_COMMAND));
        assertEquals(MBFetchEventCommand.class, transformer.getMapping(MbCommandContentType.MB_FETCH_EVENT_COMMAND));
        assertEquals(MBFetchEventsCommand.class, transformer.getMapping(MbCommandContentType.MB_FETCH_EVENTS_COMMAND));
        assertEquals(MBFetchMarketCommand.class, transformer.getMapping(MbCommandContentType.MB_FETCH_MARKET_COMMAND));
        assertEquals(MBFetchMarketsCommand.class, transformer.getMapping(MbCommandContentType.MB_FETCH_MARKETS_COMMAND));
        assertEquals(MBFetchPositionsCommand.class, transformer.getMapping(MbCommandContentType.MB_FETCH_POSITIONS_COMMAND));
        assertEquals(MBLoginCommand.class, transformer.getMapping(MbCommandContentType.MB_LOGIN_COMMAND));
    }

    @Test
    public void transformmbFetchRunnerCommandTest() throws IOException,  IllegalArgumentException {
       String message = objectMapper.writeValueAsString(mbFetchRunnerCommand);

       MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
               .setHeader(MessageHeaders.CONTENT_TYPE, MbCommandContentType.MB_FETCH_RUNNER_COMMAND);
 
        transformer.setObjectMapper(new ObjectMapper());
        KFCEvent KFCEvent = transformer.transform(builder.build());
        assertTrue(KFCEvent instanceof MBFetchRunnerCommand); 
    }
    
    @Test
    public void transformmbFetchEventCommandTest() throws IOException,  IllegalArgumentException {
       String message = objectMapper.writeValueAsString(mbFetchEventCommand);

       MessageBuilder<JsonNode> builder = MessageBuilder.withPayload(objectMapper.readTree(message))
               .setHeader(MessageHeaders.CONTENT_TYPE, MbCommandContentType.MB_FETCH_EVENT_COMMAND);
 
        transformer.setObjectMapper(new ObjectMapper());
        KFCEvent KFCEvent = transformer.transform(builder.build());
        assertTrue(KFCEvent instanceof MBFetchEventCommand);
    }

}
