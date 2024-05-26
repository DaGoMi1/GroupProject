package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private String author;

    @ManyToOne
    @JoinColumn(name = "posting_id")
    private Posting posting;

    private String userId;

    private LocalDateTime createdDate;
}