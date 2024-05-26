package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostingDTO {
    private Long id;
    private String userId;
    private String title;
    private String author;
    private String content;
    private String boardType;
    private LocalDateTime createdAt;
    private List<CommentDTO> comments;
}
