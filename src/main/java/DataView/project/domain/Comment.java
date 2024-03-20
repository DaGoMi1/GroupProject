package DataView.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "posting_id")
    private Posting posting;
    private String userId;
    private LocalDateTime createdDate;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Setter
    @Getter
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> childComments;

    public void addChildComment(Comment childComment) {
        if (childComments == null) {
            childComments = new ArrayList<>();
        }
        childComments.add(childComment);
        childComment.setParentComment(this);
    }
}