package KangWCB.comgram.board.comment;

import KangWCB.comgram.board.comment.dto.CommentRequestDto;
import KangWCB.comgram.config.jwt.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Slf4j
public class CommentApiController {
    private final CommentService commentService;
    /**
     * 댓글 생성 API
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentRequestDto dto,
                                      @AuthenticationPrincipal SecurityUser user){
        return ResponseEntity.ok(commentService.commentSave(user.getMember().getEmail(),id,dto));
    }
}