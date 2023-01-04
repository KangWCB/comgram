package KangWCB.comgram.photo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class PhotoService {

    @Value("${file.dir}")
    private String fileDir;

    private final PhotoRepository fileRepository;

    public Long saveFile(MultipartFile files){
        if (files.isEmpty()) {
            return null;
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
        String savedPath = fileDir + savedName;

        // 파일 엔티티 생성
        Photo file = Photo.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        // 실제로 로컬에 uuid를 파일명으로 저장
        try {
            files.transferTo(new File(savedPath));
        } catch (IOException e) {
            log.info("error: {}",e.getMessage());
            throw new IllegalStateException("파일저장 실패");

        }
        // 데이터베이스에 파일 정보 저장
        Photo savedFile = fileRepository.save(file);
        return savedFile.getId();
    }

    public String findSavePath(Long imgId){
        Photo photo = fileRepository.findById(imgId).orElse(null);
        return photo.getSavedPath();
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
