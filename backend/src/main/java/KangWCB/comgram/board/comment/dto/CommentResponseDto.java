package KangWCB.comgram.board.comment.dto;

import KangWCB.comgram.board.comment.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String comment;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private final String writerName;
    private final String  writerEmail;
    private final Long boardId;

    /* Entity -> Dto */
    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.writerName = comment.getMember().getName();
        this.boardId = comment.getBoards().getId();
        this.writerEmail=comment.getMember().getEmail();
    }
}
