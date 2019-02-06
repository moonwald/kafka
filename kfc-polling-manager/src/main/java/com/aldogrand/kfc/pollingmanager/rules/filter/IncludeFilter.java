package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

@JsonTypeName("include")
public class IncludeFilter implements Filter {

    private DataType dataType;

    private Boolean value;

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.include(dataType);
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
