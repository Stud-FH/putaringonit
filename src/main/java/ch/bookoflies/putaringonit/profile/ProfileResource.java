package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.invitation.InvitationResource;
import ch.bookoflies.putaringonit.dishSelection.DishSelectionResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResource {

    private String identifier;
    private String firstName;
    private String familyName;
    private String nickname;
    private String email;
    private Boolean blockEmail;
    private Collection<DishSelectionResource> dishSelections;
    private Collection<InvitationResource> invitations;

    public ProfileResource(Profile profile) {
        identifier = profile.getIdentifier();
        firstName = profile.getFirstName();
        familyName = profile.getFamilyName();
        nickname = profile.getNickname();
        email = profile.getEmail();
        blockEmail = profile.getBlockEmail();
        dishSelections = profile.getDishSelections().stream().map(DishSelectionResource::new).collect(Collectors.toList());
        invitations = profile.getInvitations().stream().map(InvitationResource::new).collect(Collectors.toList());
    }
}
