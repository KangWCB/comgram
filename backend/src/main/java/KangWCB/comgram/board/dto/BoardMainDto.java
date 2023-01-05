package KangWCB.comgram.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardMainDto {

    private Long id; // 게시물 id
    private String  content; // 본문
    private String  contentImgPath; //본문 사진
    private Long likeCount; // 좋아요 수

    // 작성자
    private String nickName; // 작성자 별명
    private String profileImgPath; // 작성자 사진

    private Boolean pushLike; // 좋아요 누른지 안누른지
    @Builder
    public BoardMainDto(Long id, String content, String contentImgPath, Long likeCount, String nickName, String profileImgPath, Boolean pushLike) {
        this.id = id;
        this.content = content;
        this.contentImgPath = contentImgPath;
        this.likeCount = likeCount;
        this.nickName = nickName;
        this.profileImgPath = profileImgPath;
        this.pushLike = pushLike;
    }
}