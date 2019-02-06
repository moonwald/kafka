package com.aldogrand.kfc.pollingmanager.acceptance;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/com/aldogrand/kfc/pollingmanager/acceptance/feature/"},
                glue = {"com.aldogrand.kfc.pollingmanager.acceptance"}, dryRun = false, strict = true, monochrome = true,
                format = {"pretty"}, tags = {"~@ignore"})
public class RunAcceptanceTests {

}
