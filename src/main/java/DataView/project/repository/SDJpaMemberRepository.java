package DataView.project.repository;

import DataView.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SDJpaMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String userId);

    @Query("SELECT DISTINCT m FROM Member m " +
            "JOIN FETCH m.timeTables tt " +
            "WHERE m.Id = :memberId")
    Member findByIdWithTimeTables(Long memberId);
}
