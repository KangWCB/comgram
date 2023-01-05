package KangWCB.comgram.member;

import KangWCB.comgram.member.dto.MemberUpdateForm;
import KangWCB.comgram.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                Long savedImgId = photoService.saveFile(file.orElseThrow());
                findMember.updatePhoto(savedImgId);
            }
            findMember.updateMember(memberUpdateForm);
        } catch (Exception e){
            log.info("file_update_error={}",e.getMessage());
        }
        return findMember.getId();
    }


}
