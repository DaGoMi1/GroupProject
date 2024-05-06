package DataView.project.repository;


import DataView.project.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaFileRepository extends JpaRepository<File, Long> {
}
