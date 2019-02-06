package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

@JsonTypeName("start-time")
public class StartTimeFilter implements Filter {

    private Duration from;

    private Duration to;

    @Override
    public DataType getDataType() {
        return DataType.EVENT;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.startDate(from, to);
    }

    public Duration getFrom() {
        return from;
    }

    public void setFrom(Duration from) {
        this.from = from;
    }

    public Duration getTo() {
        return to;
    }

    public void setTo(Duration to) {
        this.to = to;
    }
}
