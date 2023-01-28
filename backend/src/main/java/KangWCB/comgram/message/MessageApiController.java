package KangWCB.comgram.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageApiController {


    // 모두 json 데이터로 담아서 보낸 사람 받는 사람 보낼 예정
    // 메세지 보내기
    @PostMapping("/send")


    // 메세지 리스트 받기
    @GetMapping("/read")

    // 메세지 채팅방 읽기 => 읽음 표시 나오게, 그리고 읽은 날짜 저장
    @GetMapping("/read/{chatroomId}")

}
