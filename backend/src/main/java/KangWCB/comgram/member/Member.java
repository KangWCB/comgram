package KangWCB.comgram.member;

import KangWCB.comgram.config.audit.BaseTimeEntity;
import KangWCB.comgram.member.dto.MemberFormDto;
import KangWCB.comgram.member.dto.MemberUpdateForm;
import KangWCB.comgram.photo.Photo;
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

    private String introMsg = ""; // 소개글
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="photo_id")
    private Photo photo;

    private String refreshToken; // 리프레쉬 토큰 저장
    @Enumerated(EnumType.STRING)
    private Role role; // 역할


    @Builder
    public Member(String email, String password, String name, String nickname, String introMsg, Role role ,Photo photo) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.introMsg = introMsg;
        this.nickName = nickname;
        this.role = role;
        this.photo = photo;
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
    public void updateNickName(MemberUpdateForm memberUpdateForm){
        this.nickName = memberUpdateForm.getNickname();
        this.introMsg = memberUpdateForm.getIntroMsg();
    }
    public void updatePhoto(Photo photo) {
        this.photo = photo;
    }

    public void registerRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void addProfilePhoto(Photo photo){
        this.photo = photo;
    }
}

