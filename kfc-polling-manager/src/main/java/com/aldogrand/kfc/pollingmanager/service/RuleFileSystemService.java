package com.aldogrand.kfc.pollingmanager.service;

import com.aldogrand.kfc.pollingmanager.rules.Rule;

public interface RuleFileSystemService extends RuleService {

    /**
     * Loads a Rule into memory.
     * 
     * @param rule Rule to be loaded into memory
     * @param path Rule path in system
     */
    public void addRuleToMemory(Rule rule, String path);

    /**
     * Remove a rule from memory.
     * 
     * @param path Path to identify rule/s to be removed from memory
     */
    public void removeRuleFromMemory(String path);
}
