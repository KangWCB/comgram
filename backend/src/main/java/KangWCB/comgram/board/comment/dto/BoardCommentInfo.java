package KangWCB.comgram.board.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentInfo {

    private String commentUserNickname;
    private String commentContext;

}
