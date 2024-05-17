package DataView.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequest {

    private Long id;                // 파일 번호 (PK)
    private Long postId;            // 게시글 번호 (FK)
    private String originalName;    // 원본 파일명
    private String saveName;        // 저장 파일명
    private long size;              // 파일 크기

    @Builder
    public FileRequest(Long postId,String originalName, String saveName, long size) {
        this.postId=postId;
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
    }

}
