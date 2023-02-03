package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.invitation.InvitationResource;
import ch.bookoflies.putaringonit.dishSelection.DishSelectionResource;

import java.util.Collection;
import java.util.stream.Collectors;

public class ProfileResource {

    public final String identifier;
    public final String firstName;
    public final String familyName;
    public final String nickname;
    public final String email;
    public final Boolean blockEmail;
    public final Collection<DishSelectionResource> dishSelections;
    public final Collection<InvitationResource> invitations;

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
