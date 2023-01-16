package KangWCB.comgram.board.boardLike.repository;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.boardLike.QBoardLike;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardLikeRepositoryImpl implements BoardLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QBoardLike boardLike = QBoardLike.boardLike;
    QMember qMember =QMember.member;

    @Override
    public List<Member> findLikeMember(Board board) {
        List<Member> member = queryFactory.select(boardLike.member)
                .from(boardLike)
                .leftJoin(boardLike.member, qMember)
                .where(boardLike.board.eq(board))
                .fetch();

        return member;
    }

    @Override
    public boolean isPush(Member member,Board board){

        return !queryFactory.select(boardLike)
                .from(boardLike)
                .where(boardLike.board.eq(board), boardLike.member.eq(member))
                .fetch().isEmpty();

    }
}
