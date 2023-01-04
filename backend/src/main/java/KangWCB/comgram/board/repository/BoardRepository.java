package KangWCB.comgram.board.repository;


import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.boardLike.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

     @Query("select count(b.likes) from Board b where b.id = :id")
     Long countLikes(@Param("id") Long boardId);
}
