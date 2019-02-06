package com.aldogrand.kfc.msg.events.integration.filter;

import java.util.Date;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.kfc.msg.events.integration.CommandVisitor;
import com.aldogrand.kfc.msg.events.integration.Filter;

@JsonTypeName("period")
public class PeriodFilter implements Filter {
    private final Date before;
    private final Date after;

    @JsonCreator
    public PeriodFilter(@JsonProperty("before") Date before, @JsonProperty("after") Date after) {
        super();
        this.before = Validate.notNull(before, "A before value is required");
        this.after = Validate.notNull(after, "An after values is required");
    }

    @Override
    public <C, T> void accept(CommandVisitor<C, T> visitor) {
        visitor.add("before", before);
        visitor.add("after", after);
    }

    public Date getBefore() {
        return before;
    }

    public Date getAfter() {
        return after;
    }
}
