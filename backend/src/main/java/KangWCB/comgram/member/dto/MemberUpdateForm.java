package KangWCB.comgram.member.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberUpdateForm {

    @NotEmpty(message = "닉네임 칸을 채워주세요.")
    private String nickname;

}
