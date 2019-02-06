package com.aldogrand.kfc.pollingmanager.scheduler;

import java.util.Collection;

import org.springframework.messaging.MessageChannel;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.scheduler.model.Task;

public interface TaskManager {
    
    public Task createTask(RuleSchedule rule, MessageChannel outChannel);
    
    public Task updateTask(RuleSchedule rule);
    
    public Task removeTask(RuleSchedule rule);
    
    public Collection<Task> getAll();
    
    public Task getTask(String ruleId);

}
