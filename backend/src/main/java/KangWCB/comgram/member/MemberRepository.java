package KangWCB.comgram.member;

import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.repository.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
}
