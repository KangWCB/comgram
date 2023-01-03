package KangWCB.comgram.board;

import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.config.audit.BaseTimeEntity;
import KangWCB.comgram.member.Member;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    private String content; // 본문
    private int viewCount; // 조회수

    private Long imgId; // 저장된 이미지 id

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 작성자


    @Builder
    public Board(String content, int viewCount, Member member, Long imgId) {
        this.content = content;
        this.viewCount = viewCount;
        this.member = member;
        this.imgId = imgId;
    }

    // 생성 메소드
    public static Board createBoard(BoardFormDto boardFormDto){
        Board writeBoard = Board.builder()
                .content(boardFormDto.getContent())
                .viewCount(0)
                .imgId(boardFormDto.getImgId())
                .member(boardFormDto.getMember())
                .build();

        return writeBoard;
    }

}
