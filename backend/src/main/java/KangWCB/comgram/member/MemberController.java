package KangWCB.comgram.member;

import KangWCB.comgram.config.jwt.JwtTokenProvider;
import KangWCB.comgram.config.jwt.SecurityUser;
import KangWCB.comgram.error.ErrorResult;
import KangWCB.comgram.member.dto.MemberFormDto;
import KangWCB.comgram.member.dto.MemberInfoDto;
import KangWCB.comgram.member.dto.MemberLoginDto;
import KangWCB.comgram.member.dto.MemberUpdateForm;
import KangWCB.comgram.photo.PhotoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PhotoService photoService;
    @Value("${default.profile}")
    private String defaultProfile;
    // 회원가입
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody MemberFormDto memberFormDto) {
        Member savedMember = memberRepository.save(Member.createMember(memberFormDto, passwordEncoder));
        return new ResponseEntity<>(savedMember.getId(), HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public Result<String> login(@RequestBody MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }
        Result<String> result = new Result<>(jwtTokenProvider.createToken("local", member.getEmail(), member.getRole()), member.getId());
        return result;
    }
    // 회원 정보 출력
    @GetMapping("/info")
    public MemberInfoDto memberInfo(@AuthenticationPrincipal SecurityUser member){
        return MemberInfoDto.builder()
                .email(member.getMember().getEmail())
                .nickname(member.getMember().getNickName())
                .profilePhotoUrl(getSavePath(member))
                .build();
    }
    /**
     * 프로필 사진 주소
     */
    private String getSavePath(SecurityUser member) {
        Long photoProfileId = member.getMember().getPhotoProfileId();
        return photoProfileId!= null ?  photoService.findSavePath(photoProfileId): defaultProfile;
    }

    // 회원수정
    @PostMapping("/{id}/update")
    public ResponseEntity memberUpdate(MemberUpdateForm memberUpdateForm,
                                       @RequestParam(name = "photo") Optional<MultipartFile> file,
                                       @PathVariable(name = "id") Long memberId){
        Long updateMemberId = memberService.update(memberUpdateForm, memberId,file);
        return new ResponseEntity<>(updateMemberId, HttpStatus.OK);
    }
<<<<<<< HEAD


=======
>>>>>>> backend
    // 회원 삭제
    @DeleteMapping("/{id}/delete")
    public void memberDelete(@PathVariable(name = "id") Long memberId){
        memberRepository.deleteById(memberId);
    }

    /**
     * Login 토큰
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T token;
        private Long id;
    }
}
