package KangWCB.comgram.follow.repository;

import KangWCB.comgram.follow.Follow;
import KangWCB.comgram.follow.QFollow;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepository {
    private final JPAQueryFactory queryFactory;
    QFollow qFollow = QFollow.follow;
    /**
     * 팔로우 여부 확인
     */
    public Boolean isFollow(Long id, Long memberId){
        Optional<Follow> follow = Optional.ofNullable(queryFactory.select(qFollow)
                .from(qFollow)
                .where(qFollow.follwer.id.eq(id), qFollow.following.id.eq(memberId))
                .fetchOne());
        if(follow.isPresent()){
            return true;
        }
        return false;
    }


}
