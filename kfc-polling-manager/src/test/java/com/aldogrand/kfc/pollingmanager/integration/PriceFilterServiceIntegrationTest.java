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
public class PriceFilterServiceIntegrationTest {

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
    public void decorateAllPricesRule() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(3, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());

        EventAttributes attributes3 = eventAttributesList.get(2);
        assertEquals(rule, attributes3.getRule());
        assertEquals("223424", attributes3.getEventId());
        assertEquals("000", attributes3.getMarketId());
    }

    @Test
    public void decorateAllPricesRuleWithIdFilterMarket() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        IdFilter marketIdFilter = new IdFilter(DataType.MARKET, new String[] {"789"});
        rule.setFilters(Arrays.asList((Filter) marketIdFilter));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(1, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("789", attributes1.getMarketId());
    }

    @Test
    public void decorateAllPricesRuleWithIdFilterEvent() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        IdFilter eventIdFilter = new IdFilter(DataType.EVENT, new String[] {"234234"});
        rule.setFilters(Arrays.asList((Filter) eventIdFilter));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(2, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());

    }

    @Test
    public void decorateAllPricesRuleWithIdFilterRunner() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        IdFilter eventIdFilter = new IdFilter(DataType.RUNNER, new String[] {"234234"});
        rule.setFilters(Arrays.asList((Filter) eventIdFilter));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(3, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());

        EventAttributes attributes3 = eventAttributesList.get(2);
        assertEquals(rule, attributes3.getRule());
        assertEquals("223424", attributes3.getEventId());
        assertEquals("000", attributes3.getMarketId());

    }

    @Test
    public void decorateAllPricesRuleWithIdFilterEventAndMarket() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        IdFilter f1 = new IdFilter(DataType.EVENT, new String[] {"234234"});
        IdFilter f2 = new IdFilter(DataType.MARKET, new String[] {"456"});
        rule.setFilters(Arrays.asList((Filter) f1, (Filter) f2));

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
    public void decorateAllPricesRuleWithIdFilterMarketWithTwoIds() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        IdFilter f1 = new IdFilter(DataType.MARKET, new String[] {"456", "789"});
        rule.setFilters(Arrays.asList((Filter) f1));

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
    public void decorateAllPricesRuleWithMarketType() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        MarketTypeFilter mtf = new MarketTypeFilter();
        mtf.setValues(new MarketType[] {MarketType.ASIAN_HANDICAP});
        rule.setFilters(Arrays.asList((Filter) mtf));

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
        assertEquals("223424", attributes2.getEventId());
        assertEquals("000", attributes2.getMarketId());

    }

    @Test
    public void decorateAllPricesRuleWithMetaTags() {
        // Given
        MetaTagFilter mtf = new MetaTagFilter();
        mtf.setKey(MetaTag.Type.SPORT);
        mtf.setValues(new String[] {"Golf"});
        MarketStatusFilter f2 = new MarketStatusFilter();
        f2.setValues(new MarketStatus[] {MarketStatus.CLOSED});

        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));

        rule.setFilters(Arrays.asList((Filter) mtf, (Filter) f2));

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
