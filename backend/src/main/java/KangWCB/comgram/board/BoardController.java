package KangWCB.comgram.board;

import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.config.jwt.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public Long write(@RequestParam(value = "content") String content,
//                                  @RequestParam(value="photo", required=false) List<MultipartFile> files,
                                  @AuthenticationPrincipal SecurityUser member) {
        BoardFormDto boardFormDto = BoardFormDto.builder()
                .content(content)
                .member(member.getMember())
                .build();

        log.info("controller: {}",boardService.write(boardFormDto));
        return null;
    }
}
