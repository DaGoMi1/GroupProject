package DataView.project.service;

import DataView.project.domain.DataCredit;
import DataView.project.domain.LiberalArtsCredit;
import DataView.project.domain.Member;
import DataView.project.domain.Subject;
import DataView.project.dto.*;
import DataView.project.repository.SDJpaDataCreditRepository;
import DataView.project.repository.SDJpaGeneralEducationCurriculumRepository;
import DataView.project.repository.SDJpaLiberalArtsCreditRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class CreditService {
    private final SDJpaDataCreditRepository dataCreditRepository;
    private final SDJpaLiberalArtsCreditRepository liberalArtsCreditRepository;
    private final SDJpaGeneralEducationCurriculumRepository generalEducationCurriculumRepository;

    public CreditService(SDJpaDataCreditRepository dataCreditRepository,
                         SDJpaLiberalArtsCreditRepository liberalArtsCreditRepository,
                         SDJpaGeneralEducationCurriculumRepository generalEducationCurriculumRepository) {
        this.dataCreditRepository = dataCreditRepository;
        this.liberalArtsCreditRepository = liberalArtsCreditRepository;
        this.generalEducationCurriculumRepository = generalEducationCurriculumRepository;
    }

    public DataCredit getDataCredit() {
        if (dataCreditRepository.findById(1L).isPresent()) {
            return dataCreditRepository.findById(1L).get();
        } else {
            return null;
        }
    }

    public LiberalArtsCredit getLiberalCredit() {
        if (liberalArtsCreditRepository.findById(1L).isPresent()) {
            return liberalArtsCreditRepository.findById(1L).get();
        } else {
            return null;
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
                liberalArtsCreditDTO.getTotal(), 0, liberalArtsCreditDTO.getTotal());

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
}
