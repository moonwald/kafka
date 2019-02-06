package com.aldogrand.kfc.pollingmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.model.SourceEvent;
import com.aldogrand.kfc.pollingmanager.model.SourceMarket;
import com.aldogrand.kfc.pollingmanager.model.SourceRunner;
import com.aldogrand.kfc.pollingmanager.repository.EventRepository;
import com.aldogrand.kfc.pollingmanager.repository.MarketRepository;
import com.aldogrand.kfc.pollingmanager.repository.RunnerRepository;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.visitor.EventSpecificationVisitor;
import com.aldogrand.kfc.pollingmanager.rules.visitor.MarketSpecificationVisitor;
import com.aldogrand.kfc.pollingmanager.rules.visitor.PriceSpecificationVisitor;
import com.aldogrand.kfc.pollingmanager.rules.visitor.RunnerSpecificationVisitor;

public class FilterService {

    private final EventRepository eventRepository;
    private final MarketRepository marketRepository;
    private final RunnerRepository runnerRepository;


    public FilterService(EventRepository eventRepository, MarketRepository marketRepository, RunnerRepository runnerRepository) {
        super();
        this.eventRepository = eventRepository;
        this.marketRepository = marketRepository;
        this.runnerRepository = runnerRepository;
    }

    public List<EventAttributes> decorateRule(Rule rule) {
        Validate.notNull(rule, "Rule cannot be null for FilterService.");
        List<EventAttributes> eventAttributesList;
        switch (rule.getDataType()) {
            case EVENT:
                eventAttributesList = decorateEventRule(rule);
                break;
            case MARKET:
                eventAttributesList = decorateMarketRule(rule);
                break;
            case RUNNER:
                eventAttributesList = decorateRunnerRule(rule);
                break;
            case PRICE:
                eventAttributesList = decoratePriceRule(rule);
                break;
            case POSITION:
                eventAttributesList = decoratePositionRule(rule);
                break;
            default:
                throw new IllegalStateException("Unsupported Data Type : " + rule.getDataType());
        }

        return eventAttributesList;
    }

    private List<EventAttributes> decoratePositionRule(Rule rule) {
        switch (rule.getRuleType()) {
            case ALL:
                return decorateAllPositionsRule(rule);
            default:
                throw new IllegalStateException("Unknown RuleType for events: " + rule.getRuleType());
        }
    }

    private List<EventAttributes> decorateEventRule(Rule rule) {
        switch (rule.getRuleType()) {
            case ALL:
                return decorateAllEventsRule(rule);
            case REFRESH:
                return decorateRefreshEventsRule(rule);
            default:
                throw new IllegalStateException("Unknown RuleType for events: " + rule.getRuleType());
        }
    }

    private List<EventAttributes> decorateMarketRule(Rule rule) {
        switch (rule.getRuleType()) {
            case ALL:
                return decorateAllMarketsRule(rule);
            case REFRESH:
                return decorateRefreshMarketsRule(rule);
            default:
                throw new IllegalStateException("Unknown RuleType for markets: " + rule.getRuleType());
        }
    }

    private List<EventAttributes> decorateRunnerRule(Rule rule) {
        switch (rule.getRuleType()) {
            case ALL:
                return decorateAllRunnersRule(rule);
            case REFRESH:
                return decorateRefreshRunnersRule(rule);
            default:
                throw new IllegalStateException("Unknown RuleType for runners: " + rule.getRuleType());
        }
    }

    private List<EventAttributes> decoratePriceRule(Rule rule) {
        switch (rule.getRuleType()) {
            case ALL:
                return decorateAllPricesRule(rule);
            case REFRESH:
                throw new IllegalStateException("For a Get Prices Rule, RuleType must be " + RuleType.ALL + " not " + rule.getRuleType());
            default:
                throw new IllegalStateException("Unknown RuleType for events: " + rule.getRuleType());
        }
    }

    private List<EventAttributes> decorateAllPositionsRule(Rule rule) {

        EventAttributes eventAttributes = new EventAttributes();
        eventAttributes.setRule(rule);

        List<EventAttributes> eventAttributesList = new ArrayList<EventAttributes>();
        eventAttributesList.add(eventAttributes);

        return eventAttributesList;
    }


