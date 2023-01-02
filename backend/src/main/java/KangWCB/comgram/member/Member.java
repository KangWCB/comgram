package KangWCB.comgram.member;

import KangWCB.comgram.config.audit.BaseTimeEntity;
import KangWCB.comgram.member.dto.MemberFormDto;
import KangWCB.comgram.member.dto.MemberUpdateForm;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name  = "member_id")
    private Long id;

    // 로그인 시 필요
    @Column(unique = true)
    private String email;
    private String password;

    // 개인 정보
    private String name;
    private String nickName;

    //프로필 사진
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String password, String name, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickname;
        this.role = role;
    }

    //== 생성 메소드
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .password(passwordEncoder.encode(memberFormDto.getPassword())) // 암호화
                .nickname(memberFormDto.getNickname())
                .role(Role.USER)
                .build();
        return member;
    }

    public void updateMember(MemberUpdateForm memberUpdateForm){
        this.nickName = memberUpdateForm.getNickname();
        this.name = memberUpdateForm.getName();
    }

}
