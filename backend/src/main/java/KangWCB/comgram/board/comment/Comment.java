package KangWCB.comgram.board.comment;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment{

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment; // 댓글 내용

    // 어떤 게시물에 댓글이 작성되었는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board boards;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "created_date")
    @CreatedDate
    private String createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;
}
