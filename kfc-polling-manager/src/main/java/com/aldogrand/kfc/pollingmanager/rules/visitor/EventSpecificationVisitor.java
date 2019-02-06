package com.aldogrand.kfc.pollingmanager.rules.visitor;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.aldogrand.sbpc.connectors.model.EventStatus;
import com.aldogrand.sbpc.connectors.model.MetaTag.Type;
import com.aldogrand.kfc.pollingmanager.model.MetaTag;
import com.aldogrand.kfc.pollingmanager.model.SourceEvent;
import com.aldogrand.kfc.pollingmanager.rules.DataType;

public class EventSpecificationVisitor extends SpecificationVisitor<SourceEvent> {

    @Override
    public void entityStatus(final Object[] values) {
        isEntityStatusSpecified = true;
        final String[] statusList = applyToStringToElementsOfArrayOfObjects(values);

        add(new Specification<SourceEvent>() {

            @Override
            public Predicate toPredicate(Root<SourceEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get("status").in((Object[]) statusList);
            }

        });
    }

    @Override
    public void filterIds(DataType dataType, final String[] ids) {
        add(new Specification<SourceEvent>() {

            @Override
            public Predicate toPredicate(Root<SourceEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get("sourceId").in((Object[]) ids);
            }

        });
    }

    @Override
    public void metaTags(final Type key, final String[] values) {
        add(new Specification<SourceEvent>() {
            @Override
            public Predicate toPredicate(Root<SourceEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<SourceEvent, MetaTag> metaTag = root.join("metaTags");
                Predicate type = cb.equal(metaTag.get("type").as(com.aldogrand.sbpc.connectors.model.MetaTag.Type.class), key);
                Predicate value = metaTag.get("name").as(String.class).in(Arrays.asList(values));
                return cb.and(type, value);
            }

        });
    }

    @Override
    public Specifications<SourceEvent> specifications() {
        if (!isEntityStatusSpecified) {
            entityStatus(new EventStatus[] {EventStatus.OPEN});
        }
        return specifications;
    }
}
