package KangWCB.comgram.board.repository;

import KangWCB.comgram.board.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final JPAQueryFactory queryFactory;
    QBoard qBoard = QBoard.board;

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

}
