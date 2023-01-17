package KangWCB.comgram.member.dto;

import KangWCB.comgram.member.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFormDto {
    @Email
    @NotEmpty(message = "칸을 채워주세요.")
    private String email;
    @NotEmpty(message = "비밀번호 칸을 채워주세요.")
    @Size(max = 10, min = 4 , message = "비밀번호 길이가 4 이상 10 이하여야 합니다")
    private String password;
    @NotEmpty(message = "이름 칸을 채워주세요.")
    private String name;
    @NotEmpty(message = "닉네임 칸을 채워주세요.")
    private String nickname;
    private Role role;
    @Builder
    public MemberFormDto(String email, String password, String name, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
    }
}
