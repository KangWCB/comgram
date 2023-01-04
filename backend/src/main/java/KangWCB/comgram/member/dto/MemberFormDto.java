package KangWCB.comgram.member.dto;

import KangWCB.comgram.member.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private Long imgId;

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
