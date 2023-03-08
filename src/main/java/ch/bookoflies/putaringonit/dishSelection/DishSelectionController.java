package ch.bookoflies.putaringonit.dishSelection;

import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.meal.Meal;
import ch.bookoflies.putaringonit.meal.MealService;
import ch.bookoflies.putaringonit.profile.Profile;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController()
@RequestMapping("/dish-selection")
public class DishSelectionController {

    private final AccountService accountService;
    private final MealService mealService;
    private final DishSelectionService dishSelectionService;


    @PostMapping("/{mealId}/select")
    public DishSelectionResource select(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @RequestBody DishSelectionResource data,
            @PathVariable long mealId
    ) {
        Profile profile = accountService.profileLogin(token, profileId);
        Meal meal = mealService.findById(mealId);
        DishSelection dishSelection = this.dishSelectionService.select(profile, meal, data);
        return new DishSelectionResource(dishSelection);
    }

    @DeleteMapping("/{mealId}/delete")
    public void delete(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable long mealId
    ) {
        Profile profile = accountService.profileLogin(token, profileId);
        Meal meal = mealService.findById(mealId);
        this.dishSelectionService.delete(profile, meal);
    }


}
