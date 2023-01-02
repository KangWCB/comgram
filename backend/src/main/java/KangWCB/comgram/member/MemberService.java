package KangWCB.comgram.member;

import KangWCB.comgram.member.dto.MemberUpdateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long update(MemberUpdateForm memberUpdateForm, Long memberId){
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("회원이 없습니다."));
        findMember.updateMember(memberUpdateForm);
        return findMember.getId();
    }

}
