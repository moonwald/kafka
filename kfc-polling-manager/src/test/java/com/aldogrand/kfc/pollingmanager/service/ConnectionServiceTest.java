package com.aldogrand.kfc.pollingmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.aldogrand.kfc.pollingmanager.model.Connection;
import com.aldogrand.kfc.pollingmanager.model.Connector;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.model.EventAttributesMother;
import com.aldogrand.kfc.pollingmanager.repository.ConnectionRepository;

public class ConnectionServiceTest {

    private ConnectionService connectionService;
    private ConnectionRepository connectionRepository;

    @Before
    public void setup() {
        connectionRepository = Mockito.mock(ConnectionRepository.class);
        connectionService = new ConnectionService(connectionRepository);
    }

    @Test(expected = NullPointerException.class)
    public void connectionRepositoryCannotBeNull() {
        new ConnectionService(null);
    }

    @Test(expected = NullPointerException.class)
    public void eventAttributesCannotBeNull() {
        connectionService.decorateEventAttributes(null);
    }

    @Test(expected = NullPointerException.class)
    public void eventAttributesRuleCannotBeNull() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        eventAttributes.setRule(null);

        // When
        connectionService.decorateEventAttributes(eventAttributes);
    }

    @Test
    public void decorateEventAttributes() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        List<Connection> connections = createTestConnections(eventAttributes);
        Mockito.when(connectionRepository.findByConnectorName(eventAttributes.getIntegrationModuleName())).thenReturn(connections);

        // When
        List<EventAttributes> eventAttributesList = connectionService.decorateEventAttributes(eventAttributes);

        // Then
        Assert.assertNotNull(eventAttributesList);
        Assert.assertEquals(1, eventAttributesList.size());
        Assert.assertEquals(eventAttributes.getRule(), eventAttributesList.get(0).getRule());
        Mockito.verify(connectionRepository).findByConnectorName(eventAttributes.getIntegrationModuleName());
    }
    
    @Test
    public void decorateEventAttributesWhenNoModuleNameSet() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        eventAttributes.setIntegrationModuleName(null);
        List<Connection> connections = createTestConnections(eventAttributes);
        Mockito.when(connectionRepository.findAll()).thenReturn(connections);

        // When
        List<EventAttributes> eventAttributesList = connectionService.decorateEventAttributes(eventAttributes);

        // Then
        Assert.assertNotNull(eventAttributesList);
        Assert.assertEquals(1, eventAttributesList.size());
        Assert.assertEquals(eventAttributes.getRule(), eventAttributesList.get(0).getRule());
        Mockito.verify(connectionRepository).findAll();
    }

    private List<Connection> createTestConnections(EventAttributes eventAttributes) {
        List<Connection> connections = new ArrayList<>();
        Connection connection = createTestConnection(eventAttributes);
        connections.add(connection);
        return connections;
    }

    private Connection createTestConnection(EventAttributes eventAttributes) {
        Connection connection = new Connection();
        connection.setId(1L);
        connection.setBaseUrl("baseUrl");
        connection.setSessionToken("token");
        Connector connector = new Connector();
        connector.setId(1L);
        connector.setName(eventAttributes.getIntegrationModuleName());
        connection.setConnector(connector);
        return connection;
    }
}
