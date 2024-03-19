package DataView.project.repository;

import DataView.project.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SDJpaPostingRepository extends JpaRepository<Posting, Long> {
}
