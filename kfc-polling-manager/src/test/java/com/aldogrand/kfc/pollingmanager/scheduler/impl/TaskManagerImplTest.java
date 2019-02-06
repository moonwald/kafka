package com.aldogrand.kfc.pollingmanager.scheduler.impl;

import static org.junit.Assert.assertNotNull;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleFileSystemManager;
import com.aldogrand.kfc.pollingmanager.scheduler.model.Task;
import com.aldogrand.kfc.pollingmanager.service.RulesLoaderFileSystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kfc-polling-manager.xml"})
public class TaskManagerImplTest {

    @Autowired
    private RulesLoaderFileSystemService loaderService;

    @Autowired
    private TaskManagerImpl taskManager;

    private Collection<Task> tasksInMemory;

    @Autowired
    private RuleFileSystemManager ruleManager;

    private List<RuleSchedule> rulesInMemory;

    private static String TEST_RESOURCES_RULES = "src/test/resources/rules";

    private static Path testPath = FileSystems.getDefault().getPath(TEST_RESOURCES_RULES);


    @Before
    public void setup() throws Exception {
        tasksInMemory = new ArrayList<Task>();
        rulesInMemory = new ArrayList<RuleSchedule>();
        loaderService.loadRulesFromPath(testPath.toAbsolutePath().toString(), false);
        tasksInMemory = taskManager.getAll();
        rulesInMemory = ruleManager.getAll();
    }

    @After
    public void tearDown() throws Exception {
        ruleManager.removeAll();
    }

    @Ignore
    @Test
    public void createTaskTest() {
        assertNotNull(rulesInMemory);
        assertNotNull(tasksInMemory);
    }

    public void updateTaskTest() {

    }

    public void removeTaskTest() {

    }

    public RuleFileSystemManager getRuleManager() {
        return ruleManager;
    }

    public void setRuleManager(RuleFileSystemManager ruleManager) {
        this.ruleManager = ruleManager;
    }

    public RulesLoaderFileSystemService getLoaderService() {
        return loaderService;
    }

    public void setLoaderService(RulesLoaderFileSystemService loaderService) {
        this.loaderService = loaderService;
    }

    public TaskManagerImpl getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(TaskManagerImpl taskManager) {
        this.taskManager = taskManager;
    }
}
