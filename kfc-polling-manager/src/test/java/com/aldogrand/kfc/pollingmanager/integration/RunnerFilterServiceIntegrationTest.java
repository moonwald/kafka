package com.aldogrand.kfc.pollingmanager.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.connectors.model.MetaTag;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.repository.EventRepository;
import com.aldogrand.kfc.pollingmanager.repository.MarketRepository;
import com.aldogrand.kfc.pollingmanager.repository.RunnerRepository;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.filter.IdFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MarketStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MetaTagFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.RunnerStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.RunnerTypeFilter;
import com.aldogrand.kfc.pollingmanager.service.FilterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/settings/persistence-context.xml"})
public class RunnerFilterServiceIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private RunnerRepository runnerRepository;

    private FilterService filterService;

    @Before
    public void setUp() {
        filterService = new FilterService(eventRepository, marketRepository, runnerRepository);
    }

    @Test
    public void decorateRefreshRunnersRuleWithIdFilterRunner() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        IdFilter idFilter = new IdFilter(DataType.RUNNER, new String[] {"1020"});

        rule.setFilters(Arrays.asList((Filter) idFilter));
        // When
        List<EventAttributes> attributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());

        EventAttributes attributes1 = attributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("1020", attributes1.getRunnerId());
        assertEquals("456", attributes1.getMarketId());
        assertEquals("234234", attributes1.getEventId());
    }

    @Test
    public void decorateRefreshRunnersRuleWithIdFilterEventAndMarket() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));

        IdFilter idFilter1 = new IdFilter(DataType.EVENT, new String[] {"234234"});
        IdFilter idFilter2 = new IdFilter(DataType.MARKET, new String[] {"456"});
        rule.setFilters(Arrays.asList((Filter) idFilter1, (Filter) idFilter2));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(1, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());

        assertEquals("456", attributes1.getMarketId());
        assertEquals("234234", attributes1.getEventId());
    }

    @Test
    public void verifyRefreshRunnersRuleWithIdFilterEventAndMarketAndRunner() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));

        IdFilter idFilter1 = new IdFilter(DataType.EVENT, new String[] {"234234"});
        IdFilter idFilter2 = new IdFilter(DataType.MARKET, new String[] {"456"});
        IdFilter idFilter3 = new IdFilter(DataType.RUNNER, new String[] {"1020"});
        rule.setFilters(Arrays.asList((Filter) idFilter1, (Filter) idFilter2, (Filter) idFilter3));

        // When
        List<EventAttributes> attributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());

        EventAttributes attributes1 = attributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("1020", attributes1.getRunnerId());
        assertEquals("456", attributes1.getMarketId());
        assertEquals("234234", attributes1.getEventId());
    }

    @Test
    public void verifyRefreshRunnersRuleWithstatusOpen() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));

        RunnerStatusFilter filter = new RunnerStatusFilter();
        filter.setValues(new RunnerStatus[] {RunnerStatus.OPEN});
        rule.setFilters(Arrays.asList((Filter) filter));

        // When
        List<EventAttributes> attributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());

        EventAttributes attributes1 = attributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("1020", attributes1.getRunnerId());
        assertEquals("456", attributes1.getMarketId());
        assertEquals("234234", attributes1.getEventId());

        EventAttributes attributes2 = attributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("1025", attributes2.getRunnerId());
        assertEquals("789", attributes2.getMarketId());
        assertEquals("234234", attributes2.getEventId());
    }

    public void verifyRefreshRunnersRuleWithRunnerType() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));

        RunnerTypeFilter runnerTypeFilter = new RunnerTypeFilter();
        runnerTypeFilter.setValues(new RunnerType[] {RunnerType.PARTICIPANT});
        rule.setFilters(Arrays.asList((Filter) runnerTypeFilter));

        // When
        List<EventAttributes> attributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());

        EventAttributes attributes1 = attributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());
        assertEquals("1020", attributes1.getRunnerId());

        EventAttributes attributes2 = attributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());
        assertEquals("1025", attributes2.getRunnerId());
    }

    @Test
    public void decorateRefreshRunnersRuleWithMetaTags() {
        // Given
        MetaTagFilter metaTagFilter = new MetaTagFilter();
        metaTagFilter.setKey(MetaTag.Type.SPORT);
        metaTagFilter.setValues(new String[] {"Soccer"});
        MarketStatusFilter marketStatusFilter = new MarketStatusFilter();
        marketStatusFilter.setValues(new MarketStatus[] {MarketStatus.OPEN});
        Rule rule = new Rule(RuleType.REFRESH, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));
        rule.setFilters(Arrays.asList((Filter) metaTagFilter, (Filter) marketStatusFilter));

        // When
        List<EventAttributes> attributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());

        EventAttributes attributes1 = attributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());
        assertEquals("1020", attributes1.getRunnerId());

        EventAttributes attributes2 = attributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());
        assertEquals("1025", attributes2.getRunnerId());
    }
}
