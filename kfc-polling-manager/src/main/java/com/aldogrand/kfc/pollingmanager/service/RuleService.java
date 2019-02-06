package com.aldogrand.kfc.pollingmanager.service;

import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.pollingmanager.rules.Rule;

/**
 *
 * <p>
 * <b>Title</b>: RuleService.java
 * </p>
 * <p>
 * <b>Description</b>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 *
 * @author dlehane
 *
 */
public interface RuleService {

    /**
     * Method to process rules by Spring Integration Messaging.
     * 
     * @param header Message Header
     * @param rule Rule
     */
    public void processRule(MessageHeaders header, Rule rule);

    /**
     * Loads a Rule into memory.
     * 
     * @param rule Rule to be loaded into memory
     * @param ruleId Unique identify value of the rule
     */
    public void addRuleToMemory(Rule rule, String ruleId);

    /**
     * Enable a rule in memory.
     *
     * @param ruleId Unique identify value of the rule
     */
    public void enableRule(String ruleId);

    /**
     * Disable a rule in memory.
     *
     * @param ruleId Unique identify value of the rule
     */
    public void disableRule(String ruleId);

    /**
     * Remove a rule from memory.
     *
     * @param ruleId Unique identify value of the rule
     */
    public void removeRuleFromMemory(String ruleId);

}
