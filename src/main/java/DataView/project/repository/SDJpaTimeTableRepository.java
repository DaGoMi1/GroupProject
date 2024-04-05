package DataView.project.repository;

import DataView.project.domain.Member;
import DataView.project.domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaTimeTableRepository extends JpaRepository<TimeTable, Long> {
    TimeTable findByMemberAndGradeAndSemester(Member member, int grade, String semester);
}
