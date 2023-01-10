package KangWCB.comgram.board.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -1675426869L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment1 = new QComment("comment1");

    public final KangWCB.comgram.board.QBoard boards;

    public final StringPath comment = createString("comment");

    public final StringPath createdDate = createString("createdDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final KangWCB.comgram.member.QMember member;

    public final StringPath modifiedDate = createString("modifiedDate");

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boards = inits.isInitialized("boards") ? new KangWCB.comgram.board.QBoard(forProperty("boards"), inits.get("boards")) : null;
        this.member = inits.isInitialized("member") ? new KangWCB.comgram.member.QMember(forProperty("member")) : null;
    }

}

