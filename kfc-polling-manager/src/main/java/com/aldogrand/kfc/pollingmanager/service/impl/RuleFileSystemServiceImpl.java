package com.aldogrand.kfc.pollingmanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleOperation;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleFileSystemManager;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleManager;
import com.aldogrand.kfc.pollingmanager.scheduler.TaskProperties;
import com.aldogrand.kfc.pollingmanager.scheduler.model.TaskOperation;
import com.aldogrand.kfc.pollingmanager.service.RuleFileSystemService;
import com.aldogrand.kfc.pollingmanager.service.RuleProperties;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 * 
 * <p>
 * <b>Title</b> RuleFileSystemServiceImpl
 * </p>
 * <p>
 * <b>Description</b> Rule service implementation to get rules from file system.
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
public class RuleFileSystemServiceImpl extends RuleServiceImpl implements RuleFileSystemService {

    private Logger logger = LogManager.getLogger(RuleFileSystemServiceImpl.class.getClass());

    @Autowired
    private RuleFileSystemManager ruleMgrFileSystem;

    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;
    
    @Autowired
    @Qualifier("to-scheduler-manager")
    private MessageChannel schedulerInputChannel;

    @Override
    public void processRule(MessageHeaders header, Rule rule) {
        Object ruleOperation = header.get(RuleProperties.MESSAGE_HEADER_KEY_OPERATION);

        if (ruleOperation == null) {
            String msgWarning = String.format("Null rule operation. ");
            logger.warn(msgWarning);

            if (errorChannel != null) {
                errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msgWarning));
            }
        }

        Object rulePath = header.get(RuleProperties.MESSAGE_HEADER_KEY_RULE_ID);
        if (rulePath != null) {
            processRule(rule, ruleOperation.toString(), rulePath.toString());
        } else {
            super.processRule(header, rule);
        }
    }

    private void processRule(Rule rule, String ruleOperation, String rulePath) {

        if (ruleOperation.equalsIgnoreCase(RuleOperation.LOAD_RULE.toString())) {
            this.addRuleToMemory(rule, rulePath);

        } else if (ruleOperation.equalsIgnoreCase(RuleOperation.UNLOAD_RULE.toString())) {
            this.removeRuleFromMemory(rulePath);

        } else if (ruleOperation.equalsIgnoreCase(RuleOperation.UNLOAD_RULES.toString())) {
            this.removeRuleFromMemory(rulePath);
        }

        // TODO: Enable and disable rules by path
    }

    @Override
    public void addRuleToMemory(Rule rule, String path) {
        RuleSchedule ruleSchedule = new RuleSchedule(path, rule);
        
        boolean toUpdated = false;
        if (ruleMgrFileSystem.get(path) != null) {
            toUpdated = true;
            logger.debug("Updating rule in memory, ruleId: " + rule != null ? ruleSchedule.getRuleId() : null);
        } else {
            logger.debug("Adding rule to memory, ruleId: " + rule != null ? ruleSchedule.getRuleId() : null);
        }
        
        ruleMgrFileSystem.addOrUpdate(ruleSchedule, path);
        
        Map<String, String> header = new HashMap<String, String>();
        header.put(RuleProperties.MESSAGE_HEADER_KEY_RULE_ID, ruleSchedule.getRuleId());
        header.put(RuleProperties.MESSAGE_HEADER_KEY_ENABLED, rule.isEnabled().toString());
        
        if (toUpdated) {
            header.put(TaskProperties.MESSAGE_HEADER_KEY_TASK_OPERATION, TaskOperation.UPDATED.toString());
        } else {
            header.put(TaskProperties.MESSAGE_HEADER_KEY_TASK_OPERATION, TaskOperation.CREATED.toString());
        }
        
        schedulerInputChannel.send(SpringIntegrationMessaging.buildRuleScheduleMessage(ruleSchedule, header));
    }

    @Override
    public void removeRuleFromMemory(String path) {
        if (path == null) {
            return;
        }

        // Path is a rule file in memory
        if (ruleMgrFileSystem.getByPath(path) != null) {
            logger.debug("Removing rule from memory, path: " + path);
            RuleSchedule ruleDeleted = ruleMgrFileSystem.removeByPath(path);

            Map<String, String> header = new HashMap<String, String>();
            header.put(RuleProperties.MESSAGE_HEADER_KEY_RULE_ID, path);
            header.put(TaskProperties.MESSAGE_HEADER_KEY_TASK_OPERATION, TaskOperation.DELETED.toString());
            schedulerInputChannel.send(SpringIntegrationMessaging.buildRuleScheduleMessage(ruleDeleted, header));
        } else {
            /*
             * Path is a folder Get all rules by path and remove any rule stored in the input path
             */
            List<RuleSchedule> rules = ruleMgrFileSystem.getAll();
            for (RuleSchedule rule: rules) {
                if (rule.getRuleId().startsWith(path)) {
                    logger.debug("Removing rule from memory, path: " + rule.getRuleId());
                    String pathDeleted = rule.getRuleId();
                    RuleSchedule ruleDeleted = ruleMgrFileSystem.removeByPath(pathDeleted);
                    
                    Map<String, String> header = new HashMap<String, String>();
                    header.put(RuleProperties.MESSAGE_HEADER_KEY_RULE_ID, pathDeleted);
                    header.put(TaskProperties.MESSAGE_HEADER_KEY_TASK_OPERATION, TaskOperation.DELETED.toString());
                    schedulerInputChannel.send(SpringIntegrationMessaging.buildRuleScheduleMessage(ruleDeleted, header));
                }
            }
        }
    }


    public RuleFileSystemManager getRuleMgrFileSystem() {
        return ruleMgrFileSystem;
    }


    public void setRuleMgrFileSystem(RuleFileSystemManager ruleMgrFileSystem) {
        this.ruleMgrFileSystem = ruleMgrFileSystem;
        super.setRuleMgr(ruleMgrFileSystem);
    }

    /**
     * @return the ruleMgr.
     */
    @Override
    public RuleManager getRuleMgr() {
        return (RuleManager) ruleMgrFileSystem;
    }

    public MessageChannel getSchedulerInputChannel() {
        return schedulerInputChannel;
    }

    public void setSchedulerInputChannel(MessageChannel schedulerInputChannel) {
        this.schedulerInputChannel = schedulerInputChannel;
    }    
}
