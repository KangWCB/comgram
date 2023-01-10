package KangWCB.comgram.follow;

import KangWCB.comgram.follow.repository.FollowJpaRepository;
import KangWCB.comgram.follow.repository.FollowQueryRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowJpaRepository followJpaRepository;

    @Transactional
    public String follow(Long memberId, Long followingId){

        Optional<Follow> follow = followJpaRepository.isFollow(memberId, followingId);
        if (follow.isEmpty()) {
                Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
                Member fMember = memberRepository.findById(followingId).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
                Follow newFollow = Follow.builder().follower(member).following(fMember).build();
                followJpaRepository.save(newFollow);
                return "팔로우 성공!";
            } else {
                Follow existFollow = follow.get();
                followJpaRepository.delete(existFollow);
                return "언팔로우";
            }

    }
}
