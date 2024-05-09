package DataView.project.service;

import DataView.project.domain.File;
import DataView.project.repository.SDJpaFileRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


public class FileService {

    private final SDJpaFileRepository fileRepository;

    public FileService(SDJpaFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public void saveFile(MultipartFile file, Long postId) throws IOException {
        // 파일 저장 경로 설정
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 이름 설정 (중복을 피하기 위해 UUID 사용)
        String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + saveName;

        // 파일 저장
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // 파일 정보 저장
        File fileEntity = File.builder()
                .postId(postId)
                .originalName(file.getOriginalFilename())
                .saveName(filePath)
                .size(file.getSize())
                .build();
        fileRepository.save(fileEntity);
    }

    public void deleteFile(Long fileId) throws IOException {
        // 파일 정보 가져오기
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다"));

        // 파일 삭제
        Path filePath = Paths.get(file.getSaveName());
        Files.deleteIfExists(filePath);

        // 데이터베이스에서 파일 정보 삭제
        fileRepository.deleteById(fileId);
    }


}

