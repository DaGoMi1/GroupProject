package DataView.project.repository;

import DataView.project.domain.GeneralEducationCurriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaGeneralEducationCurriculumRepository extends JpaRepository<GeneralEducationCurriculum, Long> {
    GeneralEducationCurriculum findByYearAndCourseCode(int year, String courseCode);
}
