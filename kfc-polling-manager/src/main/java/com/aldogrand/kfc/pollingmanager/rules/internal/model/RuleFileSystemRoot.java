package com.aldogrand.kfc.pollingmanager.rules.internal.model;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;

/**
 * 
 * <p>
 * <b>Title</b> RuleFileSystemRoot
 * </p>
 * <p>
 * <b>Description</b> The root container for the rule data model when rules coming from the file system. There is a new memory map to
 * identify rules by path.
 * 
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
public class RuleFileSystemRoot extends RuleRoot {

    /**
     * UID.
     */
    private static final long serialVersionUID = -2045021244919148563L;

    public RuleFileSystemRoot() {
        super();
    }

    /**
     * Get a rule {@link Rule}.
     * 
     * @param path Rule's path
     * @return {@link RuleSchedule}
     */
    public RuleSchedule getRule(String path) {
        return super.getRule(path);
    }
    
    /**
     * Add a rule {@link Rule} and sort out by path as well.
     * 
     * @param rule {@link RuleSchedule}
     */
    public void addRule(RuleSchedule rule) {        
        super.addRule(rule);
    }

    /**
     * Remove a rule {@link Rule}.
     * 
     * @param rulePath Rule path
     * @return RuleSchedule
     */
    public RuleSchedule deleteRule(String rulePath) {
        return super.deleteRule(rulePath);
    }

    /**
     * Removes all rules {@link Rule}.
     */
    public void deleteAllRules() {
        super.deleteAllRules();       
    }

}
