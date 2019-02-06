package com.aldogrand.kfc.msg.events.integration;

import java.net.URL;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Proxy {
    private final String protocol;
    private final String host;
    private final Integer port;
    private Credentials credentials;

    @JsonCreator
    public Proxy(@JsonProperty("protocol") String protocol, @JsonProperty("host") String host, @JsonProperty("port") Integer port) {
        super();
        this.protocol = Validate.notBlank(protocol, "A protocol is required");
        this.host = Validate.notBlank(host, "A host is required");
        this.port = Validate.notNull(port, "A port is required");
    }

    public Proxy(URL url) {
        this(url.getProtocol(), url.getHost(), url.getPort());
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
