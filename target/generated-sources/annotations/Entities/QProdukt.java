package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProdukt is a Querydsl query type for Produkt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProdukt extends EntityPathBase<Produkt> {

    private static final long serialVersionUID = 437629114L;

    public static final QProdukt produkt = new QProdukt("produkt");

    public final ListPath<BestProd, QBestProd> bestProd = this.<BestProd, QBestProd>createList("bestProd", BestProd.class, QBestProd.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Double> preis = createNumber("preis", Double.class);

    public QProdukt(String variable) {
        super(Produkt.class, forVariable(variable));
    }

    public QProdukt(Path<? extends Produkt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProdukt(PathMetadata metadata) {
        super(Produkt.class, metadata);
    }

}

