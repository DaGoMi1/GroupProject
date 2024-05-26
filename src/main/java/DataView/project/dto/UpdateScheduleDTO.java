package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateScheduleDTO {
    private Long id;
    private String startDay;
    private String endDay;
    private String changeTitle;
    private String color;
}
