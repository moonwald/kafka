package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

@JsonTypeName("market-status")
public class MarketStatusFilter implements Filter {

    private MarketStatus[] values;

    @Override
    public DataType getDataType() {
        return DataType.MARKET;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.entityStatus(values);
    }
    
    public MarketStatus[] getValues() {
        return values;
    }

    public void setValues(MarketStatus[] values) {
        this.values = values;
    }
}
