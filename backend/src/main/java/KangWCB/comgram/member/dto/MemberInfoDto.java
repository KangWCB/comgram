package KangWCB.comgram.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoDto {
    private String nickname;
    private String email;
}
