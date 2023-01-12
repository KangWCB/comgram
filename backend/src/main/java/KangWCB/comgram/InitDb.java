package KangWCB.comgram;

import KangWCB.comgram.board.Board;
import KangWCB.comgram.board.boardLike.BoardLike;
import KangWCB.comgram.board.comment.Comment;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.Role;
import KangWCB.comgram.member.dto.MemberFormDto;
import KangWCB.comgram.photo.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

/**
 * 시범용 데이터
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        @Value("${test.ImgPath}")
        String testImgPath;

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            makeMember("정우진","test@naver.com","갱킹맨");
        }

        private Member makeMember(String name, String email,String nickName) {
            MemberFormDto memberFormDto = MemberFormDto.builder().name(name).email(email).password("1234")
                    .nickname(nickName).role(Role.USER).build();
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            em.persist(member);
            return member;
        }

        /**
         * 게시물 , 좋아요 누르기 , 댓글 테스트데이터
         */
        public void dbInit2(){
            Member member = makeMember("신봉규","test2@naver.com","나이트메어");
            for (int i = 0; i < 5; i++) {
                Photo testPhoto = Photo.builder().orgNm("testImg").savedNm("testImgSave").savedPath(testImgPath).build();
                em.persist(testPhoto);
                Long testPhotoId = testPhoto.getId();
                Board board = Board.builder().viewCount(0).member(member).imgId(testPhotoId).content("안녕하세요!" + i).build();
                em.persist(board);
                Comment comment = Comment.builder().comment("재밌네요 친추할게요!" + i)
                        .createdDate(String.valueOf(LocalDateTime.now()))
                        .modifiedDate(String.valueOf(LocalDateTime.now()))
                        .member(member)
                        .boards(board)
                        .build();
                em.persist(comment);

                BoardLike boardLike = BoardLike.builder().board(board).member(member).build();
                em.persist(boardLike);
            }

        }

    }

}


