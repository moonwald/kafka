package com.aldogrand.kfc.msg.events.integration;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GenericMessageMixIn<T> {
    public GenericMessageMixIn(@JsonProperty("payload") T payload, @JsonProperty("headers") Map<String, Object> headers) {}
}
