package KangWCB.comgram.follow;

import KangWCB.comgram.config.audit.BaseTimeEntity;
import KangWCB.comgram.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following")
    private Member following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    private Member follower;

    @Builder
    public Follow(Member following, Member follower) {
        this.following = following;
        this.follower = follower;
    }
}
