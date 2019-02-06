package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;

@JsonTypeName("id")
public class IdFilter implements Filter {

    private DataType dataType;
    private String[] ids;

    public IdFilter(DataType dataType, String[] ids) {
        this.dataType = dataType;
        this.ids = ids;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.filterIds(dataType, ids);
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    public String[] getIds() {
        return ids;
    }
}
