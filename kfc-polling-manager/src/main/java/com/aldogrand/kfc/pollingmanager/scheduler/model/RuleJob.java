package com.aldogrand.kfc.pollingmanager.scheduler.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleSchedule;
import com.aldogrand.kfc.pollingmanager.utils.SpringIntegrationMessaging;

/**
 * 
 * <p>
 * <b>Title</b> RuleJob.java
 * </p>
 * <p>
 * <b>Description</b> Job to process a rule.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * @author Aldo Grand
 * @see {@link QuartzJobBean}
 *
 */
public class RuleJob extends QuartzJobBean {

    private Logger logger = LogManager.getLogger(RuleJob.class);

    private RuleSchedule ruleSchedule;
    
    private MessageChannel outChannel;

    public RuleJob() {
        super();
        logger.debug("RuleJob fired.");
    }
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            if (ruleSchedule != null) {
                Rule rule = ruleSchedule.getRule();

                logger.debug("Sending rule: " + rule.toString() + " to Filter Service");
                outChannel.send(SpringIntegrationMessaging.buildRuleMessage(rule, null));
            } else {
                logger.error(String.format("A scheduled job is running and no rule schedule in. TriggerKey: %s", context.getTrigger()
                                .getKey()));
            }
        } catch (RuntimeException rte) {
            logger.error(String.format("Error sending rule %s to filter service channel %s.", ruleSchedule.getRule(), outChannel), rte);
        }
    }
   
    public RuleSchedule getRuleSchedule() {
        return ruleSchedule;
    }

    public void setRuleSchedule(RuleSchedule ruleSchedule) {
        this.ruleSchedule = ruleSchedule;
    }

    public MessageChannel getOutChannel() {
        return outChannel;
    }

    public void setOutChannel(MessageChannel outChannel) {
        this.outChannel = outChannel;
    }

}
