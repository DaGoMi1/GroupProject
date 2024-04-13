package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiberalArtsCreditDTO {
    private int globalCommunication;
    private int maritimeCulture;
    private int maritimeSportsArts;
    private int historyCulture;
    private int societyEconomy;
    private int personalDevelopment;
    private int total;
    private int rest;

    public LiberalArtsCreditDTO(int a, int b, int c, int d, int e, int f, int g, int h) {
        globalCommunication = a;
        maritimeCulture = b;
        maritimeSportsArts = c;
        historyCulture = d;
        societyEconomy = e;
        personalDevelopment = f;
        total = g;
        rest = h;
    }

    public int getCreditBySubArea(String subArea) {
        return switch (subArea) {
            case "글로벌의사소통" -> globalCommunication;
            case "해양문화" -> maritimeCulture;
            case "해양스포츠와예술" -> maritimeSportsArts;
            case "역사화문화" -> historyCulture;
            case "사회와경제" -> societyEconomy;
            case "인성" -> personalDevelopment;
            default -> -1; // 해당하는 학점이 없을 경우 0을 반환하거나 예외 처리할 수 있습니다.
        };
    }
}
