package KangWCB.comgram.board.boardLike;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardLike is a Querydsl query type for BoardLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardLike extends EntityPathBase<BoardLike> {

    private static final long serialVersionUID = -248743161L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardLike boardLike = new QBoardLike("boardLike");

    public final KangWCB.comgram.board.QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final KangWCB.comgram.member.QMember member;

    public QBoardLike(String variable) {
        this(BoardLike.class, forVariable(variable), INITS);
    }

    public QBoardLike(Path<? extends BoardLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardLike(PathMetadata metadata, PathInits inits) {
        this(BoardLike.class, metadata, inits);
    }

    public QBoardLike(Class<? extends BoardLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new KangWCB.comgram.board.QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new KangWCB.comgram.member.QMember(forProperty("member")) : null;
    }

}

