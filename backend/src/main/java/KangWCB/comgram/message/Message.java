package KangWCB.comgram.message;

import KangWCB.comgram.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Message {

    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id; // 메세지 식별자

    private Long roomNm; // 채팅방 번호

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 멤버 상태에 따라 변경되며 사라지면 사라짐
    @JoinColumn(name = "member_id")
    private Member sender;// 보낸 사람

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member receiver; // 받는 사람

    private String content; // 메세지 내용

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendTime; // 보낸시간

    private LocalDateTime readTime; // 읽은 시간

    private Boolean readCheck; // 읽었는지 여부


    @Builder
    public Message(Long roomNm, Member sender, Member receiver, String content, LocalDateTime sendTime, LocalDateTime readTime, Boolean readCheck) {
        this.roomNm = roomNm;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
        this.readTime = readTime;
        this.readCheck = readCheck;
    }

    // 생성 메소드
    public Message createMessage(Long roomNm,String content,Member sender, Member receiver){
        Message msg = Message.builder()
                .roomNm(roomNm)
                .content(content)
                .sender(sender)
                .receiver(receiver)
                .readCheck(false)
                .build();
        return msg;
    }

    // 읽음 체크
    public void readMsg(){
        this.readTime =  LocalDateTime.now();
        if(this.readCheck == false){
            readCheck = true;
        }
    }
}
