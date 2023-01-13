package KangWCB.comgram.photo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPhoto is a Querydsl query type for Photo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhoto extends EntityPathBase<Photo> {

    private static final long serialVersionUID = 1626937401L;

    public static final QPhoto photo = new QPhoto("photo");

    public final KangWCB.comgram.config.audit.QBaseTimeEntity _super = new KangWCB.comgram.config.audit.QBaseTimeEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath orgNm = createString("orgNm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath savedNm = createString("savedNm");

    public final StringPath savedPath = createString("savedPath");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QPhoto(String variable) {
        super(Photo.class, forVariable(variable));
    }

    public QPhoto(Path<? extends Photo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhoto(PathMetadata metadata) {
        super(Photo.class, metadata);
    }

}

