package DataView.project.controller;

import DataView.project.domain.Comment;
import DataView.project.domain.Member;
import DataView.project.domain.Posting;
import DataView.project.dto.BoardTypeRequest;
import DataView.project.dto.PostingDTO;
import DataView.project.service.CommentService;
import DataView.project.service.MemberService;
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
    private final MemberService memberService;

    public PostingController(PostingService postingService,
                             CommentService commentService,
                             MemberService memberService) {
        this.postingService = postingService;
        this.commentService = commentService;
        this.memberService = memberService;
    }

    @PostMapping("")
    public ResponseEntity<?> savePosting(@RequestBody Posting posting) {
        try {
            String boardType = posting.getBoardType(); // BoardType 변수에 저장

            if (!("free".equals(boardType) || "notice".equals(boardType))) { // BoardType이 free나 notice가 아니라면
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 게시글 유형");
            }

            Member member = memberService.getMember(); // 현재 사용자 가져오기
            postingService.postSave(member, posting); // 게시글 저장

            return ResponseEntity.ok().body("게시글 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 저장 실패");
        }
    }

    @PatchMapping("")
    public ResponseEntity<?> updatePosting(@RequestBody Posting posting) {
        try {
            Member member = memberService.getMember();
            postingService.updatePosting(posting, member);
            return ResponseEntity.ok().body("수정 완료!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패: " + e.getMessage());
        }
    }

    @PostMapping("/list")
    public ResponseEntity<?> postingList(@RequestBody BoardTypeRequest request) {
        try {
            // BoardType에 맞는 게시글 리스트 가져오기
            List<PostingDTO> postings = postingService.findListByBoardType(request.getBoardType());
            return ResponseEntity.ok().body(postings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 불러오기 실패: " + e.getMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> postingDelete(@RequestBody Posting posting) {
        try {
            Member member = memberService.getMember(); // 현재 사용자 가져오기

            if (postingService.checkMemberPosting(member, posting.getId())) { // 게시글을 쓴 사람이 사용자와 같다면
                postingService.deletePosting(posting.getId()); // 게시글 삭제
                return ResponseEntity.ok().body("게시글 삭제 완료");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("본인의 게시글이 아닙니다"); // 다르다면
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제 실패: " + e.getMessage());
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody Comment comment) {
        try {
            Member member = memberService.getMember(); // 현재 사용자 가져오기
            commentService.commentSave(member, comment); // 댓글 저장
            return ResponseEntity.ok().body("댓글 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 저장 실패: " + e.getMessage());
        }
    }

    @PatchMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment) {
        try {
            Member member = memberService.getMember(); // 현재 사용자 가져오기

            // 현재 사용자가 댓글 작성자인지 확인
            if (commentService.checkMemberComment(member, comment.getId())) {
                // 댓글 수정 메서드 호출
                commentService.commentUpdate(member, comment);
                return ResponseEntity.ok().body("댓글이 성공적으로 수정되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("본인의 댓글만 수정할 수 있습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 수정 실패: " + e.getMessage());
        }
    }


    @DeleteMapping("/comment")
    public ResponseEntity<?> commentDelete(@RequestBody Comment comment) {
        try {
            Member member = memberService.getMember(); // 현재 사용자 가져오기

            if (commentService.checkMemberComment(member, comment.getId())) { // 댓글을 쓴 사람이 사용자가 맞다면
                commentService.deleteComment(comment.getId()); // 댓글 삭제
                return ResponseEntity.ok().body("댓글 삭제 완료");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("본인의 댓글이 아닙니다"); // 아니라면
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패: " + e.getMessage());
        }
    }
}

