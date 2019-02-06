package com.aldogrand.kfc.pollingmanager.service.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.aldogrand.sbpc.connectors.model.MetaTag.Type;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.kfc.msg.events.KFCEvent;
import com.aldogrand.kfc.msg.events.integration.CommandType;
import com.aldogrand.kfc.msg.events.integration.FilterCommand;
import com.aldogrand.kfc.msg.events.integration.filter.KeyValueFilter;
import com.aldogrand.kfc.pollingmanager.model.EventAttributes;
import com.aldogrand.kfc.pollingmanager.model.EventAttributesMother;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.Rule;
import com.aldogrand.kfc.pollingmanager.rules.RuleType;
import com.aldogrand.kfc.pollingmanager.rules.filter.IdFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.IncludeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MarketTypeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MetaTagFilter;

public class CommandGeneratorTest {

    private CommandGenerator commandGenerator;
    private EventAttributes matchbookEventAttributes;

    @Before
    public void setup() {
        commandGenerator = new CommandGenerator();
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
    }

    @Test(expected = NullPointerException.class)
    public void eventAttributesCannotBeNull() {
        commandGenerator.generateCommands(null);
    }

    @Test(expected = NullPointerException.class)
    public void eventAttributesRuleCannotBeNull() {
        // Given
        EventAttributes eventAttributes = EventAttributesMother.createMatchbookAllEventsAttributes();
        eventAttributes.setRule(null);

        // When
        commandGenerator.generateCommands(eventAttributes);
    }

    @Test
    public void generateAllEventCommand() {
        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> eventsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(eventsCommand);
        Assert.assertEquals(CommandType.FETCH_EVENTS_COMMAND, eventsCommand.getType());
    }

