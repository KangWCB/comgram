package KangWCB.comgram.board.comment.dto;

import KangWCB.comgram.board.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentInfo {

    private Long commentId;
    private String commentUserNickname;
    private Long commentUserId;
    private String commentContext;
    private String createdDate;
    private String modifiedDate;

    public BoardCommentInfo(Comment comment){
        this.commentUserId = comment.getMember().getId();
        this.commentId = comment.getId();
        this.commentUserNickname = comment.getMember().getNickName();
        this.commentContext = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
