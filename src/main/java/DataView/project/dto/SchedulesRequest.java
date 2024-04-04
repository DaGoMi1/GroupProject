package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchedulesRequest {
    private String startDay;
    private String endDay;
    private String title;
    private String content;
    private String color;
    private Long userId;
}
