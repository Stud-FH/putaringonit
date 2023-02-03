package ch.bookoflies.putaringonit.invitation;

public class InvitationResource {
    public final long programId;
    public final Boolean accepted;

    public InvitationResource(Invitation invitation) {
        programId = invitation.getProgramId();
        accepted = invitation.getAccepted();
    }
}
