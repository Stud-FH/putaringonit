package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController()
@RequestMapping("/profile")
public class ProfileController {

    private final AccountService accountService;
    private final ProfileService profileService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ProfileResource> getAll(
            @RequestParam("token") String token
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        return this.profileService.findAll().stream().map(ProfileResource::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProfileResource get(
            @RequestParam("token") String token,
            @PathVariable String id
    ) {
        this.accountService.loginWithToken(token).matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Profile profile = this.profileService.findById(id);
        return new ProfileResource(profile);
    }

    // TODO create endpoint

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ProfileResource update(
            @RequestParam("token") String token,
            @PathVariable String id,
            @RequestBody ProfileResource data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(id)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.update(id, data, updates);
        return new ProfileResource(profile);
    }


}
