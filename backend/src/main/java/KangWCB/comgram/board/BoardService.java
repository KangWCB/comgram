package KangWCB.comgram.board;

import KangWCB.comgram.board.boardLike.BoardLike;
import KangWCB.comgram.board.boardLike.repository.BoardLikeQueryRepository;
import KangWCB.comgram.board.boardLike.repository.BoardLikeRepository;
import KangWCB.comgram.board.comment.Comment;
import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.board.dto.maindto.BoardMainCommentInfo;
import KangWCB.comgram.board.dto.maindto.BoardMainDto;
import KangWCB.comgram.board.dto.maindto.BoardMainLikeInfo;
import KangWCB.comgram.board.repository.BoardQueryRepository;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.photo.Photo;
import KangWCB.comgram.photo.PhotoRepository;
import KangWCB.comgram.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    @Value("${default.profile}")
    private String defaultProfile;

    private final BoardRepository boardRepository;
    private final BoardLikeQueryRepository boardLikeQueryRepository;
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
            String saveImgPath = getSavePath(board.getMember());
            BoardMainDto boardMainDto = BoardMainDto.toDto(isPushLike(member, board), photo, board, saveImgPath);
            if(!board.getComments().isEmpty()){
                Comment comment = board.getComments().get(0);
                boardMainDto.setBoardMainCommentInfo(new BoardMainCommentInfo(comment.getMember().getNickName(), comment.getComment()));
            }
            if(!board.getLikes().isEmpty()){
                Member likeMember = boardLikeQueryRepository.findLikeMember(board);
                boardMainDto.setBoardMainLikeInfo(new BoardMainLikeInfo(likeMember.getNickName(), getSavePath(likeMember)));
            }
            boardMainDtos.add(boardMainDto);
        }
        return boardMainDtos;
    }

    private String getSavePath(Member member) {
        if (member.getPhotoProfileId() != null){
            return photoService.findSavePath(member.getPhotoProfileId());
        }
        return defaultProfile;
    }

    private boolean isPushLike(Member member, Board board) {
        return board.likes.contains(member);
    }
}
