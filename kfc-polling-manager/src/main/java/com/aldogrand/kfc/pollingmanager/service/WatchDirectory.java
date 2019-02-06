package com.aldogrand.kfc.pollingmanager.service;

public interface WatchDirectory {

    void startWatch();

    void stopWatch();

    void processEvents();
}
