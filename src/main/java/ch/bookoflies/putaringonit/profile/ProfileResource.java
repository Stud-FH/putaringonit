package ch.bookoflies.putaringonit.profile;

import ch.bookoflies.putaringonit.common.TextReferencable;
import ch.bookoflies.putaringonit.invitation.InvitationResource;
import ch.bookoflies.putaringonit.dishSelection.DishSelectionResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    private String phoneNumber;
    private Boolean blockPhoneNumber;
    private Collection<DishSelectionResource> dishSelections;
    private Collection<InvitationResource> invitations;

    public ProfileResource(Profile profile) {
        Map<String, TextReferencable> dictionary = new HashMap<>();
        profile.getDishSelections().forEach(ref -> dictionary.put(ref.getReferenceKey(), ref));

        profile.getTexts().forEach(text -> dictionary.get(text.getReferenceKey()).setText(text.getContent()));

        identifier = profile.getIdentifier();
        firstName = profile.getFirstName();
        familyName = profile.getFamilyName();
        nickname = profile.getNickname();
        email = profile.getEmail();
        blockEmail = profile.getBlockEmail();
        phoneNumber = profile.getPhoneNumber();
        blockPhoneNumber = profile.getBlockPhoneNumber();
        dishSelections = profile.getDishSelections().stream().map(DishSelectionResource::new).collect(Collectors.toList());
        invitations = profile.getInvitations().stream().map(InvitationResource::new).collect(Collectors.toList());
    }
}
