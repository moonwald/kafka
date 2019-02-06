package com.aldogrand.kfc.pollingmanager.rules;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * <p>
 * <b>Title</b> Rule.java
 * </p>
 * <p>
 * <b>Description</b> Rule <br/>
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2015
 * </p>
 * 
 * @author aldogrand
 * @version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class Rule {

    private final RuleType ruleType;
    private final DataType dataType;
    private final Duration periodicity;

    private Date expirationTime;
    private Date startDate;
    private List<Filter> filters;
    private List<String> accounts;
    private boolean onlyInterestedClients;
    private Boolean enabled = true;

    public enum FilterDestination {
        DATABASE, INTEGRATION_MODULE
    }

    @JsonCreator
    public Rule(@JsonProperty("ruleType") RuleType ruleType, @JsonProperty("dataType") DataType dataType,
                    @JsonProperty("periodicity") Duration periodicity) {
        super();
        Validate.notNull(ruleType, "A ruleType is required");
        Validate.notNull(dataType, "A dataType is required");
        Validate.notNull(periodicity, "A periodicity is required");
        Validate.validState(periodicity.getDuration() > 0, "The periodicity must be positive[ %s]", periodicity.getDuration());

        this.ruleType = ruleType;
        this.dataType = dataType;
        this.periodicity = periodicity;
    }


    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public boolean isOnlyInterestedClients() {
        return onlyInterestedClients;
    }

    public void setOnlyInterestedClients(boolean onlyInterestedClients) {
        this.onlyInterestedClients = onlyInterestedClients;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Duration getPeriodicity() {
        return periodicity;
    }

    // public void accept(RuleVisitor visitor, FilterDestination filterDestination) {
    public void accept(RuleVisitor visitor, FilterDestination filterDestination) {

        if (filters == null) {
            return;
        }

        List<Filter> filtersForDatabase = new ArrayList<Filter>();
        List<Filter> filtersForIntegrationModule = new ArrayList<Filter>();

        switch (ruleType) {
            case ALL:
                for (Filter filter : filters) {
                    if (dataType == DataType.POSITION) {
                        filtersForIntegrationModule.add(filter);
                    } else if (dataType.ordinal() > filter.getDataType().ordinal()) {
                        filtersForDatabase.add(filter);
                    } else {
                        filtersForIntegrationModule.add(filter);
                    }
                }
                break;
            case REFRESH:
                for (Filter filter : filters) {
                    if (filter.getDataType() == null) {
                        filtersForIntegrationModule.add(filter);
                    } else if (dataType.ordinal() >= filter.getDataType().ordinal()) {
                        filtersForDatabase.add(filter);
                    } else {
                        filtersForIntegrationModule.add(filter);
                    }
                }
                break;
            default:
                throw new IllegalStateException("ruleType should be either ALL or REFRESH");
        }

        List<Filter> filtersForVisitor;
        switch (filterDestination) {
            case DATABASE:
                filtersForVisitor = filtersForDatabase;
                break;
            case INTEGRATION_MODULE:
                filtersForVisitor = filtersForIntegrationModule;
                break;
            default:
                throw new IllegalStateException("filterDestination should be either DATABASE or INTEGRATION_MODULE");
        }

        for (Filter filter : filtersForVisitor) {
            filter.accept(visitor);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Filter> getMatchingFilters(DataType refreshDataType) {
        List<Filter> matchingFilters = new ArrayList<Filter>();
        if (filters != null) {
            for (Filter filter : filters) {
                DataType dataType = filter.getDataType();
                switch (refreshDataType) {
                    case EVENT: {
                        if (shouldAddEventFilter(dataType)) {
                            matchingFilters.add(filter);
                        }
                    }
                        break;
                    case MARKET: {
                        if (shouldAddMarketFilter(dataType)) {
                            matchingFilters.add(filter);
                        }
                    }
                        break;
                    case RUNNER: {
                        if (shouldAddRunnerFilter(dataType)) {
                            matchingFilters.add(filter);
                        }
                    }
                        break;
                    default:
                        break;
                }
            }
        }
        return matchingFilters;
    }

    private boolean shouldAddRunnerFilter(DataType dataType) {
        return shouldAddEventFilter(dataType) && !dataType.equals(DataType.MARKET) && !dataType.equals(DataType.RUNNER);
    }

    private boolean shouldAddMarketFilter(DataType dataType) {
        return shouldAddEventFilter(dataType) && !dataType.equals(DataType.MARKET);
    }

    private boolean shouldAddEventFilter(DataType dataType) {
        return !dataType.equals(DataType.EVENT);
    }

    @Override
    public String toString() {
        return "Rule [ruleType=" + ruleType + ", dataType=" + dataType + ", periodicity=" + periodicity + ", expirationTime="
                        + expirationTime + ", startDate=" + startDate + ", filters=" + filters + ", accounts=" + accounts
                        + ", onlyInterestedClients=" + onlyInterestedClients + ", enabled=" + enabled + "]";
    }
}
