package DataView.project.controller;

import DataView.project.domain.Comment;
import DataView.project.domain.Posting;
import DataView.project.dto.BoardTypeRequest;
import DataView.project.service.CommentService;
import DataView.project.service.PostingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posting")
public class PostingController {
    private final PostingService postingService;
    private final CommentService commentService;

    public PostingController(PostingService postingService,
                             CommentService commentService) {
        this.postingService = postingService;
        this.commentService = commentService;
    }

    @PostMapping("/save")
    public String savePosting(@RequestBody Posting posting) {
        try {
            String boardType = posting.getBoardType();
            if (!("free".equals(boardType) || "notice".equals(boardType))) {
                return "게시글 저장 실패: 유효하지 않은 게시글 유형입니다.";
            }
            postingService.postSave(posting);
            return "게시글 저장 완료";
        } catch (Exception e) {
            return "게시글 저장 실패: " + e.getMessage();
        }
    }

    @PostMapping("/list")
    public ResponseEntity<?> postingList(@RequestBody BoardTypeRequest request) {
        try {
            List<Posting> postings = postingService.findListByBoardType(request.getBoardType());
            return ResponseEntity.ok().body(postings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 저장 실패: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> postingDelete(@RequestBody Posting posting) {
        try {
            postingService.delete(posting.getId());
            return ResponseEntity.ok().body("게시글 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 삭제 실패: " + e.getMessage());
        }
    }

    @PostMapping("/comment/save")
    public ResponseEntity<?> saveComment(@RequestBody Comment comment) {
        try {
            commentService.commentSave(comment);
            return ResponseEntity.ok().body("댓글 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 저장 실패: " + e.getMessage());
        }
    }

    @PostMapping("/comment/delete")
    public ResponseEntity<?> commentDelete(@RequestBody Comment comment) {
        try {
            commentService.delete(comment.getId());
            return ResponseEntity.ok().body("댓글 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 삭제 실패: " + e.getMessage());
        }
    }


}
