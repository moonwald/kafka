package com.aldogrand.kfc.pollingmanager.model;

import com.aldogrand.kfc.pollingmanager.rules.Rule;

public class EventAttributes {

    private Rule rule;
    private String integrationModuleName;
    private String integrationModuleId;
    private String sessionToken;
    private String baseUrl;
    private String eventId;
    private String marketId;
    private String runnerId;
    private String username;
    private String password;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getIntegrationModuleName() {
        return integrationModuleName;
    }

    public void setIntegrationModuleName(String integrationModuleName) {
        this.integrationModuleName = integrationModuleName;
    }

    public String getIntegrationModuleId() {
        return integrationModuleId;
    }

    public void setIntegrationModuleId(String integrationModuleId) {
        this.integrationModuleId = integrationModuleId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EventAttributes clone() {
        EventAttributes eventAttributes = new EventAttributes();
        eventAttributes.setRule(rule);
        eventAttributes.setBaseUrl(baseUrl);
        eventAttributes.setSessionToken(sessionToken);
        eventAttributes.setIntegrationModuleId(integrationModuleId);
        eventAttributes.setIntegrationModuleName(integrationModuleName);
        eventAttributes.setEventId(eventId);
        eventAttributes.setMarketId(marketId);
        eventAttributes.setRunnerId(runnerId);
        eventAttributes.setUsername(username);
        eventAttributes.setPassword(password);
        return eventAttributes;
    }
}
