package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTO {
    private Long id;
    private String startDay;
    private String endDay;
    private String title;
    private String content;
    private String color;
}
