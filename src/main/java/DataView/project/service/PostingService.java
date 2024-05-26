package DataView.project.service;

import DataView.project.domain.Comment;
import DataView.project.domain.Member;
import DataView.project.domain.Posting;
import DataView.project.dto.CommentDTO;
import DataView.project.dto.PostingDTO;
import DataView.project.repository.SDJpaPostingRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
public class PostingService {
    private final SDJpaPostingRepository postingRepository;

    public PostingService(SDJpaPostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    public void postSave(Member member, Posting posting) {
        posting.setUserId(member.getUsername());
        posting.setAuthor(member.getName());
        posting.setCreated_at(LocalDateTime.now());

        postingRepository.save(posting);
    }

    public List<PostingDTO> findListByBoardType(String boardType) {
        List<Posting> postings = postingRepository.findAllByBoardTypeWithComments(boardType);
        return postings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PostingDTO convertToDTO(Posting posting) {
        PostingDTO dto = new PostingDTO();
        dto.setId(posting.getId());
        dto.setUserId(posting.getUserId());
        dto.setTitle(posting.getTitle());
        dto.setAuthor(posting.getAuthor());
        dto.setContent(posting.getContent());
        dto.setBoardType(posting.getBoardType());
        dto.setCreatedAt(posting.getCreated_at());
        dto.setComments(posting.getComments().stream().map(this::convertToDTO).collect(Collectors.toList()));
        return dto;
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        dto.setCreatedDate(comment.getCreatedDate());
        dto.setUserId(comment.getUserId());
        return dto;
    }

    public Posting getPostingById(Long id) {
        Optional<Posting> posting = postingRepository.findById(id);
        return posting.orElse(null);
    }

    public boolean checkMemberPosting(Member member, Long postingId) {
        return getPostingById(postingId).getUserId().equals(member.getUsername());
    }

    public void deletePosting(Long postingId) {
        postingRepository.deleteById(postingId);
    }

    public void updatePosting(Posting posting, Member member) throws Exception {
        // 게시글 아이디로 데이터베이스에서 해당 게시글 가져오기
        Optional<Posting> optionalPosting = postingRepository.findById(posting.getId());

        if (optionalPosting.isPresent()) {
            Posting existingPosting = optionalPosting.get();

            // 게시글을 작성한 사용자와 로그인한 사용자가 같은지 확인
            if (!existingPosting.getUserId().equals(member.getUsername())) {
                throw new Exception("게시글을 수정할 수 있는 권한이 없습니다.");
            }

            // 요청으로 받은 값으로 게시글 정보 업데이트
            existingPosting.setTitle(posting.getTitle());
            existingPosting.setContent(posting.getContent());

            // 수정 시간을 현재 시간으로 업데이트
            existingPosting.setCreated_at(LocalDateTime.now());

            // 수정된 게시글 저장
            postingRepository.save(existingPosting);
        } else {
            throw new Exception("존재하지 않는 게시글 아이디입니다.");
        }
    }

}