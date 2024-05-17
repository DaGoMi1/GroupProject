package DataView.project.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 파일 번호 (PK)

    private Long postId;            // 게시글 번호 (FK)
    private String originalName;    // 원본 파일명
    private String saveName;        // 저장 파일명
    private long size;              // 파일 크기

    @ManyToOne
    @JoinColumn(name = "posting_id")
    private Posting posting;

}
