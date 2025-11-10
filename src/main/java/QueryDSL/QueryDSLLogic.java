package QueryDSL;

import Entities.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import Reusables.SQLDefinition;

import java.util.List;

@Getter
@Setter
public class QueryDSLLogic {
    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public <T> List<T> searchEntity(EntityPath<T> sourceEntity, List<SQLDefinition> params) {
        JPAQuery<T> query = queryFactory.selectFrom(sourceEntity);
        BooleanBuilder whereClause = new BooleanBuilder();

        QueryDSLOperatorDefinition operatorDefinition = new QueryDSLOperatorDefinition(whereClause, sourceEntity);
        for (SQLDefinition param : params) {
            operatorDefinition.checkLogicalOperator(param);
        }

        return query.where(whereClause).fetch();
    }

    public <T> List<T> searchEntityJoin(EntityPath<T> sourceQEntity, String targetEntity, List<SQLDefinition> params) {
        JPAQuery<T> query = queryFactory.selectFrom(sourceQEntity);
        BooleanBuilder whereClause = new BooleanBuilder();

        EntityPath<?> joinedEntity = createJoinLogic(sourceQEntity, query, targetEntity.toLowerCase());

        QueryDSLOperatorDefinition operatorDefinition = new QueryDSLOperatorDefinition(whereClause, joinedEntity);
        for (SQLDefinition param : params) {
            operatorDefinition.checkLogicalOperator(param);
        }

        return query.where(whereClause).fetch();
    }

    private <T> EntityPath<?> createJoinLogic(EntityPath<?> sourceEntity, JPAQuery<T> query, String targetEntity) {
        EntityPath<?> current = sourceEntity;
        String currentEntity = sourceEntity.getClass().getSimpleName().toLowerCase();
        entityValidation(currentEntity, targetEntity);

        QProdukt produkt = new QProdukt("produkt");
        QBestProd bestProd = new QBestProd("bestProd");
        QBestellung bestellung = new QBestellung("bestellung");
        QKunde kunde = new QKunde("kunde");
        QOrt ort = new QOrt("ort");
        QLandkreis landkreis = new QLandkreis("landkreis");
        QBundesland bundesland = new QBundesland("Bundesland");

        while (!currentEntity.equals(targetEntity)) {
            switch (currentEntity) {
                case "qprodukt":
                    query = query.leftJoin(produkt.bestProd, bestProd);
                    current = bestProd;
                    currentEntity = "qbestprod";
                    break;
                case "qbestprod":
                    query = query.leftJoin(bestProd.bestellung(), bestellung);
                    current = bestellung;
                    currentEntity = "qbestellung";
                    break;
                case "qbestellung":
                    query = query.leftJoin(bestellung.kunde(), kunde);
                    current = kunde;
                    currentEntity = "qkunde";
                    break;
                case "qkunde":
                    query = query.leftJoin(kunde.ort(), ort);
                    current = ort;
                    currentEntity = "qort";
                    break;
                case "qort":
                    query = query.leftJoin(ort.landkreis(), landkreis);
                    current = landkreis;
                    currentEntity = "qlandkreis";
                    break;
                case "qlandkreis":
                    query = query.leftJoin(landkreis.bundesland(), bundesland);
                    current = bundesland;
                    currentEntity = "qbundesland";
                    break;
                default:
                    return current;
            }
        }
        return current;
    }

    private void entityValidation(String sourceEntity, String targetEntity) {
        switch (sourceEntity) {
            case "qprodukt":
                if (!targetEntity.equals("bestprod") && !targetEntity.equals("bestellung") && !targetEntity.equals("kunde")
                        && !targetEntity.equals("ort") && !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "qbestprod":
                if (!targetEntity.equals("bestellung") && !targetEntity.equals("kunde") && !targetEntity.equals("ort") &&
                        !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "qbestellung":
                if (!targetEntity.equals("kunde") && !targetEntity.equals("ort") &&
                        !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "qkunde":
                if (!targetEntity.equals("ort") && !targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "qort":
                if (!targetEntity.equals("landkreis") && !targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            case "qlandkreis":
                if (!targetEntity.equals("bundesland")) {
                    throw new IllegalArgumentException("Invalid target entity: " + targetEntity + " for source entity: " + sourceEntity);
                }
                break;
            default: throw new IllegalArgumentException("Invalid source entity: " + sourceEntity);
        }
    }


    public <T> void save(T entity) {
        entityManager.persist(entity);
    }
}
