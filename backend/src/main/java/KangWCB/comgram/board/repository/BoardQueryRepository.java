package KangWCB.comgram.board.repository;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.QBoard;
import KangWCB.comgram.follow.QFollow;
import KangWCB.comgram.follow.repository.FollowQueryRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final JPAQueryFactory queryFactory;
    QBoard qBoard = QBoard.board;
    QFollow qFollow = QFollow.follow;
    QMember qMember = QMember.member;

    /**
     * 내 게시물 올린 갯수 조회
     */
    public Long countMyBoard(Long memberId){
        Long count = queryFactory.select(qBoard)
                .from(qBoard)
                .where(qBoard.member.id.eq(memberId))
                .fetchCount();
        return count;
    }
    //  Page(4) 로 FOLLOWING 하고 있는 사람들 게시물만 가져오기
//    public Page

    /**
     * 내가 팔로우 한 사람의 글만 보여준다.
     * 없으면 전체 사람보여준다.
     */
    public QueryResults<Board>findFollowingBoard(Long memberId){
        List<Long> followingId = queryFactory.select(qFollow.following.id)
                .from(qFollow)
                .where(qFollow.follower.id.eq(memberId))
                .fetch();

        QueryResults<Board> boardQueryResults = queryFactory.select(qBoard)
                .from(qBoard)
                .where(eqFollowing(followingId))
                .orderBy(qBoard.regTime.desc())
                .fetchResults();

        return boardQueryResults;
    }

    private BooleanExpression eqFollowing(List<Long> followingId) {
        if (followingId.isEmpty()) {
            return null;
        }
        return qBoard.member.eq((Member) followingId.stream().collect(Collectors.toList()));
    }
}
