package DataView.project.repository;

import DataView.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SDJpaMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserNumber(String name);
    Optional<Member> findByNameAndPassword(String name, String password);
    Optional<Member> findByEmail(String email);
}
