package ch.bookoflies.putaringonit.invitation;

import ch.bookoflies.putaringonit.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByProfileAndProgramId(Profile profile, long profileId);
    boolean existsByProfileAndProgramId(Profile profile, long profileId);

}
