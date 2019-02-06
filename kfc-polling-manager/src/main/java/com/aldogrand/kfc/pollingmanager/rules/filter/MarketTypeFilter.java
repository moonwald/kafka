package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.model.MarketType;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

@JsonTypeName("market-type")
public class MarketTypeFilter implements Filter {

    private MarketType[] values;

    @Override
    public DataType getDataType() {
        return DataType.MARKET;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.entityType(values);
    }

    public MarketType[] getValues() {
        return values;
    }

    public void setValues(MarketType[] values) {
        this.values = values;
    }
}
