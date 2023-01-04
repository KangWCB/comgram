package KangWCB.comgram.board.boardLike;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public boolean addLike(Member member, Long boardId){
        Board findBoard = boardRepository.findById(boardId).orElseThrow();

        if (isNotAlreadyLike(member,findBoard)){
            boardLikeRepository.save(BoardLike.builder().member(member).board(findBoard).build());
            return true;
        }
        return false;
    }

    private boolean isNotAlreadyLike(Member member, Board board){
        return boardLikeRepository.findByMemberAndBoard(member,board).isEmpty();
    }
}
