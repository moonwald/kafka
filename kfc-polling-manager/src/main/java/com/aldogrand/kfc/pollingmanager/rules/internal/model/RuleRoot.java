package com.aldogrand.kfc.pollingmanager.rules.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;

/**
 * 
 * <p>
 * <b>Title</b> RuleRoot
 * </p>
 * <p>
 * <b>Description</b> The root container for the rule data model.
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
public class RuleRoot implements Serializable {

    /**
     * UID.
     */
    private static final long serialVersionUID = 193172553960468769L;

    private Map<String, RuleSchedule> rulesById;

    private Map<RuleType, Map<String, RuleSchedule>> rulesByType;

    /**
     * Default constructor.
     */
    public RuleRoot() {
        rulesById = new HashMap<>();
        rulesByType = new HashMap<>();
    }

    /**
     * Get a rule {@link Rule}.
     * 
     * @param ruleId Rule unique identify
     * @return {@link Rule}
     */
    public RuleSchedule getRule(String ruleId) {
        return rulesById.get(ruleId);
    }

    /**
     * Add a rule {@link Rule}.
     * 
     * @param rule {@link Rule}
     */
    public void addRule(RuleSchedule rule) {
        rulesById.put(rule.getRuleId(), rule);

        if (rule.getRule().getRuleType() != null) {
            addRuleByType(rule);
        }
    }

    /**
     * Remove a rule {@link Rule}.
     * 
     * @param ruleId Rule unique identify
     * @return RuleSchedule
     */
    public RuleSchedule deleteRule(String ruleId) {
        deleteRuleByType(rulesById.get(ruleId));
        return rulesById.remove(ruleId);
    }

    /**
     * Removes all rules {@link Rule}.
     */
    public void deleteAllRules() {
        if (rulesById != null) {
            rulesById.clear();
        }
        if (rulesByType != null) {
            rulesByType.clear();
        }
    }

    public List<RuleSchedule> getAllRules() {
        return new ArrayList<RuleSchedule>(rulesById.values());
    }

    public List<RuleSchedule> getRulesByType(RuleType ruleType) {
        Map<String, RuleSchedule> rulesMap = rulesByType.get(ruleType);
        return new ArrayList<RuleSchedule>(rulesMap.values());
    }

    /**
     * Add a rule by {@link RuleType}.
     * 
     * @param rule {@link Rule}
     */
    private void addRuleByType(RuleSchedule rule) {
        Map<String, RuleSchedule> rulesMap = rulesByType.get(rule.getRule().getRuleType());
        if (rulesMap == null) {
            rulesMap = new HashMap<>();
        }

        rulesMap.put(rule.getRuleId(), rule);
        rulesByType.put(rule.getRule().getRuleType(), rulesMap);
    }

    /**
     * Delete a rule {@link Rule} by type.
     * 
     * @param rule {@link Rule}
     */
    private void deleteRuleByType(RuleSchedule rule) {
        if (rule == null) {
            return;
        }

        Map<String, RuleSchedule> rulesMap = rulesByType.get(rule.getRule().getRuleType());
        if (rulesMap != null) {
            rulesMap.remove(rule.getRuleId());
        }
    }

}
