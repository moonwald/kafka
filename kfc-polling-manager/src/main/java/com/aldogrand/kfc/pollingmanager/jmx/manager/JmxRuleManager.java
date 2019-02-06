package com.aldogrand.kfc.pollingmanager.jmx.manager;

import java.util.List;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;

public interface JmxRuleManager {

    public List<RuleSchedule> getAll();

    public RuleSchedule get(String ruleId);

    public void enable(String ruleId);

    public void disable(String ruleId);


}