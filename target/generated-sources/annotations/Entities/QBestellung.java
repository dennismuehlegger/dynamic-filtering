package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBestellung is a Querydsl query type for Bestellung
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBestellung extends EntityPathBase<Bestellung> {

    private static final long serialVersionUID = 1900683610L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBestellung bestellung = new QBestellung("bestellung");

    public final DatePath<java.time.LocalDate> bestDat = createDate("bestDat", java.time.LocalDate.class);

    public final ListPath<BestProd, QBestProd> bestProd = this.<BestProd, QBestProd>createList("bestProd", BestProd.class, QBestProd.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    protected QKunde kunde;

    public final DatePath<java.time.LocalDate> liefDat = createDate("liefDat", java.time.LocalDate.class);

    public QBestellung(String variable) {
        this(Bestellung.class, forVariable(variable), INITS);
    }

    public QBestellung(Path<? extends Bestellung> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBestellung(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBestellung(PathMetadata metadata, PathInits inits) {
        this(Bestellung.class, metadata, inits);
    }

    public QBestellung(Class<? extends Bestellung> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.kunde = inits.isInitialized("kunde") ? new QKunde(forProperty("kunde"), inits.get("kunde")) : null;
    }

    public QKunde kunde() {
        if (kunde == null) {
            kunde = new QKunde(forProperty("kunde"));
        }
        return kunde;
    }

}

