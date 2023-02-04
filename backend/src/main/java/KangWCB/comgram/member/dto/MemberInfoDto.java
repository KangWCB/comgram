package KangWCB.comgram.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoDto {
    private Long memberId;
    private String nickname;
    private String email;
    private String profilePhotoUrl;
    private String name;
    private String introMsg;
}
