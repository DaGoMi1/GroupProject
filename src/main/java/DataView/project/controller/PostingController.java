package DataView.project.controller;

import DataView.project.domain.Posting;
import DataView.project.service.PostingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posting")
public class PostingController {
    private final PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @PostMapping("/save")
    public String savePosting(@RequestBody Posting posting,
                              @RequestParam MultipartFile image,
                              @RequestParam MultipartFile video,
                              @RequestParam MultipartFile file) {
        try {
            postingService.postSave(posting, image, video, file);
            return "게시글 저장 완료";
        } catch (Exception e) {
            return "게시글 저장 실패: " + e.getMessage();
        }
    }
}
