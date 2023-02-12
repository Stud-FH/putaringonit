package ch.bookoflies.putaringonit.dishSelection;

import ch.bookoflies.putaringonit.dish.Dish;
import ch.bookoflies.putaringonit.dish.DishService;
import ch.bookoflies.putaringonit.meal.Meal;
import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.text.Text;
import ch.bookoflies.putaringonit.text.TextService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DishSelectionService {

    private final DishService dishService;
    private final DishSelectionRepository dishSelectionRepository;
    private final TextService textService;

    public DishSelection select(Profile profile, Meal meal, DishSelectionResource data) {
        Dish dish = dishService.findById(data.getDishId());
        if (!Objects.equals(dish.getMealId(), meal.getId())) throw ErrorResponse.Unprocessable("invalid reference").get();
        // TODO check if invited
        Optional<DishSelection> previous = dishSelectionRepository.findByProfileIdAndMealId(profile.getIdentifier(), meal.getId());
        previous.ifPresent(dishSelectionRepository::delete);
        DishSelection dishSelection = new DishSelection();
        dishSelection.setProfile(profile);
        dishSelection.setProfileId(profile.getIdentifier());
        dishSelection.setMealId(meal.getId());
        dishSelection.setDishId(dish.getId());
        dishSelection = dishSelectionRepository.save(dishSelection);
        Text text = textService.persist(data.getComment(), dishSelection);
        dishSelection.setText(text.getContent());
        return dishSelection;
    }

    public void delete(Profile profile, Meal meal) {
        this.dishSelectionRepository.deleteByProfileIdAndMealId(profile.getIdentifier(), meal.getId());
    }
}
