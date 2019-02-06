package com.aldogrand.kfc.pollingmanager.service.impl;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.aldogrand.kfc.pollingmanager.service.RuleException;
import com.aldogrand.kfc.pollingmanager.service.WatchDirectory;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 * 
 * <p>
 * <b>Title</b> WatchDirectory implementation.
 * </p>
 * <p>
 * <b>Description</b>
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
public class WatchDirectoryImpl implements WatchDirectory {

    private Logger logger = LogManager.getLogger(getClass());

    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    private final boolean recursive;
    private boolean trace = false;
    private AtomicBoolean enable = new AtomicBoolean(false);

    private TaskExecutor taskExecutor;

    @Autowired
    @Qualifier("RulesLoaderInputChannel")
    private MessageChannel rulesLoaderInputChannel;

    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    /**
     * <<<<<<< HEAD Creates a WatchService and registers the given directory ======= Creates a WatchService and registers the given
     * directory. >>>>>>> branch 'polling-manager' of git@bitbucket.org:xcl-mb/kfc.git
     * 
     * @throws IOException, RuleException
     * 
     */
    public WatchDirectoryImpl(TaskExecutor taskExecutor, String path, boolean recursive) throws RuleException, IOException {
        this.taskExecutor = taskExecutor;
        Path dir = null;
        if (path != null) {
            dir = Paths.get(path);
        }

        if (dir != null && Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
            this.watcher = dir.getFileSystem().newWatchService();
            // this.watcher = FileSystems.getDefault().newWatchService();
            this.keys = new HashMap<WatchKey, Path>();
            this.recursive = recursive;

            if (recursive) {
                logger.debug(String.format("Scanning %s ...\n", dir));
                registerAll(dir);
            } else {
                register(dir);
            }

            // enable trace after initial registration
            this.trace = true;
            this.enable.set(true);

        } else {
            this.watcher = null;
            this.keys = new HashMap<WatchKey, Path>();
            this.recursive = recursive;

            String msgWarning = String.format("Watcher service can't be initialized. " + "Invalid path: %s", path);
            logger.warn(msgWarning);

            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning));
            }


        }

    }

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    /**
     * <<<<<<< HEAD Register the given directory with the WatchService ======= Register the given directory with the WatchService. >>>>>>>
     * branch 'polling-manager' of git@bitbucket.org:xcl-mb/kfc.git
     */
    private void register(Path dir) throws IOException {
        WatchKey key =
                        dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                                        StandardWatchEventKinds.ENTRY_MODIFY);

        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                logger.debug(String.format("register: %s\n", dir));
            } else {
                if (!dir.equals(prev)) {
                    logger.debug(String.format("update: %s -> %s\n", prev, dir));
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * <<<<<<< HEAD Process all events for keys queued to the watcher ======= Process all events for keys queued to the watcher. >>>>>>>
     * branch 'polling-manager' of git@bitbucket.org:xcl-mb/kfc.git
     */
    @Override
    public void processEvents() {
        while (enable.get()) {

            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException ie) {


                String msgWarning = String.format("Error taken the key. \n");
                logger.warn(msgWarning, ie);

                if (errorChannel != null) {

                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning, ie));

                }
                return;


            } catch (ClosedWatchServiceException cwse) {


                String msgWarning = String.format("Error watching the key or closed while waiting for the key. \n");
                logger.warn(msgWarning, cwse);

                if (errorChannel != null) {

                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning, cwse));
                }
                return;


            }

            Path dir = keys.get(key);
            if (dir == null) {


                String msgWarning = String.format("WatchKey not recognized!! ", key);
                logger.warn(msgWarning);

                if (errorChannel != null) {

                    errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning));
                }


                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {

                Kind<?> kind = event.kind();

                // OVERFLOW event is handled, discarded
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> evt = cast(event);
                Path name = evt.context();
                Path child = dir.resolve(name);

                logger.debug(String.format("%s: %s\n", event.kind().name(), child));

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    try {
                        if (recursive && Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }

                        if (Files.isRegularFile(child, LinkOption.NOFOLLOW_LINKS)) {

                            Message<String> msg = createMessage(child, StandardWatchEventKinds.ENTRY_CREATE);
                            rulesLoaderInputChannel.send(msg);
                        }
                    } catch (IOException io) {


                        String msg = String.format("Error registering %s \n", child);
                        logger.error(msg, io);
                        if (errorChannel != null) {

                            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, io));
                        } else {

                            throw new RuntimeException(msg, io);
                        }

                    } catch (SecurityException se) {


                        String msg = String.format("Error in denying reading access in the registering %s \n", child);
                        logger.error(msg, se);
                        if (errorChannel != null) {
                            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, se));
                        } else {

                            throw new RuntimeException(msg, se);
                        }

                    }

                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    if (Files.isRegularFile(child, LinkOption.NOFOLLOW_LINKS)) {
                        rulesLoaderInputChannel.send(createMessage(child, kind));
                    }
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    rulesLoaderInputChannel.send(createMessage(child, kind));
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {


                    String msgWarning = String.format("All directories are inaccesible. \n");
                    logger.warn(msgWarning);

                    if (errorChannel != null) {

                        errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning));
                    }


                    break;
                }
            }
        }
    }

    @Override
    public void startWatch() {
        if (watcher != null) {
            this.enable.set(true);
            taskExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    processEvents();
                }
            });
        }
    }

    @Override
    public void stopWatch() {
        this.enable.set(false);
    }

    /**
     * Create a SI message {@link Message}. Payload is string content of a file
     * 
     * @param file Payload
     * @param kind Type of event watched
     * @return Message
     */
    private Message<String> createMessage(Path file, Kind<?> kind) {
        String absoulutePath = file.toAbsolutePath().toString();
        MessageBuilder<String> builder = MessageBuilder.withPayload(absoulutePath).setHeader("eventKind", kind.toString());

        return (Message<String>) builder.build();
    }


    public MessageChannel getRulesLoaderInputChannel() {
        return rulesLoaderInputChannel;
    }

    public void setRulesLoaderInputChannel(MessageChannel rulesLoaderInputChannel) {
        this.rulesLoaderInputChannel = rulesLoaderInputChannel;
    }
}
