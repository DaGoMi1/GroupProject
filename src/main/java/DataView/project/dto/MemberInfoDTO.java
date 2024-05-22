package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

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
