package DataView.project.service;

import DataView.project.domain.Comment;
import DataView.project.dto.CustomUserDetails;
import DataView.project.repository.SDJpaCommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class CommentService {
    private final SDJpaCommentRepository commentRepository;

    public CommentService(SDJpaCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void commentSave(Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        comment.setUserId(userDetails.getUsername());
        comment.setCreatedDate(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
