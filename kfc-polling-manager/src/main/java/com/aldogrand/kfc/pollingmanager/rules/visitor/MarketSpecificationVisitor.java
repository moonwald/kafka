package com.aldogrand.kfc.pollingmanager.rules.visitor;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.aldogrand.sbpc.connectors.model.MetaTag.Type;
import com.aldogrand.sbpc.model.MarketStatus;
import com.aldogrand.kfc.pollingmanager.model.MetaTag;
import com.aldogrand.kfc.pollingmanager.model.SourceEvent;
import com.aldogrand.kfc.pollingmanager.model.SourceMarket;
import com.aldogrand.kfc.pollingmanager.rules.DataType;

public class MarketSpecificationVisitor extends SpecificationVisitor<SourceMarket> {

    @Override
    public void filterIds(final DataType dataType, final String[] ids) {
        add(new Specification<SourceMarket>() {

            @Override
            public Predicate toPredicate(Root<SourceMarket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                switch (dataType) {
                    case MARKET:
                        return root.get("sourceId").in((Object[]) ids);
                    case EVENT:
                        return root.get("sourceEvent").get("sourceId").in((Object[]) ids);
                    default:
                        return null;
                }
            }

        });
    }

    @Override
    public void metaTags(final Type key, final String[] values) {
        add(new Specification<SourceMarket>() {
            @Override
            public Predicate toPredicate(Root<SourceMarket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<SourceEvent, MetaTag> metaTags = root.join("sourceEvent").join("metaTags");
                Predicate type = cb.equal(metaTags.get("type").as(com.aldogrand.sbpc.connectors.model.MetaTag.Type.class), key);
                Predicate value = metaTags.get("name").as(String.class).in(Arrays.asList(values));
                return cb.and(type, value);
            }

        });
    }

    @Override
    public void entityType(final Object[] values) {
        final String[] typeList = applyToStringToElementsOfArrayOfObjects(values);

        add(new Specification<SourceMarket>() {

            @Override
            public Predicate toPredicate(Root<SourceMarket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get("type").in((Object[]) typeList);
            }

        });
    }

    @Override
    public void entityStatus(final Object[] values) {
        isEntityStatusSpecified = true;
        final String[] statusList = applyToStringToElementsOfArrayOfObjects(values);
        add(new Specification<SourceMarket>() {

            @Override
            public Predicate toPredicate(Root<SourceMarket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get("status").in((Object[]) statusList);
            }

        });
    }

    @Override
    public Specifications<SourceMarket> specifications() {
        if (!isEntityStatusSpecified) {
            this.entityStatus(new MarketStatus[] {MarketStatus.OPEN});
        }
        return specifications;
    }
}
