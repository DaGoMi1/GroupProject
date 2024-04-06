package DataView.project.service;

import DataView.project.domain.Course;
import DataView.project.domain.Member;
import DataView.project.domain.Subject;
import DataView.project.domain.TimeTable;
import DataView.project.dto.CourseDTO;
import DataView.project.dto.SubjectDTO;
import DataView.project.dto.SubjectRequest;
import DataView.project.repository.SDJpaCourseRepository;
import DataView.project.repository.SDJpaSubjectRepository;
import DataView.project.repository.SDJpaTimeTableRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Transactional
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

    public List<SubjectDTO> getMemberSubjectList(Member member, int grade, String semester) {
        TimeTable timeTable = loadTimeTable(member, grade, semester);
        return mapSubjectsToDTO(timeTable.getSubjects());
    }

    private List<SubjectDTO> mapSubjectsToDTO(List<Subject> subjects) {
        List<SubjectDTO> subjectDTOList = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setId(subject.getId());
            subjectDTO.setArea(subject.getArea());
            subjectDTO.setSubArea(subject.getSubArea());
            subjectDTO.setCollege(subject.getCollege());
            subjectDTO.setDepartment(subject.getDepartment());
            subjectDTO.setSubjectYear(subject.getSubjectYear());
            subjectDTO.setCurriculumType(subject.getCurriculumType());
            subjectDTO.setCourseCode(subject.getCourseCode());
            subjectDTO.setCourseName(subject.getCourseName());
            subjectDTO.setProfessor(subject.getProfessor());
            subjectDTO.setCredit(subject.getCredit());
            subjectDTO.setLectureTime(subject.getLectureTime());

            subjectDTOList.add(subjectDTO);
        }
        return subjectDTOList;
    }

    public CourseDTO getCourse(int year, String semester, String curriculumType) {
        Course course = courseRepository.findByYearAndSemesterAndCurriculumType(year, semester, curriculumType);
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setSubjects(mapSubjectsToDTO(course.getSubjects()));
        return courseDTO;
    }

    public List<SubjectDTO> getSubjectList(CourseDTO course) {
        return course.getSubjects();
    }
}
