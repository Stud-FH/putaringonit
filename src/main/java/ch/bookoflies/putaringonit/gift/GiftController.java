package ch.bookoflies.putaringonit.gift;

import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.gift.Gift;
import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.wish.Wish;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.gift.GiftService;
import ch.bookoflies.putaringonit.profile.ProfileService;
import ch.bookoflies.putaringonit.wish.WishService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/gift")
public class GiftController {

    private final AccountService accountService;
    private final ProfileService profileService;
    private final WishService wishService;
    private final GiftService giftService;

    @PostMapping("/{wishId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Gift create(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long wishId,
            @RequestBody Gift data
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Wish wish = wishService.findById(wishId);
        return this.giftService.create(data, wish, profile);
    }

    @PutMapping("/{wishId}/update")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Gift update(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long wishId,
            @RequestBody Gift data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Wish wish = wishService.findById(wishId);
        return this.giftService.update(wish, profile, data, updates);
    }

    @DeleteMapping("/{wishId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void delete(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long wishId
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Wish wish = wishService.findById(wishId);
        this.giftService.delete(wish, profile);
    }


}
