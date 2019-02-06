package com.aldogrand.kfc.pollingmanager.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldogrand.sbpc.connectors.model.EventStatus;
import com.aldogrand.sbpc.connectors.model.MetaTag;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.repository.EventRepository;
import com.aldogrand.kfc.pollingmanager.repository.MarketRepository;
import com.aldogrand.kfc.pollingmanager.repository.RunnerRepository;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.filter.EventStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.IdFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MetaTagFilter;
import com.aldogrand.kfc.pollingmanager.service.FilterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/settings/persistence-context.xml"})
public class EventFilterServiceIntegrationTest {

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
    public void decorateRefreshEventAttributes() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(2, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());
        assertEquals("1", attributes1.getIntegrationModuleId());
        assertEquals("Matchbook", attributes1.getIntegrationModuleName());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
    }

    @Test
    public void refreshEventsWithSoccerMetaTag() {
        // Given
        MetaTagFilter soccerFilter = new MetaTagFilter();
        soccerFilter.setKey(MetaTag.Type.SPORT);
        soccerFilter.setValues(new String[] {"Soccer"});
        List<Filter> filterList = new ArrayList<>();
        filterList.add(soccerFilter);

        Rule rule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        rule.setFilters(filterList);

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(1, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals("234234", attributes1.getEventId());
    }


    @Test
    public void refreshEventsWithEventStatusFilterClosed() {
        // Given
        EventStatusFilter eventStatusFilter = new EventStatusFilter();
        eventStatusFilter.setValues(new EventStatus[] {EventStatus.CLOSED});
        List<Filter> filterList = new ArrayList<>();
        filterList.add(eventStatusFilter);

        Rule rule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        rule.setFilters(filterList);

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(0, eventAttributesList.size());
    }

    @Test
    public void refreshEventsRuleWithEventStatusOpenFilter() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        EventStatusFilter eventStatusFilter = new EventStatusFilter();
        eventStatusFilter.setValues(new EventStatus[] {EventStatus.OPEN});
        rule.setFilters(Arrays.asList((Filter) eventStatusFilter));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(2, eventAttributesList.size());

        EventAttributes attributes1 = eventAttributesList.get(0);
        assertEquals(rule, attributes1.getRule());
        assertEquals("234234", attributes1.getEventId());

        EventAttributes attributes2 = eventAttributesList.get(1);
        assertEquals(rule, attributes2.getRule());
    }

    @Test
    public void refreshEventsRuleWithMetaTagFilter() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));

        MetaTagFilter metaTagFilter = new MetaTagFilter();
        metaTagFilter.setKey(MetaTag.Type.SPORT);
        metaTagFilter.setValues(new String[] {"Golf"});
        Filter idFilter = new IdFilter(DataType.EVENT, new String[] {"223424"});
        rule.setFilters(Arrays.asList(metaTagFilter, idFilter));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(1, eventAttributesList.size());
        EventAttributes eventAttributes0 = eventAttributesList.get(0);
        assertEquals(rule, eventAttributes0.getRule());
        assertEquals("223424", eventAttributes0.getEventId());
    }

    @Test(expected = NullPointerException.class)
    public void refreshEventsRuleWithIdFilterWithNullIds() {
        // Given
        Rule rule = new Rule(RuleType.REFRESH, DataType.EVENT, new Duration(1, TimeUnit.HOURS));
        Filter idFilter = new IdFilter(DataType.EVENT, null);
        rule.setFilters(Arrays.asList(idFilter));

        // When
        List<EventAttributes> eventAttributesList = filterService.decorateRule(rule);

        // Then
        assertNotNull(eventAttributesList);
        assertEquals(2, eventAttributesList.size());
    }
}
