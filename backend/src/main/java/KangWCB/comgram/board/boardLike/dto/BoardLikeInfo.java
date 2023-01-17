package KangWCB.comgram.board.boardLike.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardLikeInfo {

    private String likeUserNickName; // 좋아요 누른사람중  id 하나
    private String likeUserProfilePath; // 위 사람의 사진 url

    @Builder
    public BoardLikeInfo(String likeUserNickName, String likeUserProfilePath) {
        this.likeUserNickName = likeUserNickName;
        this.likeUserProfilePath = likeUserProfilePath;
    }
}
