package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;

@JsonTypeName("module")
public class ModuleFilter implements Filter {

    private String[] values;

    @Override
    public DataType getDataType() {
        return DataType.EVENT;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.filterModules(values);
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
}
