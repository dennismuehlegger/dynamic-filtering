package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBestProd is a Querydsl query type for BestProd
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBestProd extends EntityPathBase<BestProd> {

    private static final long serialVersionUID = -767356184L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBestProd bestProd = new QBestProd("bestProd");

    protected QBestellung bestellung;

    protected QBestProdId id;

    protected QProdukt produkt;

    public QBestProd(String variable) {
        this(BestProd.class, forVariable(variable), INITS);
    }

    public QBestProd(Path<? extends BestProd> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBestProd(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBestProd(PathMetadata metadata, PathInits inits) {
        this(BestProd.class, metadata, inits);
    }

    public QBestProd(Class<? extends BestProd> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bestellung = inits.isInitialized("bestellung") ? new QBestellung(forProperty("bestellung"), inits.get("bestellung")) : null;
        this.id = inits.isInitialized("id") ? new QBestProdId(forProperty("id")) : null;
        this.produkt = inits.isInitialized("produkt") ? new QProdukt(forProperty("produkt")) : null;
    }

    public QBestellung bestellung() {
        if (bestellung == null) {
            bestellung = new QBestellung(forProperty("bestellung"));
        }
        return bestellung;
    }

    public QBestProdId id() {
        if (id == null) {
            id = new QBestProdId(forProperty("id"));
        }
        return id;
    }

    public QProdukt produkt() {
        if (produkt == null) {
            produkt = new QProdukt(forProperty("produkt"));
        }
        return produkt;
    }

}

