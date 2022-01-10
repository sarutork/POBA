package com.obu.tech.poba.utils.search;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class SearchCondition<T> implements Specification<T> {

    String fieldName;
    SearchOperator operator;
    Object value;
    boolean isOrOperator = false;

    public SearchCondition(String fieldName, SearchOperator operator, Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        switch (operator) {
            case EQUAL:
                return builder.equal(root.get(fieldName), value);
            case NOT_EQUAL:
                return builder.notEqual(root.get(fieldName), value);
            case LIKE:
                return builder.like(root.get(fieldName), "%" + value + "%");
            case NOT_LIKE:
                return builder.notLike(root.get(fieldName), "%" + value + "%");
            case IN:
                return root.get(fieldName).in(value);
            case NOT_IN:
                return root.get(fieldName).in(value).not();
            case DATE_BEFORE:
                return builder.lessThan(root.get(fieldName), (LocalDate) value);
            case DATE_BEFORE_OR_EQUAL:
                return builder.lessThanOrEqualTo(root.get(fieldName), (LocalDate) value);
            case DATE_AFTER:
                return builder.greaterThan(root.get(fieldName), (LocalDate) value);
            case DATE_AFTER_OR_EQUAL:
                return builder.greaterThanOrEqualTo(root.get(fieldName), (LocalDate) value);
        }
        return null;
    }
}
