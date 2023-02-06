package ch.bookoflies.putaringonit.invitation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvitationResource {
    private long programId;
    private String profileId;
    private Boolean accepted;

    public InvitationResource(Invitation invitation) {
        programId = invitation.getProgramId();
        profileId = invitation.getProfileId();
        accepted = invitation.getAccepted();
    }
}
