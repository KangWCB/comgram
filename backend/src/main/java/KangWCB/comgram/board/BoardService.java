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
import com.querydsl.core.QueryResults;
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
    private final BoardQueryRepository boardQueryRepository;
    private final BoardLikeQueryRepository boardLikeQueryRepository;
    private final PhotoRepository photoRepository;
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
        Member member = memberRepository.findById(memberId).orElseThrow();

        return getBoardMainDtos(allBoard, member);
    }

    private List<BoardMainDto> getBoardMainDtos(List<Board> allBoard, Member member) {
        List<BoardMainDto> boardMainDtos = new ArrayList<>();
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

    public List<BoardMainDto> allMyList(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        QueryResults<Board> followingBoard = boardQueryRepository.findFollowingBoard(memberId);
        List<Board> boards = followingBoard.getResults();
        return getBoardMainDtos(boards,member);
    }
}
