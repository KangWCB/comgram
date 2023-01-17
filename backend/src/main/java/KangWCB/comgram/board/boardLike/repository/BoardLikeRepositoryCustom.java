package KangWCB.comgram.board.boardLike.repository;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.member.Member;

import java.util.List;

public interface BoardLikeRepositoryCustom {
    List<Member> findLikeMember(Board board);
    boolean isPush(Member member,Board board);

}
