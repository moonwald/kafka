package com.aldogrand.kfc.pollingmanager.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.service.WatchDirectory;
import com.aldogrand.kfc.pollingmanager.service.WatchDirectoryTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager-test.xml"})
public class WatchDirectoryTestImpl implements WatchDirectoryTest {

    private static String TEST_RESOURCES_RULES = "src/test/resources/rules";
    private static String TEMP_RULE = TEST_RESOURCES_RULES + "/tmp.json";
    private static String TEMP_RULE_IN_SECOND_LEVEL = TEST_RESOURCES_RULES + "/sports/tmp.json";

    private Logger logger = LogManager.getLogger(getClass());

    private AtomicBoolean entryEvent = new AtomicBoolean(false);

    @SuppressWarnings("unused")
    private WatchEvent.Kind<?> watchEventKind = null;

    @Autowired
    private WatchDirectory watcher;

    @Override
    public void processTestEntryEvent(MessageHeaders headers, String json) {
        String eventKind = headers.get("eventKind").toString();

        if (eventKind.equalsIgnoreCase(StandardWatchEventKinds.ENTRY_CREATE.toString())) {
            watchEventKind = StandardWatchEventKinds.ENTRY_CREATE;
            logger.info("File created, path: " + json);
        } else if (eventKind.equalsIgnoreCase(StandardWatchEventKinds.ENTRY_DELETE.toString())) {
            watchEventKind = StandardWatchEventKinds.ENTRY_DELETE;
            logger.info("File deleted, path: " + json);
        } else if (eventKind.equalsIgnoreCase(StandardWatchEventKinds.ENTRY_MODIFY.toString())) {
            watchEventKind = StandardWatchEventKinds.ENTRY_MODIFY;
            logger.info("File updated, path: " + json);
        }
        this.entryEvent.set(true);
    }


    @Test
    public void watchEntryCreateTest() throws InterruptedException {
        Path filePath = FileSystems.getDefault().getPath(TEMP_RULE);
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            fail("Error creating the file: " + filePath);
        }

        Path filePath2 = FileSystems.getDefault().getPath(TEMP_RULE_IN_SECOND_LEVEL);
        try {
            Files.createFile(filePath2);
        } catch (IOException e) {
            fail("Error creating the file: " + filePath);
        }

        Thread.sleep(2000);
        this.watchEntryUpdateTest();

        Thread.sleep(2000);
        this.watchEntryDeleteTest();

        Thread.sleep(2000);
        assertEquals(true, true);

    }


    public void watchEntryUpdateTest() {
        Path filePath = FileSystems.getDefault().getPath(TEMP_RULE_IN_SECOND_LEVEL);

        List<String> lines = new ArrayList<String>();
        lines.add("Line added");

        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail("Error updating the file: " + filePath);
        }

    }


    public void watchEntryDeleteTest() {

        Path filePath = FileSystems.getDefault().getPath(TEMP_RULE);
        Path filePath2 = FileSystems.getDefault().getPath(TEMP_RULE_IN_SECOND_LEVEL);
        try {
            Files.delete(filePath);
            Files.delete(filePath2);
        } catch (IOException e) {
            fail("Error deleting the file: " + filePath);
        }
    }

    public WatchDirectory getWatcher() {
        return watcher;
    }

    public void setWatcher(WatchDirectory watcher) {
        this.watcher = watcher;
    }

}
