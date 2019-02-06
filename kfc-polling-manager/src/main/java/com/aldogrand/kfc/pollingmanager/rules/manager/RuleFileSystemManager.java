package com.aldogrand.kfc.pollingmanager.rules.manager;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;

public interface RuleFileSystemManager extends RuleManager {

    /**
     * Return a rule by path.
     * 
     * @param path Path of the rule
     * @return {@link RuleSchedule}
     */
    public RuleSchedule getByPath(String path);

    /**
     * Add or update a rule {@link Rule} to track the rule by path.
     * 
     * @param rule {@link RuleSchedule}
     * @param path Rule path
     */
    public void addOrUpdate(RuleSchedule rule, String path);

    /**
     * Delete an in-memory rule from a path.
     * 
     * @param path Rule path
     * @return RuleSchedule
     */
    public RuleSchedule removeByPath(String path);

}
