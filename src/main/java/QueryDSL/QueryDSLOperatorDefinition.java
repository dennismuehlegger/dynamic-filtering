package QueryDSL;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.*;
import Reusables.SQLDefinition;

import java.util.Collection;

public class QueryDSLOperatorDefinition {
    private BooleanBuilder booleanBuilder;
    private EntityPath<?> sourceEntity;

    public QueryDSLOperatorDefinition(BooleanBuilder whereClause, EntityPath<?> sourceEntity) {
        this.booleanBuilder = whereClause;
        this.sourceEntity = sourceEntity;
    }

    public void checkLogicalOperator(SQLDefinition param) {
        BooleanExpression predicate = createPredicate(param);
        if (predicate != null) {
            if ("AND".equalsIgnoreCase(param.getLogicalOperator()) || param.getLogicalOperator() == null) {
                booleanBuilder.and(predicate);
            } else if ("OR".equalsIgnoreCase(param.getLogicalOperator())) {
                booleanBuilder.or(predicate);
            } else if ("NOT".equalsIgnoreCase(param.getLogicalOperator())) {
                booleanBuilder.and(predicate.not());
            } else {
                throw new IllegalArgumentException("Invalid logical operator: " + param.getLogicalOperator());
            }
        }
    }

    private BooleanExpression createPredicate(SQLDefinition param) {
        String fieldName = param.getKey();
        Object value = param.getValue();

        return switch (param.getOperation()) {
            case "EQUAL" -> Expressions.simplePath(Object.class, sourceEntity, fieldName).eq(value);
            case "LESS_THAN" -> {
                if (value instanceof Integer) {
                    yield Expressions.numberPath(Integer.class, sourceEntity, fieldName).lt((Integer) value);
                } else if (value instanceof Double) {
                    yield Expressions.numberPath(Double.class, sourceEntity, fieldName).lt((Double) value);
                } else {
                    throw new IllegalArgumentException("Operation only supports Integer and Double.");
                }
            }
            case "GREATER_THAN" -> {
                if (value instanceof Integer) {
                    yield Expressions.numberPath(Integer.class, sourceEntity, fieldName).gt((Integer) value);
                } else if (value instanceof Double) {
                    yield Expressions.numberPath(Double.class, sourceEntity, fieldName).gt((Double) value);
                } else {
                    throw new IllegalArgumentException("Operation only supports Integer and Double.");
                }
            }
            case "IN" -> Expressions.simplePath(Object.class, sourceEntity, fieldName).in((Collection<?>) value);
            case "NOT_IN" -> Expressions.simplePath(Object.class, sourceEntity, fieldName).notIn((Collection<?>) value);
            case "IS_NULL" -> Expressions.simplePath(Object.class, sourceEntity, fieldName).isNull();
            case "IS_NOT_NULL" -> Expressions.simplePath(Object.class, sourceEntity, fieldName).isNotNull();
            case "LIKE" -> Expressions.stringPath(sourceEntity, fieldName).like("%" + value + "%");
            default -> throw new IllegalArgumentException("Invalid operation: " + param.getOperation());
        };

    }

}
