package com.aldogrand.kfc.pollingmanager.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;

import com.aldogrand.kfc.pollingmanager.service.RuleException;
import com.aldogrand.kfc.pollingmanager.service.RulesReaderService;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 * 
 * <p>
 * <b>Title</b> RulesReaderServiceImpl
 * </p>
 * <p>
 * <b>Description</b> Rules reader.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author Aldo Grand
 *
 */
public class RulesReaderServiceImpl implements RulesReaderService {

    private static String JSON_FILE_EXTENSION = ".json";

    private Logger logger = LogManager.getLogger(RulesReaderServiceImpl.class.getClass());


    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    /**
     * Default constructor.
     */
    public RulesReaderServiceImpl() {}

    @Override
    public List<File> readAllRules(String rulesPath) throws RuleException {
        // Validate.notEmpty(rulesPath, "Invalid rulesPath: " + rulesPath + ". The rulesPath isn't a directory.");

        List<File> rules = new ArrayList<File>();
        Path path = Paths.get(rulesPath);

        if (Files.isDirectory(path)) {
            rules = internalReadRulesFromPath(path);

        } else {
            throw new RuleException("Invalid path: " + path.toString() + ". The path isn't a directory.");
        }

        return rules;
    }

    @Override
    public File readRule(String file) {
        Path path = Paths.get(file);

        if (Files.isRegularFile(path)) {
            return path.toFile();
        } else {
            logger.error("Error loading rule: " + file,
                            new RuleException("Invalid file: " + path.toString() + ". It isn't a regular file."));
        }
        return null;
    }

    /**
     * Read rules from a system folder and return rules as JSON.
     * 
     * @param rulesPath Rules folder path
     * @return List of rules as JSON string
     * @throws RuleException
     */
    private List<File> internalReadRulesFromPath(Path path) throws RuleException {
        List<File> ruleFiles = new ArrayList<File>();
        FileVisitor visitor = new FileVisitor();

        try {
            /*
             * Walking file tree will recursively visit all the files in a file tree.
             */
            Files.walkFileTree(path, visitor);

            // Get all files visited
            ruleFiles.addAll(visitor.getFiles());
        } catch (IOException e) {
            String msg = "Error reading files from path: " + path.toAbsolutePath().toString();
            logger.error(msg, e);
            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, e));
            } else {
                throw new RuleException("I/O Error, path:" + path.toAbsolutePath().toString(), e);
            }

        } catch (SecurityException se) {
            String msg = "Error denying access to the reading files from path: " + path.toAbsolutePath().toString();
            logger.error(msg);
            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, se));
            } else {
                throw new RuntimeException("Error denying access to the reading files from path.", se);
            }
        } catch (UnsupportedOperationException ue) {
            String msg = "Error adding files unsupported in the reading from path: " + visitor.getFiles().toString();
            logger.error(msg);
            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, ue));
            } else {
                throw new RuntimeException("Error adding files unsupported in the reading from path.", ue);
            }
        } catch (NullPointerException npe) {
            String msg =
                            "Error about the list of files that contain null element/s in the reading from path : "
                                            + visitor.getFiles().toString();
            logger.error(msg);
            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, npe));
            } else {
                throw new RuntimeException("Error about the list of files that contain null element/s in the reading from path.", npe);
            }
        }

        return ruleFiles;
    }

    /**
     * 
     * <p>
     * <b>Title</b> FileVisitor
     * </p>
     * <p>
     * <b>Description</b> Class to visit all files in a tree.
     * </p>
     * <p>
     * <b>Company</b> AldoGrand Consultancy Ltd
     * </p>
     * <p>
     * <b>Copyright</b> Copyright (c) 2015
     * </p>
     * 
     * @author Aldo Grand
     *
     */
    private class FileVisitor extends SimpleFileVisitor<Path> {

        private Logger logger = LogManager.getLogger(FileVisitor.class.getClass());
        List<File> files;

        FileVisitor() {
            files = new ArrayList<File>();
        }

        void addFile(File file) {
            files.add(file);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {

            if (attr.isRegularFile() && file.toString().toLowerCase().endsWith(JSON_FILE_EXTENSION)) {
                logger.debug(String.format("Regular file: %s ", file));
                addFile(file.toFile());

            } else {

                String msgWarning = String.format("Regular file isn't a JSON. File: %s ", file.toString());
                logger.warn(msgWarning);

                if (errorChannel != null) {
                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning));
                }


            }
            return FileVisitResult.CONTINUE;
        }


        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            logger.debug(String.format("Directory: %s%n", dir));
            return FileVisitResult.CONTINUE;
        }


        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            logger.error(String.format("Error visiting file %s: ", file), exc);
            return FileVisitResult.CONTINUE;
        }

        public List<File> getFiles() {
            return files;
        }
    }
}
