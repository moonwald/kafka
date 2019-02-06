package com.aldogrand.kfc.pollingmanager.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.aldogrand.kfc.pollingmanager.rules.RuleOperation;
import com.aldogrand.kfc.pollingmanager.service.RuleException;
import com.aldogrand.kfc.pollingmanager.service.RuleProperties;
import com.aldogrand.kfc.pollingmanager.service.RulesLoaderFileSystemService;
import com.aldogrand.kfc.pollingmanager.service.RulesReaderService;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 * 
 * <p>
 * <b>Title</b> RulesLoaderServiceImpl.
 * </p>
 * <p>
 * <b>Description</b> Rules loader
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * s
 * @author Aldo Grand
 *
 */
public class RulesLoaderFileSystemServiceImpl implements RulesLoaderFileSystemService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private RulesReaderService rulesReader;

    private String rulesPath;

    @Autowired
    @Qualifier("transformerInputChannel")
    private MessageChannel transformerInputChannel;


    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    /**
     * Constructor.
     * 
     * @param rulesReader Rules reader
     * @param rulesPath Rules folder path
     */
    public RulesLoaderFileSystemServiceImpl(RulesReaderService rulesReader, String rulesPath) {
        this.rulesPath = rulesPath;
        this.rulesReader = rulesReader;
    }

    @Override
    public void loadAllRules(boolean resetRules) {
        try {
            List<File> ruleFiles = rulesReader.readAllRules(getRulesPath());

            // send rules to transformerInputChannel to be loaded in memory
            if (ruleFiles != null && !ruleFiles.isEmpty()) {
                for (File file : ruleFiles) {
                    transformerInputChannel.send(createMessage(file, RuleOperation.LOAD_RULE));
                }
            }
        } catch (RuleException e) {

            String msg = "Error loading all rules from path: " + getRulesPath();

            logger.error(msg, e);
            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, e));
            } else {
                throw new RuntimeException("Error loading all rules from path.", e);
            }
        }
    }

    @Override
    public void loadRulesFromPath(String path, boolean resetRules) {
        try {
            List<File> ruleFiles = rulesReader.readAllRules(path);

            // send rules to transformerInputChannel to be loaded in memory
            if (ruleFiles != null && !ruleFiles.isEmpty()) {
                for (File file : ruleFiles) {
                    transformerInputChannel.send(createMessage(file, RuleOperation.LOAD_RULE));
                }
            }
        } catch (RuleException e) {

            String msg = "Error loading all rules from path: " + path.toString() + ", resetRules: " + resetRules;

            logger.error(msg, e);
            if (errorChannel != null) {

                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, e));
            } else {
                throw new RuntimeException("Error loading all rules from path.", e);
            }
        }
    }

    @Override
    public void loadRule(String filePath) {
        File file = rulesReader.readRule(filePath);
        if (file != null) {
            // send rules to transformerInputChannel to be loaded in memory
            transformerInputChannel.send(createMessage(file, RuleOperation.LOAD_RULE));
        } else {
            String msgWarning = String.format("Not loading rule from path: : %s ", filePath);
            logger.warn(msgWarning);

            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning));
            }
        }
    }

    @Override
    public void unLoadRules(String path) {
        // TODO: send rules to be unloaded in memory

    }

    @Override
    public void unLoadRule(String file) {
        // TODO Auto-generated method stub
    }

    /**
     * Create a SI message {@link Message}. fileContent is string content of the file to be placed in the payload, path will be in the
     * header.
     * 
     * @param fileContent
     * @param path 
     * @return Message
     */
    private Message<String> createMessage(File file, RuleOperation operation) {
        String absoulutePath = file.getAbsolutePath();
        MessageBuilder<String> builder =
                        MessageBuilder.withPayload(convertFileToString(file.toPath()))
                        .setHeader(RuleProperties.MESSAGE_HEADER_KEY_RULE_ID, absoulutePath)
                        .setHeader(RuleProperties.MESSAGE_HEADER_KEY_OPERATION, operation.toString());

        return (Message<String>) builder.build();
    }

    /**
     * Method to read the file content as String.
     * 
     * @param file File path
     * @return Content of the file as string
     */
    private String convertFileToString(Path path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException x) {
            String msg = "Error converting a file in string. File: " + path.toAbsolutePath().toString();
            logger.error(msg, x);
            if (errorChannel != null) {

                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, x));
            } else {
                throw new RuntimeException("Error converting a file in string. File.", x);
            }
            return null;

        } catch (SecurityException se) {
            String msg =
                            "Error problem security accessing reading a file for the conversion in string. File: "
                                            + path.toAbsolutePath().toString();
            logger.error(msg);

            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, se));
            } else {
                throw new RuntimeException("Error problem security accessing reading a file for the conversion in string.", se);
            }
        }

        return builder.toString();
    }

    public String getRulesPath() {
        return rulesPath;
    }

    public RulesReaderService getRulesReader() {
        return rulesReader;
    }
}
