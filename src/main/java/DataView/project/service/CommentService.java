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
        comment.setUserId(member.getUsername());
        comment.setCreatedDate(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    public boolean deleteComment(Member member, Long commentId) {
        if (getCommentById(commentId).getPosting().getUserId().equals(member.getUsername())) {
            commentRepository.deleteById(commentId);
            return true;
        } else {
            return false;
        }
    }
}
