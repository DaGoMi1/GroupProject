package DataView.project.service;

import DataView.project.domain.Course;
import DataView.project.domain.Member;
import DataView.project.domain.Subject;
import DataView.project.domain.TimeTable;
import DataView.project.dto.SubjectRequest;
import DataView.project.repository.SDJpaCourseRepository;
import DataView.project.repository.SDJpaSubjectRepository;
import DataView.project.repository.SDJpaTimeTableRepository;

import java.util.List;

public class TimeTableService {
    private final SDJpaTimeTableRepository timeTableRepository;
    private final SDJpaSubjectRepository subjectRepository;
    private final SDJpaCourseRepository courseRepository;

    public TimeTableService(SDJpaTimeTableRepository timeTableRepository,
                            SDJpaSubjectRepository subjectRepository,
                            SDJpaCourseRepository courseRepository) {
        this.timeTableRepository = timeTableRepository;
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
    }

    public TimeTable loadTimeTable(Member member, int grade, String semester) {
        return timeTableRepository.findByMemberAndGradeAndSemester(member, grade, semester);
    }

    public void addSubject(TimeTable timeTable, Subject subject) {
        subject.setTimeTable(timeTable);
        subjectRepository.save(subject);
    }

    public Subject mapSubjectRequestToSubject(SubjectRequest request) {
        Subject subject = new Subject();
        subject.setArea(request.getArea());
        subject.setSubArea(request.getSubArea());
        subject.setCollege(request.getCollege());
        subject.setDepartment(request.getDepartment());
        subject.setSubjectYear(request.getSubjectYear());
        subject.setCurriculumType(request.getCurriculumType());
        subject.setCourseCode(request.getCourseCode());
        subject.setCourseName(request.getCourseName());
        subject.setProfessor(request.getProfessor());
        subject.setCredit(request.getCredit());
        subject.setLectureTime(request.getLectureTime());
        return subject;
    }

    public List<Subject> getMemberSubjectList(TimeTable timeTable) {
        return timeTable.getSubjects();
    }

    public Course getCourse(int year, String semester, String curriculumType) {
        return courseRepository.findByYearAndSemesterAndCurriculumType(year, semester, curriculumType);
    }

    public List<Subject> getSubjectList(Course course){
        return course.getSubjects();
    }
}
