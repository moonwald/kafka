package com.aldogrand.kfc.pollingmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.aldogrand.kfc.pollingmanager.model.Connection;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.repository.ConnectionRepository;

public class ConnectionService {

    private ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        Validate.notNull(connectionRepository, "ConnectionRepository cannot be null.");
        this.connectionRepository = connectionRepository;
    }

    public List<EventAttributes> decorateEventAttributes(EventAttributes eventAttributes) {
        validateAttributes(eventAttributes);

        List<EventAttributes> eventAttributesList = new ArrayList<>();
        Iterable<Connection> connections = getConnections(eventAttributes);
        for (Connection connection : connections) {
            EventAttributes connectionEventAttributes = eventAttributes.clone();
            connectionEventAttributes.setBaseUrl(connection.getBaseUrl());
            connectionEventAttributes.setSessionToken(connection.getSessionToken());
            eventAttributesList.add(connectionEventAttributes);
        }

        return eventAttributesList;
    }

    private Iterable<Connection> getConnections(EventAttributes eventAttributes) {
        Iterable<Connection> connections;
        String integrationModuleName = eventAttributes.getIntegrationModuleName();
        if (StringUtils.isBlank(integrationModuleName)) {
            connections = connectionRepository.findAll();
        } else {
            connections = connectionRepository.findByConnectorName(integrationModuleName);
        }
        return connections;
    }

    private void validateAttributes(EventAttributes eventAttributes) {
        Validate.notNull(eventAttributes, "EventAttributes cannot be null for ConnectionService.");
        Validate.notNull(eventAttributes.getRule(), "EventAttributes rule cannot be null for ConnectionService.");
    }
}
