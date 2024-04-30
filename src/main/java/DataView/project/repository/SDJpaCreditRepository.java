package DataView.project.repository;

import DataView.project.domain.Credit;
import DataView.project.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaCreditRepository extends JpaRepository<Credit, Long> {
    Credit findBySubject(Subject subject);
}
