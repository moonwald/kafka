package com.aldogrand.kfc.pollingmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.kfc.pollingmanager.model.Connector;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.model.SourceEvent;
import com.aldogrand.kfc.pollingmanager.model.SourceMarket;
import com.aldogrand.kfc.pollingmanager.repository.EventRepository;
import com.aldogrand.kfc.pollingmanager.repository.MarketRepository;
import com.aldogrand.kfc.pollingmanager.repository.RunnerRepository;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;

public class FilterServiceTest {

    private FilterService filterService;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private MarketRepository marketRepository;
    @Mock
    private RunnerRepository runnerRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        filterService = new FilterService(eventRepository, marketRepository, runnerRepository);
    }

    @Test(expected = NullPointerException.class)
    public void ruleCannotBeNull() {
        filterService.decorateRule(null);
    }


    @Test
    public void decorateAllEventsAttributes() {
        // Given
        Rule allEventsRule = new Rule(RuleType.ALL, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(allEventsRule);

        // Then
        Assert.assertNotNull(eventAttributesList);
        Assert.assertEquals(1, eventAttributesList.size());
        Assert.assertEquals(allEventsRule, eventAttributesList.get(0).getRule());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void decorateRefreshEventsAttributes() {
        // Given
        when(eventRepository.findAll(any(Specifications.class))).thenReturn(Arrays.asList(createEvent("123"), createEvent("456")));
        Rule rule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(2, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("123", attributes1.getEventId());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void decorateAllMarketsAttributes() {
        // Given
        when(eventRepository.findAll(any(Specifications.class))).thenReturn(Arrays.asList(createEvent("123"), createEvent("456")));
        Rule rule = new Rule(RuleType.ALL, DataType.MARKET, new Duration(1, TimeUnit.HOURS));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(2, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("123", attributes1.getEventId());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
    }

    @Test(expected = IllegalStateException.class)
    public void decorateRuleThrowsExceptionForSettledBets() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.SETTLED_BET, new Duration(1, TimeUnit.HOURS));

        // When
        filterService.decorateRule(rule);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void decorateAllRunnersRule() {
        // Given
        when(marketRepository.findAll(any(Specifications.class))).thenReturn(
                        Arrays.asList(createMarket("234234", "456"), createMarket("234234", "789")));

        Rule rule = new Rule(RuleType.ALL, DataType.RUNNER, new Duration(1, TimeUnit.HOURS));

        // When
        List<EventAttributes> attributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());

        EventAttributes attributes1 = attributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

        EventAttributes attributes2 = attributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("234234", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());
    }

    @Test(expected = IllegalStateException.class)
    public void decorateRefreshPricesRule() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.PRICE, new Duration(1, TimeUnit.HOURS));

        // When
        filterService.decorateRule(rule);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void decorateAllPricesRule() {
        // Given
        Rule rule = new Rule(RuleType.ALL, DataType.PRICE, new Duration(1, TimeUnit.HOURS));
        when(marketRepository.findAll(any(Specification.class))).thenReturn(
                        Arrays.asList(createMarket("123", "456"), createMarket("123", "789")));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(2, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("123", attributes1.getEventId());
        assertEquals("456", attributes1.getMarketId());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
        assertEquals("123", attributes2.getEventId());
        assertEquals("789", attributes2.getMarketId());
    }


    private SourceEvent createEvent(String sourceId) {
        SourceEvent event = new SourceEvent();
        event.setSourceId(sourceId);

        Connector connector = new Connector();
        connector.setId(1L);
        connector.setName("Matchbook");

        event.setConnector(connector);

        return event;
    }

    private SourceMarket createMarket(String eventId, String marketId) {

        SourceMarket market = new SourceMarket();

        market.setId(1L);
        market.setPeriod("1h");
        market.setHandicap(0.5);
        market.setSourceEvent(createEvent(eventId));
        market.setConnector(market.getSourceEvent().getConnector());
        market.setSourceId(marketId);
        market.setSourceName(null);
        market.setStatus("OPEN");
        market.setType(MarketType.HANDICAP.toString());

        return market;
    }
}
