package DataView.project.repository;

import DataView.project.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaSubjectRepository extends JpaRepository<Subject, Long> {
}
