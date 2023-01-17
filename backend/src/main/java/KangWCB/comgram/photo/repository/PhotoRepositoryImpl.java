package KangWCB.comgram.photo.repository;

import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.QMember;
import KangWCB.comgram.photo.QPhoto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepositoryCustom{
    @Value("${default.profile}")
    private String defaultProfile;
    private final JPAQueryFactory queryFactory;

    QMember qMember = QMember.member;
    QPhoto qPhoto = QPhoto.photo;

    @Override
    public String findProfileSavedPath(Member member) {
        String path = queryFactory.select(qMember.photo.savedPath.coalesce(defaultProfile))
                .from(qMember)
                .leftJoin(qMember.photo,qPhoto)
                .where(qMember.eq(member))
                .fetchOne();
        return path;
    }
}
