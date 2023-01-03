package KangWCB.comgram.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginDto {

    @Email(message = "이메일 형식으로 입력해주세요")
    @NotEmpty(message = "칸을 채워주세요.")
    private String email;

    @NotEmpty(message = "칸을 채워주세요.")
    private String password;
}
