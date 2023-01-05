package KangWCB.comgram.board.comment;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.dto.BoardMainDto;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.member.Role;
import KangWCB.comgram.member.dto.MemberFormDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class CommentServiceTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    @Test
    public void Comment등록() throws Exception {
        MemberFormDto memberFormDto = MemberFormDto.builder().email("test2@naver.com").name("test User").nickname("가나다라").role(Role.USER).password("12345").build();
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        em.persist(member);

        Board board = Board.builder().content("박찬호의 연설").imgId(111L).member(member).viewCount(0).build();
        em.persist(board);

        Comment comment = Comment.builder().comment("저도요").createdDate(String.valueOf(LocalDateTime.now())).boards(board).member(member).build();
        em.persist(comment);



    }





}