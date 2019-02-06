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
import com.aldogrand.sbpc.model.MarketType;
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
import com.aldogrand.kfc.pollingmanager.rules.filter.MarketTypeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MetaTagFilter;
import com.aldogrand.kfc.pollingmanager.service.FilterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/settings/persistence-context.xml"})
public class MarketFilterServiceIntegrationTest {

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
    public void decorateRefreshMarketsRuleWithIdFilterMarket() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        IdFilter idFilter = new IdFilter(DataType.MARKET, new String[] {"789"});
        rule.setFilters(Arrays.asList((Filter) idFilter));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(1, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("789", attributes1.getMarketId());

    }

    @Test
    public void decorateRefreshMarketsRuleWithIdFilterEvent() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        IdFilter idFilter = new IdFilter(DataType.EVENT, new String[] {"234234"});
        rule.setFilters(Arrays.asList((Filter) idFilter));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(2, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

    }

    @Test
    public void decorateRefreshMarketsRuleWithIdFilterEventAndMarket() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        IdFilter filter1 = new IdFilter(DataType.EVENT, new String[] {"234234"});
        IdFilter filter2 = new IdFilter(DataType.MARKET, new String[] {"456"});
        rule.setFilters(Arrays.asList((Filter) filter1, (Filter) filter2));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(1, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

    }

    @Test
    public void decorateRefreshMarketsRuleWithIdFilterMoreIds() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        IdFilter filter1 = new IdFilter(DataType.MARKET, new String[] {"456", "789"});
        rule.setFilters(Arrays.asList((Filter) filter1));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(2, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());
        EventAttributes attributes2 = marketAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());
    }

    @Test
    public void decorateRefreshMarketsRuleWithStatusOpen() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        MarketStatusFilter filter = new MarketStatusFilter();
        filter.setValues(new MarketStatus[] {MarketStatus.OPEN});
        rule.setFilters(Arrays.asList((Filter) filter));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(2, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

        EventAttributes attributes2 = marketAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());
    }

    @Test
    public void decorateRefreshMarketsRuleWithStatusDefault() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(2, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

        EventAttributes attributes2 = marketAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());
    }

    @Test
    public void decorateRefreshMarketsRuleWithIdAndStatusFilter() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        MarketStatusFilter marketStatusFilter = new MarketStatusFilter();
        marketStatusFilter.setValues(new MarketStatus[] {MarketStatus.OPEN});
        IdFilter idFilter = new IdFilter(DataType.MARKET, new String[] {"789", "000"});
        rule.setFilters(Arrays.asList((Filter) marketStatusFilter, (Filter) idFilter));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(1, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("789", attributes1.getMarketId());

    }

    @Test
    public void decorateRefreshMarketsRuleWithMarketType() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        MarketTypeFilter marketTypeFilter = new MarketTypeFilter();
        marketTypeFilter.setValues(new MarketType[] {MarketType.ASIAN_HANDICAP});
        rule.setFilters(Arrays.asList((Filter) marketTypeFilter));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(1, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());
    }

    @Test
    public void decorateRefreshMarketsRuleWithMetaTags() {
        // Given
        MetaTagFilter metaTagFilter = new MetaTagFilter();
        metaTagFilter.setKey(MetaTag.Type.SPORT);
        metaTagFilter.setValues(new String[] {"Golf"});
        MarketStatusFilter marketStatusFilter = new MarketStatusFilter();
        marketStatusFilter.setValues(new MarketStatus[] {MarketStatus.CLOSED});
        Rule rule = new Rule(RuleType.REFRESH, DataType.MARKET, new Duration(1, TimeUnit.HOURS));
        rule.setFilters(Arrays.asList((Filter) metaTagFilter, (Filter) marketStatusFilter));

        // When
        List<EventAttributes> marketAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(marketAttributesList);
        assertEquals(1, marketAttributesList.size());

        EventAttributes attributes1 = marketAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("223424", attributes1.getEventId());
        assertEquals("000", attributes1.getMarketId());
    }
}
