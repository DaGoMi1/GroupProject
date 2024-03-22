package DataView.project.repository;

import DataView.project.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaCommentRepository extends JpaRepository<Comment, Long> {
}
