package com.aldogrand.kfc.msg.events.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;

public class CommandBuilder {
    private static final String MESSAGE_FOR_ID = "Builder for %s requires an id";
    private static final String MESSAGE_FOR_MARKET_ID = "Builder for %s requires a marketId";
    private static final String MESSAGE_FOR_RUNNER_ID = "Builder for %s requires a runnerId";

    private String integrationModuleName;
    private String integrationModuleId;
    private Connection connection;
    private Context context;
    private String sessionToken;
    private List<Filter> filters;
    private String id;
    private String marketId;
    private String runnerId;

    public static CommandBuilder builder() {
        return new CommandBuilder();
    }

    CommandBuilder() {}

    public CommandBuilder integrationModuleName(String integrationModuleName) {
        this.integrationModuleName = integrationModuleName;
        return this;
    }

    public CommandBuilder integrationModuleId(String integrationModuleId) {
        this.integrationModuleId = integrationModuleId;
        return this;
    }

    public CommandBuilder connection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public CommandBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public CommandBuilder sessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    public CommandBuilder filter(Filter filter) {
        if (this.filters == null) {
            this.filters = new ArrayList<>();
        }
        this.filters.add(filter);
        return this;
    }

    public CommandBuilder filter(Collection<? extends Filter> filters) {
        if (this.filters == null) {
            this.filters = new ArrayList<>();
        }
        this.filters.addAll(filters);
        return this;
    }

    public CommandBuilder id(String id) {
        this.id = id;
        return this;
    }

    public CommandBuilder marketId(String marketId) {
        this.marketId = marketId;
        return this;
    }

    public CommandBuilder runnerId(String runnerId) {
        this.runnerId = runnerId;
        return this;
    }

    private void addBaseProperties(Command<?> command) {
        command.setIntegrationModuleName(Validate.notBlank(integrationModuleName, "An integrationModuleName is required"));
        command.setIntegrationModuleId(Validate.notBlank(integrationModuleId, "An integrationModuleId is required"));
        command.setContext(context);
    }

    private <C> ContentCommand<C> buildContentCommand(CommandType type, C body) {
        ContentCommand<C> command = new ContentCommand<C>(type, connection, body);
        addBaseProperties(command);
        return command;
    }

    private <C> ContentCommand<C> buildSecureContentCommand(CommandType type, C body) {
        ContentCommand<C> command = buildContentCommand(type, body);
        command.setSessionToken(sessionToken);
        return command;
    }

    private FilterCommand<?> buildFilterCommand(CommandType type) {
        FilterCommand<?> command = new FilterCommand<>(type, connection, sessionToken);
        if (filters != null) {
            command.setFilters(Collections.unmodifiableList(new ArrayList<>(filters)));
        }
        addBaseProperties(command);
        return command;
    }

    private <C> FilterContentCommand<C> buildFilterContentCommand(CommandType type, C body) {
        FilterContentCommand<C> command = new FilterContentCommand<C>(type, connection, body);
        if (filters != null) {
            command.setFilters(Collections.unmodifiableList(new ArrayList<>(filters)));
        }
        addBaseProperties(command);
        return command;
    }

    private <C> FilterContentCommand<C> buildSecureFilterContentCommand(CommandType type, C body) {
        FilterContentCommand<C> command = buildFilterContentCommand(type, body);
        command.setSessionToken(sessionToken);
        return command;
    }

    public <C> ContentCommand<C> login(C content) {
        return buildContentCommand(CommandType.LOGIN_COMMAND, content);
    }

    public FilterCommand<?> events() {
        return buildFilterCommand(CommandType.FETCH_EVENTS_COMMAND);
    }

    public FilterCommand<?> event() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_EVENT_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_EVENT_COMMAND));
        return command;
    }

    public FilterCommand<?> markets() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_MARKETS_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_MARKETS_COMMAND));
        return command;
    }

    public FilterCommand<?> market() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_MARKET_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_MARKET_COMMAND));
        command.setMarketId(Validate.notBlank(marketId, MESSAGE_FOR_MARKET_ID, CommandType.FETCH_MARKET_COMMAND));
        return command;
    }

    public FilterCommand<?> runners() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_RUNNERS_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_RUNNERS_COMMAND));
        command.setMarketId(Validate.notBlank(marketId, MESSAGE_FOR_MARKET_ID, CommandType.FETCH_RUNNERS_COMMAND));
        return command;
    }

    public FilterCommand<?> runner() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_RUNNER_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_RUNNER_COMMAND));
        command.setMarketId(Validate.notBlank(marketId, MESSAGE_FOR_MARKET_ID, CommandType.FETCH_RUNNER_COMMAND));
        command.setRunnerId(Validate.notBlank(runnerId, MESSAGE_FOR_RUNNER_ID, CommandType.FETCH_RUNNER_COMMAND));
        return command;
    }

    public FilterCommand<?> prices() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_PRICES_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_PRICES_COMMAND));
        command.setMarketId(Validate.notBlank(marketId, MESSAGE_FOR_MARKET_ID, CommandType.FETCH_PRICES_COMMAND));
        return command;
    }

    public FilterCommand<?> positions() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_POSITIONS_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_POSITIONS_COMMAND));
        command.setMarketId(Validate.notBlank(marketId, MESSAGE_FOR_MARKET_ID, CommandType.FETCH_POSITIONS_COMMAND));
        return command;
    }

    public FilterCommand<?> offer() {
        FilterCommand<?> command = buildFilterCommand(CommandType.FETCH_OFFER_COMMAND);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.FETCH_OFFER_COMMAND));
        return command;
    }

    public FilterCommand<?> offers() {
        return buildFilterCommand(CommandType.FETCH_OFFERS_COMMAND);
    }

    public <C> ContentCommand<C> submitOffer(C offer) {
        return buildSecureContentCommand(CommandType.SUBMIT_OFFER_COMMAND, offer);
    }

    public <C> ContentCommand<C> submitOffers(C offers) {
        return buildSecureContentCommand(CommandType.SUBMIT_OFFERS_COMMAND, offers);
    }

    public <C> FilterContentCommand<C> cancelOffer(C offer) {
        FilterContentCommand<C> command = buildSecureFilterContentCommand(CommandType.CANCEL_OFFER_COMMAND, offer);
        command.setId(Validate.notBlank(id, MESSAGE_FOR_ID, CommandType.CANCEL_OFFER_COMMAND));
        return command;
    }

    public <C> FilterContentCommand<C> cancelOffers(C offers) {
        return buildSecureFilterContentCommand(CommandType.CANCEL_OFFERS_COMMAND, offers);
    }
}
