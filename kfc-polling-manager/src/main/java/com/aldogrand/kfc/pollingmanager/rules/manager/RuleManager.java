package com.aldogrand.kfc.pollingmanager.rules.manager;

import java.util.List;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;

/**
 * 
 * <p>
 * <b>Title</b> RuleManager
 * </p>
 * <p>
 * <b>Description</b> Rules management interface.
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
public interface RuleManager {

    /**
     * Return all in-memory rules.
     * 
     * @return List of {@link Rule}
     */
    public List<RuleSchedule> getAll();

    /**
     * Return a rule by id.
     * 
     * @param ruleId Unique rule id
     * @return
     */
    public RuleSchedule get(String ruleId);

    /**
     * Add or update a rule {@link Rule}.
     * 
     * @param rule {@link Rule}
     */
    public void addOrUpdate(RuleSchedule rule);

    /**
     * Delete an in-memory rule.
     * 
     * @param ruleId Unique rule id
     * @return RuleSchedule
     */
    public RuleSchedule remove(String ruleId);

    /**
     * Deletes all in-memory rules.
     */
    public void removeAll();

    /**
     * Enable a rule {@link Rule}.
     * 
     * @param ruleId Unique rule id
     */
    public void enable(String ruleId);

    /**
     * Disable a rule {@link Rule}.
     * 
     * @param ruleId Unique rule id
     */
    public void disable(String ruleId);
}
