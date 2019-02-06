package com.aldogrand.kfc.pollingmanager.service.generator;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.support.MessageBuilder;

import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.model.EventAttributesMother;

public class EventTopicGeneratorTest {

    private EventTopicGenerator eventTopicGenerator;
    private CommandGenerator generator;

    @Before
    public void setup() {
        eventTopicGenerator = new EventTopicGenerator();
        generator = new CommandGenerator();
    }

    @Test
    public void generateTopicForMatchbookEvents() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        MessageBuilder<KFCEvent> commandBuilder = createCommandMessageBuilder(eventAttributes);

        // When
        String topic = eventTopicGenerator.generateTopic(commandBuilder.build());

        // Then
        Assert.assertEquals(EventAttributesMother.MATCHBOOK_MODULE_NAME + EventTopicGenerator.DATA_REQUEST, topic);
    }
    
    @Test
    public void generateTopicForThreeetEvents() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createThreeetAllEventsAttributes();
        MessageBuilder<KFCEvent> commandBuilder = createCommandMessageBuilder(eventAttributes);

        // When
        String topic = eventTopicGenerator.generateTopic(commandBuilder.build());

        // Then
        Assert.assertEquals(EventAttributesMother.THREEET_MODULE_NAME + EventTopicGenerator.DATA_REQUEST, topic);
    }

    private MessageBuilder<KFCEvent> createCommandMessageBuilder(EventAttributes eventAttributes) {
        MessageBuilder<EventAttributes> builder = MessageBuilder.withPayload(eventAttributes);
        KFCEvent command = generator.generateCommands(builder.build().getPayload());
        MessageBuilder<KFCEvent> commandBuilder = MessageBuilder.withPayload(command);
        return commandBuilder;
    }
}
