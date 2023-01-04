package KangWCB.comgram.board;

import KangWCB.comgram.board.boardLike.BoardLikeRepository;
import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.board.dto.BoardMainDto;
import KangWCB.comgram.board.repository.BoardQueryRepository;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.photo.Photo;
import KangWCB.comgram.photo.PhotoRepository;
import KangWCB.comgram.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository queryRepository;
    private final PhotoRepository photoRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final MemberRepository memberRepository;
    private final PhotoService photoService;
    /**
     * 글 작성
     */
    @Transactional
    public Long write(BoardFormDto boardFormDto){
        Board saveBoards = boardRepository.save(Board.createBoard(boardFormDto));
        return saveBoards.getId();
    }

    public List<BoardMainDto> allList(Long memberId) {
        List<Board> allBoard = boardRepository.findAll();
        List<BoardMainDto> boardMainDtos = new ArrayList<>();
        Member member = memberRepository.findById(memberId).orElseThrow();
        for (Board board : allBoard) {
            Photo photo = photoRepository.findById(board.getImgId()).orElseThrow(() -> new NoSuchElementException());
            Long likesCount = boardLikeRepository.countLikes(board.getId());
            BoardMainDto boardMainDto = BoardMainDto.builder()
                    .id(board.getId())
                    .content(board.getContent())
                    .contentImgPath(photo.getSavedPath())
                    .likeCount(likesCount)
                    .nickName(board.getMember().getNickName())
                    .profileImgPath(photoService.findSavePath(board.getMember().getPhotoProfileId()))
                    .pushLike(isPushLike(member, board))
                    .build();
            boardMainDtos.add(boardMainDto);
        }
        return boardMainDtos;
    }

    private boolean isPushLike(Member member, Board board) {
        return boardLikeRepository.existsBoardLike(board.getId(), member).isPresent() ? true : false;
    }
}
