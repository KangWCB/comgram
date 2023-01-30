package KangWCB.comgram.board.comment.repository;

import KangWCB.comgram.board.QBoard;
import KangWCB.comgram.board.comment.QComment;
import KangWCB.comgram.board.comment.dto.BoardCommentInfo;
import KangWCB.comgram.member.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    QBoard qBoard =QBoard.board;
    QComment qComment = QComment.comment1;
    QMember qMember = QMember.member;

    @Override
    public List<BoardCommentInfo> findBoardComment(Long boardId){
        List<BoardCommentInfo> result = queryFactory.select(Projections.constructor(BoardCommentInfo.class,
                        qComment.id,
                        qComment.member.nickName,
                        qComment.comment,
                        qComment.createdDate,
                        qComment.modifiedDate))
                .from(qComment)
                .join(qComment.boards, qBoard)
                .join(qComment.member, qMember)
                .where(qComment.boards.id.eq(boardId))
                .fetch();

        return result;
    }
}
