package com.aldogrand.kfc.pollingmanager.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.aldogrand.kfc.pollingmanager.service.impl.RulesLoaderFileSystemServiceImpl;

@Component
public class Application implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = Logger.getLogger(Application.class);

    @Value("${auto.load.rules}")
    private String autoLoadRules;

    @Autowired
    private RulesLoaderFileSystemServiceImpl loaderService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if ("true".equalsIgnoreCase(autoLoadRules)) {
            LOG.debug("Load all rules on context refreshed ApplicationEvent.");
            loaderService.loadAllRules(true);
        } else {
            LOG.debug("NOT loading rules on context refreshed ApplicationEvent.");
        }
    }

}
