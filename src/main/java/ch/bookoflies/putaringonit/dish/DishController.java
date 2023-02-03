package ch.bookoflies.putaringonit.dish;

import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.meal.Meal;
import ch.bookoflies.putaringonit.meal.MealService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/dish")
public class DishController {

    private final AccountService accountService;
    private final MealService mealService;
    private final DishService dishService;

    @PostMapping("/{mealId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DishResource create(
            @RequestParam("token") String token,
            @PathVariable long mealId,
            @RequestBody DishResource data
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Meal meal = mealService.findById(mealId);
        Dish dish = this.dishService.create(meal, data);
        return new DishResource(dish);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DishResource update(
            @RequestParam("token") String token,
            @PathVariable Long id,
            @RequestBody DishResource data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Dish dish = this.dishService.update(id, data, updates);
        return new DishResource(dish);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void delete(
            @RequestParam("token") String token,
            @PathVariable Long id
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        this.dishService.delete(id);
    }
}
