package ch.bookoflies.putaringonit.wish;

import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/wish")
public class WishController {

    private AccountService accountService;
    private final WishService wishService;

    @PostMapping("/create")
    public WishResource create(
            @RequestParam("token") String token,
            @RequestBody WishResource data
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Wish wish = this.wishService.create(account.getContextName(), data);
        return new WishResource(wish);
    }

    @PutMapping("/{id}/update")
    public WishResource update(
            @RequestParam("token") String token,
            @PathVariable Long id,
            @RequestBody WishResource data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Wish wish = this.wishService.update(id, data, updates);
        return new WishResource(wish);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(
            @RequestParam("token") String token,
            @PathVariable Long id
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        this.wishService.delete(id);
    }


}
