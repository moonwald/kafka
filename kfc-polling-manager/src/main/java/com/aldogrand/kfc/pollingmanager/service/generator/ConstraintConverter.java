package com.aldogrand.kfc.pollingmanager.service.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aldogrand.sbpc.connectors.model.EventStatus;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.kfc.msg.events.integration.Filter;
import com.aldogrand.kfc.msg.events.integration.filter.BooleanFilter;
import com.aldogrand.kfc.msg.events.integration.filter.KeyValueFilter;
import com.aldogrand.kfc.msg.events.integration.filter.PeriodFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.EventStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.IncludeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MarketStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.MarketTypeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.RunnerStatusFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.RunnerTypeFilter;
import com.aldogrand.kfc.pollingmanager.rules.filter.StartTimeFilter;


public class ConstraintConverter {

    public List<Filter> convertContraints(List<com.aldogrand.kfc.pollingmanager.rules.Filter> constraints) {
        List<Filter> matchingFilters = new ArrayList<Filter>();
        for (com.aldogrand.kfc.pollingmanager.rules.Filter constraint : constraints) {
            matchingFilters.add(convertConstraint(constraint));
        }
        return matchingFilters;
    }

    //TODO needs further refactoring to be more elegant than using if/else
    private Filter convertConstraint(com.aldogrand.kfc.pollingmanager.rules.Filter constraint) {
        Filter filter = null;
        if (constraint instanceof EventStatusFilter) {
            filter = convertEventStatusFilter((EventStatusFilter) constraint);
        } else if (constraint instanceof IncludeFilter) {
            filter = convertIncludesFilter((IncludeFilter) constraint);
        } else if (constraint instanceof MarketStatusFilter) {
            filter = convertMarketStatusFilter((MarketStatusFilter) constraint);
        } else if (constraint instanceof MarketTypeFilter) {
            filter = convertMarketTypeFilter((MarketTypeFilter) constraint);
        } else if (constraint instanceof RunnerStatusFilter) {
            filter = convertRunnerStatusFilter((RunnerStatusFilter) constraint);
        } else if (constraint instanceof RunnerTypeFilter) {
            filter = convertRunnerTypeFilter((RunnerTypeFilter) constraint);
        } else if (constraint instanceof StartTimeFilter) {
            filter = convertStartTimeFilter((StartTimeFilter) constraint);
        }
        return filter;
    }

    public Filter convertEventStatusFilter(EventStatusFilter eventStatusFilter) {
        List<String> names = new ArrayList<String>();
        for (EventStatus status : eventStatusFilter.getValues()) {
            names.add(status.name());
        }
        return new KeyValueFilter("status", (String[]) names.toArray());
    }

    private Filter convertIncludesFilter(IncludeFilter constraint) {
        return new BooleanFilter("include-" + constraint.getDataType().name().toLowerCase() + "s");
    }

    private Filter convertMarketStatusFilter(MarketStatusFilter constraint) {
        List<String> names = new ArrayList<String>();
        for (MarketStatus status : constraint.getValues()) {
            names.add(status.name());
        }
        return new KeyValueFilter("status", (String[]) names.toArray());
    }

    private Filter convertMarketTypeFilter(MarketTypeFilter constraint) {
        List<String> names = new ArrayList<String>();
        for (MarketType type : constraint.getValues()) {
            names.add(type.name());
        }
        return new KeyValueFilter("types", (String[]) names.toArray());
    }

    private Filter convertRunnerStatusFilter(RunnerStatusFilter constraint) {
        List<String> names = new ArrayList<String>();
        for (RunnerStatus status : constraint.getValues()) {
            names.add(status.name());
        }
        return new KeyValueFilter("status", (String[]) names.toArray());
    }

    private Filter convertRunnerTypeFilter(RunnerTypeFilter constraint) {
        List<String> names = new ArrayList<String>();
        for (RunnerType type : constraint.getValues()) {
            names.add(type.name());
        }
        return new KeyValueFilter("types", (String[]) names.toArray());
    }

    private Filter convertStartTimeFilter(StartTimeFilter constraint) {
        return new PeriodFilter(new Date(constraint.getFrom().getDuration()), new Date(constraint.getTo().getDuration()));
    }
}
