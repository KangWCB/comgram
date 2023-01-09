package KangWCB.comgram.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MemberLoginDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(max = 10, min = 4 , message = "비밀번호 길이가 4 이상 10 이하여야 합니다")
    private String password;
}
