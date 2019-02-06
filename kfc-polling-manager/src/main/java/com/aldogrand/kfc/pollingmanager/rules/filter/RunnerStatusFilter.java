package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

@JsonTypeName("runner-status")
public class RunnerStatusFilter implements Filter {

    private RunnerStatus[] values;

    @Override
    public DataType getDataType() {
        return DataType.RUNNER;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.entityStatus(values);
    }

    public RunnerStatus[] getValues() {
        return values;
    }

    public void setValues(RunnerStatus[] values) {
        this.values = values;
    }

}
