package KangWCB.comgram.board.boardLike;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.config.audit.BaseTimeEntity;
import KangWCB.comgram.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardLike extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name="board_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public BoardLike(Board board,Member member){
        this.board = board;
        this.member = member;
    }
}
