package ch.bookoflies.putaringonit.dishSelection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishSelectionRepository extends JpaRepository<DishSelection, Long> {
    Optional<DishSelection> findByProfileIdAndMealId(String profileid, Long mealId);
    boolean existsByProfileIdAndMealId(String profileid, Long mealId);
    void deleteByProfileIdAndMealId(String profileid, Long mealId);
}
