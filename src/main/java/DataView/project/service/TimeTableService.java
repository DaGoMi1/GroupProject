package DataView.project.service;

import DataView.project.domain.Course;
import DataView.project.domain.Member;
import DataView.project.domain.Subject;
import DataView.project.domain.TimeTable;
import DataView.project.dto.*;
import DataView.project.repository.SDJpaCourseRepository;
import DataView.project.repository.SDJpaMemberRepository;
import DataView.project.repository.SDJpaSubjectRepository;
import DataView.project.repository.SDJpaTimeTableRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class TimeTableService {
    private final SDJpaTimeTableRepository timeTableRepository;
    private final SDJpaSubjectRepository subjectRepository;
    private final SDJpaCourseRepository courseRepository;
    private final SDJpaMemberRepository memberRepository;

    public TimeTableService(SDJpaTimeTableRepository timeTableRepository,
                            SDJpaSubjectRepository subjectRepository,
                            SDJpaCourseRepository courseRepository,
                            SDJpaMemberRepository memberRepository) {
        this.timeTableRepository = timeTableRepository;
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.memberRepository = memberRepository;
    }

    public TimeTable loadTimeTable(Member member, int grade, String semester) {
        return timeTableRepository.findByMemberAndGradeAndSemester(member, grade, semester);
    }

    public void addSubject(TimeTable timeTable, Subject subject) {
        subject.setTimeTable(timeTable);
        subject.setCourse(null);
        subject.setId(null);
        subjectRepository.save(subject);
    }

    public Subject getSubjectById(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.orElse(null);
    }

    public boolean deleteMemberSubject(Member member, Long id) {
        Subject subject = getSubjectById(id);

        if (subject.getTimeTable().getMember() == member) {
            subjectRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
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


    public List<Subject> getAllSubject(Member member) {
        member = memberRepository.findByIdWithTimeTables(member.getId());

        List<TimeTable> timeTables = member.getTimeTables();
        timeTables.replaceAll(timeTable -> {
            TimeTable updatedTimeTable = timeTableRepository.findByIdWithSubjects(timeTable.getId());
            return updatedTimeTable != null ? updatedTimeTable : new TimeTable();
        });

        List<Subject> subjectList = new ArrayList<>();
        timeTables.forEach(timeTable -> {
            List<Subject> subjects = timeTable.getSubjects();
            if (subjects != null) {
                subjectList.addAll(subjects);
            }
        });

        return subjectList;
    }
}