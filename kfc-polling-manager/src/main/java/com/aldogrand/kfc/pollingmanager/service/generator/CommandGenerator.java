package com.aldogrand.kfc.pollingmanager.service.generator;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;

import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.integration.CommandBuilder;
import com.aldogrand.kfc.msg.events.integration.Connection;
import com.aldogrand.kfc.msg.events.integration.FilterCommand;
import com.aldogrand.kfc.msg.events.integration.filter.KeyValueFilter;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.filter.IdFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MarketTypeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MetaTagFilter;

public class CommandGenerator {

    public KFCEvent generateCommands(EventAttributes eventAttributes) {
        validateAttributes(eventAttributes);
        Rule rule = eventAttributes.getRule();
        KFCEvent event;
        switch (rule.getDataType()) {
            case EVENT:
                event = createEventsCommand(eventAttributes);
                break;
            case MARKET:
                event = createMarketsCommand(eventAttributes);
                break;
            case RUNNER:
                event = createRunnersCommand(eventAttributes);
                break;
            case PRICE:
                event = createPricesCommand(eventAttributes);
                break;
            case POSITION:
                event = createPositionsCommand(eventAttributes);
                break;
            default:
                throw new IllegalArgumentException("No command generation for data type: " + rule.getDataType());
        }
        return event;
    }

    private KFCEvent createPositionsCommand(EventAttributes eventAttributes) {
        KFCEvent event;
        Rule rule = eventAttributes.getRule();
        switch (rule.getRuleType()) {
            case ALL:
                event = createPositionCommand(eventAttributes);
                break;
            default:
                throw new IllegalArgumentException("No event command generation for rule type: " + rule.getRuleType());
        }
        return event;
    }

    private FilterCommand<?> createPositionCommand(EventAttributes eventAttributes) {
        List<com.aldogrand.kfc.msg.events.integration.Filter> resultFilters = new LinkedList<>();

        CommandBuilder allBuilder = createBaseCommandBuilder(eventAttributes);
        Rule rule = eventAttributes.getRule();
        for (Filter filter : rule.getFilters()) {
            if (filter instanceof IdFilter) {
                IdFilter idf = (IdFilter) filter;
                switch (filter.getDataType()) {
                    case EVENT:
                        allBuilder.id(StringUtils.join(idf.getIds(), ","));
                        break;
                    case MARKET:
                        allBuilder.marketId(StringUtils.join(idf.getIds(), ","));
                        break;
                    case RUNNER:
                        break;
                    default:
                        break;
                }
            }

            if (filter instanceof MetaTagFilter) {
                MetaTagFilter mtf = (MetaTagFilter) filter;
                resultFilters.add(new KeyValueFilter(mtf.getKey().toString(), mtf.getValues()));
            }
            if (filter instanceof MarketTypeFilter) {
                MarketTypeFilter mtf = (MarketTypeFilter) filter;
                String[] marketTypes = new String[mtf.getValues().length];
                for (int i = 0; i < marketTypes.length; i++) {
                    marketTypes[i] = mtf.getValues()[i].toString();
                }
                resultFilters.add(new KeyValueFilter("marketType", marketTypes));
            }

            // TODO add interested clients filter
        }
        allBuilder.filter(resultFilters);
        return allBuilder.positions();
    }

    private void validateAttributes(EventAttributes eventAttributes) {
        Validate.notNull(eventAttributes, "EventAttributes cannot be null for CommandGenerator.");
        Validate.notNull(eventAttributes.getRule(), "EventAttributes rule cannot be null for CommandGenerator.");
    }

    private KFCEvent createEventsCommand(EventAttributes eventAttributes) {
        KFCEvent event;
        Rule rule = eventAttributes.getRule();
        switch (rule.getRuleType()) {
            case ALL:
                CommandBuilder allBuilder = createBaseCommandBuilder(eventAttributes);
                event = allBuilder.events();
                break;
            case REFRESH:
                CommandBuilder refreshBuilder = createBaseCommandBuilder(eventAttributes).id(eventAttributes.getEventId());
                addRefreshFiltersToCommand(eventAttributes, refreshBuilder, DataType.EVENT);
                event = refreshBuilder.event();
                break;
            default:
                throw new IllegalArgumentException("No event command generation for rule type: " + rule.getRuleType());
        }
        return event;
    }

