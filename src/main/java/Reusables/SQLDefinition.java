package Reusables;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SQLDefinition {
    private String key;
    private String operation;
    private Object value;
    private String logicalOperator;

    public SQLDefinition(String key, String operation, Object value, String logicalOperator) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.logicalOperator = logicalOperator;
    }
    public SQLDefinition(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
    public SQLDefinition(String key, String operation) {
        this.key = key;
        this.operation = operation;
    }
}
