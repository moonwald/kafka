package com.aldogrand.kfc.pollingmanager.rules.manager.impl;

import java.util.List;

import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.internal.model.RuleRoot;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleManager;

/**
 * 
 * <p>
 * <b>Title</b> RuleManagerImpl.
 * </p>
 * <p>
 * <b>Description</b> Rules management implementation
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author Aldo Grand
 *
 */
public class RuleManagerImpl implements RuleManager {

    protected RuleRoot ruleRoot;

    /**
     * Default constructor.
     */
    public RuleManagerImpl() {
        ruleRoot = new RuleRoot();
    }

    @Override
    public List<RuleSchedule> getAll() {
        return ruleRoot.getAllRules();
    }

    @Override
    public RuleSchedule get(String ruleId) {
        return ruleRoot.getRule(ruleId);
    }

    @Override
    public void addOrUpdate(RuleSchedule rule) {
        if (rule != null) {
            ruleRoot.addRule(rule);
        }
    }

    @Override
    public RuleSchedule remove(String ruleId) {
        return ruleRoot.deleteRule(ruleId);
    }

    @Override
    public void removeAll() {
        ruleRoot.deleteAllRules();
    }

    @Override
    public void enable(String ruleId) {
        RuleSchedule rule = ruleRoot.getRule(ruleId);
        if (rule != null) {
            rule.getRule().setEnabled(true);
        }
    }

    @Override
    public void disable(String ruleId) {
        RuleSchedule rule = ruleRoot.getRule(ruleId);
        if (rule != null) {
            rule.getRule().setEnabled(false);
        }
    }

}
