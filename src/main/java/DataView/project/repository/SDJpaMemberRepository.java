package DataView.project.repository;

import DataView.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SDJpaMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserNumber(String userId);
    Optional<Member> findByUserIdAndPassword(String userId, String password);
    Optional<Member> findByEmail(String email);
}
