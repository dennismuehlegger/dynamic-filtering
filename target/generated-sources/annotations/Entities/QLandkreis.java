package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLandkreis is a Querydsl query type for Landkreis
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLandkreis extends EntityPathBase<Landkreis> {

    private static final long serialVersionUID = 2130088080L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLandkreis landkreis = new QLandkreis("landkreis");

    protected QBundesland bundesland;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Ort, QOrt> orte = this.<Ort, QOrt>createList("orte", Ort.class, QOrt.class, PathInits.DIRECT2);

    public QLandkreis(String variable) {
        this(Landkreis.class, forVariable(variable), INITS);
    }

    public QLandkreis(Path<? extends Landkreis> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLandkreis(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLandkreis(PathMetadata metadata, PathInits inits) {
        this(Landkreis.class, metadata, inits);
    }

    public QLandkreis(Class<? extends Landkreis> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bundesland = inits.isInitialized("bundesland") ? new QBundesland(forProperty("bundesland")) : null;
    }

    public QBundesland bundesland() {
        if (bundesland == null) {
            bundesland = new QBundesland(forProperty("bundesland"));
        }
        return bundesland;
    }

}

