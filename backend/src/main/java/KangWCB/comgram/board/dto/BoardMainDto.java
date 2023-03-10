package KangWCB.comgram.board.dto;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.boardLike.dto.BoardLikeInfo;
import KangWCB.comgram.board.comment.dto.BoardCommentInfo;
import KangWCB.comgram.photo.Photo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardMainDto {

    private Long id; // 게시물 id
    private String  content; // 본문
    private String  contentImgPath; //본문 사진
    private Long likeCount = 0L; // 좋아요 수

    // 작성자
    private String nickName; // 작성자 별명
    private String profileImgPath; // 작성자 사진
    private Long writerId; // 작성자 아이디
    private Boolean pushLike; // 좋아요 누른지 안누른지

    private LocalDateTime regTime; // 글 작성 시간
    private Long commentCount= 0L; // 댓글 갯수

    private BoardLikeInfo boardLikeInfo;
    private BoardCommentInfo boardCommentInfo;

    @Builder
    public BoardMainDto(Long id, String content, String contentImgPath,Long writerId, Long likeCount, String nickName, String profileImgPath, Boolean pushLike, LocalDateTime regTime, Long commentCount) {
        this.id = id;
        this.content = content;
        this.contentImgPath = contentImgPath;
        this.likeCount = likeCount;
        this.nickName = nickName;
        this.writerId = writerId;
        this.profileImgPath = profileImgPath;
        this.pushLike = pushLike;
        this.regTime = regTime;
        this.commentCount = commentCount;
    }

    public static BoardMainDto toDto(boolean pushLike, String contextImgPath, Board board,String writerImgPath){
        BoardMainDto boardMainDto = BoardMainDto.builder()
                .id(board.getId())
                .content(board.getContent())
                .contentImgPath(contextImgPath)
                .likeCount(board.getLikes().stream().count())
                .writerId(board.getMember().getId())
                .nickName(board.getMember().getNickName())
                .commentCount(board.getComments().stream().count())
                .profileImgPath(writerImgPath)
                .pushLike(pushLike)
                .regTime(board.getRegTime())
                .build();
        return boardMainDto;
    }

}