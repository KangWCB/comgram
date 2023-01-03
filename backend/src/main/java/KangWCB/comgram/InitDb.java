package KangWCB.comgram;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.member.Role;
import KangWCB.comgram.member.dto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 시범용 데이터
 */
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;


    @PostConstruct
    public void init(){

        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final MemberRepository memberRepository;

        public void dbInit1() {
            MemberFormDto memberFormDto = MemberFormDto.builder().name("정우진").email("test@naver.com").password("1234")
                    .nickname("갱킹맨").role(Role.USER).build();
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            em.persist(member);
//
        }
        public void dbInit2(){
            Member findMember = memberRepository.findByEmail("test@naver.com").orElseThrow(
                    () -> new UsernameNotFoundException("찾는 사용자가 없습니다.")
            );
            for (int i = 0; i < 20; i++) {
                Board board = Board.builder().content("aaa").member(findMember).build();
                em.persist(board);
            }
        }

    }

}


