package KangWCB.comgram.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -1202576735L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final KangWCB.comgram.config.audit.QBaseTimeEntity _super = new KangWCB.comgram.config.audit.QBaseTimeEntity(this);

    public final ListPath<KangWCB.comgram.board.comment.Comment, KangWCB.comgram.board.comment.QComment> comments = this.<KangWCB.comgram.board.comment.Comment, KangWCB.comgram.board.comment.QComment>createList("comments", KangWCB.comgram.board.comment.Comment.class, KangWCB.comgram.board.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<KangWCB.comgram.board.boardLike.BoardLike, KangWCB.comgram.board.boardLike.QBoardLike> likes = this.<KangWCB.comgram.board.boardLike.BoardLike, KangWCB.comgram.board.boardLike.QBoardLike>createSet("likes", KangWCB.comgram.board.boardLike.BoardLike.class, KangWCB.comgram.board.boardLike.QBoardLike.class, PathInits.DIRECT2);

    public final KangWCB.comgram.member.QMember member;

    public final KangWCB.comgram.photo.QPhoto photo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new KangWCB.comgram.member.QMember(forProperty("member"), inits.get("member")) : null;
        this.photo = inits.isInitialized("photo") ? new KangWCB.comgram.photo.QPhoto(forProperty("photo")) : null;
    }

}

