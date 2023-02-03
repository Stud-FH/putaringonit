package ch.bookoflies.putaringonit.account;

import ch.bookoflies.putaringonit.context.ContextService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController()
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final ContextService contextService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountResource get(
            @RequestParam("token") String token
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.setContext(contextService.findAndDressUp(account.getContextName()));
        return new AccountResource(account);
    }

    @PostMapping("/code")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountResource loginWithCode(
            @RequestBody AccountLoginCode data
    ) {
        Account account = this.accountService.loginWithCode(data);
        account.setContext(contextService.findAndDressUp(account.getContextName()));
        return new AccountResource(account);
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountResource loginWithPassword(
            @RequestBody AccountLoginPassword data
    ) {
        Account account = this.accountService.loginWithPassword(data);
        account.setContext(contextService.findAndDressUp(account.getContextName()));
        return new AccountResource(account);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Boolean existsUsername(
            @PathVariable String username
    ) {
        return this.accountService.existsUsername(username);
    }


}
