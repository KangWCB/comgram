package KangWCB.comgram.board;

import KangWCB.comgram.board.dto.BoardDetailDto;
import KangWCB.comgram.board.dto.BoardFormDto;
import KangWCB.comgram.config.jwt.SecurityUser;
import KangWCB.comgram.photo.Photo;
import KangWCB.comgram.photo.PhotoService;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    public ResponseEntity write(@RequestParam(value = "content", required = true) String content,
                                  @RequestParam(value="photo", required=true) MultipartFile file,
                                  @AuthenticationPrincipal SecurityUser member) {
        BoardFormDto boardFormDto = BoardFormDto.builder()
                .content(content)
                .member(member.getMember())
                .build();
        try{
            Photo photo = photoService.savedBoardPhoto(file);
            if(photo!=null){
                boardFormDto.setPhoto(photo);
            }
            boardService.write(boardFormDto);
        } catch (UsernameNotFoundException e){
            log.info("exception:{}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 팔로우가 있으면 팔로우한 사람 게시물
     * 없으면 전체 게시물
     */
    @GetMapping("/list")
    public Result<List> list(@AuthenticationPrincipal SecurityUser user){
        return new Result<>(boardService.allMyList(user.getMember().getId()));
    }
    @GetMapping("{boardId}")
    public BoardDetailDto boardDetail(@PathVariable(name="boardId") Long boardId){
        return boardService.findBoardDetail(boardId);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
