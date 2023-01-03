package KangWCB.comgram.member.dto;

import KangWCB.comgram.member.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class MemberFormDto {

    @Email
    @NotEmpty(message = "칸을 채워주세요.")
    private String email;

    @NotEmpty(message = "칸을 채워주세요.")
    @Min(value = 4)
    private String password;

    @NotEmpty(message = "칸을 채워주세요.")
    private String name;

    @NotEmpty(message = "칸을 채워주세요.")
    private String nickname;

    private Role role;

}
