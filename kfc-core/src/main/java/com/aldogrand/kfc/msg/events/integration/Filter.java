package com.aldogrand.kfc.msg.events.integration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.aldogrand.kfc.msg.events.integration.filter.BooleanFilter;
import com.aldogrand.kfc.msg.events.integration.filter.KeyValueFilter;
import com.aldogrand.kfc.msg.events.integration.filter.PeriodFilter;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "@filter")
@JsonSubTypes({@Type(PeriodFilter.class), @Type(KeyValueFilter.class), @Type(BooleanFilter.class)})
public interface Filter {

    <C, T> void accept(CommandVisitor<C, T> visitor);

}
