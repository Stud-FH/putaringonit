package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/profile")
public class ProfileController {

    private final AccountService accountService;
    private final ProfileService profileService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Profile> getAll(
            @RequestParam("token") String token
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        return this.profileService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Profile get(
            @RequestParam("token") String token,
            @PathVariable String id
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        return this.profileService.findById(id);
    }

    // TODO create endpoint

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Profile update(
            @RequestParam("token") String token,
            @PathVariable String id,
            @RequestBody Profile data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(id)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        return profileService.update(id, data, updates);
    }


}
