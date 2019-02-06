package com.aldogrand.kfc.msg.events.integration.filter;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.kfc.msg.events.integration.CommandVisitor;
import com.aldogrand.kfc.msg.events.integration.Filter;


@JsonTypeName("key-value")
public class KeyValueFilter implements Filter {
    private final String key;
    private final String[] values;


    @JsonCreator
    public KeyValueFilter(@JsonProperty("key") String key, @JsonProperty("values") String... values) {
        super();
        this.key = Validate.notBlank(key, "A key is required");
        this.values = Validate.notEmpty(values, "Values cannot be empty.");
    }

    @Override
    public <C, T> void accept(CommandVisitor<C, T> visitor) {
        visitor.add(key, values);
    }

    public String getKey() {
        return key;
    }

    public String[] getValues() {
        return values;
    }



}
