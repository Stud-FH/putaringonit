package ch.bookoflies.putaringonit.account;

import ch.bookoflies.putaringonit.context.ContextService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController()
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final ContextService contextService;

    @GetMapping("")
    public AccountResource get(
            @RequestParam("token") String token
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.setContext(contextService.findByName(account.getContextName()));
        return new AccountResource(account);
    }

    @PostMapping("/code")
    public AccountResource loginWithCode(
            @RequestBody AccountLoginCode data
    ) {
        Account account = this.accountService.loginWithCode(data);
        account.setContext(contextService.findByName(account.getContextName()));
        return new AccountResource(account);
    }

    @PostMapping("/password")
    public AccountResource loginWithPassword(
            @RequestBody AccountLoginPassword data
    ) {
        Account account = this.accountService.loginWithPassword(data);
        account.setContext(contextService.findByName(account.getContextName()));
        return new AccountResource(account);
    }

    @GetMapping("/username/{username}")
    public Boolean existsUsername(
            @PathVariable String username
    ) {
        return this.accountService.existsUsername(username);
    }


}
