package KangWCB.comgram.member.repository;

import KangWCB.comgram.member.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMember(String word);

    BooleanExpression likeMember(String word);
}
