package com.aldogrand.kfc.pollingmanager.rules;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.aldogrand.kfc.pollingmanager.rules.filter.EventStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.IncludeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MarketTypeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MetaTagFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.ModuleFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.StartTimeFilter;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "@filter")
@JsonSubTypes({@Type(EventStatusFilter.class), @Type(MetaTagFilter.class), @Type(StartTimeFilter.class), @Type(MarketTypeFilter.class),
                @Type(IncludeFilter.class), @Type(ModuleFilter.class)})
public interface Filter {

    DataType getDataType();

    void accept(RuleVisitor visitor);
}
