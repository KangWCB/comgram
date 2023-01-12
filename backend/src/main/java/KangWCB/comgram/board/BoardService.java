package KangWCB.comgram.board;

import KangWCB.comgram.board.boardLike.repository.BoardLikeQueryRepository;
import KangWCB.comgram.board.comment.Comment;
import KangWCB.comgram.board.dto.BoardDetailDto;
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

//    public List<BoardMainDto> allList(Long memberId) {
//        List<Board> allBoard = boardRepository.findAll();
//        Member member = memberRepository.findById(memberId).orElseThrow();
//
//        return getBoardMainDtos(allBoard, member);
//    }

    public List<BoardMainDto> allMyList(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<Board> boards = boardQueryRepository.findFollowingBoard(memberId);
        return getBoardMainDtos(boards,member);
    }

    /**
     * 게시물 하나 정보 보내주기
     */
    public BoardDetailDto findBoardDetail(Long boardId) {
        Board board = boardQueryRepository.findBoard(boardId);
        Photo photo = photoRepository.findById(board.getImgId()).orElseThrow(() -> new NoSuchElementException());
        String saveImgPath = getSavePath(board.getMember());
        BoardDetailDto boardDetailDto = BoardDetailDto.toDto(isPushLike(board.getMember(), board), photo, board, saveImgPath);
        List<Comment> comments = board.getComments();
        List<BoardMainCommentInfo> boardMainCommentInfos = new ArrayList<>();
        List<BoardMainLikeInfo> boardMainLikeInfos = new ArrayList<>();

        if(!comments.isEmpty()){
            for (Comment comment : comments) {
                boardMainCommentInfos.add(new BoardMainCommentInfo(comment.getMember().getNickName(), comment.getComment()));
            }
            boardDetailDto.setBoardMainCommentInfo(boardMainCommentInfos);
        }
        List<Member> likeMember = boardLikeQueryRepository.findLikeMember(board);
        if(!likeMember.isEmpty()){
            for (Member member : likeMember) {
                boardMainLikeInfos.add(new BoardMainLikeInfo(likeMember.get(0).getNickName(), getSavePath(likeMember.get(0))));
            }
            boardDetailDto.setBoardMainLikeInfo(boardMainLikeInfos);
        }
        return boardDetailDto;
    }

    private boolean isPushLike(Member member, Board board) {
        return boardLikeQueryRepository.isPush(member,board);
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
                List<Member> likeMember = boardLikeQueryRepository.findLikeMember(board);
                boardMainDto.setBoardMainLikeInfo(new BoardMainLikeInfo(likeMember.get(0).getNickName(), getSavePath(likeMember.get(0))));
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
}
