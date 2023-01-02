package KangWCB.comgram.member.dto;

import KangWCB.comgram.member.Role;
import lombok.Data;

@Data
public class MemberFormDto {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private Role role;

}
