package KangWCB.comgram.photo;

import KangWCB.comgram.board.repository.BoardRepository;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class PhotoService {

    @Value("${file.dir}")
    private String fileDir;



    private final PhotoRepository photoRepository;
    private final MemberRepository memberRepository;

    /**
     * 수정 시에 포토를 바꾸는거니깐
     * 멤버ID를 조회해서 변경
     */
    @Transactional
    public Photo saveMemberPhoto(MultipartFile files, Long memberId){
        if (files.isEmpty()) {
            return null;
        }
        // 파일 경로 폴더 설정
        String savedFolder = fileDir +"/member";
        File dir = new File(savedFolder);
        if(dir.exists() == false){
            dir.mkdirs();
        }
        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();
        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();
        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = savedFolder + savedName;

        // 파일 엔티티 생성
        Photo file = Photo.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        // 실제로 로컬에 uuid를 파일명으로 저장
        try {
            files.transferTo(new File(savedPath));
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("유저없음"));
            member.addProfilePhoto(file);
        } catch (IOException e) {
            log.info("error: {}",e.getMessage());
            throw new IllegalStateException("파일저장 실패");

        }
        // 데이터베이스에 파일 정보 저장
        return photoRepository.save(file);
    }

    /**
     * 저장시에 일어남 + 이후 여러건 넣어줄 예정
     */
    @Transactional
    public Photo savedBoardPhoto(MultipartFile files){
        if (files.isEmpty()) {
            return null;
        }
        // 파일 경로 폴더 설정
        String savedFolder = fileDir +"/board";
        File dir = new File(savedFolder);
        if(dir.exists() == false){
            dir.mkdirs();
        }
        String origName = files.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = origName.substring(origName.lastIndexOf("."));
        String savedName = uuid + extension;
        String savedPath = savedFolder + savedName;
        Photo file = Photo.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();
        try {
            files.transferTo(new File(savedPath));
        } catch (IOException e) {
            log.info("error: {}",e.getMessage());
            throw new IllegalStateException("파일저장 실패");
        }
        // 데이터베이스에 파일 정보 저장
        return photoRepository.save(file);
    }
    public String noPhotoFinder(Member member){
        String profileSavedPath = photoRepository.findProfileSavedPath(member);
        return profileSavedPath;
    }
//    /**
//     * 파일 지우기
//     */
//    public void deleteFile(Long fileId) {
//        FileEntity fileEntity = fileRepository.findById(fileId).orElseThrow();
//        File file = new File(fileEntity.getSavedPath());
//
//        if (file.exists()) {
//            if (file.delete()) {
//                System.out.println("파일삭제 성공");
//            } else {
//                System.out.println("파일삭제 실패");
//            }
//        } else {
//            System.out.println("파일이 존재하지 않습니다.");
//        }
//
//        fileRepository.deleteById(fileId);
//    }
}
