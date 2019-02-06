package com.aldogrand.kfc.pollingmanager.scheduler.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.scheduler.TaskManager;
import com.aldogrand.kfc.pollingmanager.scheduler.model.RuleJob;
import com.aldogrand.kfc.pollingmanager.scheduler.model.Task;
import com.aldogrand.kfc.pollingmanager.utils.DateTimeUtils;

/**
 * 
 * <p>
 * <b>Title</b> TaskManagerImpl.java
 * </p>
 * <p>
 * <b>Description</b> Task manager implementation.
 * Save in memory the tasks of the rules
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
public class TaskManagerImpl implements TaskManager {

    private Map<String, Task> taskMap;
    
    @Autowired
    private SimpleTriggerFactoryBean simpleTriggerFactory;
    
    public TaskManagerImpl() {
        taskMap = new HashMap<String, Task>();
    }

    @Override
    public Task createTask(RuleSchedule rule, MessageChannel outChannel) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(RuleSchedule.RULE_SCHEDULE_KEY, rule);
        jobDataMap.put(RuleSchedule.RULE_SCHEDULE_OUT_CHANNEL, outChannel);
         
        JobDetail jobDetail = JobBuilder.newJob(RuleJob.class)
                        .setJobData(jobDataMap)
                        .withIdentity(rule.getRuleId())
                        .build();

        SimpleTrigger trigger = createTrigger(rule, jobDetail);
        
        Task task = new Task(rule.getRuleId(), jobDetail, trigger);        
        taskMap.put(rule.getRuleId(), task);
        
        return task;
    }

    @Override
    public Task updateTask(RuleSchedule rule) {
        Task task = null;
        if (rule != null) {
            task = taskMap.get(rule.getRuleId());
        }

        if (task != null) {
            task.setRuleId(rule.getRuleId());
            task.setTrigger(createTrigger(rule, task.getJobDetail()));
            task.getJobDetail().getJobDataMap().put(RuleSchedule.RULE_SCHEDULE_KEY, rule);
        }

        return task;
    }

    @Override
    public Task removeTask(RuleSchedule rule) {
        return taskMap.remove(rule.getRuleId());
    }

    @Override
    public Collection<Task> getAll() {
        return taskMap.values();
    }

    @Override
    public Task getTask(String ruleId) {
        return taskMap.get(ruleId);
    }

    public Map<String, Task> getTaskMap() {
        return taskMap;
    }

    public SimpleTriggerFactoryBean getSimpleTriggerFactory() {
        return simpleTriggerFactory;
    }

    public void setSimpleTriggerFactory(SimpleTriggerFactoryBean simpleTriggerFactory) {
        this.simpleTriggerFactory = simpleTriggerFactory;
    }    

    private SimpleTrigger createTrigger(RuleSchedule rule, JobDetail jobDetail) {
        simpleTriggerFactory.setJobDetail(jobDetail);
        
        SimpleTriggerImpl trigger = (SimpleTriggerImpl)simpleTriggerFactory.getObject();        
        if (rule.getRule().getExpirationTime() != null) {
            trigger.setEndTime(rule.getRule().getExpirationTime());
        }
        trigger.setKey(TriggerKey.triggerKey(rule.getRuleId()));
        trigger.setName(rule.getRuleId());
        trigger.setStartTime(rule.getRule().getStartDate());
        trigger.setRepeatInterval(DateTimeUtils.convertToMilliseconds(rule.getRule().getPeriodicity()));
        trigger.setJobKey(jobDetail.getKey());
        
        return trigger;
    }
}
