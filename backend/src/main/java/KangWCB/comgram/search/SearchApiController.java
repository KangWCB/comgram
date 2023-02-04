package KangWCB.comgram.search;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.repository.BoardRepositoryImpl;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.repository.MemberRepositoryImpl;
import KangWCB.comgram.message.Message;
import KangWCB.comgram.photo.PhotoService;
import KangWCB.comgram.search.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@Slf4j
@RequiredArgsConstructor
public class SearchApiController {
    private final MemberRepositoryImpl memberRepositoryCustomImpl;
    private final BoardRepositoryImpl boardRepositoryImpl;
    private final PhotoService photoService;
    /**
     * 찾아주기
     * NoSearch, Board, Member
     */
    @GetMapping
    public SearchResponseDto find(@RequestParam(name = "word") String word){
        // 빈칸이라면
        if(word.isEmpty()) {
            return new SearchResponseDto("NoSearch",0, new ArrayList());
        }
        if (word.startsWith("#")){
            word = word.substring(1);
            List<Board> wordContent = boardRepositoryImpl.findWordContent(word);
            List<SearchBoardDto> result = new ArrayList<>();
            wordContent.forEach(board -> {
                String savePath = board.getPhoto().getSavedPath();
                result.add(new SearchBoardDto(board.getId(), savePath));
            });
            return new SearchResponseDto("Board", wordContent.size(), result);
        } else {
            List<SearchMemberDto> result = new ArrayList<>();
            memberRepositoryCustomImpl.findMember(word).forEach(member -> {
                String savePath = photoService.noPhotoFinder(member);
                result.add(new SearchMemberDto(member.getId(), member.getNickName(), savePath, member.getIntroMsg()));
            });
            return new SearchResponseDto("Member", memberRepositoryCustomImpl.findMember(word).size(), result);
        }
    }

}
