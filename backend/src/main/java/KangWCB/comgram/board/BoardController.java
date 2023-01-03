package KangWCB.comgram.board;

import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.config.jwt.SecurityUser;
import KangWCB.comgram.photo.Photo;
import KangWCB.comgram.photo.PhotoService;
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
    private final PhotoService photoService;

    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity write(@RequestParam(value = "content") String content,
                                  @RequestParam(value="photo", required=false) MultipartFile file,
                                  @AuthenticationPrincipal SecurityUser member) {
        BoardFormDto boardFormDto = BoardFormDto.builder()
                .content(content)
                .member(member.getMember())
                .build();
        try{
            Long savedImgId = photoService.saveFile(file);
            if(savedImgId!=null){
                boardFormDto.setImgId(savedImgId);
            }
            boardService.write(boardFormDto);
        } catch (UsernameNotFoundException e){
            log.info("exception:{}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        log.info("controller: {}",boardService.write(boardFormDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
