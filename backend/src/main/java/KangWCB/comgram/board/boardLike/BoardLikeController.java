package KangWCB.comgram.board.boardLike;

import KangWCB.comgram.config.jwt.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards/")
public class BoardLikeController {
    private final BoardLikeService boardLikeService;

    @PostMapping("/{boardId}/like")
    public ResponseEntity<String> addLike(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable(name = "boardId") Long boardId){
        boolean result = false;
        if (securityUser != null){
            result = boardLikeService.addLike(securityUser.getMember().getId(),boardId);
        }

        return result ?
                new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
