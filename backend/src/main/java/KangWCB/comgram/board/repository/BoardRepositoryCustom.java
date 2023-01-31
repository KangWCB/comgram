package KangWCB.comgram.board.repository;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.dto.BoardMyListDto;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface BoardRepositoryCustom {
    Long countMyBoard(Long memberId);

    List<Board> findFollowingBoard(Long memberId);

    List<Board> orderByLike();

    List<Board> findWordContent(String word);
    BooleanExpression likeWord(String word);
    Board findBoard(Long boardId);

    /**
     * 내가 쓴 글 들
     */
    List<BoardMyListDto> findMyList(Long memberId);

}
