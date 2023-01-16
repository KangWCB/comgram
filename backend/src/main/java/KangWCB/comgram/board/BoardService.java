package KangWCB.comgram.board;

import KangWCB.comgram.board.boardLike.repository.BoardLikeRepositoryImpl;
import KangWCB.comgram.board.comment.Comment;
import KangWCB.comgram.board.comment.CommentRepository;
import KangWCB.comgram.board.dto.BoardDetailDto;
import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.board.comment.dto.BoardCommentInfo;
import KangWCB.comgram.board.dto.BoardMainDto;
import KangWCB.comgram.board.boardLike.dto.BoardLikeInfo;
import KangWCB.comgram.board.repository.BoardRepositoryImpl;
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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    @Value("${default.profile}")
    private String defaultProfile;

    private final BoardRepository boardRepository;
    private final BoardLikeRepositoryImpl boardLikeRepositoryImpl;
    private final CommentRepository commentRepository;
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
        List<Board> boards = boardRepository.findFollowingBoard(memberId);
        return getBoardMainDtos(boards,member);
    }

    /**
     * 게시물 하나 정보 보내주기
     */
    public BoardDetailDto findBoardDetail(Long boardId) {
        Board board = boardRepository.findBoard(boardId);
        Photo photo = photoRepository.findById(board.getImgId()).orElseThrow(() -> new NoSuchElementException());
        String saveImgPath = getSavePath(board.getMember());
        BoardDetailDto boardDetailDto = BoardDetailDto.toDto(isPushLike(board.getMember(), board), photo, board, saveImgPath);
        List<BoardLikeInfo> boardLikeInfos = new ArrayList<>();

       boardDetailDto.setBoardCommentInfo(commentRepository.findBoardComment(boardId));

        List<Member> likeMember = boardLikeRepositoryImpl.findLikeMember(board);
        if(!likeMember.isEmpty()){
            boardLikeInfos = likeMember.stream().map(member -> new BoardLikeInfo(member.getNickName(), getSavePath(member))).collect(Collectors.toList());
            boardDetailDto.setBoardLikeInfo(boardLikeInfos);
        }
        return boardDetailDto;
    }

    private boolean isPushLike(Member member, Board board) {
        return boardLikeRepositoryImpl.isPush(member,board);
    }

    private List<BoardMainDto> getBoardMainDtos(List<Board> allBoard, Member member) {
        List<BoardMainDto> boardMainDtos = new ArrayList<>();
        for (Board board : allBoard) {
            Photo photo = photoRepository.findById(board.getImgId()).orElseThrow(() -> new NoSuchElementException());
            String saveImgPath = getSavePath(board.getMember());
            BoardMainDto boardMainDto = BoardMainDto.toDto(isPushLike(member, board), photo, board, saveImgPath);
            if(!board.getComments().isEmpty()){
                Comment comment = board.getComments().get(0);
                boardMainDto.setBoardCommentInfo(new BoardCommentInfo(comment.getMember().getNickName(), comment.getComment()));
            }
            if(!board.getLikes().isEmpty()){
                List<Member> likeMember = boardLikeRepositoryImpl.findLikeMember(board);
                boardMainDto.setBoardLikeInfo(new BoardLikeInfo(likeMember.get(0).getNickName(), getSavePath(likeMember.get(0))));
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
