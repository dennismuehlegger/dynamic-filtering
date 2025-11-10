package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBestProdId is a Querydsl query type for BestProdId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBestProdId extends BeanPath<BestProdId> {

    private static final long serialVersionUID = 1305084451L;

    public static final QBestProdId bestProdId = new QBestProdId("bestProdId");

    public final NumberPath<Long> bestellungId = createNumber("bestellungId", Long.class);

    public final NumberPath<Long> produktId = createNumber("produktId", Long.class);

    public QBestProdId(String variable) {
        super(BestProdId.class, forVariable(variable));
    }

    public QBestProdId(Path<? extends BestProdId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBestProdId(PathMetadata metadata) {
        super(BestProdId.class, metadata);
    }

}

