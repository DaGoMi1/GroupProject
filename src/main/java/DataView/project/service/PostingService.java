package DataView.project.service;

import DataView.project.domain.Posting;
import DataView.project.dto.CustomUserDetails;
import DataView.project.repository.SDJpaPostingRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;


@Transactional
public class PostingService {
    @Value("${file.upload.directory}")
    private String uploadDirectory;  // application.properties에서 설정된 파일 업로드 디렉토리
    private final SDJpaPostingRepository postingRepository;

    public PostingService(SDJpaPostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    public void postSave(Posting posting, MultipartFile image, MultipartFile video, MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        posting.setUserId(userDetails.getUsername());
        posting.setCreated_at(LocalDateTime.now());

        updateFilePaths(posting, image, video, file);
        postingRepository.save(posting);
    }

    private void updateFilePaths(Posting posting, MultipartFile image, MultipartFile video, MultipartFile file) {
        // 파일 업로드 및 저장
        String imagePath = StringUtils.isNotBlank(image.getOriginalFilename()) ? saveImageAndReturnPath(image) : null;
        String videoPath = StringUtils.isNotBlank(video.getOriginalFilename()) ? saveVideoAndReturnPath(video) : null;
        String filePath = StringUtils.isNotBlank(file.getOriginalFilename()) ? saveFileAndReturnPath(file) : null;

        // 엔터티의 해당 필드 업데이트
        posting.setImagePath(imagePath);
        posting.setVideoPath(videoPath);
        posting.setFilePath(filePath);
    }

    public String saveImageAndReturnPath(MultipartFile image) {
        try {
            String fileName = UUID.randomUUID() + ".png";

            // 이미지를 서버에 저장
            Path destinationDirectory = Paths.get(uploadDirectory, "image");

            Path destination = destinationDirectory.resolve(fileName);
            Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return "/image/" + fileName;
        } catch (Exception e) {
            return "이미지 저장 실패: " + e.getMessage();
        }
    }

    public String saveFileAndReturnPath(MultipartFile file) {
        try {
            String fileName = "file_" + System.currentTimeMillis() + ".pdf";

            // 파일을 서버에 저장
            Path destinationDirectory = Paths.get(uploadDirectory, "file");

            Path destination = destinationDirectory.resolve(fileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return "/file/" + fileName;
        } catch (Exception e) {
            return "파일 저장 실패: " + e.getMessage();
        }
    }

    public String saveVideoAndReturnPath(MultipartFile video) {
        try {
            String fileName = "video_" + UUID.randomUUID() + ".avi";

            // 비디오를 서버에 저장
            Path destinationDirectory = Paths.get(uploadDirectory, "video");

            Path destination = destinationDirectory.resolve(fileName);
            Files.copy(video.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return "/video/" + fileName;
        } catch (Exception e) {
            return "비디오 저장 실패: " + e.getMessage();
        }
    }

}