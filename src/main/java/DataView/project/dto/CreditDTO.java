package DataView.project.dto;

import DataView.project.domain.DataCredit;
import DataView.project.domain.LiberalArtsCredit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditDTO {
    private DataCredit dataCredit;
    private LiberalArtsCredit liberalArtsCredit;
}
