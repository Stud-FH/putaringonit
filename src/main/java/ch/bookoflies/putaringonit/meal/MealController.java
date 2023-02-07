package ch.bookoflies.putaringonit.meal;

import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.program.Program;
import ch.bookoflies.putaringonit.program.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/meal")
public class MealController {

    private final AccountService accountService;
    private final ProgramService programService;
    private final MealService mealService;

    @PostMapping("/{programId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public MealResource create(
            @RequestParam("token") String token,
            @PathVariable Long programId,
            @RequestBody MealResource data
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Program program = programService.findById(programId);
        Meal meal = this.mealService.create(program, data);
        return new MealResource(meal);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MealResource update(
            @RequestParam("token") String token,
            @PathVariable Long id,
            @RequestBody MealResource data,
            @RequestParam() List<String> updates
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Meal meal = this.mealService.update(id, data, updates);
        return new MealResource(meal);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void delete(
            @RequestParam("token") String token,
            @PathVariable Long id
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        this.mealService.delete(id);
    }
}
