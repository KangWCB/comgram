package KangWCB.comgram.board;

import KangWCB.comgram.board.boardLike.BoardLike;
import KangWCB.comgram.board.comment.Comment;
import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.config.audit.BaseTimeEntity;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.photo.Photo;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 작성자

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    Set<BoardLike> likes = new HashSet<>(); // 좋아요 누른 항목

    @OneToMany(mappedBy = "boards", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments = new ArrayList<>() ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;


    @Builder
    public Board(String content, int viewCount, Member member,Photo photo) {
        this.content = content;
        this.viewCount = viewCount;
        this.member = member;
        this.photo = photo;
    }

    // 생성 메소드
    public static Board createBoard(BoardFormDto boardFormDto){
        Board writeBoard = Board.builder()
                .content(boardFormDto.getContent())
                .viewCount(0)
                .member(boardFormDto.getMember())
                .photo(boardFormDto.getPhoto()) // 포토넣기
                .build();

        return writeBoard;
    }

}
