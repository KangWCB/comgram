package KangWCB.comgram.board;

import KangWCB.comgram.board.boardLike.repository.BoardLikeRepositoryImpl;
import KangWCB.comgram.board.comment.Comment;
import KangWCB.comgram.board.comment.CommentRepository;
import KangWCB.comgram.board.dto.BoardDetailDto;
import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.board.comment.dto.BoardCommentInfo;
import KangWCB.comgram.board.dto.BoardMainDto;
import KangWCB.comgram.board.boardLike.dto.BoardLikeInfo;
import KangWCB.comgram.board.dto.BoardMyListDto;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    private final MemberRepository memberRepository;
    private final PhotoService photoService;

    /**
     * 글 작성
     */
    @Transactional
    public Long write(BoardFormDto boardFormDto) {
        Board saveBoards = boardRepository.save(Board.createBoard(boardFormDto));
        return saveBoards.getId();
    }

//    public List<BoardMainDto> allList(Long memberId) {
//        List<Board> allBoard = boardRepository.findAll();
//        Member member = memberRepository.findById(memberId).orElseThrow();
//        return getBoardMainDtos(allBoard, member);
//    }

    public List<BoardMainDto> allMyList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<Board> boards = boardRepository.findFollowingBoard(memberId);
        return getBoardMainDtos(boards, member);
    }

    /**
     * 게시물 하나 정보 보내주기
     */
    public BoardDetailDto findBoardDetail(Long boardId) {
        Board board = boardRepository.findBoard(boardId);
        String contextImgPath = board.getPhoto().getSavedPath();
        BoardDetailDto boardDetailDto = BoardDetailDto.toDto(isPushLike(board.getMember(), board), contextImgPath, board, photoService.noPhotoFinder(board.getMember()));
        List<BoardLikeInfo> boardLikeInfos = new ArrayList<>();

        boardDetailDto.setBoardCommentInfo(commentRepository.findBoardComment(boardId));

        List<Member> likeMember = boardLikeRepositoryImpl.findLikeMember(board);
        if (!likeMember.isEmpty()) {
            boardLikeInfos = likeMember.stream()
                    .map(member -> new BoardLikeInfo(member.getId(),member.getNickName(), photoService.noPhotoFinder(member)))
                    .collect(Collectors.toList());
            boardDetailDto.setBoardLikeInfo(boardLikeInfos);
        }
        return boardDetailDto;
    }
    private boolean isPushLike(Member member, Board board) {
        return boardLikeRepositoryImpl.isPush(member, board);
    }
    private List<BoardMainDto> getBoardMainDtos(List<Board> allBoard, Member member) {
        List<BoardMainDto> boardMainDtos = new ArrayList<>();
        for (Board board : allBoard) {
            String saveImgPath = board.getPhoto().getSavedPath(); // 게시물 이미지
            BoardMainDto boardMainDto = BoardMainDto.toDto(
                    isPushLike(member, board),
                    saveImgPath, board,
                    photoService.noPhotoFinder(board.getMember()));
            if (!board.getComments().isEmpty()) {
                Comment comment = board.getComments().get(0);
                boardMainDto.setBoardCommentInfo(BoardCommentInfo.toDto(comment));
            }
            if (!board.getLikes().isEmpty()) {
                Member likeMember = boardLikeRepositoryImpl.findLikeMember(board).get(0);
                boardMainDto.setBoardLikeInfo(new BoardLikeInfo(likeMember.getId(),likeMember.getNickName(), photoService.noPhotoFinder(likeMember)));
            }
            boardMainDtos.add(boardMainDto);
        }
        return boardMainDtos;
    }

    public List<BoardMyListDto> findMyList(Long id) {
        List<BoardMyListDto> myList = boardRepository.findMyList(id);
        return myList;
    }

    public Long countMyList(Long id){
        Long count = boardRepository.countMyBoard(id);
        return count;
    }
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalStateException("없는 게시물"));
        boardRepository.delete(board);
    }
}