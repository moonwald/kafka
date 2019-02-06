package com.aldogrand.kfc.pollingmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.manager.impl.RuleManagerImpl;
import com.aldogrand.kfc.pollingmanager.service.impl.RulesLoaderFileSystemServiceImpl;
import com.aldogrand.kfc.pollingmanager.service.impl.RulesReaderServiceImpl;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager.xml"})
public class ApplicationTest {

    @Autowired
    private Application application;

    @Autowired
    ApplicationContext ctx;

    @Autowired
    private RuleManagerImpl ruleManager;

    @Value("${auto.load.rules}")
    private String autoLoadRules;

    @Value("${rules.path}")
    private String rulesPath;

    @Autowired
    RulesReaderServiceImpl readerService;

    @Autowired
    RulesLoaderFileSystemServiceImpl loaderService;

    @Before
    public void setUp() throws Exception {
        List<RuleSchedule> results = ruleManager.getAll();

        System.out.println("****************");
        System.out.println("b4 if setup: " + results.size());
        System.out.println("****************");

        if (results != null && !results.isEmpty()) {
            System.out.println("");
            System.out.println("");
            System.out.println("****************");
            System.out.println("****************");
            System.out.println("setup: " + results.size());
            for (RuleSchedule rule : results) {
                System.out.println("ruleId:" + rule.getRuleId());
            }
            System.out.println("****************");
            System.out.println("****************");
            System.out.println("");
            System.out.println("");
            ruleManager.removeAll();
            List<RuleSchedule> results2 = ruleManager.getAll();
            System.out.println("setup after removing: " + results2.size());
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnApplicationEvent() {

        application.onApplicationEvent(new ContextRefreshedEvent(ctx));

        List<RuleSchedule> results = ruleManager.getAll();
        assertNotNull(results);
        if ("true".equalsIgnoreCase(autoLoadRules)) {
            System.out.println("");
            System.out.println("");
            System.out.println("****************");
            System.out.println("****************");
            for (RuleSchedule rule : results) {
                System.out.println("ruleId:" + rule.getRuleId());
            }
            System.out.println("results:" + rulesPath);
            System.out.println("****************");
            System.out.println("****************");
            System.out.println("");
            System.out.println("");
            assertEquals(fileCount(), results.size());
        } else {
            assertEquals(0, results.size());
        }
    }

    private int fileCount() {
        FileVisitor visitor = new FileVisitor();
        Path path = Paths.get(rulesPath);
        System.out.println("****************");
        System.out.println("****************");
        System.out.println("TEST rulesPath=" + rulesPath);
        System.out.println("****************");
        System.out.println("****************");

        /*
         * Walking file tree will recursively visit all the files in a file tree.
         */
        try {
            Files.walkFileTree(path, visitor);
            return visitor.getFiles().size();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

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

            if (attr.isRegularFile() && file.toString().toLowerCase().endsWith(".json")) {
                logger.debug(String.format("Regular file: %s ", file));
                addFile(file.toFile());

            } else {

                String msgWarning = String.format("Regular file isn't a JSON. File: %s ", file.toString());
                logger.warn(msgWarning);


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