    private List<EventAttributes> decorateAllEventsRule(Rule rule) {
        EventAttributes eventAttributes = new EventAttributes();
        eventAttributes.setRule(rule);
        List<EventAttributes> eventAttributesList = new ArrayList<EventAttributes>();
        eventAttributesList.add(eventAttributes);
        return eventAttributesList;
    }

    private List<EventAttributes> decorateRefreshEventsRule(Rule rule) {

        List<EventAttributes> eventAttributesList = new ArrayList<>();

        EventSpecificationVisitor eventSpecificationVisitor = new EventSpecificationVisitor();

        rule.accept(eventSpecificationVisitor, Rule.FilterDestination.DATABASE);

        eventAttributesList = new ArrayList<>();
        for (SourceEvent event : eventRepository.findAll(eventSpecificationVisitor.specifications())) {
            EventAttributes eventAttributes = new EventAttributes();
            eventAttributes.setRule(rule);
            eventAttributes.setEventId(event.getSourceId());
            eventAttributes.setIntegrationModuleId(event.getConnector().getId().toString());
            eventAttributes.setIntegrationModuleName(event.getConnector().getName());
            eventAttributesList.add(eventAttributes);
        }

        return eventAttributesList;
    }

    private List<EventAttributes> decorateAllMarketsRule(Rule rule) {
        return decorateRefreshEventsRule(rule);
    }

    private List<EventAttributes> decorateRefreshMarketsRule(Rule rule) {
        List<EventAttributes> marketAttributesList = new ArrayList<>();

        MarketSpecificationVisitor visitor = new MarketSpecificationVisitor();

        rule.accept(visitor, Rule.FilterDestination.DATABASE);

        for (SourceMarket market : marketRepository.findAll(visitor.specifications())) {
            EventAttributes marketAttributes = new EventAttributes();
            marketAttributes.setRule(rule);
            marketAttributes.setEventId(market.getSourceEvent().getSourceId());
            marketAttributes.setMarketId(market.getSourceId());
            marketAttributes.setIntegrationModuleId(market.getConnector().getId().toString());
            marketAttributes.setIntegrationModuleName(market.getConnector().getName());
            marketAttributesList.add(marketAttributes);
        }

        return marketAttributesList;
    }

    private List<EventAttributes> decorateAllRunnersRule(Rule rule) {
        return decorateRefreshMarketsRule(rule);
    }

    private List<EventAttributes> decorateRefreshRunnersRule(Rule rule) {
        List<EventAttributes> attributesList = new ArrayList<>();

        RunnerSpecificationVisitor visitor = new RunnerSpecificationVisitor();

        rule.accept(visitor, Rule.FilterDestination.DATABASE);

        for (SourceRunner runner : runnerRepository.findAll(visitor.specifications())) {
            EventAttributes attributes = new EventAttributes();
            attributes.setRule(rule);
            attributes.setEventId(runner.getSourceMarket().getSourceEvent().getSourceId());
            attributes.setMarketId(runner.getSourceMarket().getSourceId());
            attributes.setRunnerId(runner.getSourceId());
            attributes.setIntegrationModuleId(runner.getConnector().getId().toString());
            attributes.setIntegrationModuleName(runner.getConnector().getName());
            attributesList.add(attributes);
        }

        return attributesList;
    }

    private List<EventAttributes> decorateAllPricesRule(Rule rule) {
        List<EventAttributes> marketAttributesList = new ArrayList<>();

        PriceSpecificationVisitor visitor = new PriceSpecificationVisitor();

        rule.accept(visitor, Rule.FilterDestination.DATABASE);

        for (SourceMarket market : marketRepository.findAll(visitor.specifications())) {
            EventAttributes marketAttributes = new EventAttributes();
            marketAttributes.setRule(rule);
            marketAttributes.setEventId(market.getSourceEvent().getSourceId());
            marketAttributes.setMarketId(market.getSourceId());
            marketAttributes.setIntegrationModuleId(market.getConnector().getId().toString());
            marketAttributes.setIntegrationModuleName(market.getConnector().getName());
            marketAttributesList.add(marketAttributes);
        }

        return marketAttributesList;
    }


}
