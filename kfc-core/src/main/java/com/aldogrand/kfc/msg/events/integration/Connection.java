package com.aldogrand.kfc.msg.events.integration;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Connection {

    @JsonProperty("base-url")
    private final String baseUrl;

    private Proxy proxy;

    @JsonCreator
    public Connection(@JsonProperty("base-url") String baseUrl) {
        this.baseUrl = Validate.notBlank(baseUrl, "A base-url property is required");
    }

    public Connection(String baseUrl, Proxy proxy) {
        this(baseUrl);
        this.proxy = proxy;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }
}
