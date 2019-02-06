package com.aldogrand.kfc.msg.events.integration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("LOGIN_COMMAND")
public class LoginCommand extends ContentCommand<Credentials> {

    @JsonCreator
    public LoginCommand(@JsonProperty("type") CommandType type, @JsonProperty("connection") Connection connection,
                    @JsonProperty("content") Credentials content) {
        super(type, connection, content);
    }

}
