package KangWCB.comgram.board.dto;

import KangWCB.comgram.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardFormDto {

    private String content;// 본문
    private Member member; // 저장된 img id
    private Long imgId;

    @Builder
    public BoardFormDto(String content, Member member) {
        this.content = content;
        this.member = member;
    }
}
