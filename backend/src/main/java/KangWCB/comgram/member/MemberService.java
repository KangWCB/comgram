package KangWCB.comgram.member;

import KangWCB.comgram.member.dto.MemberInfoDto;
import KangWCB.comgram.member.dto.MemberUpdateForm;
import KangWCB.comgram.photo.Photo;
import KangWCB.comgram.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PhotoService photoService;

    @Transactional
    public Long update(MemberUpdateForm memberUpdateForm, Long memberId, Optional<MultipartFile> file){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("회원이 없습니다."));
        try{
            if(file.isPresent()){
                Photo photo = photoService.saveMemberPhoto(file.orElseThrow(), memberId);
                findMember.updatePhoto(photo);
            }
            // GET으로 줘야함. 나머지 정보들 현재 null 들어옴
            findMember.updateNickName(memberUpdateForm);
        } catch (Exception e){
            log.info("file_update_error={}",e.getMessage());
        }
        return findMember.getId();
    }
    @Transactional
    public void updateRefreshToken(String userPk, String refreshToken){
        Member member = memberRepository.findByEmail(userPk).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
        member.registerRefreshToken(refreshToken);
    }

    public MemberInfoDto findMemberInfo(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("유저 없음"));
        MemberInfoDto build = MemberInfoDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickName())
                .profilePhotoUrl(photoService.noPhotoFinder(member))
                .name(member.getName())
                .build();
        return build;
    }
}
