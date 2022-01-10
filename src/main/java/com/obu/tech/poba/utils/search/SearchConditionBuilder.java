package com.obu.tech.poba.utils.search;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchConditionBuilder<T> {

    private final List<SearchCondition<T>> arguments = new ArrayList<>();

    public SearchConditionBuilder<T> and(String fieldName, SearchOperator operator, Object value) {
        arguments.add(new SearchCondition<>(fieldName, operator, value));
        return this;
    }

    public SearchConditionBuilder<T> or(String fieldName, SearchOperator operator, Object value) {
        arguments.add(new SearchCondition<>(fieldName, operator, value, true));
        return this;
    }

    public SearchConditionBuilder<T> ifNotNullThenAnd(String fieldName, SearchOperator operator, Object value) {
        if (Objects.nonNull(value)) {
            return and(fieldName, operator, value);
        }
        return this;
    }

    public SearchConditionBuilder<T> ifNotNullThenOr(String fieldName, SearchOperator operator, Object value) {
        if (Objects.nonNull(value)) {
            return or(fieldName, operator, value);
        }
        return this;
    }

    public Specification<T> build() {
        Specification<T> result = null;
        if (!arguments.isEmpty()) {
            result = Specification.where(arguments.get(0)); // WHERE ...
            for (int i = 1; i < arguments.size(); ++i) {
                SearchCondition<T> condition = arguments.get(i);
                result = condition.isOrOperator
                        ? result.or(condition) // OR ...
                        : result.and(condition); // AND ...
            }
        }
        return result;
    }
}
