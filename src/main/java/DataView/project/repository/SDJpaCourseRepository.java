package DataView.project.repository;

import DataView.project.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaCourseRepository extends JpaRepository<Course,Long> {
    Course findByYearAndSemesterAndCurriculumType(int year, String semester, String curriculumType);
}
