package KangWCB.comgram.board.repository;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.QBoard;
import KangWCB.comgram.board.boardLike.QBoardLike;
import KangWCB.comgram.follow.QFollow;
import KangWCB.comgram.follow.repository.FollowQueryRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final JPAQueryFactory queryFactory;
    QBoard qBoard = QBoard.board;
    QFollow qFollow = QFollow.follow;
    QMember qMember = QMember.member;
    QBoardLike qBoardLike = QBoardLike.boardLike;

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
    public List<Board> findFollowingBoard(Long memberId){
        BooleanBuilder builder = new BooleanBuilder(); // where문 작성을 위해

        // 팔로우한 사람을 찾아서 where절에 or로 전부 넣어주기
        List<Long> followingId = queryFactory.select(qFollow.following.id)
                .from(qFollow)
                .where(qFollow.follower.id.eq(memberId))
                .fetch();
        for (Long following : followingId) {
            builder.or(qBoard.member.id.eq(following));
        }

        // 팔로우한 사람 게시물 찾기
        List<Board> result = queryFactory.select(qBoard)
                .from(qBoard)
                .where(builder)
                .orderBy(qBoard.regTime.desc())
                .fetch();

        return result;
    }

    /**
     * 좋아요 많이한 게시물 순서로 6개 보여주기
     */
    public List<Board> orderByLike(){
        List<Board> result = queryFactory.selectFrom(qBoard)
                .orderBy(qBoard.likes.size().desc())
                .limit(6L)
                .fetch();
        return result;
    }

    /**
     * 게시물에 해당 내용이 들어있나 판별해서 보여주기(6개)
     */
    public List<Board> findWordContent(String word){
        List<Board> result = queryFactory.selectFrom(qBoard)
                .where(likeWord(word))
                .orderBy(qBoard.likes.size().desc())
                .limit(6)
                .fetch();

        return result;
    }
    private BooleanExpression likeWord(String word){
        if(StringUtils.isEmpty(word)){
            return null;
        }
        return qBoard.content.contains(word);
    }




}
