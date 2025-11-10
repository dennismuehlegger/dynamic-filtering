package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKunde is a Querydsl query type for Kunde
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKunde extends EntityPathBase<Kunde> {

    private static final long serialVersionUID = 2127767384L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKunde kunde = new QKunde("kunde");

    protected QBestellung bestellung;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    protected QOrt ort;

    public final StringPath strasse = createString("strasse");

    public QKunde(String variable) {
        this(Kunde.class, forVariable(variable), INITS);
    }

    public QKunde(Path<? extends Kunde> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKunde(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKunde(PathMetadata metadata, PathInits inits) {
        this(Kunde.class, metadata, inits);
    }

    public QKunde(Class<? extends Kunde> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bestellung = inits.isInitialized("bestellung") ? new QBestellung(forProperty("bestellung"), inits.get("bestellung")) : null;
        this.ort = inits.isInitialized("ort") ? new QOrt(forProperty("ort"), inits.get("ort")) : null;
    }

    public QBestellung bestellung() {
        if (bestellung == null) {
            bestellung = new QBestellung(forProperty("bestellung"));
        }
        return bestellung;
    }

    public QOrt ort() {
        if (ort == null) {
            ort = new QOrt(forProperty("ort"));
        }
        return ort;
    }

}

