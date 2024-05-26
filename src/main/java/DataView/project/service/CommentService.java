package DataView.project.service;

import DataView.project.domain.Comment;
import DataView.project.domain.Member;
import DataView.project.repository.SDJpaCommentRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class CommentService {
    private final SDJpaCommentRepository commentRepository;

    public CommentService(SDJpaCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public void commentSave(Member member, Comment comment) {
        comment.setAuthor(member.getName());
        comment.setUserId(member.getUsername());
        comment.setCreatedDate(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public void commentUpdate(Member member, Comment comment) throws Exception {
        // 먼저 댓글이 존재하는지 확인
        Comment existingComment = getCommentById(comment.getId());
        if (existingComment == null) {
            throw new Exception("존재하지 않는 댓글입니다.");
        }

        // 댓글을 작성한 회원이 맞는지 확인
        if (!existingComment.getUserId().equals(member.getUsername())) {
            throw new Exception("댓글을 수정할 수 있는 권한이 없습니다.");
        }

        // 새로운 댓글 내용으로 업데이트
        existingComment.setComment(comment.getComment());
        existingComment.setAuthor(member.getName());
        existingComment.setCreatedDate(LocalDateTime.now());

        // 수정된 댓글 저장
        commentRepository.save(existingComment);
    }



    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    public boolean checkMemberComment(Member member, Long commentId) {
        return getCommentById(commentId).getPosting().getUserId().equals(member.getUsername());
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


}
