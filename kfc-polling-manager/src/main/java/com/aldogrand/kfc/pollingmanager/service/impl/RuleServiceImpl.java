package com.aldogrand.kfc.pollingmanager.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleOperation;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.rules.manager.RuleManager;
import com.aldogrand.kfc.pollingmanager.service.RuleProperties;
import com.aldogrand.kfc.pollingmanager.service.RuleService;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 *
 * <p>
 * <b>Title</b>: RuleServiceImpl.java
 * </p>
 * <p>
 * <b>Description</b> Rule service implementation
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
public class RuleServiceImpl implements RuleService {

    private Logger logger = LogManager.getLogger(RuleServiceImpl.class.getClass());

    private RuleManager ruleMgr;

    @Autowired
    @Qualifier("errorChannel")
    private MessageChannel errorChannel;

    /**
     * @return the ruleMgr.
     */
    public RuleManager getRuleMgr() {
        return ruleMgr;
    }

    /**
     * @param ruleMgr the ruleMgr to set.
     */
    public void setRuleMgr(RuleManager ruleMgr) {
        this.ruleMgr = ruleMgr;
    }

    @Override
    public void processRule(MessageHeaders header, Rule rule) {
        Object ruleOperation = header.get(RuleProperties.MESSAGE_HEADER_KEY_OPERATION);
        Object ruleId = header.get(RuleProperties.MESSAGE_HEADER_KEY_RULE_ID);        
        if (ruleOperation == null) {
            String msg = ("Null rule operation. ");
            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg));
        }
        
        if (ruleId == null) {
            String msg = ("Null rule id. ");
            errorChannel.send(SpringIntegrationMessaging.buildErrorMessage(msg));
        }

        processRule(rule, ruleOperation.toString(), ruleId.toString());
    }

    private void processRule(Rule rule, String ruleOperation, String ruleId) {
        if (ruleOperation.equalsIgnoreCase(RuleOperation.LOAD_RULE.toString())) {
            this.addRuleToMemory(rule, ruleId);

        } else if (ruleOperation.equalsIgnoreCase(RuleOperation.UNLOAD_RULE.toString())) {
            this.removeRuleFromMemory(ruleId);

        } else if (ruleOperation.equalsIgnoreCase(RuleOperation.ENABLE.toString())) {
            this.enableRule(ruleId);

        } else if (ruleOperation.equalsIgnoreCase(RuleOperation.DISABLE.toString())) {
            this.disableRule(ruleId);
        }
    }

    @Override
    public void addRuleToMemory(Rule rule, String ruleId) {
        RuleSchedule ruleSchedule = new RuleSchedule(ruleId, rule);
        
        logger.debug(String.format("Adding rule to memory, ruleId: %s. Rule %s .", ruleId, rule));
        ruleMgr.addOrUpdate(ruleSchedule);
    }


    @Override
    public void enableRule(String ruleId) {
        logger.debug("Enabling rule in memory, ruleId: " + ruleId);
        ruleMgr.enable(ruleId);
    }

    @Override
    public void disableRule(String ruleId) {
        logger.debug("Disabling rule in memory, ruleId: " + ruleId);
        ruleMgr.disable(ruleId);
    }

    @Override
    public void removeRuleFromMemory(String ruleId) {
        logger.debug("Removing rule from memory, ruleId: " + ruleId);
        ruleMgr.remove(ruleId);
    }
}
