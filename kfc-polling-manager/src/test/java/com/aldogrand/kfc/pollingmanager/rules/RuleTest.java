package com.aldogrand.kfc.pollingmanager.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.aldogrand.kfc.pollingmanager.rules.filter.EventStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.IncludeFilter;

public class RuleTest {
    
    @Test
    public void eventRuleHasIncludeFilter() {
        // Given
        Rule eventRule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        addMarketIncludeFilterToRule(eventRule);

        // When
        List<Filter> matchingFilters = eventRule.getMatchingFilters(DataType.EVENT);

        // Then
        Assert.assertTrue(matchingFilters.size() == 1);
    }

    @Test
    public void eventRuleExcludesEventStatusFilter() {
        // Given
        Rule eventRule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new EventStatusFilter());
        eventRule.setFilters(filters);

        // When
        List<Filter> matchingFilters = eventRule.getMatchingFilters(DataType.EVENT);

        // Then
        Assert.assertTrue(matchingFilters.size() == 0);
    }

    @Test
    public void marketCommandHasIncludeFilter() {
        // Given
        Rule marketRule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));
        addRunnerIncludeFilterToRule(marketRule);

        // When
        List<Filter> matchingFilters = marketRule.getMatchingFilters(DataType.MARKET);

        // Then
        Assert.assertTrue(matchingFilters.size() == 1);
    }

    @Test
    public void marketCommandExcludesEventFilter() {
        // Given
        Rule marketRule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));
        addEventIncludeFilterRule(marketRule);

        // When
        List<Filter> matchingFilters = marketRule.getMatchingFilters(DataType.MARKET);

        // Then
        Assert.assertTrue(matchingFilters.size() == 0);
    }

    @Test
    public void marketCommandExcludesMarketFilter() {
        // Given
        Rule marketRule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));
        addMarketIncludeFilterToRule(marketRule);

        // When
        List<Filter> matchingFilters = marketRule.getMatchingFilters(DataType.MARKET);

        // Then
        Assert.assertTrue(matchingFilters.size() == 0);
    }

    @Test
    public void runnerCommandHasIncludeFilter() {
     // Given
        Rule marketRule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        addPriceIncludeFilterToRule(marketRule);

        // When
        List<Filter> matchingFilters = marketRule.getMatchingFilters(DataType.RUNNER);

        // Then
        Assert.assertTrue(matchingFilters.size() == 1);
    }

    @Test
    public void runnerCommandExcludesEventFilter() {
        // Given
        Rule runnerRule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        addEventIncludeFilterRule(runnerRule);

        // When
        List<Filter> matchingFilters = runnerRule.getMatchingFilters(DataType.RUNNER);

        // Then
        Assert.assertTrue(matchingFilters.size() == 0);
    }

    @Test
    public void runnerCommandExcludesMarketFilter() {
        // Given
        Rule runnerRule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        addMarketIncludeFilterToRule(runnerRule);

        // When
        List<Filter> matchingFilters = runnerRule.getMatchingFilters(DataType.RUNNER);

        // Then
        Assert.assertTrue(matchingFilters.size() == 0);
    }

    @Test
    public void runnerCommandExcludesRunnerFilter() {
        // Given
        Rule runnerRule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        addRunnerIncludeFilterToRule(runnerRule);

        // When
        List<Filter> matchingFilters = runnerRule.getMatchingFilters(DataType.RUNNER);

        // Then
        Assert.assertTrue(matchingFilters.size() == 0);
    }

    private void addEventIncludeFilterRule(Rule rule) {
        List<Filter> filters = new ArrayList<Filter>();
        IncludeFilter includeFilter = new IncludeFilter();
        includeFilter.setDataType(DataType.EVENT);
        filters.add(includeFilter);
        rule.setFilters(filters);
    }

    private void addMarketIncludeFilterToRule(Rule rule) {
        List<Filter> filters = new ArrayList<Filter>();
        IncludeFilter includeFilter = new IncludeFilter();
        includeFilter.setDataType(DataType.MARKET);
        filters.add(includeFilter);
        rule.setFilters(filters);
    }

    private void addRunnerIncludeFilterToRule(Rule rule) {
        List<Filter> filters = new ArrayList<Filter>();
        IncludeFilter includeFilter = new IncludeFilter();
        includeFilter.setDataType(DataType.RUNNER);
        filters.add(includeFilter);
        rule.setFilters(filters);
    }

    private void addPriceIncludeFilterToRule(Rule rule) {
        List<Filter> filters = new ArrayList<Filter>();
        IncludeFilter includeFilter = new IncludeFilter();
        includeFilter.setDataType(DataType.PRICE);
        filters.add(includeFilter);
        rule.setFilters(filters);
    }
}
