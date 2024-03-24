package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false)
    private TimeTable timeTable;

    @Column(name = "area")
    private String area; // 영역

    @Column(name = "sub_area")
    private String subArea; // 소영역

    @Column(name = "college", nullable = false)
    private String college; // 개설 단과 대학

    @Column(name = "department", nullable = false)
    private String department; // 개설 학과

    @Column(name = "subject_year", nullable = false)
    private int subjectYear; // 수강 학년

    @Column(name = "curriculum_type", nullable = false)
    private String curriculumType; // 교과 구분

    @Column(name = "course_code", nullable = false)
    private String courseCode; // 과목 코드

    @Column(name = "course_name", nullable = false)
    private String courseName; // 과목명

    @Column(name = "professor", nullable = false)
    private String professor; // 담당 교수

    @Column(name = "credit", nullable = false)
    private String credit; // 과목 학점

    @Column(name = "lecture_time")
    private String lectureTime; //강의 시간
}
