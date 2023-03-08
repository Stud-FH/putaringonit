package ch.bookoflies.putaringonit.gift;

import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.wish.Wish;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.profile.ProfileService;
import ch.bookoflies.putaringonit.wish.WishService;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import lombok.AllArgsConstructor;
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
    public GiftResource create(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long wishId,
            @RequestBody GiftResource data
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Wish wish = wishService.findById(wishId);
        Gift gift = this.giftService.create(data, wish, profile);
        return new GiftResource(gift);
    }

    @PutMapping("/{wishId}/update")
    public GiftResource update(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long wishId,
            @RequestBody GiftResource data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Wish wish = wishService.findById(wishId);
        Gift gift = this.giftService.update(wish, profile, data, updates);
        return new GiftResource(gift);
    }

    @DeleteMapping("/{wishId}/delete")
    public GiftResource delete(
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
        Gift gift = this.giftService.delete(wish, profile);
        return new GiftResource(gift);
    }


}
