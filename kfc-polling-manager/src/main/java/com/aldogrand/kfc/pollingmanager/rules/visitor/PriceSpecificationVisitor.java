package com.aldogrand.kfc.pollingmanager.rules.visitor;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.aldogrand.sbpc.connectors.model.MetaTag.Type;
import com.aldogrand.kfc.pollingmanager.model.MetaTag;
import com.aldogrand.kfc.pollingmanager.model.SourceMarket;
import com.aldogrand.kfc.pollingmanager.rules.DataType;

public class PriceSpecificationVisitor extends SpecificationVisitor<SourceMarket> {

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
    public void entityType(Object[] values) {
        final String[] typeList = applyToStringToElementsOfArrayOfObjects(values);
        add(new Specification<SourceMarket>() {

            @Override
            public Predicate toPredicate(Root<SourceMarket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get("type").in((Object[]) typeList);
            }

        });
    }

    @Override
    public void metaTags(final Type key, final String[] values) {
        add(new Specification<SourceMarket>() {
            @Override
            public Predicate toPredicate(Root<SourceMarket> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Join<SourceMarket, MetaTag> metaTag = root.join("sourceEvent").join("metaTags");
                Predicate type = cb.equal(metaTag.get("type").as(com.aldogrand.sbpc.connectors.model.MetaTag.Type.class), key);
                Predicate value = metaTag.get("name").as(String.class).in(Arrays.asList(values));
                return cb.and(type, value);
            }

        });
    }
}
