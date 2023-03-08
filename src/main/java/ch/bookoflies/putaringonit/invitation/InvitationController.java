package ch.bookoflies.putaringonit.invitation;

import ch.bookoflies.putaringonit.account.Account;
import ch.bookoflies.putaringonit.account.AccountService;
import ch.bookoflies.putaringonit.account.Clearance;
import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.profile.ProfileService;
import ch.bookoflies.putaringonit.program.Program;
import ch.bookoflies.putaringonit.program.ProgramService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/{programId}/{profileId}/create")
    public InvitationResource create(
            @RequestParam("token") String token,
            @PathVariable String profileId,
            @PathVariable Long programId
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());

        Profile profile = profileService.findById(profileId);
        Program program = programService.findById(programId);
        Invitation invitation = this.invitationService.create(profile, program);
        return new InvitationResource(invitation);
    }

    @PutMapping("/{programId}/{profileId}/update")
    public InvitationResource update(
            @RequestParam("token") String token,
            @PathVariable String profileId,
            @PathVariable Long programId,
            @RequestBody InvitationResource data,
            @RequestParam() List<String> updates
    ) {
        Account account = this.accountService.loginWithToken(token);
        if (!account.getProfileIds().contains(profileId)) {
            account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        }
        Profile profile = profileService.findById(profileId);
        Program program = programService.findById(programId);
        Invitation invitation = this.invitationService.update(profile, program, data, updates);
        return new InvitationResource(invitation);
    }

    @DeleteMapping("/{programId}/{profileId}/delete")
    public void delete(
            @RequestParam("token") String token,
            @PathVariable String profileId,
            @PathVariable Long programId
    ) {
        Account account = this.accountService.loginWithToken(token);
        account.matchOrThrow(Clearance.Manager, ErrorResponse.Forbidden());
        Profile profile = profileService.findById(profileId);
        Program program = programService.findById(programId);
        this.invitationService.delete(profile, program);
    }


}
