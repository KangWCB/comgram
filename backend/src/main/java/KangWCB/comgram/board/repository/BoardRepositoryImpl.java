package KangWCB.comgram.board.repository;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.QBoard;
import KangWCB.comgram.board.dto.BoardMyListDto;
import KangWCB.comgram.follow.QFollow;
import KangWCB.comgram.member.QMember;
import KangWCB.comgram.photo.QPhoto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QBoard qBoard = QBoard.board;
    QFollow qFollow = QFollow.follow;
    QMember qMember = QMember.member;
    QPhoto qPhoto = QPhoto.photo;

    /**
     * 내 게시물 올린 갯수 조회
     * fetch join 최적화
     */
    @Override
    public Long countMyBoard(Long memberId){
        Long count = queryFactory.select(qBoard.count())
                .from(qBoard)
                .leftJoin(qBoard.member,qMember)
                .fetchJoin()
                .where(qBoard.member.id.eq(memberId))
                .fetchCount(); // fetchCount는 deprecate
        return count;
    }
    /**
     * 내가 팔로우 한 사람의 글만 보여준다.
     * 없으면 전체 사람보여준다.
     */
    @Override
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
        builder.or(qBoard.member.id.eq(memberId));

        // 팔로우한 사람 게시물 찾기
        List<Board> result = queryFactory.select(qBoard)
                .from(qBoard)
                .leftJoin(qBoard.member,qMember)
                .fetchJoin()
                .where(builder)
                .orderBy(qBoard.regTime.desc())
                .fetch();
        return result;
    }
    
    /**
     * 좋아요 많이한 게시물 순서로 6개 보여주기
     */
    @Override
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
    @Override
    public List<Board> findWordContent(String word){
        List<Board> result = queryFactory.selectFrom(qBoard)
                .where(likeWord(word))
                .orderBy(qBoard.likes.size().desc())
                .limit(6)
                .fetch();
        return result;
    }
    @Override
    public BooleanExpression likeWord(String word){
        if(StringUtils.isEmpty(word)){
            return null;
        }
        return qBoard.content.contains(word);
    }
    /**
     * 보드 게시물 하나 찾아주기
     */
    public Board findBoard(Long boardId) {
        Board board = queryFactory.select(qBoard)
                .from(qBoard)
                .leftJoin(qBoard.member, qMember)
                .fetchJoin()
                .where(qBoard.id.eq(boardId))
                .fetchOne();
        return board;
    }
    /**
     * 내가 쓴 게시물 찾아주기
     */
    @Override
    public List<BoardMyListDto> findMyList(Long memberId) {
        List<BoardMyListDto> fetch = queryFactory.select(Projections.constructor(BoardMyListDto.class,
                        qBoard.id,
                        qBoard.photo.savedPath))
                .from(qBoard)
                .leftJoin(qBoard.photo, qPhoto)
                .where(qBoard.member.id.eq(memberId))
                .fetch();
        return fetch;
    }
}
