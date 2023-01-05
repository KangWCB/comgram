package KangWCB.comgram.board.comment.dto;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.comment.Comment;
import KangWCB.comgram.member.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentRequestDto {

    private Long id;
    private String comment;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

    private Member member;
    private Board boards;

    public Comment toEntity(){
        Comment comments = Comment.builder()
                .id(id)
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .member(member)
                .boards(boards)
                .build();
        return comments;
    }
}
