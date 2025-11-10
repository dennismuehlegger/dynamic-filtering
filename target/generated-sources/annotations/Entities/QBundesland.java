package Entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBundesland is a Querydsl query type for Bundesland
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBundesland extends EntityPathBase<Bundesland> {

    private static final long serialVersionUID = 1613354607L;

    public static final QBundesland bundesland = new QBundesland("bundesland");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Landkreis, QLandkreis> landkreise = this.<Landkreis, QLandkreis>createList("landkreise", Landkreis.class, QLandkreis.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QBundesland(String variable) {
        super(Bundesland.class, forVariable(variable));
    }

    public QBundesland(Path<? extends Bundesland> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBundesland(PathMetadata metadata) {
        super(Bundesland.class, metadata);
    }

}

