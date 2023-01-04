package KangWCB.comgram.board.boardLike;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardLike {

    @Id @GeneratedValue
    @Column(name="board_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public BoardLike(Board board,Member member){
        this.board = board;
        this.member = member;
    }
}
