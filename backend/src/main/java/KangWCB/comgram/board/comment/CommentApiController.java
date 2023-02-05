package KangWCB.comgram.board.comment;

import KangWCB.comgram.board.comment.dto.CommentRequestDto;
import KangWCB.comgram.config.jwt.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentApiController {
    private final CommentService commentService;
    /**
     * 댓글 생성 API
     */
    @PostMapping("/{boardId}/comment")
    public ResponseEntity commentSave(@PathVariable(name = "boardId") Long boardId, @RequestBody CommentRequestDto dto,
                                      @AuthenticationPrincipal SecurityUser user){
        Long savedId = commentService.commentSave(user.getMember().getEmail(), boardId, dto);
        return ResponseEntity.ok(savedId);
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(name = "commentId") Long commentId,
                                        @AuthenticationPrincipal SecurityUser user){
        String message = commentService.delete(commentId, user.getMember().getEmail());
        return ResponseEntity.ok(message);
    }
}