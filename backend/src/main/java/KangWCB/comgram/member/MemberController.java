package KangWCB.comgram.member;

import KangWCB.comgram.board.BoardService;
import KangWCB.comgram.board.dto.BoardMyListDto;
import KangWCB.comgram.config.jwt.JwtTokenProvider;
import KangWCB.comgram.config.jwt.SecurityUser;
import KangWCB.comgram.config.jwt.dto.TokenInfo;
import KangWCB.comgram.ex.member.MemberLoginEx;
import KangWCB.comgram.follow.repository.FollowJpaRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
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
    private final BoardService boardService;

    private final FollowJpaRepository followJpaRepository;
    @Value("${default.profile}")
    private String defaultProfile;


    // 회원가입
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated MemberFormDto memberFormDto) {
        Member savedMember = memberRepository.save(Member.createMember(memberFormDto, passwordEncoder));
        return new ResponseEntity<>(savedMember.getId(), HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public Result<TokenInfo> login(@RequestBody @Valid MemberLoginDto memberLoginDto, BindingResult bindingResult) {
        Member member = memberRepository.findByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new MemberLoginEx("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())) {
            throw new MemberLoginEx("이메일 또는 비밀번호가 맞지 않습니다.");
        }
        Result<TokenInfo> result = new Result<>(jwtTokenProvider.createToken("local", member.getEmail(), member.getRole()), member.getId());
        return result;
    }
    // 로그인 회원 정보 출력
    @GetMapping("/info")
    public MemberInfoDto loginMemberInfo(@AuthenticationPrincipal SecurityUser member){
        return memberService.findMemberInfo(member.getMember().getId());
    }
    // 상세페이지 회원 정보 출력
    @GetMapping("/{memberId}/info")
    public MemberInfoDto memberInfo(@PathVariable(name = "memberId") Long memberId){
        return memberService.findMemberInfo(memberId);
    }
    // 회원수정
    @PostMapping("/{id}/update")
    public ResponseEntity memberUpdate(@Valid MemberUpdateForm memberUpdateForm,
                                       @RequestParam(value = "photo") Optional<MultipartFile> file,
                                       @PathVariable(name = "id") Long memberId){
        Long updateId = memberService.update(memberUpdateForm, memberId, file);
        return new ResponseEntity<>(memberService.findMemberInfo(updateId), HttpStatus.OK);
    }
    // 회원 삭제
    @DeleteMapping("/{id}/delete")
    public void memberDelete(@PathVariable(name = "id") Long memberId){
        memberRepository.deleteById(memberId);
    }

    // 테스트용 follow
    @GetMapping("/{id}/followingCount")
    public Count followingCount(@PathVariable(name = "id") Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
        Long countFollower = followJpaRepository.countByFollower(member);
        return new Count(countFollower);
    }
    /**
     * 팔로워
     * @param memberId
     * @return
     */
    @GetMapping("/{id}/followerCount")
    public Count followerCount(@PathVariable(name = "id") Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
        Long countFollowing = followJpaRepository.countByFollowing(member);
        return new Count(countFollowing);
    }
    /**
     * 각자 작성한 글들 리스트
     *
     */
    @GetMapping("/{memberId}/boards")
    public MyList findMyList(@PathVariable(name = "memberId") Long id){
        List<BoardMyListDto> myList = boardService.findMyList(id);
        return new MyList(boardService.countMyList(id),boardService.findMyList(id));
    }
    @GetMapping("/{memberId}/isFollow")
    public isFollow findMyList(@PathVariable(name = "memberId") Long id,
                                     @AuthenticationPrincipal SecurityUser user){
        if(id == user.getMember().getId())
            return new isFollow("mine"); // 내 게시물일때
        if (followJpaRepository.isFollow(user.getMember().getId(),id).isEmpty()){
            return new isFollow("notFollow"); // 팔로우가 안되어 있을때
        }
        return new isFollow("follow"); //팔로우가 되어있을 때
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

    /**
     * 팔로우
     */
    @Data
    @AllArgsConstructor
    static class Count{
        private Long count;
    }

    /**
     * 마이 리스트
     */
    @Data
    @AllArgsConstructor
    static class MyList<T> {
        private Long count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class isFollow<T> {
        private String isFollow;
    }
}