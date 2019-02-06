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
import com.aldogrand.sbpc.model.RunnerStatus;
import com.aldogrand.kfc.pollingmanager.model.MetaTag;
import com.aldogrand.kfc.pollingmanager.model.SourceMarket;
import com.aldogrand.kfc.pollingmanager.model.SourceRunner;
import com.aldogrand.kfc.pollingmanager.rules.DataType;
import com.aldogrand.kfc.pollingmanager.rules.RuleVisitor;

public class RunnerSpecificationVisitor extends SpecificationVisitor<SourceRunner> implements RuleVisitor {

    @Override
    public void filterIds(final DataType dataType, final String[] ids) {
        add(new Specification<SourceRunner>() {

            @Override
            public Predicate toPredicate(Root<SourceRunner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                switch (dataType) {
                    case RUNNER:
                        return root.get("sourceId").in((Object[]) ids);
                    case MARKET:
                        return root.get("sourceMarket").get("sourceId").in((Object[]) ids);
                    case EVENT:
                        return root.get("sourceMarket").get("sourceEvent").get("sourceId").in((Object[]) ids);
                    default:
                        return null;
                }
            }

        });
    }

    @Override
    public void metaTags(final Type key, final String[] values) {
        add(new Specification<SourceRunner>() {
            @Override
            public Predicate toPredicate(Root<SourceRunner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<SourceMarket, MetaTag> metaTag = root.join("sourceMarket").join("sourceEvent").join("metaTags");
                Predicate type = cb.equal(metaTag.get("type").as(com.aldogrand.sbpc.connectors.model.MetaTag.Type.class), key);
                Predicate value = metaTag.get("name").as(String.class).in(Arrays.asList(values));
                return cb.and(type, value);
            }
        });
    }

    @Override
    public void entityStatus(Object[] values) {
        isEntityStatusSpecified = true;
        final String[] statusList = applyToStringToElementsOfArrayOfObjects(values);

        add(new Specification<SourceRunner>() {

            @Override
            public Predicate toPredicate(Root<SourceRunner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get("status").in((Object[]) statusList);
            }

        });
    }

    @Override
    public void entityType(final Object[] values) {
        final String[] typeList = Arrays.copyOf(values, values.length, String[].class);
        add(new Specification<SourceRunner>() {

            @Override
            public Predicate toPredicate(Root<SourceRunner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return root.get("type").in((Object[]) typeList);
            }
        });
    }

    @Override
    public Specifications<SourceRunner> specifications() {
        if (!isEntityStatusSpecified) {
            this.entityStatus(new RunnerStatus[] {RunnerStatus.OPEN});
        }
        return specifications;
    }


}
