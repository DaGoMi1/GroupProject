package DataView.project.service;

import DataView.project.domain.*;
import DataView.project.dto.*;
import DataView.project.repository.SDJpaCreditRepository;
import DataView.project.repository.SDJpaDataCreditRepository;
import DataView.project.repository.SDJpaGeneralEducationCurriculumRepository;
import DataView.project.repository.SDJpaLiberalArtsCreditRepository;
import jakarta.transaction.Transactional;

import java.util.*;

@Transactional
public class CreditService {
    private final SDJpaDataCreditRepository dataCreditRepository;
    private final SDJpaLiberalArtsCreditRepository liberalArtsCreditRepository;
    private final SDJpaGeneralEducationCurriculumRepository generalEducationCurriculumRepository;
    private final SDJpaCreditRepository creditRepository;

    public CreditService(SDJpaDataCreditRepository dataCreditRepository,
                         SDJpaLiberalArtsCreditRepository liberalArtsCreditRepository,
                         SDJpaGeneralEducationCurriculumRepository generalEducationCurriculumRepository,
                         SDJpaCreditRepository creditRepository) {
        this.dataCreditRepository = dataCreditRepository;
        this.liberalArtsCreditRepository = liberalArtsCreditRepository;
        this.generalEducationCurriculumRepository = generalEducationCurriculumRepository;
        this.creditRepository = creditRepository;
    }

    public DataCredit getDataCredit() {
        if (dataCreditRepository.findById(1L).isPresent()) {
            return dataCreditRepository.findById(1L).get();
        } else {
            throw new NoSuchElementException("학점을 찾을 수가 없습니다.");
        }
    }

    public LiberalArtsCredit getLiberalCredit() {
        if (liberalArtsCreditRepository.findById(1L).isPresent()) {
            return liberalArtsCreditRepository.findById(1L).get();
        } else {
            throw new NoSuchElementException("학점을 찾을 수가 없습니다.");
        }
    }

    public CreditDTO getCredit(DataCredit dataCredit, LiberalArtsCredit liberalArtsCredit) {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setDataCredit(dataCredit);
        creditDTO.setLiberalArtsCredit(liberalArtsCredit);
        return creditDTO;
    }

    public GradeDTO getGrade(DataCreditDTO dataCredit, LiberalArtsCreditDTO liberalArtsCredit) {
        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setDataCreditDTO(dataCredit);
        gradeDTO.setLiberalArtsCreditDTO(liberalArtsCredit);
        return gradeDTO;
    }

