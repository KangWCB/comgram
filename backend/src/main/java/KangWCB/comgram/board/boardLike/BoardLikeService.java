package KangWCB.comgram.board.boardLike;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean addLike(Long memberId, Long boardId){
        Board findBoard = boardRepository.findById(boardId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        if (isNotAlreadyLike(member,findBoard)){
            boardLikeRepository.save(BoardLike.builder().member(member).board(findBoard).build());
            return true;
        }
        else if (!isNotAlreadyLike(member,findBoard)){
            BoardLike boardLike = boardLikeRepository.findByMemberAndBoard(member, findBoard)
                    .orElseThrow(() -> new IllegalStateException("좋아요 한 기록이 없습니다."));
            boardLikeRepository.delete(boardLike);
            return true;
        }
        return false;
    }

    private boolean isNotAlreadyLike(Member member, Board board){
        return boardLikeRepository.findByMemberAndBoard(member,board).isEmpty();
    }
}
