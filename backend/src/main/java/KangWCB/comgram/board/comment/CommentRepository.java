package KangWCB.comgram.board.comment;

import KangWCB.comgram.board.comment.repository.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>, CommentRepositoryCustom {
}
