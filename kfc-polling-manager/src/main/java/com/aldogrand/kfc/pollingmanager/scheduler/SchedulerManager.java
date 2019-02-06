package com.aldogrand.kfc.pollingmanager.scheduler;

import org.quartz.SchedulerException;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;

public interface SchedulerManager {

    public void scheduleRule(MessageHeaders header, RuleSchedule rule);
    
    public void createScheduledTask(RuleSchedule rule);
    
    public void updateScheduledTask(RuleSchedule rule);
    
    public void removeScheduledTask(RuleSchedule rule);
    
    public void start()  throws SchedulerException;
}
