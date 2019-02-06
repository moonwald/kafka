package com.aldogrand.kfc.pollingmanager.rules.filter;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.aldogrand.sbpc.connectors.model.MetaTag;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Filter;

@JsonTypeName("meta-tag")
public class MetaTagFilter implements Filter {

    private MetaTag.Type key;

    private String[] values;

    @Override
    public DataType getDataType() {
        return DataType.EVENT;
    }

    @Override
    public void accept(RuleVisitor visitor) {
        visitor.metaTags(key, values);
    }

    public MetaTag.Type getKey() {
        return key;
    }

    public void setKey(MetaTag.Type key) {
        this.key = key;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
}
