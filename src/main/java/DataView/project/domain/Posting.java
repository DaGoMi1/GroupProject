package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "boardType")
    private String boardType;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Comment> comments;
}