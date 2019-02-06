package com.aldogrand.kfc.pollingmanager.jmx.manager.impl;

import java.util.List;

import com.aldogrand.kfc.pollingmanager.jmx.manager.JmxRuleManager;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;

public class JmxRuleManagerImpl implements JmxRuleManager {


    private JmxRuleManager jmxRuleMaganer;

    @Override
    public List<RuleSchedule> getAll() {

        return jmxRuleMaganer.getAll();
    }

    @Override
    public RuleSchedule get(String ruleId) {

        return jmxRuleMaganer.get(ruleId);
    }

    @Override
    public void enable(String ruleId) {
        jmxRuleMaganer.enable(ruleId);

    }

    @Override
    public void disable(String ruleId) {
        jmxRuleMaganer.disable(ruleId);

    }



}