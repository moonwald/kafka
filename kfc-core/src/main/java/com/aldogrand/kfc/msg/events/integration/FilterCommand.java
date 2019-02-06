package com.aldogrand.kfc.msg.events.integration;

import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;



public class FilterCommand<C> extends Command<C> {

    private List<Filter> filters;
    private String id;
    private String marketId;
    private String runnerId;

    public FilterCommand(@JsonProperty("type") CommandType type, @JsonProperty("connection") Connection connection,
                    @JsonProperty("session-token") String sessionToken) {
        super(type, connection, sessionToken);
    }
    
    public CommandBuilder builder() {
        CommandBuilder builder = super.builder().id(id).marketId(marketId).runnerId(runnerId);
        if (filters != null) {
            builder.filter(filters);
        }
        return builder;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public <T> void accept(CommandVisitor<C, T> visitor) {
        if (filters != null) {
            for (Filter filter : filters) {
                filter.accept(visitor);
            }
        }
        if (StringUtils.hasText(id)) {
            visitor.add("id", id);
        }
        if (StringUtils.hasText(marketId)) {
            visitor.add("marketId", marketId);
        }
        if (StringUtils.hasText(runnerId)) {
            visitor.add("runnerId", runnerId);
        }
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    protected void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getId() {
        return id;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getRunnerId() {
        return runnerId;
    }

}
