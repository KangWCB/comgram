package KangWCB.comgram.follow;

import KangWCB.comgram.config.jwt.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{id}")
    public ResponseEntity follow(@PathVariable(name = "id") Long followerId,
                                 @AuthenticationPrincipal SecurityUser user){
        boolean result = false;
        String msg = null;
        if(user != null){
            msg = followService.follow(user.getMember().getId(), followerId);
            result = true;
        }
        return result ? ResponseEntity.ok().body(msg) : ResponseEntity.badRequest().body("잘못됨");
    }
}
