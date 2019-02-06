package com.aldogrand.kfc.pollingmanager.scheduler.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.scheduler.SchedulerManager;
import com.aldogrand.kfc.pollingmanager.scheduler.SchedulerManagerException;
import com.aldogrand.kfc.pollingmanager.scheduler.TaskManager;
import com.aldogrand.kfc.pollingmanager.scheduler.TaskProperties;
import com.aldogrand.kfc.pollingmanager.scheduler.model.Task;
import com.aldogrand.kfc.pollingmanager.scheduler.model.TaskOperation;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 * <p>
 * <b>Title</b> SchedulerManagerImpl.java
 * </p>
 * <p>
 * <b>Description</b> Scheduled jobs management implementation
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 *
 */
public class SchedulerManagerImpl implements SchedulerManager {

    private Logger logger = LogManager.getLogger(getClass());

    private TaskManager taskManager;

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    private Scheduler scheduler;

    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    private MessageChannel toServiceChannel;
    
    /**
     * Default constructor.
     * @param taskManager Manager to create, update and remove tasks
     * @param toServiceChannel Spring Integration Channel where will be sent the rules
     */
    public SchedulerManagerImpl(TaskManager taskManager, MessageChannel toServiceChannel) {
        this.taskManager = taskManager;
        this.toServiceChannel = toServiceChannel;
    }

    public void scheduleRule(MessageHeaders header, RuleSchedule rule) {
        String taskOperation = (String) header.get(TaskProperties.MESSAGE_HEADER_KEY_TASK_OPERATION);
        if (taskOperation == null) {
            String msg = ("Null rule event received. ");
            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg));
        }

        if (taskOperation.equalsIgnoreCase(TaskOperation.CREATED.toString())) {
            createScheduledTask(rule);
        } else if (taskOperation.equalsIgnoreCase(TaskOperation.UPDATED.toString())) {
            updateScheduledTask(rule);
        } else if (taskOperation.equalsIgnoreCase(TaskOperation.DELETED.toString())) {
            removeScheduledTask(rule);
        } else {
            logger.debug("Invalid task operation");
        }
    }

    @Override
    public void createScheduledTask(RuleSchedule rule) {        
        Task task = taskManager.createTask(rule, toServiceChannel);
        
        try {
            Set<SimpleTrigger> triggers = new HashSet<SimpleTrigger>();
            triggers.add(task.getTrigger());
            
            getScheduler().scheduleJob(task.getJobDetail(), triggers, true);
        } catch (SchedulerException e) {
            String msg = "Error creating a task. Rule: " + rule.toString();
            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, new SchedulerManagerException(e)));
        }
    }

    public void updateScheduledTask(RuleSchedule rule) {
        Task task = taskManager.updateTask(rule);
        if (task == null) {
            logger.warn("No task to update");
            return;
        }

        try {
            TriggerKey triggerKey = task.getTrigger().getKey();
            Set<SimpleTrigger> triggers = new HashSet<SimpleTrigger>();
            
            if (getScheduler().checkExists(triggerKey)) {
                triggers.add(task.getTrigger());
                getScheduler().scheduleJob(task.getJobDetail(), triggers, true);
                
                if (rule.getRule().isEnabled()) {
                    getScheduler().resumeTrigger(triggerKey);
                } else {
                    getScheduler().pauseTrigger(triggerKey);
                }
            }
        } catch (SchedulerException e) {
            String msg = "Error updating a task. Rule: " + rule.toString();
            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, new SchedulerManagerException(e)));
        }
    }

    @Override
    public void removeScheduledTask(RuleSchedule rule) {
        Task taskRemoved = taskManager.removeTask(rule);

        if (taskRemoved == null) {
            logger.warn("No task to remove");
            return;
        }

        try {
            TriggerKey triggerKey = taskRemoved.getTrigger().getKey();
            if (getScheduler().checkExists(triggerKey)) {
                getScheduler().unscheduleJob(triggerKey);
            }
        } catch (SchedulerException e) {
            String msg = "Error updating a task. Rule: " + rule.toString();
            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg, new SchedulerManagerException(e)));
        }
    }

    public Scheduler getScheduler() {       
        return scheduler;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public SchedulerFactoryBean getSchedulerFactory() {
        return schedulerFactory;
    }

    public void setSchedulerFactory(SchedulerFactoryBean schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    @Override
    public void start()  throws SchedulerException {
        if (scheduler == null) {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();   
        }
        
    }
}
