package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDTO {
    private String name;
    private String studentId;

    public MemberInfoDTO(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }
}
