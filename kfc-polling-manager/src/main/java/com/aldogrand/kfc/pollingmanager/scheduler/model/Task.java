package com.aldogrand.kfc.pollingmanager.scheduler.model;

import java.io.Serializable;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;

/**
 * 
 * <p>
 * <b>Title</b> Task model.
 * </p>
 * <p>
 * <b>Description</b> Task model encapsulate the trigger and job of a RuleSchedule.
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
public class Task implements Serializable {
    
    private static final long serialVersionUID = 6020698619010473634L;

    private String ruleId;
    
    private SimpleTrigger trigger;

    private JobDetail jobDetail;

    public Task(String ruleId, JobDetail jobDetail, SimpleTrigger trigger) {
        this.jobDetail = jobDetail;
        this.trigger = trigger;
        this.ruleId = ruleId;
    }

    public SimpleTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(SimpleTrigger trigger) {
        this.trigger = trigger;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }    
}
