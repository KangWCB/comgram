package KangWCB.comgram.board.boardLike;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike,Long> {
    Optional <BoardLike> findByMemberAndBoard(Member member, Board board);
}
