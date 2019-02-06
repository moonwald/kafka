package com.aldogrand.kfc.pollingmanager.rules.manager.impl;


import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.internal.model.RuleFileSystemRoot;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleFileSystemManager;

/**
 * 
 * <p>
 * <b>Title</b> RuleManagerFileSystemImpl.
 * </p>
 * <p>
 * <b>Description</b> Rule manager implementation to manage rules from files in the file system
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
public class RuleFileSystemManagerImpl extends RuleManagerImpl implements RuleFileSystemManager {

    public RuleFileSystemManagerImpl() {
        ruleRoot = new RuleFileSystemRoot();
    }

    @Override
    public void addOrUpdate(RuleSchedule rule, String path) {
        if (rule != null && path != null) {
            ((RuleFileSystemRoot) ruleRoot).addRule(rule);
        }
        
    }

    @Override
    public RuleSchedule removeByPath(String path) {        
        return ((RuleFileSystemRoot) ruleRoot).deleteRule(path);
    }


    @Override
    public RuleSchedule getByPath(String path) {        
        return ((RuleFileSystemRoot) ruleRoot).getRule(path);
    }
}
