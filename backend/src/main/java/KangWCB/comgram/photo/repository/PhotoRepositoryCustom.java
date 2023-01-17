package KangWCB.comgram.photo.repository;

import KangWCB.comgram.member.Member;

public interface PhotoRepositoryCustom {
    String findProfileSavedPath(Member member);
}
