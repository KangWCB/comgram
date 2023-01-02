package KangWCB.comgram.member;

import KangWCB.comgram.config.jwt.JwtTokenProvider;
import KangWCB.comgram.config.jwt.SecurityUser;
import KangWCB.comgram.member.dto.MemberFormDto;
import KangWCB.comgram.member.dto.MemberInfoDto;
import KangWCB.comgram.member.dto.MemberLoginDto;
import KangWCB.comgram.member.dto.MemberUpdateForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public Long register(@RequestBody MemberFormDto memberFormDto) {
        return memberRepository.save(
                Member.createMember(memberFormDto,passwordEncoder)
        ).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(member.getEmail(), member.getRole());
    }

    // 회원 정보 출력
    @GetMapping("/info")
    public MemberInfoDto memberInfo(@AuthenticationPrincipal SecurityUser member){
        return MemberInfoDto.builder()
                .email(member.getMember().getEmail())
                .nickname(member.getMember().getNickname())
                .build();
    }

    // 회원수정
    @PostMapping("/{id}/update")
    public ResponseEntity memberUpdate(@RequestBody MemberUpdateForm memberUpdateForm,
                                       @PathVariable(name = "id") Long memberId){
        Long updateMemberId = memberService.update(memberUpdateForm, memberId);
        return new ResponseEntity<>(updateMemberId, HttpStatus.OK);
    }


//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private T data;
//    }

}
