package KangWCB.comgram.board.repository;


import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.boardLike.repository.BoardLikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>, BoardRepositoryCustom {
}
