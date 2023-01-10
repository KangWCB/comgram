package KangWCB.comgram.follow.repository;

import KangWCB.comgram.follow.Follow;
import KangWCB.comgram.follow.QFollow;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepository {
    private final JPAQueryFactory queryFactory;
    QFollow qFollow = QFollow.follow;


}
