package KangWCB.comgram.board.dto.maindto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardMainLikeInfo {
    private String likeUserNickName; // 좋아요 누른사람중  id 하나
    private String likeUserProfilePath; // 위 사람의 사진 url

    @Builder
    public BoardMainLikeInfo(String likeUserNickName, String likeUserProfilePath) {
        this.likeUserNickName = likeUserNickName;
        this.likeUserProfilePath = likeUserProfilePath;
    }
}
