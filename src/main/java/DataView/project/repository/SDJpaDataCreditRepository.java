package DataView.project.repository;

import DataView.project.domain.DataCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SDJpaDataCreditRepository extends JpaRepository<DataCredit, Long> {
    @Query("SELECT CASE WHEN :fieldName = '전공기초' THEN d.majorBasic " +
            "               WHEN :fieldName = '전공선택' THEN d.majorElective " +
            "               WHEN :fieldName = '전공필수' THEN d.majorCompulsory " +
            "               WHEN :fieldName = '교양필수' THEN d.liberalArtsCompulsory " +
            "          ELSE 0 END " +
            " FROM DataCredit d WHERE d.id = :id")
    int findFieldValueByIdAndFieldName(Long id, String fieldName);
}
