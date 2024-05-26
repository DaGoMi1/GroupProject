package DataView.project.repository;

import DataView.project.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SDJpaPostingRepository extends JpaRepository<Posting, Long> {

    @Query("SELECT p FROM Posting p LEFT JOIN FETCH p.comments WHERE p.boardType = :boardType ORDER BY p.Id ASC")
    List<Posting> findAllByBoardTypeWithComments(@Param("boardType") String boardType);
}
