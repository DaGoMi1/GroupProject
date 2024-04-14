package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {
    private int grade; // 시간표 저장 학년
    private String semester; // 시간표 저장 학기
    private Long subjectId; // Subject ID
}
