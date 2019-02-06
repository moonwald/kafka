package com.aldogrand.kfc.msg.events.integration;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {
    private final String username;
    private final String password;

    @JsonCreator
    public Credentials(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        super();
        this.username = Validate.notBlank(username, "A username is requried");
        this.password = Validate.notBlank(password, "A password is requried");;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
