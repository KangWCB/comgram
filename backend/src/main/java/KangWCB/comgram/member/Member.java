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
    private String email; // 이메일
    private String password; // 비밀번호
    // 개인 정보
    private String name; // 이름
    private String nickName; // 닉네임
    private Long photoProfileId; // 프로필 생성
    @Enumerated(EnumType.STRING)
    private Role role; // 역할
    @Builder
    public Member(String email, String password, String name, String nickname, Role role, Long photoProfileId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickname;
        this.role = role;
        this.photoProfileId = photoProfileId;
    }

    //== 생성 메소드
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .password(passwordEncoder.encode(memberFormDto.getPassword())) // 암호화
                .nickname(memberFormDto.getNickname())
                .photoProfileId(1L)
                .role(Role.USER)
                .build();
        return member;
    }

    public void updateMember(MemberUpdateForm memberUpdateForm){
        this.nickName = memberUpdateForm.getNickname();
        this.name = memberUpdateForm.getName();
    }
    public void updatePhoto(Long savedImgId) {
        this.photoProfileId = savedImgId;
    }
}
