package ticketmaster.demo.posting.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ticketmaster.demo.posting.dto.Posting;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    Optional<Posting> findById(Long id);
}
