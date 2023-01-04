package KangWCB.comgram.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardMainDto {
    private String  content;
    private String  savedImgPath;
    private Long likeCount;
}
