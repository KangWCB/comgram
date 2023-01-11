package KangWCB.comgram.board.boardLike.repository;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.boardLike.BoardLike;
import KangWCB.comgram.board.boardLike.QBoardLike;
import KangWCB.comgram.member.Member;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardLikeQueryRepository {

    private final JPAQueryFactory queryFactory;
    QBoardLike boardLike = QBoardLike.boardLike;

    public Member findLikeMember(Board board) {
        Member member = queryFactory.select(boardLike.member)
                .from(boardLike)
                .where(boardLike.board.eq(board))
                .fetchFirst();

        return member;
    }

    public boolean isPush(Member member,Board board){

        if(queryFactory.select(boardLike)
                .from(boardLike)
                .where(boardLike.board.eq(board), boardLike.member.eq(member))
                .fetch().isEmpty()){
            return false;
        }
        return true;

    }
}
