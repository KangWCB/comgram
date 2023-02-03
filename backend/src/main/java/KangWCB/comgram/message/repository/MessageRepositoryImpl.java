package KangWCB.comgram.message.repository;

import KangWCB.comgram.member.Member;
import KangWCB.comgram.message.Message;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * message 개발중
 */
@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Message> findReceiverMessage(Member member) {
        return null;
    }

}
