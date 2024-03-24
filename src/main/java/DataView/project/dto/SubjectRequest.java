package DataView.project.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {
    @Column(nullable = false)
    private int grade; // 시간표 저장 학년

    @Column(nullable = false)
    private String semester; // 시간표 저장 학기

    private String area; // 영역

    private String subArea; // 소영역

    @Column(nullable = false)
    private String college; // 개설 단과 대학

    @Column(nullable = false)
    private String department; // 개설 학과

    @Column(nullable = false)
    private int subjectYear; // 수강 학년

    @Column(nullable = false)
    private String curriculumType; // 교과 구분

    @Column(nullable = false)
    private String courseCode; // 과목 코드

    @Column(nullable = false)
    private String courseName; // 과목명

    @Column(nullable = false)
    private String professor; // 담당 교수

    @Column(nullable = false)
    private String credit; // 과목 학점

    private String lectureTime; //강의 시간
}
