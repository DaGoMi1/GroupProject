package DataView.project.repository;

import DataView.project.domain.LiberalArtsCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SDJpaLiberalArtsCreditRepository extends JpaRepository<LiberalArtsCredit, Long> {
    @Query("SELECT CASE WHEN :fieldName = '글로벌의사소통' THEN d.globalCommunication " +
            "               WHEN :fieldName = '해양문화' THEN d.maritimeCulture " +
            "               WHEN :fieldName = '해양스포츠와예술' THEN d.maritimeSportsArts " +
            "               WHEN :fieldName = '역사와문화' THEN d.historyCulture " +
            "               WHEN :fieldName = '사회와경제' THEN d.societyEconomy " +
            "               WHEN :fieldName = '인성' THEN d.personalDevelopment " +
            "          ELSE 0 END " +
            " FROM LiberalArtsCredit d WHERE d.id = :id")
    int findFieldValueByIdAndFieldName(Long id, String fieldName);
}
