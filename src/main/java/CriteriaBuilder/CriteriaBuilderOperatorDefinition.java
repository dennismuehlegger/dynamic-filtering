package CriteriaBuilder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.Setter;
import Reusables.SQLDefinition;

@Getter
@Setter
public class CriteriaBuilderOperatorDefinition {

    private Predicate predicate;
    private CriteriaBuilder builder;
    private From r;

    public CriteriaBuilderOperatorDefinition(Predicate predicate, CriteriaBuilder builder, From r) {
        this.predicate = predicate;
        this.builder = builder;
        this.r = r;
    }

    public void checkLogicalOperator(SQLDefinition param) {
        Predicate predicate = createPredicate(param);
        if (predicate != null) {
            if ("AND".equalsIgnoreCase(param.getLogicalOperator()) || param.getLogicalOperator() == null) {
                this.predicate = builder.and(this.predicate, predicate);
            }
            else if ("OR".equalsIgnoreCase(param.getLogicalOperator())) {
                this.predicate = builder.or(this.predicate, predicate);
            }
            else if ("NOT".equalsIgnoreCase(param.getLogicalOperator())) {
                this.predicate = builder.and(this.predicate, builder.not(predicate));
            }
            else {
                throw new IllegalArgumentException("Invalid logical operator: " + param.getLogicalOperator());
            }
        }
    }
    private Predicate createPredicate(SQLDefinition param){
        return switch (param.getOperation()) {
            case "EQUAL" -> builder.equal(r.get(param.getKey()), param.getValue());
            case "LESS_THAN" -> builder.lessThanOrEqualTo(r.get(param.getKey()), param.getValue().toString());
            case "GREATER_THAN" -> builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString());
            case "IN" -> builder.in(r.get(param.getKey())).value(param.getValue());
            case "NOT_IN" -> builder.not(builder.in(r.get(param.getKey())).value(param.getValue()));
            case "IS_NULL" -> builder.isNull(r.get(param.getKey()));
            case "IS_NOT_NULL" -> builder.isNotNull(r.get(param.getKey()));
            case "LIKE" -> builder.like(r.get(param.getKey()), "%" + param.getValue() + "%");
            default -> throw new IllegalArgumentException("Invalid operation: " + param.getOperation());
        };
    }
}