    @Test
    public void generateOneEventCommand() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookOneEventAttributes();

        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> eventCommand = (FilterCommand<?>) event;
        assertBaseAttributes(eventCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, eventCommand.getId());
        Assert.assertEquals(CommandType.FETCH_EVENT_COMMAND, eventCommand.getType());
    }

    @Test
    public void generateAllMarketsCommand() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllMarketsAttributes();

        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> marketsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(marketsCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, marketsCommand.getId());
        Assert.assertEquals(CommandType.FETCH_MARKETS_COMMAND, marketsCommand.getType());
    }

    @Test
    public void generateOneMarketCommand() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookOneMarketAttributes();

        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> marketCommand = (FilterCommand<?>) event;
        assertBaseAttributes(marketCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, marketCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, marketCommand.getMarketId());
        Assert.assertEquals(CommandType.FETCH_MARKET_COMMAND, marketCommand.getType());
    }

    @Test
    public void generateAllRunnersCommand() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllRunnersAttributes();

        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> runnersCommand = (FilterCommand<?>) event;
        assertBaseAttributes(runnersCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, runnersCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, runnersCommand.getMarketId());
        Assert.assertEquals(CommandType.FETCH_RUNNERS_COMMAND, runnersCommand.getType());
    }

    @Test
    public void generateOneRunnerCommand() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookOneRunnerAttributes();

        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> runnerCommand = (FilterCommand<?>) event;
        assertBaseAttributes(runnerCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, runnerCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, runnerCommand.getMarketId());
        Assert.assertEquals(EventAttributesMother.RUNNER_ID, runnerCommand.getRunnerId());
        Assert.assertEquals(CommandType.FETCH_RUNNER_COMMAND, runnerCommand.getType());
    }

    @Test
    public void generateRunnerCommandHasFilters() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookOneRunnerAttributes();
        addPriceIncludeFilterToEventAttributes(matchbookEventAttributes);

        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> runnerCommand = (FilterCommand<?>) event;
        assertBaseAttributes(runnerCommand);
        Assert.assertTrue(runnerCommand.getFilters().size() == 1);
    }

    private void addPriceIncludeFilterToEventAttributes(EventAttributes eventAttributes) {
        List<Filter> filters = new ArrayList<Filter>();
        IncludeFilter includeFilter = new IncludeFilter();
        includeFilter.setDataType(DataType.PRICE);
        filters.add(includeFilter);
        eventAttributes.getRule().setFilters(filters);
    }

    @Test
    public void generateAllPricesCommand() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllPricesAttributes();

        // When
        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);

        // Then
        Assert.assertNotNull(event);
        FilterCommand<?> pricesCommand = (FilterCommand<?>) event;
        assertBaseAttributes(pricesCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, pricesCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, pricesCommand.getMarketId());
        Assert.assertEquals(CommandType.FETCH_PRICES_COMMAND, pricesCommand.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateRefreshPricesCommand() {
        // Given
        matchbookEventAttributes = EventAttributesMother.createMatchbookOnePriceAttributes();

        // When
        commandGenerator.generateCommands(matchbookEventAttributes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsupportedDataTypeThrowsException() {
        // Given
        matchbookEventAttributes.setRule(new Rule(RuleType.ALL, DataType.SETTLED_BET, new Duration(1, TimeUnit.HOURS)));

        // When
        commandGenerator.generateCommands(matchbookEventAttributes);
    }

    // TODO check: the command builder ignore runner ids
    @Test
    @Ignore
    public void generateAllPositionsCommand_withEventMarketRunnerId() {
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllPositionsAttributes();

        IdFilter eventIdFilter = new IdFilter(DataType.EVENT, new String[] {EventAttributesMother.EVENT_ID});
        IdFilter marketIdFilter = new IdFilter(DataType.MARKET, new String[] {EventAttributesMother.MARKET_ID});
        IdFilter runnerIdFilter = new IdFilter(DataType.RUNNER, new String[] {EventAttributesMother.RUNNER_ID});
        matchbookEventAttributes.getRule().setFilters(
                        Arrays.asList((Filter) eventIdFilter, (Filter) marketIdFilter, (Filter) runnerIdFilter));

        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);
        Assert.assertNotNull(event);
        FilterCommand<?> positionsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(positionsCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, positionsCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, positionsCommand.getMarketId());
        Assert.assertEquals(EventAttributesMother.RUNNER_ID, positionsCommand.getRunnerId());
        Assert.assertEquals(CommandType.FETCH_POSITIONS_COMMAND, positionsCommand.getType());
    }

    // TODO check: builder requires marketId
    @Test
    @Ignore
    public void generateAllPositionsCommand_withEventId() {
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllPositionsAttributes();

        IdFilter eventIdFilter = new IdFilter(DataType.EVENT, new String[] {EventAttributesMother.EVENT_ID});
        matchbookEventAttributes.getRule().setFilters(Arrays.asList((Filter) eventIdFilter));

        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);
        Assert.assertNotNull(event);
        FilterCommand<?> positionsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(positionsCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, positionsCommand.getId());
        Assert.assertNull(positionsCommand.getMarketId());
        Assert.assertNull(positionsCommand.getRunnerId());
        Assert.assertEquals(CommandType.FETCH_POSITIONS_COMMAND, positionsCommand.getType());
    }

    @Test
    public void generateAllPositionsCommand_withEventMarketId() {
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllPositionsAttributes();

        IdFilter eventIdFilter = new IdFilter(DataType.EVENT, new String[] {EventAttributesMother.EVENT_ID});
        IdFilter marketIdFilter = new IdFilter(DataType.MARKET, new String[] {EventAttributesMother.MARKET_ID});
        matchbookEventAttributes.getRule().setFilters(Arrays.asList((Filter) eventIdFilter, (Filter) marketIdFilter));

        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);
        Assert.assertNotNull(event);
        FilterCommand<?> positionsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(positionsCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, positionsCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, positionsCommand.getMarketId());
        Assert.assertNull(positionsCommand.getRunnerId());
        Assert.assertEquals(CommandType.FETCH_POSITIONS_COMMAND, positionsCommand.getType());
    }

    // TODO check: builder requires event and market id
    @Test
    @Ignore
    public void generateAllPositionsCommand_withoutIds() {
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllPositionsAttributes();

        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);
        Assert.assertNotNull(event);
        FilterCommand<?> positionsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(positionsCommand);
        Assert.assertNull(positionsCommand.getId());
        Assert.assertNull(positionsCommand.getMarketId());
        Assert.assertNull(positionsCommand.getRunnerId());
        Assert.assertEquals(CommandType.FETCH_POSITIONS_COMMAND, positionsCommand.getType());
    }

    @Test
    public void generateAllPositionsCommand_withSportMetaTagFilter() {
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllPositionsAttributes();

        IdFilter eventIdFilter = new IdFilter(DataType.EVENT, new String[] {EventAttributesMother.EVENT_ID});
        IdFilter marketIdFilter = new IdFilter(DataType.MARKET, new String[] {EventAttributesMother.MARKET_ID});
        MetaTagFilter mtf = new MetaTagFilter();
        mtf.setKey(Type.SPORT);
        mtf.setValues(new String[] {"Soccer", "Golf"});

        matchbookEventAttributes.getRule().setFilters(Arrays.asList((Filter) eventIdFilter, (Filter) marketIdFilter, (Filter) mtf));

        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);
        Assert.assertNotNull(event);
        FilterCommand<?> positionsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(positionsCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, positionsCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, positionsCommand.getMarketId());
        Assert.assertNull(positionsCommand.getRunnerId());
        Assert.assertEquals(CommandType.FETCH_POSITIONS_COMMAND, positionsCommand.getType());

        boolean soccer = false;
        boolean golf = false;
        List<com.aldogrand.kfc.msg.events.integration.Filter> cfl = positionsCommand.getFilters();
        for (com.aldogrand.kfc.msg.events.integration.Filter cf : cfl) {
            if (cf instanceof KeyValueFilter && ((KeyValueFilter) cf).getKey().equals("SPORT")) {
                String[] sportValues = ((KeyValueFilter) cf).getValues();
                for (String sv : sportValues) {
                    if (sv.equals("Soccer")) {
                        soccer = true;
                    }
                    if (sv.equals("Golf")) {
                        golf = true;
                    }
                }
            }
        }
        Assert.assertTrue(soccer && golf);
    }
    
    @Test
    public void generateAllPositionsCommand_withMarketTypeFilter() {
        matchbookEventAttributes = EventAttributesMother.createMatchbookAllPositionsAttributes();

        IdFilter eventIdFilter = new IdFilter(DataType.EVENT, new String[] {EventAttributesMother.EVENT_ID});
        IdFilter marketIdFilter = new IdFilter(DataType.MARKET, new String[] {EventAttributesMother.MARKET_ID});
        MarketTypeFilter mtf = new MarketTypeFilter();
        mtf.setValues(new MarketType[] {MarketType.ASIAN_HANDICAP});

        matchbookEventAttributes.getRule().setFilters(Arrays.asList((Filter) eventIdFilter, (Filter) marketIdFilter, (Filter) mtf));

        KFCEvent event = commandGenerator.generateCommands(matchbookEventAttributes);
        Assert.assertNotNull(event);
        FilterCommand<?> positionsCommand = (FilterCommand<?>) event;
        assertBaseAttributes(positionsCommand);
        Assert.assertEquals(EventAttributesMother.EVENT_ID, positionsCommand.getId());
        Assert.assertEquals(EventAttributesMother.MARKET_ID, positionsCommand.getMarketId());
        Assert.assertNull(positionsCommand.getRunnerId());
        Assert.assertEquals(CommandType.FETCH_POSITIONS_COMMAND, positionsCommand.getType());

        boolean markettype = false;
        List<com.aldogrand.kfc.msg.events.integration.Filter> cfl = positionsCommand.getFilters();
        for (com.aldogrand.kfc.msg.events.integration.Filter cf : cfl) {
            if (cf instanceof KeyValueFilter && ((KeyValueFilter) cf).getKey().equals("marketType")) {
                String[] sportValues = ((KeyValueFilter) cf).getValues();
                for (String sv : sportValues) {
                    if (sv.equals(MarketType.ASIAN_HANDICAP.toString())) {
                        markettype = true;
                    }
                }
            }
        }
        Assert.assertTrue(markettype);
    }

    private void assertBaseAttributes(FilterCommand<?> eventCommand) {
        Assert.assertEquals(EventAttributesMother.SESSION_TOKEN, eventCommand.getSessionToken());
        Assert.assertEquals(EventAttributesMother.MATCHBOOK_BASE_URL, eventCommand.getConnection().getBaseUrl());
        Assert.assertEquals(EventAttributesMother.MATCHBOOK_MODULE_ID, eventCommand.getIntegrationModuleId());
        Assert.assertEquals(EventAttributesMother.MATCHBOOK_MODULE_NAME, eventCommand.getIntegrationModuleName());
    }
}
