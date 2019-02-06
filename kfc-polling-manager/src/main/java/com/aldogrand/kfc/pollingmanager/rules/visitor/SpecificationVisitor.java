package com.aldogrand.kfc.pollingmanager.rules.visitor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.aldogrand.sbpc.connectors.model.MetaTag.Type;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.Duration;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

public class SpecificationVisitor<T> implements RuleVisitor {

    protected Specifications<T> specifications;
    protected boolean isEntityStatusSpecified = false;

    public Specifications<T> specifications() {
        return specifications;
    }

    @Override
    public void filterIds(DataType dataType, String[] ids) {}

    @Override
    public void entityStatus(Object[] statusValues) {}

    @Override
    public void entityType(Object[] typeValues) {}

    @Override
    public void metaTags(Type key, String[] values) {}

    @Override
    public void include(DataType dataType) {}

    @Override
    public void startDate(Duration from, Duration to) {}

    @Override
    public void filterModules(String[] modules) {}

    protected void add(Specification<T> specification) {
        if (specifications == null) {
            specifications = Specifications.where(specification);
        } else {
            specifications = specifications.and(specification);
        }
    }

    protected String[] applyToStringToElementsOfArrayOfObjects(Object[] values) {
        final String[] list = new String[values.length];
        int index = 0;
        for (Object status : values) {
            list[index] = status.toString();
            index++;
        }
        return list;
    }

}
