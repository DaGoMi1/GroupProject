package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataCreditDTO {
    private int majorBasic;
    private int majorElective;
    private int majorCompulsory;
    private int generalElective;
    private int liberalArtsCompulsory;
    private int liberalArtsElective;
    private int total;

    public DataCreditDTO(int a, int b, int c, int d, int e, int f, int g) {
        majorBasic = a;
        majorElective = b;
        majorCompulsory = c;
        generalElective = d;
        liberalArtsCompulsory = e;
        liberalArtsElective = f;
        total = g;
    }

    public int getCreditByCurriculumType(String curriculumType) {
        return switch (curriculumType) {
            case "전공기초" -> majorBasic;
            case "전공선택" -> majorElective;
            case "전공필수" -> majorCompulsory;
            case "교양필수" -> liberalArtsCompulsory;
            default -> -1; // 해당하는 학점이 없을 경우 0을 반환하거나 예외 처리할 수 있습니다.
        };
    }
}
