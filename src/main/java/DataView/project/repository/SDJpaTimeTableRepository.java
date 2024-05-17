package DataView.project.repository;

import DataView.project.domain.Member;
import DataView.project.domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SDJpaTimeTableRepository extends JpaRepository<TimeTable, Long> {
    TimeTable findByMemberAndGradeAndSemester(Member member, int grade, String semester);
    @Query("SELECT m FROM TimeTable m JOIN FETCH m.subjects WHERE m.id = :timeTableId")
    TimeTable findByIdWithSubjects(Long timeTableId);
}
