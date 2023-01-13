package KangWCB.comgram.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchMemberDto {
    private Long memberId;
    private String nickName;
    private String profileImgUrl;
}
