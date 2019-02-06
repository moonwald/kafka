package com.aldogrand.kfc.pollingmanager.rules;

import com.aldogrand.sbpc.connectors.model.MetaTag.Type;

public interface RuleVisitor {

    void filterIds(DataType dataType, String[] ids);

    void entityStatus(Object[] statusValues);

    void entityType(Object[] typeValues);

    void metaTags(Type key, String[] values);

    void include(DataType dataType);

    void startDate(Duration from, Duration to);

    void filterModules(String[] modules);
}
