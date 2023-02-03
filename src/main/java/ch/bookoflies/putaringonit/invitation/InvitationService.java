package ch.bookoflies.putaringonit.invitation;

import ch.bookoflies.putaringonit.common.ErrorResponse;
import ch.bookoflies.putaringonit.profile.Profile;
import ch.bookoflies.putaringonit.program.Program;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;

    public Invitation findByProfileAndProgram(Profile profile, Program program) {
        return invitationRepository.findByProfileAndProgramId(profile, program.getId()).orElseThrow(ErrorResponse.NotFound("invitation not found"));
    }

    public Invitation create(Profile profile, Program program) {
        if (invitationRepository.existsByProfileAndProgramId(profile, program.getId()))
            throw ErrorResponse.Conflict("invitation already exists").get();
        Invitation invitation = new Invitation();
        invitation.setProfile(profile);
        invitation.setProfileId(profile.getIdentifier());
        invitation.setProgramId(program.getId());
        return invitationRepository.saveAndFlush(invitation);
    }

    public Invitation update(Profile profile, Program program, Invitation data, List<String> updates) {
        Invitation invitation = findByProfileAndProgram(profile, program);
        List<BiConsumer<Invitation, Invitation>> handlers = updates.stream().map(this::createUpdateHandler).collect(Collectors.toList());

        handlers.forEach(handler -> handler.accept(invitation, data));
        return invitationRepository.saveAndFlush(invitation);
    }

    public void delete(Profile profile, Program program) {
        Invitation invitation = findByProfileAndProgram(profile, program);
        this.invitationRepository.delete(invitation);
    }

    private BiConsumer<Invitation, Invitation> createUpdateHandler(String attrib) {
        switch(attrib) {
            case "accepted": return (invitation, data) -> invitation.setAccepted(data.getAccepted());
            default: throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("unknown attribute %s", attrib));
        }
    }
}
