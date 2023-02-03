package ch.bookoflies.putaringonit.gift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    Optional<Gift> findByWishIdAndDonorId(long wishId, String donorId);
    boolean existsByWishIdAndDonorId(long wishId, String donorId);
}
