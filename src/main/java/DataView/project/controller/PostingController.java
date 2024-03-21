package DataView.project.controller;

import DataView.project.domain.Posting;
import DataView.project.dto.BoardTypeRequest;
import DataView.project.service.PostingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posting")
public class PostingController {
    private final PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @PostMapping("/save")
    public String savePosting(@RequestBody Posting posting) {
        try {
            postingService.postSave(posting);
            return "게시글 저장 완료";
        } catch (Exception e) {
            return "게시글 저장 실패: " + e.getMessage();
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> postingList(@RequestBody BoardTypeRequest request){
        try{
            List<Posting> postings = postingService.findListByBoardType(request.getBoardType());
            return ResponseEntity.ok().body(postings);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 저장 실패: " + e.getMessage());
        }
    }
}