    private KFCEvent createMarketsCommand(EventAttributes eventAttributes) {
        KFCEvent event;
        Rule rule = eventAttributes.getRule();
        switch (rule.getRuleType()) {
            case ALL:
                CommandBuilder allBuilder = createBaseCommandBuilder(eventAttributes).id(eventAttributes.getEventId());
                addRefreshFiltersToCommand(eventAttributes, allBuilder, DataType.EVENT);
                event = allBuilder.markets();
                break;
            case REFRESH:
                CommandBuilder refreshbuilder = createBaseCommandBuilder(eventAttributes).id(eventAttributes.getEventId());
                refreshbuilder.marketId(eventAttributes.getMarketId());
                addRefreshFiltersToCommand(eventAttributes, refreshbuilder, DataType.MARKET);
                event = refreshbuilder.market();
                break;
            default:
                throw new IllegalArgumentException("No market command generation for rule type: " + rule.getRuleType());
        }
        return event;
    }

    private KFCEvent createRunnersCommand(EventAttributes eventAttributes) {
        KFCEvent event;
        Rule rule = eventAttributes.getRule();
        switch (rule.getRuleType()) {
            case ALL:
                CommandBuilder allBuilder = createBaseCommandBuilder(eventAttributes).id(eventAttributes.getEventId());
                allBuilder.marketId(eventAttributes.getMarketId());
                addRefreshFiltersToCommand(eventAttributes, allBuilder, DataType.MARKET);
                event = allBuilder.runners();
                break;
            case REFRESH:
                CommandBuilder refreshbuilder = createBaseCommandBuilder(eventAttributes).id(eventAttributes.getEventId());
                refreshbuilder.marketId(eventAttributes.getMarketId()).runnerId(eventAttributes.getRunnerId());
                addRefreshFiltersToCommand(eventAttributes, refreshbuilder, DataType.RUNNER);
                event = refreshbuilder.runner();
                break;
            default:
                throw new IllegalArgumentException("No market command generation for rule type: " + rule.getRuleType());
        }
        return event;
    }

    private KFCEvent createPricesCommand(EventAttributes eventAttributes) {
        KFCEvent event;
        Rule rule = eventAttributes.getRule();
        switch (rule.getRuleType()) {
            case ALL:
                CommandBuilder allBuilder = createBaseCommandBuilder(eventAttributes).id(eventAttributes.getEventId());
                allBuilder.marketId(eventAttributes.getMarketId());
                event = allBuilder.prices();
                break;
            case REFRESH:
                throw new IllegalArgumentException("For a Get Prices Rule, RuleType must be " + RuleType.ALL 
                                + " not " + rule.getRuleType());
            default:
                throw new IllegalArgumentException("No price command generation for rule type: " + rule.getRuleType());
        }
        return event;
    }


    private void addRefreshFiltersToCommand(EventAttributes eventAttributes, CommandBuilder builder, DataType refreshDataType) {
        Rule rule = eventAttributes.getRule();
        List<Filter> matchingFilters = rule.getMatchingFilters(refreshDataType);
        ConstraintConverter constraintConverter = new ConstraintConverter();
        builder.filter(constraintConverter.convertContraints(matchingFilters));
    }

    private CommandBuilder createBaseCommandBuilder(EventAttributes eventAttributes) {
        return CommandBuilder.builder().integrationModuleId(eventAttributes.getIntegrationModuleId())
                        .integrationModuleName(eventAttributes.getIntegrationModuleName()).sessionToken(eventAttributes.getSessionToken())
                        .connection(new Connection(eventAttributes.getBaseUrl()));
    }
}
