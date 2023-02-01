package KangWCB.comgram.board.comment.dto;

import KangWCB.comgram.board.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardCommentInfo {

    private Long commentId;
    private String commentUserNickname;
    private Long commentUserId;
    private String commentContext;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public BoardCommentInfo(Long commentId, String commentUserNickname, Long commentUserId, String commentContext, String createdDate, String modifiedDate) {
        this.commentId = commentId;
        this.commentUserNickname = commentUserNickname;
        this.commentUserId = commentUserId;
        this.commentContext = commentContext;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static BoardCommentInfo toDto(Comment comment){
        BoardCommentInfo dto = BoardCommentInfo.builder()
                .commentId(comment.getId())
                .commentUserId(comment.getMember().getId())
                .commentContext(comment.getComment())
                .commentUserNickname(comment.getMember().getNickName())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
        return dto;
    }
}
