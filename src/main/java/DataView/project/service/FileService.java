package DataView.project.service;

import DataView.project.domain.File;
import DataView.project.domain.Member;
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


    public void saveFile(MultipartFile file, Long postId, Member member) throws IOException {
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


}

