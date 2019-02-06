package com.aldogrand.kfc.pollingmanager.service;

import org.springframework.messaging.MessageHeaders;

public interface WatchDirectoryTest {

    void processTestEntryEvent(MessageHeaders headers, String sourceEventCreated);
}
