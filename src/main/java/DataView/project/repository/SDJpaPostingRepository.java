package DataView.project.repository;

import DataView.project.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SDJpaPostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findAllByBoardType(String boardType);
}