    public LiberalArtsCreditDTO getMemberLiberalArtsCredit(Member member, List<Subject> memberSubjectList) {
        LiberalArtsCreditDTO liberalArtsCreditDTO = new LiberalArtsCreditDTO(0, 0, 0, 0, 0, 0, 0, 0);

        String username = member.getUsername();
        int year = Integer.parseInt(username.substring(0, 4));

        int rest = 0;
        Set<String> uniqueCourseCodes = new HashSet<>();
        List<Subject> subjectList = new ArrayList<>(memberSubjectList);
        subjectList.removeIf(subject -> !subject.getCurriculumType().equals("교양선택"));

        for (Subject subject : subjectList) {
            String creditString = subject.getCredit();
            int index = creditString.indexOf('(');
            String numberString = creditString.substring(0, index);
            int credit = Integer.parseInt(numberString);

            int memberCredit = liberalArtsCreditDTO.getCreditBySubArea(subject.getSubArea());
            int universityCredit = liberalArtsCreditRepository.findFieldValueByIdAndFieldName(1L, subject.getSubArea());


            boolean checkCourse = generalEducationCurriculumRepository.
                    findByYearAndCourseCode(year, subject.getCourseCode()) == null;

            if (uniqueCourseCodes.contains(subject.getCourseCode())) {
                continue;
            } else if (memberCredit == -1 || checkCourse) {
                rest += credit;
            } else if (memberCredit + credit <= universityCredit) {
                switch (subject.getSubArea()) {
                    case "글로벌의사소통" ->
                            liberalArtsCreditDTO.setGlobalCommunication(liberalArtsCreditDTO.getGlobalCommunication() + credit);
                    case "해양문화" ->
                            liberalArtsCreditDTO.setMaritimeCulture(liberalArtsCreditDTO.getMaritimeCulture() + credit);
                    case "해양스포츠와예술" ->
                            liberalArtsCreditDTO.setMaritimeSportsArts(liberalArtsCreditDTO.getMaritimeSportsArts() + credit);
                    case "역사와문화" ->
                            liberalArtsCreditDTO.setHistoryCulture(liberalArtsCreditDTO.getHistoryCulture() + credit);
                    case "사회와경제" ->
                            liberalArtsCreditDTO.setSocietyEconomy(liberalArtsCreditDTO.getSocietyEconomy() + credit);
                    case "인성" ->
                            liberalArtsCreditDTO.setPersonalDevelopment(liberalArtsCreditDTO.getPersonalDevelopment() + credit);
                }
            } else {
                switch (subject.getSubArea()) {
                    case "글로벌의사소통" -> liberalArtsCreditDTO.setGlobalCommunication(universityCredit);
                    case "해양문화" -> liberalArtsCreditDTO.setMaritimeCulture(universityCredit);
                    case "해양스포츠와예술" -> liberalArtsCreditDTO.setMaritimeSportsArts(universityCredit);
                    case "역사와문화" -> liberalArtsCreditDTO.setHistoryCulture(universityCredit);
                    case "사회와경제" -> liberalArtsCreditDTO.setSocietyEconomy(universityCredit);
                    case "인성" -> liberalArtsCreditDTO.setPersonalDevelopment(universityCredit);
                }

                rest += universityCredit - memberCredit;
            }

            uniqueCourseCodes.add(subject.getCourseCode());
            liberalArtsCreditDTO.setTotal(liberalArtsCreditDTO.getTotal() + credit);
        }

        liberalArtsCreditDTO.setRest(rest);
        liberalArtsCreditDTO.setTotal(liberalArtsCreditDTO.getTotal() - rest);

        return liberalArtsCreditDTO;
    }

    public DataCreditDTO getMemberDataCredit(List<Subject> memberSubjectList,
                                             LiberalArtsCreditDTO liberalArtsCreditDTO) {
        DataCreditDTO dataCreditDTO = new DataCreditDTO(0, 0, 0, 0,
                0, liberalArtsCreditDTO.getTotal(), liberalArtsCreditDTO.getTotal());

        int rest = liberalArtsCreditDTO.getRest();
        Set<String> uniqueCourseCodes = new HashSet<>();
        memberSubjectList.removeIf(subject -> subject.getCurriculumType().equals("교양선택"));

        for (Subject subject : memberSubjectList) {
            String creditString = subject.getCredit();
            int index = creditString.indexOf('(');
            String numberString = creditString.substring(0, index);
            int credit = Integer.parseInt(numberString);

            int memberCredit = dataCreditDTO.getCreditByCurriculumType(subject.getCurriculumType());
            int universityCredit = dataCreditRepository.findFieldValueByIdAndFieldName(1L, subject.getCurriculumType());

            if (uniqueCourseCodes.contains(subject.getCourseCode())) {
                continue;
            } else if (memberCredit + credit <= universityCredit) {
                switch (subject.getCurriculumType()) {
                    case "전공기초" -> dataCreditDTO.setMajorBasic(dataCreditDTO.getMajorBasic() + credit);
                    case "전공선택" -> dataCreditDTO.setMajorElective(dataCreditDTO.getMajorElective() + credit);
                    case "전공필수" -> dataCreditDTO.setMajorCompulsory(dataCreditDTO.getMajorCompulsory() + credit);
                    case "교양필수" ->
                            dataCreditDTO.setLiberalArtsCompulsory(dataCreditDTO.getLiberalArtsCompulsory() + credit);
                }

            } else {
                switch (subject.getCurriculumType()) {
                    case "전공기초" -> dataCreditDTO.setMajorBasic(universityCredit);
                    case "전공선택" -> dataCreditDTO.setMajorElective(universityCredit);
                    case "전공필수" -> dataCreditDTO.setMajorCompulsory(universityCredit);
                    case "교양필수" -> dataCreditDTO.setLiberalArtsCompulsory(universityCredit);
                }

                rest += universityCredit - memberCredit;
            }

            uniqueCourseCodes.add(subject.getCourseCode());
            dataCreditDTO.setTotal(dataCreditDTO.getTotal() + credit);
        }

        dataCreditDTO.setGeneralElective(rest);
        dataCreditDTO.setTotal(dataCreditDTO.getTotal() + dataCreditDTO.getGeneralElective());

        return dataCreditDTO;
    }

