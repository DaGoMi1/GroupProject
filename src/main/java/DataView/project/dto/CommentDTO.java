package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String comment;
    private String userId;
    private LocalDateTime createdDate;
}
