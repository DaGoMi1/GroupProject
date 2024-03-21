package DataView.project.service;

import DataView.project.domain.Posting;
import DataView.project.dto.CustomUserDetails;
import DataView.project.repository.SDJpaPostingRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;


@Transactional
public class PostingService {
    private final SDJpaPostingRepository postingRepository;

    public PostingService(SDJpaPostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    public void postSave(Posting posting) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        posting.setUserId(userDetails.getUsername());
        posting.setCreated_at(LocalDateTime.now());

        postingRepository.save(posting);
    }

    public List<Posting> findListByBoardType(String boardType){
        return postingRepository.findAllByBoardType(boardType);
    }
}