package KangWCB.comgram.board.boardLike.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardLikeInfo {

    private Long id; // 좋아요 누른 사람 id
    private String likeUserNickName; // 좋아요 누른사람중  닉네임 하나
    private String likeUserProfilePath; // 위 사람의 사진 url
    @Builder
    public BoardLikeInfo(Long id, String likeUserNickName, String likeUserProfilePath) {
        this.id = id;
        this.likeUserNickName = likeUserNickName;
        this.likeUserProfilePath = likeUserProfilePath;
    }
}
