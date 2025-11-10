package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrt is a Querydsl query type for Ort
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrt extends EntityPathBase<Ort> {

    private static final long serialVersionUID = 96072516L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrt ort = new QOrt("ort");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Kunde, QKunde> kunden = this.<Kunde, QKunde>createList("kunden", Kunde.class, QKunde.class, PathInits.DIRECT2);

    protected QLandkreis landkreis;

    public final StringPath name = createString("name");

    public final StringPath plz = createString("plz");

    public QOrt(String variable) {
        this(Ort.class, forVariable(variable), INITS);
    }

    public QOrt(Path<? extends Ort> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrt(PathMetadata metadata, PathInits inits) {
        this(Ort.class, metadata, inits);
    }

    public QOrt(Class<? extends Ort> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.landkreis = inits.isInitialized("landkreis") ? new QLandkreis(forProperty("landkreis"), inits.get("landkreis")) : null;
    }

    public QLandkreis landkreis() {
        if (landkreis == null) {
            landkreis = new QLandkreis(forProperty("landkreis"));
        }
        return landkreis;
    }

}

