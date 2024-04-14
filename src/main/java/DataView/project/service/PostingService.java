package DataView.project.service;

import DataView.project.domain.Member;
import DataView.project.domain.Posting;
import DataView.project.repository.SDJpaPostingRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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

    public List<Posting> findListByBoardType(String boardType) {
        return postingRepository.findAllByBoardType(boardType);
    }

    public Posting getPostingById(Long id) {
        Optional<Posting> posting = postingRepository.findById(id);
        return posting.orElse(null);
    }

    public boolean deletePosting(Member member, Long postingId) {
        if (getPostingById(postingId).getUserId().equals(member.getUsername())) {
            postingRepository.deleteById(postingId);
            return true;
        } else {
            return false;
        }
    }
}