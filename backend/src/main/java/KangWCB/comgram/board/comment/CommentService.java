package KangWCB.comgram.board.comment;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.comment.dto.CommentRequestDto;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     */
    @Transactional
    public Long commentSave(String email, Long id, CommentRequestDto requestDto){
        Member findMember = memberRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("유저가 없습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("댓글 쓰기 실패: 해당 게시물이 없습니다." + id));

        requestDto.setMember(findMember);
        requestDto.setBoards(board);

        Comment comment = requestDto.toEntity();
        commentRepository.save(comment);
        return requestDto.getId();
    }

    @Transactional
    public String delete(Long commentId, String email) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("댓글이 없습니다."));
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("찾는 사람이 없습니다."));
        if(comment.getMember() != member){
            throw new RuntimeException("댓글 작성자와 삭제하려는 사람이 다릅니다.");
        }
        commentRepository.delete(comment);
        return "성공적으로 제거 되었습니다.";
    }
}
