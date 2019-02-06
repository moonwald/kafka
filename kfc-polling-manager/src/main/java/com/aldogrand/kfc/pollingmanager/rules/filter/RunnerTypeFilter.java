package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.model.RunnerType;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

@JsonTypeName("runner-type")
public class RunnerTypeFilter implements Filter {

    private RunnerType[] values;

    @Override
    public DataType getDataType() {
        return DataType.RUNNER;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.entityType(values);
    }

    public RunnerType[] getValues() {
        return values;
    }

    public void setValues(RunnerType[] values) {
        this.values = values;
    }
}
