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
    @JoinColumn(name = "timetable_id")
    private TimeTable timeTable;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(mappedBy = "subject", cascade = CascadeType.REMOVE)
    private Credit credits;

    @Column(name = "area")
    private String area; // 영역

    @Column(name = "sub_area")
    private String subArea; // 소영역

    @Column(name = "college")
    private String college; // 개설 단과 대학

    @Column(name = "department")
    private String department; // 개설 학과

    @Column(name = "subject_year")
    private String subjectYear; // 수강 학년

    @Column(name = "curriculum_type")
    private String curriculumType; // 교과 구분

    @Column(name = "course_code")
    private String courseCode; // 과목 코드

    @Column(name = "course_name")
    private String courseName; // 과목명

    @Column(name = "professor")
    private String professor; // 담당 교수

    @Column(name = "credit")
    private String credit; // 과목 학점

    @Column(name = "lecture_time")
    private String lectureTime; //강의 시간
}
