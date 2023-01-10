package KangWCB.comgram.follow.repository;

import KangWCB.comgram.follow.Follow;
import KangWCB.comgram.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowJpaRepository extends JpaRepository<Follow,Long> {

//    /**
//     * 서로 팔로우를 했는지 확인하기 위해 count
//     */
//    @Query("select count(f) from Follow f where f.following.id = :id and f.follower.id =:memberId")
//    int isFollow(@Param("id")Long id, @Param("memberId") Long memberId);


    // 팔로우 수, 팔로잉 수  세는 것
    Long countByFollower(Member member);
    Long countByFollowing(Member member);



}
