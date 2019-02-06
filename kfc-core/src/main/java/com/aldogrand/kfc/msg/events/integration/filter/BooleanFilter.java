package com.aldogrand.kfc.msg.events.integration.filter;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.kfc.msg.events.integration.CommandVisitor;
import com.aldogrand.kfc.msg.events.integration.Filter;

@JsonTypeName("boolean")
public class BooleanFilter implements Filter {
    private final String key;
    private final boolean value;

    public BooleanFilter(String key) {
        this(key, Boolean.TRUE);
    }

    @JsonCreator
    public BooleanFilter(@JsonProperty("key") String key, @JsonProperty("value") Boolean value) {
        super();
        this.key = Validate.notBlank(key, "A key is required");
        this.value = Validate.notNull(value, "A value is required");
    }

    @Override
    public <C, T> void accept(CommandVisitor<C, T> visitor) {
        visitor.add(key, value);
    }

    public String getKey() {
        return key;
    }

    public boolean isValue() {
        return value;
    }
}
