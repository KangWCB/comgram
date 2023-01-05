package KangWCB.comgram.board.comment;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.comment.dto.CommentRequestDto;
import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
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
}