    public void saveCredit(String grade, Subject subject) {
        if (grade == null || subject == null) {
            throw new NullPointerException("요청 변수가 부족합니다.");
        } else {
            Credit credit = new Credit();
            credit.setGrade(grade);
            credit.setSubject(subject);
            creditRepository.save(credit);
        }
    }

    public double getAllCredit(List<Subject> subjectList) {
        // Credit가 P, NP, F 인 과목은 제외합니다.
        subjectList.removeIf(subject -> subject.getCredit().equals("P") || subject.getCredit().equals("NP") ||
                subject.getCredit().equals("F"));

        // 과목별 학점과 학점 수를 저장할 Map
        Map<String, String> gradeMap = new HashMap<>(); // 학점 성적 맵
        Map<String, Integer> creditMap = new HashMap<>(); // 과목 학점 맵

        // 과목을 정렬합니다.
        subjectList.sort(Comparator
                .comparing((Subject subject) -> subject.getTimeTable().getGrade())
                .thenComparing((Subject subject) -> getSemesterOrder(subject.getTimeTable().getSemester()))
        );

        double sum = 0;
        int totalCredits = 0;

        // 각 과목에 대해 학점과 학점 수를 맵에 저장합니다.
        for (Subject subject : subjectList) {
            Credit credit = creditRepository.findBySubject(subject);
            gradeMap.put(subject.getCourseCode(), credit.getGrade());

            String creditString = subject.getCredit();
            int index = creditString.indexOf('(');
            String numberString = creditString.substring(0, index);
            int memberCredit = Integer.parseInt(numberString);

            creditMap.put(subject.getCourseCode(), memberCredit);
        }

        // 각 과목에 대해 학점을 계산하고 합산합니다.
        for (Map.Entry<String, String> entry : gradeMap.entrySet()) {
            String grade = entry.getValue();
            int memberCredit = creditMap.get(entry.getKey());
            double credit = calculateCredit(grade, memberCredit);
            sum += credit;
            totalCredits += memberCredit;
        }

        // 총 학점을 과목 수로 나누어 평균 학점을 반환합니다.
        return totalCredits != 0 ? sum / totalCredits : 0;
    }

    // 학기 순서를 정의하는 메서드
    private int getSemesterOrder(String semester) {
        return switch (semester) {
            case "first" -> 1;
            case "summer" -> 2;
            case "second" -> 3;
            case "winter" -> 4;
            default -> throw new IllegalArgumentException("올바르지 않은 학기 정보입니다: " + semester);
        };
    }

    private double calculateCredit(String grade, int n) {
        // 학점에 따른 학점을 계산합니다.
        double credit;
        switch (grade) {
            case "A+" -> credit = 4.5;
            case "A" -> credit = 4;
            case "B+" -> credit = 3.5;
            case "B" -> credit = 3;
            case "C+" -> credit = 2.5;
            case "C" -> credit = 2;
            case "D+" -> credit = 1.5;
            case "D" -> credit = 1;
            case null -> throw new NullPointerException("학점 등급이 정해지지 않았습니다.");
            default -> throw new IllegalArgumentException("올바르지 않은 학점 등급입니다: " + grade);
        }

        return credit * n;
    }

}
