package com.aldogrand.kfc.msg.events.integration;

import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterContentCommand<C> extends ContentCommand<C> {

    private List<Filter> filters;
    private String id;

    @JsonCreator
    public FilterContentCommand(@JsonProperty("type") CommandType type, @JsonProperty("connection") Connection connection,
                    @JsonProperty("content") C content) {
        super(type, connection, content);
    }

    public CommandBuilder builder() {
        CommandBuilder builder = super.builder().id(id);
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
        super.accept(visitor);
        if (filters != null) {
            for (Filter filter : filters) {
                filter.accept(visitor);
            }
        }
        if (StringUtils.hasText(id)) {
            visitor.add("id", id);
        }
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
