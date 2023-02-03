package ch.bookoflies.putaringonit.invitation;

import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.invitation.Invitation;
import ch.bookoflies.putaringonit.invitation.InvitationService;
import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.profile.ProfileService;
import ch.bookoflies.putaringonit.program.Program;
import ch.bookoflies.putaringonit.program.ProgramService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/invitation")
public class InvitationController {

    private final AccountService accountService;
    private final ProfileService profileService;
    private final ProgramService programService;
    private final InvitationService invitationService;

    @PostMapping("/{programId}/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Invitation create(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long programId
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());

        Profile profile = profileService.findById(profileId);
        Program program = programService.findById(programId);
        return this.invitationService.create(profile, program);
    }

    @PutMapping("/{programId}/update")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Invitation update(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long programId,
            @RequestBody Invitation data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Program program = programService.findById(programId);
        return this.invitationService.update(profile, program, data, updates);
    }

    @DeleteMapping("/{programId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void delete(
            @RequestParam("token") String token,
            @RequestParam("profile") String profileId,
            @PathVariable Long programId
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Program program = programService.findById(programId);
        this.invitationService.delete(profile, program);
    }


}
