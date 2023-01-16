package KangWCB.comgram.board.comment.repository;

import KangWCB.comgram.board.comment.dto.BoardCommentInfo;

import java.util.List;

public interface CommentRepositoryCustom {
    List<BoardCommentInfo> findBoardComment(Long boardId);
}
