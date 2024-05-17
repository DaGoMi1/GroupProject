package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDTO {
    private Long id;
    private List<SubjectDTO> subjects;
}