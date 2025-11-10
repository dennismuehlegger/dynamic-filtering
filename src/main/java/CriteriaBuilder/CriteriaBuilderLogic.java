package CriteriaBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;
import Reusables.SQLDefinition;

import java.util.List;

@Getter
@Setter
public class CriteriaBuilderLogic {
    @PersistenceContext
    private EntityManager entityManager;

    public <T> List<T> searchEntity(Class<T> sourceEntityClass, List<SQLDefinition> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(sourceEntityClass);
        Root<T> r = query.from(sourceEntityClass);
        Predicate predicate = builder.conjunction();

        CriteriaBuilderOperatorDefinition operatorDefinition = new CriteriaBuilderOperatorDefinition(predicate, builder, r);
        for (SQLDefinition param : params) {
            operatorDefinition.checkLogicalOperator(param);
        }
        predicate = operatorDefinition.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    public <T> List<T> searchEntityJoin(Class<T> sourceEntityClass, String targetEntity, List<SQLDefinition> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(sourceEntityClass);
        Root<T> sourceRoot = query.from(sourceEntityClass);
        Predicate predicate = builder.conjunction();

        From<T, ?> joinedEntity = createJoinLogic(sourceRoot, targetEntity);

        CriteriaBuilderOperatorDefinition operatorDefinition = new CriteriaBuilderOperatorDefinition(predicate, builder, joinedEntity);
        for (SQLDefinition param : params) {
            operatorDefinition.checkLogicalOperator(param);
        }
        predicate = operatorDefinition.getPredicate();
        query.where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    private <T> From<T, ?> createJoinLogic(Root<T> sourceEntity, String targetEntity) {
        From<T, ?> current = sourceEntity;
        String currentEntity = sourceEntity.getJavaType().getSimpleName().toLowerCase();
        entityValidation(currentEntity, targetEntity);

        while (!currentEntity.equals(targetEntity.toLowerCase())) {
            switch (currentEntity) {
                case "produkt":
                    current = current.join("bestProd", JoinType.LEFT);
                    currentEntity = "bestprod";
                    break;
                case "bestprod":
                    current = current.join("bestellung", JoinType.LEFT);
                    currentEntity = "bestellung";
                    break;
                case "bestellung":
                    current = current.join("kunde", JoinType.LEFT);
                    currentEntity = "kunde";
                    break;
                case "kunde":
                    current = current.join("ort", JoinType.LEFT);
                    currentEntity = "ort";
                    break;
                case "ort":
                    current = current.join("landkreis", JoinType.LEFT);
                    currentEntity = "landkreis";
                    break;
                case "landkreis":
                    current = current.join("bundesland", JoinType.LEFT);
                    currentEntity = "bundesland";
                    break;
                default:
                    return current;
            }
        }
        return current;
    }

    private void entityValidation(String sourceEntity, String targetEntity) {
        switch (sourceEntity) {
            case "produkt":
                if (!targetEntity.equals("bestprod") && !targetEntity.equals("bestellung") && !targetEntity.equals("kunde")
                        && !targetEntity.equals("ort") && !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "bestprod":
                if (!targetEntity.equals("bestellung") && !targetEntity.equals("kunde") && !targetEntity.equals("ort") &&
                        !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "bestellung":
                if (!targetEntity.equals("kunde") && !targetEntity.equals("ort") &&
                        !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "kunde":
                if (!targetEntity.equals("ort") && !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "ort":
                if (!targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "landkreis":
                if (!targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid source entity: " + sourceEntity);
        }
    }

    public <T> void save(T entity) {
        entityManager.persist(entity);
    }
}
