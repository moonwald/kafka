package com.aldogrand.kfc.pollingmanager.rules;

import java.util.Date;

import org.apache.commons.lang3.Validate;

public class RuleSchedule {

    public static String RULE_SCHEDULE_KEY = "ruleSchedule";
    public static String RULE_SCHEDULE_OUT_CHANNEL = "outChannel";
    
    private final String ruleId;
    private Date loadTime;
    private Date lastProcessedTime;
    
    private final Rule rule;

    public RuleSchedule(String ruleId, Rule rule) {
        this.ruleId = Validate.notBlank(ruleId, "A ruleId is required");
        this.rule = Validate.notNull(rule, "A rule is required");
        this.loadTime = new Date();
    }

    public String getRuleId() {
        return ruleId;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    public Date getLastProcessedTime() {
        return lastProcessedTime;
    }

    public void setLastProcessedTime(Date lastProcessedTime) {
        this.lastProcessedTime = lastProcessedTime;
    }

    public Rule getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return "RuleSchedule [ruleId=" + ruleId + ", loadTime=" + loadTime + ", lastProcessedTime=" + lastProcessedTime + ", rule=" + rule
                        + "]";
    }
}
