package KangWCB.comgram.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginDto {

    private String email;

    private String password;
}
