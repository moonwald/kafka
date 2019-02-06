package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.connectors.model.EventStatus;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

@JsonTypeName("event-status")
public class EventStatusFilter implements Filter {

    private EventStatus[] values;

    @Override
    public DataType getDataType() {
        return DataType.EVENT;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.entityStatus(values);
    }

    public EventStatus[] getValues() {
        return values;
    }

    public void setValues(EventStatus[] values) {
        this.values = values;
    }
}
