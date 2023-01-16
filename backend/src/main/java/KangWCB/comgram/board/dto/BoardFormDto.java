package KangWCB.comgram.board.dto;

import KangWCB.comgram.member.Member;
import KangWCB.comgram.photo.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BoardFormDto {
    
    @NotNull
    private String content;// 본문
    private Member member; // 저장된 img id

    @NotNull
    private Long imgId;

    @JsonIgnore
    @NotNull
    private Photo photo;

    @Builder
    public BoardFormDto(String content, Member member) {
        this.content = content;
        this.member = member;
    }
}
