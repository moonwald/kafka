package com.aldogrand.kfc.msg.events.integration;

import java.util.Date;

public interface CommandVisitor<C, T> {

    void add(String key, Boolean value);

    void add(String key, Date... values);

    void add(String key, String... values);

    void addContent(C content);

    T build();

}
