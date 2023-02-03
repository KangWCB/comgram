package KangWCB.comgram.member.repository;


import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QMember qMember = QMember.member;
    /**
     * 닉네임 찾기
     */
    @Override
    public List<Member> findMember(String word) {
        List<Member> result = queryFactory.selectFrom(qMember)
                .where(likeMember(word))
                .limit(10)
                .fetch();
        return result;
    }

    @Override
    public BooleanExpression likeMember(String word){
        if(!StringUtils.hasText(word)){ //StringUtils.isEmpty(word) deprecate
            return null;
        }
        return qMember.nickName.contains(word);
    }
}
