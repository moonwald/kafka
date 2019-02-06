package com.aldogrand.kfc.pollingmanager.integration;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@Ignore
@RunWith(Suite.class)
@SuiteClasses({FilterToKafkaTest.class, RuleToMemoryTest.class, EventFilterServiceIntegrationTest.class,
                MarketFilterServiceIntegrationTest.class, RunnerFilterServiceIntegrationTest.class,
                PriceFilterServiceIntegrationTest.class})
public class RunIntegrationTests {

}
