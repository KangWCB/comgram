package KangWCB.comgram.board;

import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    /**
     * 글 작성
     */
    @Transactional
    public Long write(BoardFormDto boardFormDto){
        Board saveBoards = boardRepository.save(Board.createBoard(boardFormDto));
        return saveBoards.getId();
    }

}
